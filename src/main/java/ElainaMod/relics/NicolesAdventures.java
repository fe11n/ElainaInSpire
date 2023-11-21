package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCard;
import ElainaMod.cards.Strike;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
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
        this.addToTop(new RecordCard(new Strike()));
    }

    public void onPlayerEndTurn(){
        g = ((ElainaC) p).DiaryGroup;
        l = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        logger.info("This turn cards num: "+l.size());
        if(l.size()!=0){
            this.addToTop(new RecordCard(l.get(l.size()-1)));
        }
    }
    public AbstractRelic makeCopy(){
        return new NicolesAdventures();
    }

}
