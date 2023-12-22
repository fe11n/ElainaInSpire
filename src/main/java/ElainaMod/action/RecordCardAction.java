package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.cards.AbstractSeasonCard;
import ElainaMod.cards.IndelibleImprint;
import ElainaMod.cards.MarblePhantasm;
import ElainaMod.orb.ConclusionOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class RecordCardAction extends AbstractGameAction {
    public ElainaC p=(ElainaC)AbstractDungeon.player;
    public AbstractElainaCard c;
    private boolean isNotable = false;
    public CardGroup g;
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
                this.addToBot(
                        new DamageAction(
                                AbstractDungeon.getRandomMonster(),
                                new DamageInfo(p,c.magicNumber, DamageInfo.DamageType.THORNS)
                        )
                );
                this.isDone=true;
                return;
            }
            logger.info("Record in Diary: "+c.name);
            if(c instanceof MarblePhantasm){
                g.addToTop(c);
                logger.info("Diary size after record: "+g.size());
                if(g.size()==1){
                    p.channelOrb(new ConclusionOrb(c));
                }
                this.isDone=true;
                return;
            }
            g.addToBottom(c);
            logger.info("Diary size after record: "+g.size());
            p.channelOrb(new ConclusionOrb(c));
        }
        this.isDone=true;
    }
}
