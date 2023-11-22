package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeMonth extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(ChangeMonth.class);
    public ElainaC p;
    public int num;
    boolean isBack;
    public ChangeMonth(AbstractPlayer p,int num,boolean isBack){
        this.p = (ElainaC) p;
        this.num = num;
        this.isBack = isBack;
    }
    public ChangeMonth(AbstractPlayer p,int num){//默认向前
        this.p = (ElainaC) p;
        this.num = num;
        this.isBack = false;
    }

    public void update(){
        logger.info("Month before change: "+p.Month);
        if(isBack){
            p.Year += ((p.Month-num-1)/12);
            if((p.Month-num)%12==0){
                p.ChangeMonth(12);
            }
            else {
                p.ChangeMonth((p.Month-num)%12);
            }
        }
        else {
            int YearInc = (p.Month+num-1)/12;
            p.Year += YearInc;
            AbstractDungeon.player.gainGold(50*YearInc);
            if((p.Month+num)%12==0){
                p.ChangeMonth(12);
            }
            else{
                p.ChangeMonth((p.Month-num)%12);
            }
        }
        logger.info("Month after change: "+p.Month);
        this.isDone = true;
    }
}
