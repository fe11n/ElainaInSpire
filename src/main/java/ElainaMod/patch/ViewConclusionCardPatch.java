package ElainaMod.patch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "render"
)
public class ViewConclusionCardPatch {
    public ViewConclusionCardPatch(){
    }
    @SpirePostfixPatch
    public static void Postfix(AbstractPlayer p, SpriteBatch sb){
        for (AbstractOrb o : p.orbs) {
            o.render(sb);//这个patch令卡牌显示在人物上面
        }
    }
}
