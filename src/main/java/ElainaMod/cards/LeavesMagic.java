package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cardmods.SpellBoostMod;
import ElainaMod.relics.WanderingWitch;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LeavesMagic extends AbstractElainaCard {
    public static final String ID = "Elaina:LeavesMagic";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/LeavesMagic.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final Logger logger = LogManager.getLogger(WanderingWitch.class);

    public LeavesMagic() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.damage = this.baseDamage = 5;
        this.tags.add(ElainaC.Enums.MAGIC);
        this.tags.add(ElainaC.Enums.SEASONAL);
        this.isMultiDamage = true;
        this.ExtendDamage[0]=this.ExtendDamage[1]=this.ExtendDamage[2]=this.ExtendDamage[3]=5;
        this.ExtendMagicNum[0]=this.ExtendMagicNum[3]=3;
        this.ExtendMagicNum[1]=this.ExtendMagicNum[2]=2;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeDamage(1); // 将该卡牌的伤害提高1点。
            this.ExtendDamage[0]=this.ExtendDamage[1]=this.ExtendDamage[2]=this.ExtendDamage[3]=6;
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

        switch (this.getSeasonNum()){
            case 0:
                for(int i = 0; i < 3; ++i) {
                    this.addToBot(new AttackDamageRandomEnemyAction(this));
                }
                break;
            case 1:
            case 2:
                for(int i = 0; i < 2; ++i) {
                    this.addToBot(new AttackDamageRandomEnemyAction(this));
                }
                break;
            case 3:
                for(int i = 0; i < 3; ++i) {
                    this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
                }
                break;
        }
    }//基础效果，可以被使用和瞬发
}
