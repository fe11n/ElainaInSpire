package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.ItsMeAction;
import ElainaMod.action.RecordCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class Echo extends AbstractElainaCard {
    public static final String ID = "Elaina:Echo";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Echo.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Echo() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET,CardColor.COLORLESS);
        this.selfRetain = true;
        this.block = this.baseBlock = 5;
        this.tags.add(ElainaC.Enums.UNNOTABLE);
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
        }
    }

    @Override
    public void BasicEffect(ElainaC p, AbstractMonster m) {
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard c;
                Iterator it = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();
                while(it.hasNext()){
                    c = (AbstractCard) it.next();
                    if(c instanceof AbstractElainaCard){
                        addToBot(new RecordCardAction((AbstractElainaCard) c));
                    }
                }
                this.isDone = true;
            }
        });
        this.addToBot(new GainBlockAction(p,p,this.block));
    }
}
