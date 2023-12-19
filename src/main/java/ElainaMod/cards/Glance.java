package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.Elaina.Elaina;
import ElainaMod.action.RecordCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class Glance extends AbstractElainaCard {
    public static final String ID = "Elaina:Glance";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Glance.png";
    private static final int COST = -2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Glance() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.tags.add(ElainaC.Enums.UNNOTABLE);
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    public void triggerWhenDrawn() {
        if(!upgraded){
            this.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        }
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                Iterator it = AbstractDungeon.player.hand.group.iterator();
                while (it.hasNext()){
                    AbstractCard c = (AbstractCard) it.next();
                    addToBot(new RecordCardAction(c));
                }
                this.isDone = true;
            }
        });
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

}
