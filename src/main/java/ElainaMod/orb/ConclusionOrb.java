package ElainaMod.orb;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.cards.Strike;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class ConclusionOrb extends AbstractOrb {
    public static final String ID = "Elaina:ConclusionOrb";
    public AbstractCard c;
    public ConclusionOrb(AbstractCard c){
        this.c = c;
        this.name=c.name;
        this.updateDescription();
    }
    public void onStartOfTurn(){//实现瞬发机制
        if(c.hasTag(ElainaC.Enums.INSTANT)){
            ((AbstractElainaCard)c).InstantUse();
        }
    }
    @Override
    public void updateDescription() {
        this.description=c.name;
    }

    public void update(){//更新充能球卡图
        super.update();
        this.c.target_x = this.tX;
        this.c.target_y = this.tY;
        if (this.hb.hovered) {
            this.c.targetDrawScale = 1.0F;
        } else {
            this.c.targetDrawScale = Float.valueOf(0.5F);
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
