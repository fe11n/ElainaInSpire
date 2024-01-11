package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.lang.reflect.Field;
import java.util.Iterator;

public class Reversal extends AbstractElainaCard {
    public static final String ID = "Elaina:Reversal";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Reversal.png";
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Reversal() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID,CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET,CardColor.COLORLESS);
        this.exhaust = true;
        this.selfRetain = true;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeBaseCost(1);
        }
    }
    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    public void BasicEffect(ElainaC p, AbstractMonster m){
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        AbstractMonster mo;
        while(var3.hasNext()) {
            mo = (AbstractMonster)var3.next();
//            logger.info("Mo IntentDmg: "+mo.getIntentDmg());
//            logger.info("half current heath: "+p.currentHealth/2);
            if(reversalGetMonsterDamage(mo)>=p.currentHealth/magicNumber){
                addToBot(new StunMonsterAction(mo,p));
            }
        }
    }//基础效果，可以被使用和瞬发
    boolean ReversalGlowCheck(){
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        AbstractMonster mo;
        AbstractPlayer p = AbstractDungeon.player;
        while(var3.hasNext()) {
            mo = (AbstractMonster)var3.next();
//            logger.info("Mo IntentDmg: "+mo.getIntentDmg());
//            logger.info("half current heath: "+p.currentHealth/2);
            if(reversalGetMonsterDamage(mo)>=p.currentHealth/magicNumber){
                return true;
            }
        }
        return false;
    }

    private int reversalGetMonsterDamage(AbstractMonster m) {
        int tmp = 0;
        try {
            // Get the Class object for AbstractMonster
            Class<?> monsterClass = AbstractMonster.class;
            // Get the Field objects for the private fields
            Field isMultiDmgField = monsterClass.getDeclaredField("isMultiDmg");
            Field intentMultiAmtField = monsterClass.getDeclaredField("intentMultiAmt");
            Field intentDmgField = monsterClass.getDeclaredField("intentDmg");
            // Allow access to private fields
            isMultiDmgField.setAccessible(true);
            intentMultiAmtField.setAccessible(true);
            intentDmgField.setAccessible(true);
            // Get the values of the private fields
            boolean isMultiDmgValue = (boolean) isMultiDmgField.get(m);
            int intentMultiAmtValue = (int) intentMultiAmtField.get(m);
            int intentDmgValue = (int) intentDmgField.get(m);
            // Perform the desired logic with the values
            // logger.info(isMultiDmgValue + " " + intentDmgValue + " " + intentMultiAmtValue);
            if (isMultiDmgValue) {
                // logger.info("多段伤害 " +intentDmgValue+" * "+intentMultiAmtValue);
                tmp = intentDmgValue * intentMultiAmtValue;
            } else {
                tmp = intentDmgValue;
            }
            // Reset the accessibility of the fields
            isMultiDmgField.setAccessible(false);
            intentMultiAmtField.setAccessible(false);
            intentDmgField.setAccessible(false);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.warn("获取多段伤害失败，返回单段伤害");
            return m.getIntentDmg();
        }
        return tmp;
    }

    public void triggerOnGlowCheck() {
        if(ReversalGlowCheck()){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
}
