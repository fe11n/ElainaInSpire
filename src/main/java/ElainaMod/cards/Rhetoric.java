package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.GetCertainDiaryCardAction;
import ElainaMod.action.GetDiaryCardAction;
import ElainaMod.orb.ConclusionOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class Rhetoric extends AbstractElainaCard {
    public static final String ID = "Elaina:Rhetoric";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Rhetoric.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Rhetoric() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 7;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    @Override
    public void BasicEffect(ElainaC p, AbstractMonster m) {
        // AbstractCard中实现了addToBot方法，它的效果和AbstractDungeon.actionManager.addToBottom相同
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(!upgraded) {
                    if (p.getConclusion() != null) {
                        p.DiaryGroup.getBottomCard().upgrade();
                    }
                } else {
                    Iterator it = p.DiaryGroup.group.iterator();
                    while (it.hasNext()){
                        ((AbstractCard)it.next()).upgrade();
                    }
                }
                this.isDone = true;
            }
        });
        if(!upgraded){
            this.addToBot(new GetDiaryCardAction(p));
        }else {
            this.addToBot(new GetCertainDiaryCardAction(p));
        }
        this.addToBot(new GainBlockAction(p,p,this.block));
    }
}
