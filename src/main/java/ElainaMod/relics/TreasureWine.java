package ElainaMod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class TreasureWine extends CustomRelic {
    public static final String ID = "Elaina:TreasureWine";
    public TreasureWine() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/TreasureWine.png"), RelicTier.UNCOMMON, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }

}
