package ElainaMod.patch;

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
                slot.setSlot(2,3);
                slot.name = "结语槽位";
                slot.description = "日记最后记录的卡牌。暂无。不为空时，点击可以查看日记内容。";
                sb.setColor(Color.WHITE.cpy());
                int width = DiaryOrbSlotPatch.DiarySlotImg.getWidth();
                int height = DiaryOrbSlotPatch.DiarySlotImg.getHeight();
                sb.draw(DiaryOrbSlotPatch.DiarySlotImg,
                        slot.cX - (float) width / 2,
                        slot.cY - (float) height / 2,
                        (float) width / 2,
                        (float) height / 2,
                        (float) width * Settings.scale,
                        (float) height * Settings.scale,
                        1.0F,
                        1.0F,
                        0.0F,
                        0,
                        0,
                        width,
                        height,
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
