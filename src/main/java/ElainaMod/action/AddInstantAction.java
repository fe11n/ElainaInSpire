package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cardmods.toInstantCardMod;
import ElainaMod.cards.AbstractElainaCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddInstantAction extends AbstractGameAction {
    public ElainaC p;
    public AbstractCard c;
    public CardGroup g;
    public static final Logger logger = LogManager.getLogger(AddInstantAction.class);
    public AddInstantAction(ElainaC p){
       this.p = p;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    @Override
    public void update(){
        g = ElainaC.DiaryGroup;
        if(!g.isEmpty()){
            if(!p.isInstant(p.getConclusion())){
                c = g.getBottomCard();
                CardModifierManager.addModifier(c, new toInstantCardMod());
                p.getConclusionOrb().syncConclusionWithDiary();
            }
        }
        this.isDone=true;
    }
}
