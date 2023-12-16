package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.cards.IndelibleImprint;
import ElainaMod.orb.ConclusionOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class ItsMeAction extends AbstractGameAction {
    public ElainaC p;
    public ArrayList<AbstractElainaCard> g;
    public static final Logger logger = LogManager.getLogger(ItsMeAction.class);
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("Elaina:ItsMeAction").TEXT;
    public boolean upgraded;
    public ItsMeAction(ElainaC p,boolean upgraded){
        this.p = p;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        Iterator it;
        if(this.duration == Settings.ACTION_DUR_FAST){
            if(p.hand.group.size()==0){
                this.isDone = true;
                return;
            }
            it = p.hand.group.iterator();
            if(p.hand.group.size()==1){
                while(it.hasNext()){
                    AbstractCard c = (AbstractCard) it.next();
                    AbstractMonster mo = AbstractDungeon.getRandomMonster();
                    if(c.cost>(upgraded?1:0)){
                        this.addToBot(new ApplyPowerAction(mo, p,new StrengthPower(mo, c.cost-(upgraded?1:0)), c.cost-(upgraded?1:0)));
                    }
                    this.addToBot(new RecordCardAction(c));
                    //p.hand.moveToDiscardPile(c);
                }
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
            AbstractDungeon.player.hand.applyPowers();
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            it = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
            while(it.hasNext()){
                AbstractCard c = (AbstractCard) it.next();
                AbstractMonster mo = AbstractDungeon.getRandomMonster();
                if(c.cost>(upgraded?1:0)){
                    this.addToBot(new ApplyPowerAction(mo, p,new StrengthPower(mo, c.cost-(upgraded?1:0)), c.cost-(upgraded?1:0)));
                }
                this.addToBot(new RecordCardAction(c));
                p.hand.addToHand(c);
                //p.hand.moveToDiscardPile(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }
}
