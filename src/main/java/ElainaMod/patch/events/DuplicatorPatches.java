package ElainaMod.patch.events;

import ElainaMod.cardmods.toImageCardMod;
import ElainaMod.cardmods.toShorthandCardMod;
import ElainaMod.cards.AbstractElainaCard;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.shrines.Duplicator;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.Iterator;
import javassist.CtBehavior;

public class DuplicatorPatches {
    private static final UIStrings STRINGS;
    public static final String[] TEXT;
    public static final String[] OPTIONS;
    public static int myIndex;
    public static boolean choseMyOption;

    static toShorthandCardMod augment;

    public DuplicatorPatches() {
    }

    static {
        STRINGS = CardCrawlGame.languagePack.getUIString("Elaina:DuplicatorEvent");
        TEXT = STRINGS.TEXT;
        OPTIONS = STRINGS.EXTRA_TEXT;
        myIndex = -1;
        choseMyOption = false;

    }

    @SpirePatch2(
            clz = Duplicator.class,
            method = "update"
    )
    public static class UpdateSnag {
        public UpdateSnag() {
        }

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<?> update(Duplicator __instance) {
            if (DuplicatorPatches.choseMyOption) {
                if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    CardModifierManager.addModifier(c, new toShorthandCardMod());
                    AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    AbstractEvent.logMetricCardUpgrade("Duplicator", "Imbue", c);
                    DuplicatorPatches.choseMyOption = false;
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
            clz = Duplicator.class,
            method = "buttonEffect"
    )
    public static class ButtonLogic {
        public ButtonLogic() {
        }

        @SpirePrefixPatch
        public static SpireReturn<?> buttonPress(Duplicator __instance, @ByRef int[] buttonPressed, @ByRef int[] ___screenNum) {
            if (___screenNum[0] == 0) {
                if (buttonPressed[0] == DuplicatorPatches.myIndex + 1) {
                    buttonPressed[0] = 1;
                    return SpireReturn.Continue();
                }

                if (buttonPressed[0] == DuplicatorPatches.myIndex) {
                    __instance.imageEventText.clearRemainingOptions();
                    __instance.imageEventText.updateBodyText(DuplicatorPatches.TEXT[0]);
                    __instance.imageEventText.updateDialogOption(0, DuplicatorPatches.OPTIONS[2]);
                    CardGroup group = new CardGroup(CardGroupType.UNSPECIFIED);
                    Iterator var4 = AbstractDungeon.player.masterDeck.group.iterator();

                    while(var4.hasNext()) {
                        AbstractCard c = (AbstractCard)var4.next();
                        if (augment.canApplyTo(c)) {
                            group.addToTop(c);
                        }
                    }

                    AbstractDungeon.gridSelectScreen.open(group, 1, DuplicatorPatches.TEXT[1], false, false, false, false);
                    DuplicatorPatches.choseMyOption = true;
                    ___screenNum[0] = 2;
                    return SpireReturn.Return();
                }
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(
            clz = Duplicator.class,
            method = "<ctor>"
    )
    public static class EventInit {
        public EventInit() {
        }

        @SpirePostfixPatch
        public static void addOption(Duplicator __instance) {
            DuplicatorPatches.choseMyOption = false;
            DuplicatorPatches.augment = new toShorthandCardMod();
            __instance.imageEventText.clearRemainingOptions();
            DuplicatorPatches.myIndex = __instance.imageEventText.optionList.size();
            if (AbstractDungeon.player.masterDeck.group.stream().anyMatch((c) -> {
                return DuplicatorPatches.augment.canApplyTo(c);
            })) {
                __instance.imageEventText.setDialogOption(DuplicatorPatches.OPTIONS[0]);
            } else {
                __instance.imageEventText.setDialogOption(DuplicatorPatches.OPTIONS[1], true);
            }

            __instance.imageEventText.setDialogOption(DuplicatorPatches.OPTIONS[2]);
        }
    }
}
