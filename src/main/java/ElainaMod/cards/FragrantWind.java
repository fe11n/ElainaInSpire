package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.relics.WanderingWitch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class FragrantWind extends AbstractSeasonCard {
    public static final String ID = "Elaina:FragrantWind";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/FragrantWind.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public static final Logger logger = LogManager.getLogger(WanderingWitch.class);

    public FragrantWind() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.damage = this.baseDamage = 5;
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(ElainaC.Enums.SEASONAL);
        this.BestSeasonNum = 2;
        this.isMultiDamage = true;
        this.damageTypeForTurn = DamageType.HP_LOSS;
        this.exhaust = true;
        this.ExtendDamage[2]=5;
        this.ExtendMagicNum[0]=this.ExtendMagicNum[1]=this.ExtendMagicNum[2]=this.ExtendMagicNum[3]=2;
        this.ExtendExhaust[0]=this.ExtendExhaust[1]=this.ExtendExhaust[2]=this.ExtendExhaust[3]=true;
//        setPreviewCard(this);
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeDamage(1); // 将该卡牌的伤害提高3点。
            this.upgradeMagicNumber(1);
            this.ExtendMagicNum[0]=this.ExtendMagicNum[1]=this.ExtendMagicNum[2]=this.ExtendMagicNum[3]=3;
            this.ExtendDamage[2]=6;
        }
    }
    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    public void BasicEffect(ElainaC p, AbstractMonster m){
        logger.info("Season Num: "+p.getSeason());
        AbstractMonster mo;
        Iterator it = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while(it.hasNext()) {
            mo = (AbstractMonster)it.next();
            this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, magicNumber, false), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
        switch (this.getSeasonNum()){
            case 0:
            case 3:
                break;
            case 2:
                it = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
                while(it.hasNext()) {
                    mo = (AbstractMonster)it.next();
                    this.addToBot(new ApplyPowerAction(mo,p,new PoisonPower(mo,p,this.damage)));
                }
            case 1:
                it = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
                while(it.hasNext()) {
                    mo = (AbstractMonster)it.next();
                    this.addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, magicNumber, false), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                }
                break;
        }
    }//基础效果，可以被使用和瞬发
}
