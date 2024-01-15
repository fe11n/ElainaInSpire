package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cardmods.toRetainCardMod;
import ElainaMod.cards.AbstractElainaCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class CountermeasureAction extends AbstractGameAction {
    public ElainaC p;
    public ArrayList<AbstractElainaCard> g;
    public static final Logger logger = LogManager.getLogger(CountermeasureAction.class);
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("Elaina:CountermeasureAction").TEXT;
    private int num;
    public CountermeasureAction(ElainaC p,int num){
        this.p = p;
        this.num = num;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
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
//            if(p.hand.group.size()==1){
//                while(it.hasNext()){
//                    AbstractCard c = (AbstractCard) it.next();
//                    CardModifierManager.addModifier(c,new toRetainCardMod());
//                }
//                this.isDone = true;
//                return;
//            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], num, true,true);
            AbstractDungeon.player.hand.applyPowers();
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            it = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
            while(it.hasNext()){
                AbstractCard c = (AbstractCard) it.next();
                CardModifierManager.addModifier(c,new toRetainCardMod());
                p.hand.addToHand(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }
}
