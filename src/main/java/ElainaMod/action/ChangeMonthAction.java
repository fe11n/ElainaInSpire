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

import java.util.Iterator;

import static ElainaMod.Characters.ElainaC.Enums.SEASONAL;

public class ChangeMonthAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(ChangeMonthAction.class);
    public ElainaC p;
    public int num;
    boolean isBack;
    public ChangeMonthAction(AbstractPlayer p, int num, boolean isBack){
        this.p = (ElainaC) p;
        this.num = num;
        this.isBack = isBack;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    public ChangeMonthAction(AbstractPlayer p, int num){//默认向前
        this.p = (ElainaC) p;
        this.num = num;
        this.isBack = false;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update(){
        logger.info("Total Month before change: "+p.Month);
        if(isBack){
            p.ChangeMonth(p.Month-num);
        }
        else {
            p.ChangeMonth(p.Month+num);
        }
        logger.info("Total Month after change: "+p.Month);
        Iterator it = p.hand.group.iterator();
        while (it.hasNext()){
            AbstractCard ca = (AbstractCard) it.next();
            if(ca instanceof AbstractElainaCard){ //防止状态、诅咒牌引起报错
                AbstractElainaCard c = (AbstractElainaCard) ca;
                c.triggerOnMonthChanged(num,isBack);
            }
        }
        it = p.drawPile.group.iterator();
        while (it.hasNext()){
            AbstractCard ca = (AbstractCard) it.next();
            if(ca instanceof AbstractElainaCard){ //防止状态、诅咒牌引起报错
                AbstractElainaCard c = (AbstractElainaCard) ca;
                c.triggerOnMonthChanged(num,isBack);
            }
        }
        it = p.discardPile.group.iterator();
        while (it.hasNext()){
            AbstractCard ca = (AbstractCard) it.next();
            if(ca instanceof AbstractElainaCard){ //防止状态、诅咒牌引起报错
                AbstractElainaCard c = (AbstractElainaCard) ca;
                c.triggerOnMonthChanged(num,isBack);
            }
        }
        this.isDone = true;
    }
}
