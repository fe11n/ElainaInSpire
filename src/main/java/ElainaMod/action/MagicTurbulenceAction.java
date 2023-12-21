package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cardmods.toInstantCardMod;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.orb.ConclusionOrb;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class MagicTurbulenceAction extends AbstractGameAction {
    public ElainaC p;
    public AbstractElainaCard c;
    public ArrayList<AbstractElainaCard> g;
    public static final Logger logger = LogManager.getLogger(MagicTurbulenceAction.class);
    private int amount;
    public MagicTurbulenceAction(AbstractPlayer p){
       this.p = (ElainaC) p;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    @Override
    public void update(){
        Iterator it = p.DiaryGroup.iterator();
        while (it.hasNext()){
            AbstractCard c = (AbstractCard)it.next();
            c.updateCost(-1);
            logger.info(c.name+" cost:"+c.costForTurn);
        }
        Collections.shuffle(p.DiaryGroup,AbstractDungeon.miscRng.random);
        if(p.DiaryGroup.size()>0){
            p.channelOrb(new ConclusionOrb(p.getConclusion()));
        }
        this.isDone=true;
    }
}
