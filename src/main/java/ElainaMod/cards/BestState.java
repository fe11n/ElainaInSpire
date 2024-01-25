package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.powers.BestStatePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BestState extends AbstractElainaCard {
    public static final String ID = "Elaina:BestState";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/BestState.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public BestState() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.block = this.baseBlock = 7;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }
    }
    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    public void BasicEffect(ElainaC p, AbstractMonster m){
        this.addToBot(new GainBlockAction(p,block));
        this.addToBot(new ApplyPowerAction(p,p,new BestStatePower(p)));
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                p.UpdateAllSeasonalDescription();
                this.isDone = true;
            }
        });
    }//基础效果，可以被使用和瞬发
}
