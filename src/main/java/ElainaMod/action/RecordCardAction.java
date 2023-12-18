package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.cards.IndelibleImprint;
import ElainaMod.cards.MarblePhantasm;
import ElainaMod.orb.ConclusionOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class RecordCardAction extends AbstractGameAction {
    public ElainaC p=(ElainaC)AbstractDungeon.player;
    public AbstractElainaCard c;
    private boolean isNotable = false;
    public ArrayList<AbstractElainaCard> g;
    public static final Logger logger = LogManager.getLogger(RecordCardAction.class);
    public RecordCardAction(AbstractCard c){
        this.actionType = ActionType.CARD_MANIPULATION;
        if(c instanceof AbstractElainaCard){
            isNotable = ((AbstractElainaCard) c).isNotable();
        }
        else {
            isNotable = false;
        }
        if(isNotable){
            this.c = (AbstractElainaCard)c.makeStatEquivalentCopy();
        }
    }
    @Override
    public void update(){
        if(isNotable){
            g = ElainaC.DiaryGroup;

            if(g.size()!= 0 && p.getConclusion() instanceof IndelibleImprint){
                c = p.getConclusion();
                c.flash();
                this.addToBot(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(p,c.magicNumber, DamageInfo.DamageType.THORNS)));
                this.isDone=true;
                return;
            }
            logger.info("Record in Diary: "+c.name);
            if(c instanceof MarblePhantasm){
                g.add(0,c);
                logger.info("Diary size after record: "+g.size());
                for (AbstractElainaCard abstractElainaCard : g) {
                    logger.info(((AbstractCard) abstractElainaCard).name);
                }
                if(g.size()==1){
                    p.channelOrb(new ConclusionOrb(c));
                }
                this.isDone=true;
                return;
            }
            g.add(c);
            logger.info("Diary size after record: "+g.size());
            for (AbstractElainaCard abstractElainaCard : g) {
                logger.info(((AbstractCard) abstractElainaCard).name);
            }
            if(c.hasTag(ElainaC.Enums.SEASONAL)){
                c.UpdateSeasonalDescription(true);//复制的instance没有initialize，描述没有改变，也可以直接initialize
            }
            p.channelOrb(new ConclusionOrb(c));//尽管c描述已更改，但这里依然渲染的是初始描述
        }
        this.isDone=true;
    }
}
