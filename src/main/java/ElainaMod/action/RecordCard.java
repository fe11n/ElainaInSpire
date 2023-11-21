package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.orb.ConclusionOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class RecordCard extends AbstractGameAction {
    public AbstractPlayer p;
    public AbstractCard c;
    public ArrayList<AbstractCard> g;
    public static final Logger logger = LogManager.getLogger(RecordCard.class);
    public RecordCard(AbstractPlayer p,AbstractCard c){
        this.p = p;
        this.c = c;
    }
    @Override
    public void update(){
        if(p instanceof ElainaC){
            g = ((ElainaC) p).DiaryGroup;
            logger.info("Record in Diary: "+c.name);
            g.add(c.makeCopy());
            logger.info("Diary size after record: "+g.size());
            logger.info("Max orbs: "+p.maxOrbs);
            Iterator it = g.iterator();
            while(it.hasNext()){
                logger.info(((AbstractCard)it.next()).name);
            }
            p.channelOrb(new ConclusionOrb(c));
        }
        this.isDone=true;
    }
}
