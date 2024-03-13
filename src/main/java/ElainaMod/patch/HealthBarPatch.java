package ElainaMod.patch;

import ElainaMod.powers.MagicResiduePower;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class HealthBarPatch {
    HealthBarPatch() {}
    private static final Logger logger = LogManager.getLogger(HealthBarPatch.class);
    @SpirePatch(
            clz = AbstractCreature.class,
            method = "renderRedHealthBar",
            paramtypez = {SpriteBatch.class, float.class, float.class}
    )
    public static class renderHealthPatch {
        @SpirePostfixPatch
        public static void customHealthBarRender(AbstractCreature __instance, SpriteBatch sb, float x, float y) {
            if (!__instance.hasPower(MagicResiduePower.POWER_ID)) {
                return;
            }
            // 为保证兼容性，限制有魔力残留才进入。同时考虑中毒的情况。
            float HEALTH_BAR_HEIGHT = ReflectionHacks.getPrivate(__instance, AbstractCreature.class, "HEALTH_BAR_HEIGHT");
            float targetHealthBarWidth = ReflectionHacks.getPrivate(__instance, AbstractCreature.class, "targetHealthBarWidth");
            float HEALTH_BAR_OFFSET_Y = ReflectionHacks.getPrivate(__instance, AbstractCreature.class, "HEALTH_BAR_OFFSET_Y");
            float healthBarWidth = ReflectionHacks.getPrivate(__instance, AbstractCreature.class, "healthBarWidth");
            int Num;
            int residualAmt = __instance.getPower(MagicResiduePower.POWER_ID).amount;
            int selfRetainMult = 0;
            ArrayList<AbstractCard> g = AbstractDungeon.player.hand.group;
            for (AbstractCard card : g) {
                if (card.selfRetain)
                    selfRetainMult++;
            }
            g = AbstractDungeon.player.limbo.group;
            for (AbstractCard card : g) {
                if (card.selfRetain)
                    selfRetainMult++;
            }
            Num = residualAmt*selfRetainMult;
            if(Num==0){
                return;
            }

            sb.setColor(Color.valueOf("dcdbffff"));
            if (!__instance.hasPower("Poison")) {
                if (__instance.currentHealth > 0) {
                    sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
                }

                sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, targetHealthBarWidth, HEALTH_BAR_HEIGHT);
                sb.draw(ImageMaster.HEALTH_BAR_R, x + targetHealthBarWidth, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            } else {
                int poisonAmt = __instance.getPower("Poison").amount;
                if (poisonAmt > 0 && __instance.hasPower("Intangible")) {
                    poisonAmt = 1;
                }

                if (__instance.currentHealth > poisonAmt) {
                    float w = 1.0F - (float)(__instance.currentHealth - poisonAmt) / (float)__instance.currentHealth;
                    w *= targetHealthBarWidth;
                    if (__instance.currentHealth > 0) {
                        sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
                    }

                    sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, targetHealthBarWidth - w, HEALTH_BAR_HEIGHT);
                    sb.draw(ImageMaster.HEALTH_BAR_R, x + targetHealthBarWidth - w, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
                }
            }

            if (__instance.currentBlock > 0) {
                sb.setColor(Color.valueOf("31568cff"));//blue
            } else {
                sb.setColor(Color.valueOf("cc0c0cff"));//red
            }
            if(__instance.hasPower("Poison")){
                Num+=__instance.getPower("Poison").amount;
            }
            if (Num > 0 && __instance.hasPower("Intangible")) {
                Num = 1;
            }
            if (__instance.currentHealth > Num) {
                float w = 1.0F - (float)(__instance.currentHealth - Num) / (float)__instance.currentHealth;
                w *= targetHealthBarWidth;
                if (__instance.currentHealth > 0) {
                    sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
                }

                sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, targetHealthBarWidth - w, HEALTH_BAR_HEIGHT);
                sb.draw(ImageMaster.HEALTH_BAR_R, x + targetHealthBarWidth - w, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            }
        }
    }
}
