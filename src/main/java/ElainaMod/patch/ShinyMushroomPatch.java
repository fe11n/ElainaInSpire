package ElainaMod.patch;

import ElainaMod.relics.ShinyMushroom;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.GsonBuilder;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ShinyMushroomPatch {
    public ShinyMushroomPatch(){
    }
    public static final Logger logger = LogManager.getLogger(ShinyMushroomPatch.class);
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "getRewardCards"
    )
    public static class ShinyMushroomChangeCardReward{
        @SpireInsertPatch(
                locator = ShinyMushroomPatch.ShinyMushroomChangeCardReward.Locator.class,
                localvars = {"retVal2"}
        )
        public static void Insertfix(@ByRef ArrayList<AbstractCard>[] retVal2){
            if(!AbstractDungeon.player.hasRelic(ShinyMushroom.ID)) return;

            List<AbstractCard> toRemove = new ArrayList<>();
            List<AbstractCard> toAdd = new ArrayList<>();

            // 很有深意的二维数组
            for (AbstractCard c : retVal2[0]) {
                if (c.cardsToPreview != null) {
                    logger.info("Card changed:" + c.name);
                    toRemove.add(c);
                    toAdd.add(c.cardsToPreview);
                    AbstractDungeon.player.gainGold(9);
                }
            }

            retVal2[0].removeAll(toRemove);
            retVal2[0].addAll(toAdd);

        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class,"makeCopy");
                int line = LineFinder.findInOrder(ctMethodToPatch, finalMatcher)[0]+1;
                return new int[]{line};
            }
        }
    }
}
