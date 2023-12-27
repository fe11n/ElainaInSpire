package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Iterator;

public class CounterMagic extends AbstractElainaCard {
    public static final String ID = "Elaina:CounterMagic";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/CounterMagic.png";
    private static final int COST = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private int iniDamage;

    public CounterMagic() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID,CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.iniDamage = this.damage = this.baseDamage = 4;
        this.magicNumber = this.baseMagicNumber = 4;
        this.tags.add(ElainaC.Enums.MAGIC);
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeDamage(1);
            this.upgradeMagicNumber(2);
            this.iniDamage = this.baseDamage;
        }
    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        AbstractPlayer p = AbstractDungeon.player;
        if(mo.hasPower("Strength")){
            int strAmt = mo.getPower("Strength").amount;
            if(strength == null){
                p.powers.add(new StrengthPower(p,strAmt));
            }
            else {
                strength.amount += strAmt;//这个函数在指向敌人时持续更新，如果直接让baseDamage自增，则会让它持续增加
            }
        }
        super.calculateCardDamage(mo);
        if(mo.hasPower("Strength")){
            strength = AbstractDungeon.player.getPower("Strength");
            int strAmt = mo.getPower("Strength").amount;
            strength.amount -= strAmt;
            if(strength.amount == 0){
                p.powers.remove(p.getPower("Strength"));
            }
        }
    }
    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    public void BasicEffect(ElainaC p, AbstractMonster m){
        for(int i = 0;i<3;i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL) ,AbstractGameAction.AttackEffect.SMASH));
        }
        this.addToBot(new AbstractGameAction() {
            boolean triggered = false;
            @Override
            public void update() {
                logger.info(m.name);
                if((m.isDying || m.currentHealth <= 0) && !m.halfDead && !m.hasPower("Minion")){
                    logger.info("Killed by CounterMagic");
                    triggered = true;
                    Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
                    AbstractMonster mo;
                    while(var3.hasNext()) {
                        mo = (AbstractMonster)var3.next();
                        this.addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -magicNumber), -magicNumber, true, AttackEffect.NONE));
                        this.addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, magicNumber), magicNumber, true, AttackEffect.NONE));
                    }
                }
                this.isDone = true;
            }
        });

    }//基础效果，可以被使用和瞬发
}
