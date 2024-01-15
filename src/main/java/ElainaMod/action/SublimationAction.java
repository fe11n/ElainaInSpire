package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class SublimationAction extends AbstractGameAction {
    public ElainaC p;
    public ArrayList<AbstractElainaCard> g;
    public static final Logger logger = LogManager.getLogger(SublimationAction.class);
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("Elaina:SublimationAction").TEXT;
    private ArrayList<AbstractCard> notRetatinCards = new ArrayList();
    private boolean upgraded;
    public SublimationAction(ElainaC p,boolean upgraded){
        this.p = p;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        Iterator it;
        if(this.duration == Settings.ACTION_DUR_FAST){
            for(AbstractCard c:p.hand.group){
                if(!c.selfRetain){
                    notRetatinCards.add(c);
                }
            }
            if (this.notRetatinCards.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            p.hand.group.removeAll(notRetatinCards);
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 10, true,true);
            AbstractDungeon.player.hand.applyPowers();
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            int n = AbstractDungeon.handCardSelectScreen.selectedCards.group.size();
            it = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
            while(it.hasNext()){
                AbstractCard c = (AbstractCard) it.next();
                this.addToBot(new ExhaustSpecificCardAction(c,p.hand));
            }
            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

            this.addToBot(new GainEnergyAction(n));
            for(int i = 0;i<n;i++){
                if(p.discardPile.size()>i){
                    AbstractCard c = p.discardPile.group.get(p.discardPile.size()-1-i);
                    this.addToBot(new DiscardToHandAction(c));
                    if(upgraded){
                        this.addToBot(new RecordCardAction(c));
                    }
                }
            }

            this.isDone = true;
        }
        this.tickDuration();
    }
    private void returnCards() {
        Iterator var1 = this.notRetatinCards.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }
}
