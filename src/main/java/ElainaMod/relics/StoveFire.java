package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class StoveFire extends CustomRelic {
    public static final String ID = "Elaina:StoveFire";
    public static final Logger logger = LogManager.getLogger(StoveFire.class);
    ElainaC p;
    public ArrayList<AbstractCard> g = ElainaC.DiaryGroup.group;
    public StoveFire() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/WanderingWitch.png"), RelicTier.UNCOMMON, LandingSound.FLAT);
        p =(ElainaC) AbstractDungeon.player;
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }

}
