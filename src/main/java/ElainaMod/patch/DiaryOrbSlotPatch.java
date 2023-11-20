package ElainaMod.patch;

import ElainaMod.Characters.ElainaC;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.vfx.BobEffect;


public class DiaryOrbSlotPatch {
    private static final Texture DiarySlotImg = ImageMaster.loadImage("ElainaMod/img/UI/Diary.png");
    private static final BobEffect bobEffect;

    public DiaryOrbSlotPatch(){
    }

    static {
        bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
    }

    @SpirePatch(
            clz = EmptyOrbSlot.class,
            method = "render"
    )
    public static class DiarySlot{
        public DiarySlot(){}

        @SpirePrefixPatch
        public static SpireReturn ReplaceSlot(EmptyOrbSlot slot, SpriteBatch sb){
            if (AbstractDungeon.player!=null) {// && AbstractDungeon.player instanceof ElainaC
                sb.setColor(Color.WHITE.cpy());
                sb.draw(DiaryOrbSlotPatch.DiarySlotImg,
                        slot.cX - 48.0F - DiaryOrbSlotPatch.bobEffect.y / 8.0F,
                        slot.cY - 48.0F + DiaryOrbSlotPatch.bobEffect.y / 8.0F,
                        0.0F,
                        0.0F,
                        (float) DiaryOrbSlotPatch.DiarySlotImg.getWidth() * Settings.scale,
                        (float) DiaryOrbSlotPatch.DiarySlotImg.getHeight() * Settings.scale,
                        1.0F,
                        1.0F,
                        0.0F,
                        0,
                        0,
                        DiaryOrbSlotPatch.DiarySlotImg.getWidth(),
                        DiaryOrbSlotPatch.DiarySlotImg.getHeight(),
                        false,
                        false);
                slot.hb.render(sb);
                return SpireReturn.Return((Object) null);
            }
            else{
                return SpireReturn.Continue();
            }
        }
    }

}
