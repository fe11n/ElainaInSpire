package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cardmods.toInstantCardMod;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.orb.ConclusionOrb;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AddInstantAction extends AbstractGameAction {
    public ElainaC p;
    public AbstractElainaCard c;
    public ArrayList<AbstractElainaCard> g;
    public static final Logger logger = LogManager.getLogger(AddInstantAction.class);
    public AddInstantAction(ElainaC p){
       this.p = p;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    @Override
    public void update(){
        g = p.DiaryGroup;
        if(g.size()!=0){
            if(!p.getConclusion().isInstant){
                c = p.getConclusion();
                CardModifierManager.addModifier(c,new toInstantCardMod());//直接操作日记内的卡牌
                p.channelOrb(new ConclusionOrb(c));
            }
        }
        this.isDone=true;
    }
}
