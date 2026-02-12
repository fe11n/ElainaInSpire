package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class Glance extends AbstractElainaCard {
    public static final String ID = "Elaina:Glance";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "ElainaMod/img/cards/Glance.png";
    private static final int COST = -2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Glance() {
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.tags.add(ElainaC.Enums.UNNOTABLE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public void triggerWhenDrawn() {
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                this.addToTop(new DrawCardAction(1));
                Iterator it = AbstractDungeon.player.hand.group.iterator();
                while (it.hasNext()){
                    AbstractCard c = (AbstractCard) it.next();
                    addToBot(new RecordCardAction(c));
                }
                this.isDone = true;
            }
        });
        
        if(!upgraded){
            this.addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        }else{
            this.addToBot(new DiscardSpecificCardAction(this, AbstractDungeon.player.hand));
        }
    }
    
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }
}
