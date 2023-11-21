package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.relics.NicolesAdventures;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class GetDiaryCard extends AbstractGameAction {
    public AbstractPlayer p;
    public ArrayList<AbstractCard> g;
    public static final Logger logger = LogManager.getLogger(GetDiaryCard.class);
    public GetDiaryCard(AbstractPlayer p){
        this.p = p;
    }
    @Override
    public void update(){
        if(this.p instanceof ElainaC){
            g = ((ElainaC)p).DiaryGroup;
            if(g.size()>0){
                p.hand.addToHand(g.get(g.size()-1));
                g.remove(g.size()-1);
            }
            logger.info("Now Diary size: "+g.size());
        }
        this.isDone=true;
    }
}
