package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.Strike;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class NicolesAdventures extends CustomRelic {
    public static final String ID = "Elaina:NicolesAdventures";
    public static final Logger logger = LogManager.getLogger(NicolesAdventures.class);
    AbstractPlayer p =AbstractDungeon.player;
    public ArrayList<AbstractCard> l;
    public ArrayList<AbstractCard> g;
    public NicolesAdventures() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/NicolesAdventures.png"), RelicTier.STARTER, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    public void atPreBattle(){
        logger.info("Start judge");
        if(p instanceof ElainaC){
            logger.info("Judge end");
            g = ((ElainaC) p).DiaryGroup;
            g.add(new Strike());
            logger.info("Now Diary size: "+g.size());
        }
    }

    public void onPlayerEndTurn(){
        if(p instanceof ElainaC){
            g = ((ElainaC) p).DiaryGroup;
            l = AbstractDungeon.actionManager.cardsPlayedThisTurn;
            logger.info("This turn cards num:"+l.size());
            if(l.size()!=0){
                logger.info("Put in Diary: "+l.get(l.size()-1).name);
                g.add(l.get(l.size()-1).makeCopy());
                logger.info("Now Diary size: "+g.size());
            }
        }
    }


    public AbstractRelic makeCopy(){
        return new NicolesAdventures();
    }

}
