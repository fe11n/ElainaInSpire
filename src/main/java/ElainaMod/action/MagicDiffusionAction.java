package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cardmods.toInstantCardMod;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.orb.ConclusionOrb;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class MagicDiffusionAction extends AbstractGameAction {
    public AbstractPlayer p;
    public AbstractElainaCard c;
    public ArrayList<AbstractElainaCard> g;
    public static final Logger logger = LogManager.getLogger(MagicDiffusionAction.class);
    private int amount;
    public MagicDiffusionAction(AbstractPlayer p,int num){
       this.p = p;
       this.amount = num;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    @Override
    public void update(){
        logger.info("Target cards num: "+getTargetCards().size());
        for(int i = 0;i<this.amount && getTargetCards().size()>0;i++){
            AbstractCard c = getTargetCards().get(
                    AbstractDungeon.cardRandomRng.random(getTargetCards().size()-1)
            );
            c.flash();
            CardModifierManager.addModifier(c, new toInstantCardMod());
        }
//        new DiaryGroupViewScreen().open();
        this.isDone=true;
    }
    public static ArrayList<AbstractCard> getTargetCards() {
        ArrayList<AbstractCard> list = new ArrayList();
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            logger.info("Check card: " + c.name);
            if (!ElainaC.isInstant(c) && ElainaC.isNotable(c)) {
                list.add(c);
            }
        }
        return list;
    }
}
