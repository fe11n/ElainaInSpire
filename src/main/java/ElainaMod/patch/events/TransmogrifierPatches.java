package ElainaMod.patch.events;

import ElainaMod.cardmods.toInstantCardMod;
import ElainaMod.cardmods.toInstantCardMod;
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
import com.megacrit.cardcrawl.events.shrines.Transmogrifier;
import com.megacrit.cardcrawl.events.shrines.Transmogrifier;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class TransmogrifierPatches {
    private static final UIStrings STRINGS;
    public static final String[] TEXT;
    public static final String[] OPTIONS;
    public static int myIndex;
    public static boolean choseMyOption;

    static toInstantCardMod augment;

    public static Object[] enumElements;
    private static final Logger logger = LogManager.getLogger(TransmogrifierPatches.class);

    public TransmogrifierPatches() {
    }

    static {
        STRINGS = CardCrawlGame.languagePack.getUIString("Elaina:TransmogrifierEvent");
        TEXT = STRINGS.TEXT;
        OPTIONS = STRINGS.EXTRA_TEXT;
        myIndex = -1;
        choseMyOption = false;
        enumElements = null;
    }

    @SpirePatch2(
            clz = Transmogrifier.class,
            method = "update"
    )
    public static class UpdateSnag {
        public UpdateSnag() {
        }

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<?> update(Transmogrifier __instance) {
            if (TransmogrifierPatches.choseMyOption) {
                if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    CardModifierManager.addModifier(c, new toInstantCardMod());
                    AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    AbstractEvent.logMetricCardUpgrade("Transmogrifier", "Imbue", c);
                    TransmogrifierPatches.choseMyOption = false;
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
            clz = Transmogrifier.class,
            method = "buttonEffect"
    )
    public static class ButtonLogic {
        public ButtonLogic() {
        }

        @SpirePrefixPatch
        public static SpireReturn<?> buttonPress(Transmogrifier __instance, @ByRef int[] buttonPressed, Object ___screen) {
            if (___screen == TransmogrifierPatches.enumElements[0]) {
                if (buttonPressed[0] == TransmogrifierPatches.myIndex + 1) {
                    buttonPressed[0] = 1;
                    return SpireReturn.Continue();
                }

                if (buttonPressed[0] == TransmogrifierPatches.myIndex) {
                    __instance.imageEventText.clearRemainingOptions();
                    __instance.imageEventText.updateBodyText(TransmogrifierPatches.TEXT[0]);
                    __instance.imageEventText.updateDialogOption(0, TransmogrifierPatches.OPTIONS[2]);
                    CardGroup group = new CardGroup(CardGroupType.UNSPECIFIED);
                    Iterator var4 = AbstractDungeon.player.masterDeck.group.iterator();

                    while(var4.hasNext()) {
                        AbstractCard c = (AbstractCard)var4.next();
                        if (augment.canApplyTo(c)) {
                            group.addToTop(c);
                        }
                    }

                    AbstractDungeon.gridSelectScreen.open(group, 1, TransmogrifierPatches.TEXT[1], false, false, false, false);
                    TransmogrifierPatches.choseMyOption = true;
                    ReflectionHacks.setPrivate(__instance, Transmogrifier.class, "screen", TransmogrifierPatches.enumElements[1]);
                    return SpireReturn.Return();
                }
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(
            clz = Transmogrifier.class,
            method = "<ctor>"
    )
    public static class EventInit {
        public EventInit() {
        }

        @SpirePostfixPatch
        public static void addOption(Transmogrifier __instance) {
            TransmogrifierPatches.augment = null;
            if (TransmogrifierPatches.enumElements == null) {
                try {
                    Class<?> enumElement = Class.forName(Transmogrifier.class.getName() + "$CUR_SCREEN");
                    if (enumElement.isEnum()) {
                        TransmogrifierPatches.enumElements = enumElement.getEnumConstants();
                    }
                } catch (Exception var2) {
                    var2.printStackTrace();
                }
            }
            TransmogrifierPatches.choseMyOption = false;
            TransmogrifierPatches.augment = new toInstantCardMod();
            __instance.imageEventText.clearRemainingOptions();
            TransmogrifierPatches.myIndex = __instance.imageEventText.optionList.size();
            if (AbstractDungeon.player.masterDeck.group.stream().anyMatch((c) -> {
                return TransmogrifierPatches.augment.canApplyTo(c);
            })) {
                __instance.imageEventText.setDialogOption(TransmogrifierPatches.OPTIONS[0]);
            } else {
                __instance.imageEventText.setDialogOption(TransmogrifierPatches.OPTIONS[1], true);
            }

            __instance.imageEventText.setDialogOption(TransmogrifierPatches.OPTIONS[2]);
        }
    }
}
