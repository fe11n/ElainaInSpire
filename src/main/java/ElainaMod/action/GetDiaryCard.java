package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cardmods.ModRecordedCard;
import ElainaMod.orb.ConclusionOrb;
import basemod.helpers.CardModifierManager;
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
    public void update(){//将结语获取到手中，同时更新结语
        g = ((ElainaC)p).DiaryGroup;
        if(g.size()>0){
            AbstractCard c = g.get(g.size()-1);
            CardModifierManager.addModifier(c,new ModRecordedCard());
            p.hand.addToHand(c);
            g.remove(g.size()-1);
            if(g.size()>0){
                p.channelOrb(new ConclusionOrb(g.get(g.size()-1)));
            }
            else{
                p.removeNextOrb();
            }
        }
        logger.info("Now Diary size: "+g.size());
        this.isDone=true;
    }
}
