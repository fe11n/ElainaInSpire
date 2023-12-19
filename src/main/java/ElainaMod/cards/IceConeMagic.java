package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.relics.WanderingWitch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IceConeMagic extends AbstractSeasonCard {
    public static final String ID = "Elaina:IceConeMagic";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/IceConeMagic.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    public static final Logger logger = LogManager.getLogger(WanderingWitch.class);

    public IceConeMagic() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 18;
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(ElainaC.Enums.MAGIC);
        this.tags.add(ElainaC.Enums.SEASONAL);
        this.BestSeasonNum = 0;
        this.ExtendDamage[0]=this.ExtendDamage[1]=this.ExtendDamage[2]=this.ExtendDamage[3]=18;
        this.ExtendExhaust[2]=true;
        this.ExtendExhaust[0]=this.ExtendExhaust[1]=this.ExtendExhaust[3]=false;
        this.ExtendMagicNum[0] = 1;
        setPreviewCard(this);
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeDamage(2); // 将该卡牌的伤害提高3点。
            this.upgradeMagicNumber(1);
            this.ExtendDamage[0]=this.ExtendDamage[1]=this.ExtendDamage[2]=this.ExtendDamage[3]=20;
            this.ExtendMagicNum[0] = 2;
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
        logger.info("Base Damage: "+this.baseDamage);
        applyPowers();
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageType.NORMAL)));
        switch (this.getSeasonNum()){
            case 0:
                this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            case 1:
            case 3:
                break;
            case 2:
                this.exhaust=true;
                break;
        }
    }//基础效果，可以被使用和瞬发
}
