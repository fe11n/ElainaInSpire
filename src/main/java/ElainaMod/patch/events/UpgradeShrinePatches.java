package ElainaMod.patch.events;

import ElainaMod.cardmods.toRetainCardMod;
import ElainaMod.cardmods.toShorthandCardMod;
import ElainaMod.patch.HealthBarPatch;
import basemod.ReflectionHacks;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.shrines.UpgradeShrine;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class UpgradeShrinePatches {
    private static final UIStrings STRINGS;
    public static final String[] TEXT;
    public static final String[] OPTIONS;
    public static int myIndex;
    public static boolean choseMyOption;

    static toRetainCardMod augment;

    public static Object[] enumElements;
    private static final Logger logger = LogManager.getLogger(UpgradeShrinePatches.class);

    public UpgradeShrinePatches() {
    }

    static {
        STRINGS = CardCrawlGame.languagePack.getUIString("Elaina:UpgradeShrineEvent");
        TEXT = STRINGS.TEXT;
        OPTIONS = STRINGS.EXTRA_TEXT;
        myIndex = -1;
        choseMyOption = false;
        enumElements = null;
    }

    @SpirePatch2(
            clz = UpgradeShrine.class,
            method = "update"
    )
    public static class UpdateSnag {
        public UpdateSnag() {
        }

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<?> update(UpgradeShrine __instance) {
            if (UpgradeShrinePatches.choseMyOption) {
                if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    CardModifierManager.addModifier(c, new toRetainCardMod());
                    AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    AbstractEvent.logMetricCardUpgrade("UpgradeShrine", "Imbue", c);
                    UpgradeShrinePatches.choseMyOption = false;
                }

                return SpireReturn.Return();
            } else {
                return SpireReturn.Continue();
            }
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }

            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "isScreenUp");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(
            clz = UpgradeShrine.class,
            method = "buttonEffect"
    )
    public static class ButtonLogic {
        public ButtonLogic() {
        }

        @SpirePrefixPatch
        public static SpireReturn<?> buttonPress(UpgradeShrine __instance, @ByRef int[] buttonPressed, Object ___screen) {
            if (___screen == UpgradeShrinePatches.enumElements[0]) {
                if (buttonPressed[0] == UpgradeShrinePatches.myIndex + 1) {
                    buttonPressed[0] = 1;
                    return SpireReturn.Continue();
                }

                if (buttonPressed[0] == UpgradeShrinePatches.myIndex) {
                    __instance.imageEventText.clearRemainingOptions();
                    __instance.imageEventText.updateBodyText(UpgradeShrinePatches.TEXT[0]);
                    __instance.imageEventText.updateDialogOption(0, UpgradeShrinePatches.OPTIONS[2]);
                    CardGroup group = new CardGroup(CardGroupType.UNSPECIFIED);
                    Iterator var4 = AbstractDungeon.player.masterDeck.group.iterator();

                    while(var4.hasNext()) {
                        AbstractCard c = (AbstractCard)var4.next();
                        if (augment.canApplyTo(c)) {
                            group.addToTop(c);
                        }
                    }

                    AbstractDungeon.gridSelectScreen.open(group, 1, UpgradeShrinePatches.TEXT[1], false, false, false, false);
                    UpgradeShrinePatches.choseMyOption = true;
                    ReflectionHacks.setPrivate(__instance, UpgradeShrine.class, "screen", UpgradeShrinePatches.enumElements[1]);
                    return SpireReturn.Return();
                }
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(
            clz = UpgradeShrine.class,
            method = "<ctor>"
    )
    public static class EventInit {
        public EventInit() {
        }

        @SpirePostfixPatch
        public static void addOption(UpgradeShrine __instance) {
            UpgradeShrinePatches.augment = null;
            if (UpgradeShrinePatches.enumElements == null) {
                try {
                    Class<?> enumElement = Class.forName(UpgradeShrine.class.getName() + "$CUR_SCREEN");
                    if (enumElement.isEnum()) {
                        UpgradeShrinePatches.enumElements = enumElement.getEnumConstants();
                    }
                } catch (Exception var2) {
                    var2.printStackTrace();
                }
            }
            UpgradeShrinePatches.choseMyOption = false;
            UpgradeShrinePatches.augment = new toRetainCardMod();
            __instance.imageEventText.clearRemainingOptions();
            UpgradeShrinePatches.myIndex = __instance.imageEventText.optionList.size();
            if (AbstractDungeon.player.masterDeck.group.stream().anyMatch((c) -> {
                return UpgradeShrinePatches.augment.canApplyTo(c);
            })) {
                __instance.imageEventText.setDialogOption(UpgradeShrinePatches.OPTIONS[0]);
            } else {
                __instance.imageEventText.setDialogOption(UpgradeShrinePatches.OPTIONS[1], true);
            }

            __instance.imageEventText.setDialogOption(UpgradeShrinePatches.OPTIONS[2]);
        }
    }
}
