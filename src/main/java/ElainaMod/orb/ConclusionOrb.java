package ElainaMod.orb;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ConclusionOrb extends AbstractOrb {
    public static final String ID = "Elaina:ConclusionOrb";
    public AbstractCard c;
    public ConclusionOrb(AbstractCard c){
        this.c = c;
        this.name=c.name;
        this.updateDescription();
    }
    @Override
    public void updateDescription() {
        this.description=c.name;
    }

    public void update(){
        super.update();
        this.c.target_x = this.tX;
        this.c.target_y = this.tY;
        if (this.hb.hovered) {
            this.c.targetDrawScale = 1.0F;
        } else {
            this.c.targetDrawScale = Float.valueOf(0.2F);
        }
        this.c.update();
    }
    @Override
    public void onEvoke() {
    }
    @Override
    public AbstractOrb makeCopy() {
        return null;
    }
    @Override
    public void render(SpriteBatch sb) {
        this.c.render(sb);
    }
    @Override
    public void playChannelSFX() {
    }
}
