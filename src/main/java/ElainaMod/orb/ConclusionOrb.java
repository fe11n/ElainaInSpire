package ElainaMod.orb;

import ElainaMod.cards.AbstractElainaCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ConclusionOrb extends AbstractOrb {
    public static final String ID = "Elaina:ConclusionOrb";
    public AbstractElainaCard c;
    public static final Logger logger = LogManager.getLogger(ConclusionOrb.class);
    public ConclusionOrb(AbstractElainaCard c){
        this.c = c;
        this.name=c.name;
        this.updateDescription();
    }
    public void onStartOfTurn(){//实现瞬发机制
        if(c.isInstant){//如果使用tag判断INSTANT会导致更改INSTANT时同类卡牌全部INSTANT被修改
            c.InstantUse();
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
