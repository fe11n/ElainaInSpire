package ElainaMod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class NicolesAdventure extends CustomRelic {
    public static final String ID = "Elaina:NicolesAdventure";
    public NicolesAdventure() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/NicolesAdventure.png"), RelicTier.BOSS, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }

}
