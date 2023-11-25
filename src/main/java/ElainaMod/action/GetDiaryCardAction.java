package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.cards.IndelibleImprint;
import ElainaMod.orb.ConclusionOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class GetDiaryCardAction extends AbstractGameAction {
    public ElainaC p;
    public ArrayList<AbstractElainaCard> g;
    private boolean toHand;
    public static final Logger logger = LogManager.getLogger(GetDiaryCardAction.class);
    public GetDiaryCardAction(ElainaC p){
        this.p = p;
        toHand = true;
    }
    public GetDiaryCardAction(ElainaC p, boolean toHand){
        this.p = p;
        this.toHand = toHand;
    }
    @Override
    public void update(){//将结语获取到手中，同时更新结语
        g = p.DiaryGroup;
        if(g.size()!=0){//如果调用p的方法，如p.getConclusion和p.getDiarySize就会报错Null，神奇
            AbstractElainaCard c = p.getConclusion();
            if(c instanceof IndelibleImprint && !toHand){
                this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, c.magicNumber, DamageInfo.DamageType.THORNS), AttackEffect.FIRE));
                this.isDone = true;
                return;
            }
            g.remove(g.size()-1);
            if(g.size()>0){
                p.channelOrb(new ConclusionOrb(p.getConclusion()));
            }
            else{
                p.removeNextOrb();
            }
            if(toHand){
                c.toHandfromDiary();
                p.hand.addToHand(c);
            }
        }
        logger.info("Now Diary size: "+p.getDiarySize());
        this.isDone=true;
    }
}