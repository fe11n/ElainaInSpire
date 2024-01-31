package ElainaMod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ShinyMushroom extends CustomRelic {
    public static final String ID = "Elaina:ShinyMushroom";
    public ShinyMushroom() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/ShinyMushroom.png"), RelicTier.SPECIAL, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }

}
