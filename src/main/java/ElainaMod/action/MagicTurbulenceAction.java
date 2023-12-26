package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.orb.ConclusionOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;

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
        for (AbstractCard c : ElainaC.DiaryGroup.group) {
            c.updateCost(-1);
            logger.info(c.name + " cost:" + c.costForTurn);
        }
        Collections.shuffle(ElainaC.DiaryGroup.group,AbstractDungeon.miscRng.random);
        if(!ElainaC.DiaryGroup.group.isEmpty()){
            p.getConclusionOrb().syncConclusonWithDiary();
        }
        this.isDone=true;
    }
}
