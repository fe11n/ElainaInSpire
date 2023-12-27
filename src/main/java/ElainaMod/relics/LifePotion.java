package ElainaMod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class LifePotion extends CustomRelic {
    public static final String ID = "Elaina:LifePotion";
    public LifePotion() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/LifePotion.png"), RelicTier.RARE, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }

}
