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
    public StoveFire() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/StoveFire.png"), RelicTier.UNCOMMON, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }

}
