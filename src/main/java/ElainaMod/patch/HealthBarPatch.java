package ElainaMod.patch;

import ElainaMod.powers.ResidualMagicPower;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.GsonBuilder;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import static ElainaMod.patch.HealthBarPatch.renderHealthPatch.*;

public class HealthBarPatch {
    HealthBarPatch() {}
    private static final Logger logger = LogManager.getLogger(HealthBarPatch.class);
    @SpirePatch(
            clz = AbstractCreature.class,
            method = "renderRedHealthBar",
            paramtypez = {SpriteBatch.class, float.class, float.class}
    )
    public static class renderHealthPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> customHealthBarRender(AbstractCreature __instance, SpriteBatch sb, float x, float y) {
            if (!__instance.hasPower(ResidualMagicPower.POWER_ID)) {
                return SpireReturn.Continue();
            }
            // 为保证兼容性，限制有魔力残留才进入。同时考虑中毒的情况。
            float HEALTH_BAR_HEIGHT = ReflectionHacks.getPrivate(__instance, AbstractCreature.class, "HEALTH_BAR_HEIGHT");
            float targetHealthBarWidth = ReflectionHacks.getPrivate(__instance, AbstractCreature.class, "targetHealthBarWidth");
            float HEALTH_BAR_OFFSET_Y = ReflectionHacks.getPrivate(__instance, AbstractCreature.class, "HEALTH_BAR_OFFSET_Y");
            float healthBarWidth = ReflectionHacks.getPrivate(__instance, AbstractCreature.class, "healthBarWidth");

            int poisonAmt = 0;
            if (__instance.hasPower(PoisonPower.POWER_ID)) {
                poisonAmt = __instance.getPower(PoisonPower.POWER_ID).amount;
            } else {
                // 没有中毒效果，需要自己画一层底色。
                sb.setColor(Color.valueOf("dcdbffff"));  // 浅紫
                if (__instance.currentHealth > 0) {
                    sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
                }
                sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, targetHealthBarWidth, HEALTH_BAR_HEIGHT);
                sb.draw(ImageMaster.HEALTH_BAR_R, x + targetHealthBarWidth, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);

            }
            int residualAmt = __instance.getPower(ResidualMagicPower.POWER_ID).amount;
            // 回合结束洗牌的时候，会把保留牌挪到limbo牌组，导致保留卡数量读0.
            // 可以在角色内部处理保留卡牌数量获取，但如果要兼容其他角色，就不要这样。
            int selfRetainMult = 0;
            ArrayList<AbstractCard> g = AbstractDungeon.player.hand.group;
            if (g != null) {
                for (AbstractCard card : g) {
                    if (card.selfRetain)
                        selfRetainMult++;
                }
                if (selfRetainMult==0) {
                    for (AbstractCard card : g) {
                        logger.info(card);
                    }
                }
            }

            int damage = poisonAmt + residualAmt * selfRetainMult;
            if (damage > 0 && __instance.hasPower(IntangiblePower.POWER_ID)) {
                damage = 1;
            }


            if (__instance.currentHealth > damage) {
                sb.setColor(Color.valueOf("8b2ad2ff"));  // 受到效果影响后的血条颜色。原版是红色和格挡判断。这里改紫色
                float w = (1.0f - (((float) (__instance.currentHealth - damage)) / ((float) __instance.currentHealth))) * targetHealthBarWidth;
                if (__instance.currentHealth > 0) {
                    sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
                }
                sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, targetHealthBarWidth - w, HEALTH_BAR_HEIGHT);
                sb.draw(ImageMaster.HEALTH_BAR_R, (x + targetHealthBarWidth) - w, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            }

            return SpireReturn.Return();
        }
    }
}
