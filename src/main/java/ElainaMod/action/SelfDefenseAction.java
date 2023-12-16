package ElainaMod.action;

import ElainaMod.cardmods.toImageCardMod;
import ElainaMod.cardmods.toInstantCardMod;
import ElainaMod.cards.AbstractElainaCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class SelfDefenseAction extends AbstractGameAction {
    public AbstractPlayer p;
    public AbstractElainaCard c;
    public ArrayList<AbstractElainaCard> g;
    public static final Logger logger = LogManager.getLogger(SelfDefenseAction.class);
    public SelfDefenseAction(AbstractPlayer p){
       this.p = p;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    @Override
    public void update(){
        logger.info("Target cards num: "+getTargetCards().size());
        if (getTargetCards().size()>0){
            AbstractCard c = getTargetCards().get(
                    AbstractDungeon.cardRandomRng.random(getTargetCards().size()-1)
            );
            c.flash();
            CardModifierManager.addModifier(c, new toImageCardMod());
        }
        this.isDone=true;
    }
    public static ArrayList<AbstractCard> getTargetCards() {
        ArrayList<AbstractCard> list = new ArrayList();
        Iterator it = AbstractDungeon.player.hand.group.iterator();
        while(it.hasNext()){
            AbstractCard c = (AbstractCard) it.next();
            logger.info("Check card: "+c.name);
            if(c instanceof AbstractElainaCard && !CardModifierManager.hasModifier(c,"toImageCardMod")){
                list.add(c);
            }
        }
        return list;
    }
}
