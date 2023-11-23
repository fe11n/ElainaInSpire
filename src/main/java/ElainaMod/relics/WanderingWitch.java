package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCard;
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
    public ArrayList<AbstractCard> g = p.DiaryGroup;
    public int NotedMonth;
    public WanderingWitch() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/NicolesAdventures.png"), RelicTier.STARTER, LandingSound.FLAT);
        p =(ElainaC) AbstractDungeon.player;
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    public void onEnterRoom(AbstractRoom room){//进入房间后时节+1，更新计数器
        logger.info("Month before enter: "+p.Month);
        if(p.Month==12){
            AbstractDungeon.player.gainGold(50);//直接p调用函数不生效，疑似p也只是临时变量
            p.Month = 0;
            p.Year++;
            logger.info("New Year: "+p.Year);
        }
        p.Month++;
        UpdateCounter();
    }
    public void atPreBattle(){//战斗开始时记录卡牌（这个是遗物描述的），TODO 并且按季节更新所有卡牌描述（这个最好写到能力里）
        this.addToTop(new RecordCard(new Strike()));
        p.UpdateAllSeasonalDescription();
    }
    public void onPlayerEndTurn(){//回合结束时记录打出的最后一张卡牌
        l = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        logger.info("This turn cards num: "+l.size());
        if(l.size()!=0){
            this.addToTop(new RecordCard(l.get(l.size()-1)));
        }
    }
    public void onVictory(){
        g.removeAll(g);
    }//TODO 战斗结束时清空日记，这个也最好写到能力里
    public void UpdateCounter(){//更新计数器
        p =(ElainaC) AbstractDungeon.player;//角色死亡后遗物不会重新构造，因此需要重新给p赋值
        NotedMonth = p.Month;
        this.flash();
        this.counter = p.Month;
        this.description = DESCRIPTIONS[p.getSeason()+1];
        this.tips.clear();
        logger.info("Month: "+p.Month);
        this.tips.add(new PowerTip(this.name, this.description));
        logger.info("6");
        this.initializeTips();
    }
    public AbstractRelic makeCopy(){
        return new WanderingWitch();
    }

}
