package ElainaMod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class NicolesAdventures extends CustomRelic {
    public static final String ID = "Elaina:NicolesAdventures";
    public NicolesAdventures() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/NicolesAdventures.png"), RelicTier.STARTER, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    public void atPreBattle(){
        AbstractDungeon.player.increaseMaxOrbSlots(1,true);
    }

    public AbstractRelic makeCopy(){
        return new NicolesAdventures();
    }

}
