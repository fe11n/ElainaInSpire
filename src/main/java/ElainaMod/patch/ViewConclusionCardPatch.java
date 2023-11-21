package ElainaMod.patch;

import ElainaMod.orb.ConclusionOrb;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.Iterator;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "render"
)
public class ViewConclusionCardPatch {
    public ViewConclusionCardPatch(){
    }
    @SpirePostfixPatch
    public static void Postfix(AbstractPlayer p, SpriteBatch sb){
        Iterator it = p.orbs.iterator();
        while(it.hasNext()){
            AbstractOrb o = (AbstractOrb) it.next();
            o.render(sb);
        }
    }
}
