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
import java.util.Iterator;

public class RecordCardAction extends AbstractGameAction {
    public AbstractPlayer p=AbstractDungeon.player;
    public AbstractElainaCard c;
    public ArrayList<AbstractElainaCard> g;
    public static final Logger logger = LogManager.getLogger(RecordCardAction.class);
    public RecordCardAction(AbstractElainaCard c){
       this.c = c.makeInstanceCopy();
    }
    @Override
    public void update(){
        if(c.exhaust==false && c.type!= AbstractCard.CardType.POWER && !c.hasTag(ElainaC.Enums.UNNOTABLE)){
            //未被消耗，不是能力牌，无不可被记录标记时记录
            g = ((ElainaC) p).DiaryGroup;
            logger.info("Record in Diary: "+c.name);
            g.add(c);
            logger.info("Diary size after record: "+g.size());
            Iterator it = g.iterator();
            while(it.hasNext()){
                logger.info(((AbstractCard)it.next()).name);
            }
            logger.info("Orb Description(1): " + c.rawDescription);
            p.channelOrb(new ConclusionOrb(c));//尽管c描述已更改，但这里依然渲染的是初始描述
        }
        this.isDone=true;
    }
}
