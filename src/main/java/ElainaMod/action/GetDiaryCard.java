package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetDiaryCard extends AbstractGameAction {
    public AbstractPlayer p;
    public static final Logger logger = LogManager.getLogger(GetDiaryCard.class);
    public GetDiaryCard(AbstractPlayer p){
        this.p = p;
    }
    @Override
    public void update(){

        if(this.p instanceof ElainaC){
            logger.info(
                    "before get num: "+((ElainaC)p).DiaryGroup.size()
            );
            p.hand.addToTop(
                    ((ElainaC)p).DiaryGroup.get(0)
            );
        }
        this.isDone=true;
    }
}
