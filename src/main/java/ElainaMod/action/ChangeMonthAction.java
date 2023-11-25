package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeMonthAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(ChangeMonthAction.class);
    public ElainaC p;
    public int num;
    boolean isBack;
    public ChangeMonthAction(AbstractPlayer p, int num, boolean isBack){
        this.p = (ElainaC) p;
        this.num = num;
        this.isBack = isBack;
    }
    public ChangeMonthAction(AbstractPlayer p, int num){//默认向前
        this.p = (ElainaC) p;
        this.num = num;
        this.isBack = false;
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
        this.isDone = true;
    }
}