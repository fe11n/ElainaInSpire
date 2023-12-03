package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCardAction;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.cards.Strike;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class WanderingWitch extends CustomRelic {
    public static final String ID = "Elaina:WanderingWitch";
    public static final Logger logger = LogManager.getLogger(WanderingWitch.class);
    ElainaC p;
    public ArrayList<AbstractCard> l;
    public ArrayList<AbstractElainaCard> g = p.DiaryGroup;
    public int NotedMonth;
    public WanderingWitch() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/NicolesAdventures.png"), RelicTier.STARTER, LandingSound.FLAT);
        p =(ElainaC) AbstractDungeon.player;
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    public void onEnterRoom(AbstractRoom room){//进入房间后时节+1，更新计数器，此时卡牌未初始化，只能直接更新
        logger.info("Month before enter: "+p.Month);
        p.ChangeMonth(p.Month+1,false);//通过这个函数调用UpgradeCounter不生效，神奇了
        logger.info("Month before enter: "+p.Month);
        UpdateCounter();
    }
    public void atPreBattle(){//战斗开始时记录卡牌（这个是遗物描述的），TODO 并且按季节更新所有卡牌描述（这个最好写到能力里）
        ((ElainaC)AbstractDungeon.player).DiaryGroup.clear();//战斗开始时清空，不管sl了
        this.addToTop(new RecordCardAction(new Strike()));
        ((ElainaC)AbstractDungeon.player).UpdateAllSeasonalDescription();
    }
    public void onPlayerEndTurn(){//回合结束时记录打出的最后一张卡牌
        l = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        logger.info("This turn cards num: "+l.size());
        if(l.size()!=0){
            this.addToTop(new RecordCardAction((AbstractElainaCard) l.get(l.size()-1)));
        }
    }
    public void onVictory(){
        g.removeAll(g);
    }//TODO 战斗结束时清空日记，这个也最好写到能力里
    public void UpdateCounter(){//更新计数器
        logger.info("Changing RelicCounter...");
        p =(ElainaC) AbstractDungeon.player;//角色死亡后遗物不会重新构造，因此需要重新给p赋值
        NotedMonth = (p.Month%12)==0?12:(p.Month%12);
        this.flash();
        logger.info("Noted Month: "+NotedMonth);
        this.counter = NotedMonth;
        this.description = DESCRIPTIONS[p.getSeason()+1];
        this.tips.clear();
        logger.info("Total Month: "+p.Month);
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    public AbstractRelic makeCopy(){
        return new WanderingWitch();
    }

}
