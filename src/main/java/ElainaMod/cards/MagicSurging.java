package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

import java.util.ArrayList;
import java.util.Iterator;

public class MagicSurging extends AbstractElainaCard {
    public static final String ID = "Elaina:MagicSurging";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/MagicSurging.png";
    private static final int COST = 3;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public MagicSurging(){
        this(0);
    }
    public MagicSurging(int upgrades) {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(ElainaC.Enums.UNNOTABLE);
        this.timesUpgraded = upgrades;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        this.upgradeMagicNumber(2);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = CARD_STRINGS.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    @Override
    public void BasicEffect(ElainaC p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractCard> g = p.DiaryGroup.group;
                for(int i = g.size()-magicNumber>0?g.size()-magicNumber:0;i<g.size();i++){
                    AbstractElainaCard c = (AbstractElainaCard) g.get(i);
                    logger.info("Magic Surging ("+i+") : "+c.name);
                    ElainaC.InstantUse(c);
                }
                this.isDone =true;
            }
        });
    }

    @Override
    public AbstractCard makeCopy() {
        return new MagicSurging(this.timesUpgraded);
    }
}
