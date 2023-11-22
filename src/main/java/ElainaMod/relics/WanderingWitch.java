package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.ChangeMonth;
import ElainaMod.action.RecordCard;
import ElainaMod.cards.Strike;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class WanderingWitch extends CustomRelic {
    public static final String ID = "Elaina:WanderingWitch";
    public static final Logger logger = LogManager.getLogger(WanderingWitch.class);
    ElainaC p =(ElainaC) AbstractDungeon.player;
    public ArrayList<AbstractCard> l;
    public ArrayList<AbstractCard> g = p.DiaryGroup;
    public int NotedMonth;
    public WanderingWitch() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/NicolesAdventures.png"), RelicTier.STARTER, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[4];
    }
    public void onEnterRoom(AbstractRoom room){
        logger.info("Month before enter: "+p.Month);
        if(p.Month==12){
            AbstractDungeon.player.gainGold(50);//直接p调用函数不生效，疑似p也只是临时变量
            p.Month=0;
            p.Year++;
            logger.info("New Year: "+p.Year);
        }
        p.Month++;
        UpdateCounter();
    }
    public void atPreBattle(){
        this.addToTop(new RecordCard(new Strike()));
    }
    public void onPlayerEndTurn(){
        l = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        logger.info("This turn cards num: "+l.size());
        if(l.size()!=0){
            this.addToTop(new RecordCard(l.get(l.size()-1)));
        }
    }
    public void onVictory(){
        g.removeAll(g);
    }
    public void UpdateCounter(){
        logger.info("0");
        NotedMonth = p.Month;
        logger.info("1");
        this.flash();
        logger.info("2");
        this.counter = p.Month;
        logger.info("3");
        this.description = DESCRIPTIONS[p.getSeason()];
        logger.info("4");
        this.tips.clear();
        logger.info("5");
        this.tips.add(new PowerTip(this.name, this.description));
        logger.info("6");
        this.initializeTips();
    }
    public AbstractRelic makeCopy(){
        return new WanderingWitch();
    }

}
