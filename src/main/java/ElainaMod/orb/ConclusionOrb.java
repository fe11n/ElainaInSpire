package ElainaMod.orb;

import ElainaMod.Characters.ElainaC;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class ConclusionOrb extends AbstractOrb {
    public static final String ID = "Elaina:ConclusionOrb";
    public AbstractCard c;
    public ConclusionOrb(AbstractCard c){
        this.c = c;
    }
    @Override
    public void updateDescription() {

    }
    @Override
    public void onEvoke() {

    }
    @Override
    public AbstractOrb makeCopy() {
        return null;
    }
    @Override
    public void render(SpriteBatch spriteBatch) {

    }
    @Override
    public void playChannelSFX() {

    }
}
