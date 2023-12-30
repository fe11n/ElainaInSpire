package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class ThunderMagic extends AbstractElainaCard {
    public static final String ID = "Elaina:ThunderMagic";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/ThunderMagic.png";
    private static final int COST = 4;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;


    public ThunderMagic() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID,CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.tags.add(ElainaC.Enums.MAGIC);
        this.damage = this.baseDamage = 25;
        this.magicNumber = this.baseMagicNumber = 10;
        this.exhaust = true;
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null) {
            this.configureCostsOnNewCard();
        }
    }

    public void InstantUse(){
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        this.calculateCardDamage(m);
        BasicEffect((ElainaC) AbstractDungeon.player,m);
        logger.info("Modifier include toInstantCardMod: "+ CardModifierManager.hasModifier(this,"toInstantCardMod"));
    }//瞬发

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeDamage(5);
            this.upgradeMagicNumber(5);
        }
    }
    public void configureCostsOnNewCard() {
        Iterator var2 = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();
        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            if(c instanceof AbstractElainaCard && c.hasTag(ElainaC.Enums.MAGIC)){
                this.setCostForTurn(this.costForTurn-2);
            }
        }
    }
    public void triggerOnCardPlayed(AbstractCard c) {
        if(c instanceof AbstractElainaCard && c.hasTag(ElainaC.Enums.MAGIC)){
            this.setCostForTurn(this.costForTurn-2);
        }
    }

    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    public void BasicEffect(ElainaC p, AbstractMonster m){
        Iterator it = AbstractDungeon.getMonsters().monsters.iterator();
        AbstractMonster mo = null;
        while(it.hasNext()){
            AbstractMonster tmo = (AbstractMonster) it.next();
            if (tmo.currentHealth <= 0){
                continue;
            }
            if(mo == null || mo.currentHealth > tmo.currentHealth){
                mo = tmo;
            }
        }
        if(mo != null  && mo.currentHealth > 0 ){
            addToBot(new DamageAction(mo, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),AbstractGameAction.AttackEffect.SMASH));
            this.addToBot( new AbstractGameAction(){
                public AbstractMonster mo;
                public AbstractGameAction accept(AbstractMonster m){
                    mo = m;
                    return this;//向内部匿名类传参
                }
                @Override
                public void update() {
                    if((mo.isDying || mo.currentHealth <= 0) && !mo.halfDead){
                        logger.info("Killed by Thunder");
                        baseDamage += magicNumber;
                        damage += magicNumber;
                        InstantUse();
                    }
                    this.isDone = true;
                }
            }.accept(mo) );
        }
    }
}
