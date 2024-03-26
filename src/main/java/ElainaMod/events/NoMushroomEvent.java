package ElainaMod.events;

import ElainaMod.cards.Nausea;
import ElainaMod.cards.WitnessOfFriendship;
import ElainaMod.relics.ShinyMushroom;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.OddMushroom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


public class NoMushroomEvent extends AbstractImageEvent {
    public static final String ID = "Elaina:NoMushroom";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String DIALOG_1 = DESCRIPTIONS[desc.INTRO.ordinal()];
    private int healAmt;
    private int damage = 4; // 采栽蘑菇失去的生命值

    private enum CUR_SCREEN {
        INTRO,
        RESULT
    }
    private CUR_SCREEN screen = CUR_SCREEN.INTRO;
    private static enum desc {
        INTRO,


        EAT_RESULT,

        PICK_RESULT,
        GROW_RESULT,
        LEAVE_RESULT
    }

    private static enum opt {
        EAT0,
        EAT1,
        PICK,
        GROW,
        GROW_LOCK_RELIC,
        GROW_LOCK_UPGRADE,
        LEAVE
    }
    public NoMushroomEvent(){
        super(NAME, DIALOG_1, "ElainaMod/img/events/NoMushroom_noeat.png");
        healAmt = AbstractDungeon.player.maxHealth / 4;

        this.imageEventText.setDialogOption(OPTIONS[opt.EAT0.ordinal()] + healAmt + OPTIONS[opt.EAT1.ordinal()]
                , CardLibrary.getCopy(Nausea.ID));
        this.imageEventText.setDialogOption(OPTIONS[opt.PICK.ordinal()], RelicLibrary.getRelic(ShinyMushroom.ID));

        if (AbstractDungeon.player.hasRelic(OddMushroom.ID)) {
            if (AbstractDungeon.player.masterDeck.hasUpgradableCards()) {
                this.imageEventText.setDialogOption(OPTIONS[opt.GROW.ordinal()], RelicLibrary.getRelic(OddMushroom.ID));
            } else {
                this.imageEventText.setDialogOption(OPTIONS[opt.GROW_LOCK_UPGRADE.ordinal()], true);
            }
        } else {
            this.imageEventText.setDialogOption(OPTIONS[opt.GROW_LOCK_RELIC.ordinal()],true);
        }
        this.imageEventText.setDialogOption(OPTIONS[opt.LEAVE.ordinal()]);

    }

    @Override
    protected void buttonEffect(int i) {
        switch (screen) {
            case INTRO:
                switch (i) {
                    case 0:
                        AbstractDungeon.player.heal(healAmt);
                        AbstractEvent.logMetricHeal(ID, "Elaina:NoMushroom_Eat", healAmt);
                        AbstractCard c1 = new Nausea();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCopy(c1.cardID),
                                (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                        screen = CUR_SCREEN.RESULT;
                        imageEventText.clearAllDialogs();
                        imageEventText.setDialogOption(OPTIONS[opt.LEAVE.ordinal()]); //“离开”
                        this.imageEventText.updateBodyText(DESCRIPTIONS[desc.EAT_RESULT.ordinal()]);
                        this.imageEventText.loadImage("ElainaMod/img/events/NoMushroom_noeat.png");
                        return;
                    case 1:
                        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, this.damage));
                        AbstractRelic r = new ShinyMushroom();
                        logMetricObtainRelicAndDamage(ID, "PICK",r, this.damage);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);

                        screen = CUR_SCREEN.RESULT;
                        imageEventText.clearAllDialogs();
                        imageEventText.setDialogOption(OPTIONS[opt.LEAVE.ordinal()]); //“离开”
                        this.imageEventText.updateBodyText(DESCRIPTIONS[desc.PICK_RESULT.ordinal()]);
                        this.imageEventText.loadImage("ElainaMod/img/events/NoMushroom_noeat.png");
                        return;
                    case 2:
                        AbstractDungeon.player.loseRelic(OddMushroom.ID);
                        upgradeCards();

                        screen = CUR_SCREEN.RESULT;
                        imageEventText.clearAllDialogs();
                        imageEventText.setDialogOption(OPTIONS[opt.LEAVE.ordinal()]); //“离开”
                        this.imageEventText.updateBodyText(DESCRIPTIONS[desc.GROW_RESULT.ordinal()]);
                        this.imageEventText.loadImage("ElainaMod/img/events/NoMushroom_noeat.png");
                        return;
                    case 3:
                        screen = CUR_SCREEN.RESULT;
                        logMetricIgnored(ID);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[desc.LEAVE_RESULT.ordinal()]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[opt.LEAVE.ordinal()]);
                        break;
                }
                this.imageEventText.clearRemainingOptions();
                return;
            case RESULT:
                break;
            default:
                return;
        }
        if (i == 0) {
            openMap();
        }
    }

    private void upgradeCards() {
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(((float) Settings.WIDTH) / 2.0f, ((float) Settings.HEIGHT) / 2.0f));
        ArrayList<AbstractCard> upgradableCards = new ArrayList<>();
        Iterator<AbstractCard> it = AbstractDungeon.player.masterDeck.group.iterator();
        while (it.hasNext()) {
            AbstractCard c = it.next();
            if (c.canUpgrade()) {
                upgradableCards.add(c);
            }
        }
        List<String> cardMetrics = new ArrayList<>();
        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if (!upgradableCards.isEmpty()) {
            if (upgradableCards.size() == 1) {
                upgradableCards.get(0).upgrade();
                cardMetrics.add(upgradableCards.get(0).cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy()));
            } else if (upgradableCards.size() == 2) {
                upgradableCards.get(0).upgrade();
                upgradableCards.get(1).upgrade();
                cardMetrics.add(upgradableCards.get(0).cardID);
                cardMetrics.add(upgradableCards.get(1).cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(1));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy(), (((float) Settings.WIDTH) / 2.0f) - (190.0f * Settings.scale), ((float) Settings.HEIGHT) / 2.0f));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(1).makeStatEquivalentCopy(), (((float) Settings.WIDTH) / 2.0f) + (190.0f * Settings.scale), ((float) Settings.HEIGHT) / 2.0f));
            } else {
                upgradableCards.get(0).upgrade();
                upgradableCards.get(1).upgrade();
                upgradableCards.get(2).upgrade();
                cardMetrics.add(upgradableCards.get(0).cardID);
                cardMetrics.add(upgradableCards.get(1).cardID);
                cardMetrics.add(upgradableCards.get(2).cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(1));
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(2));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy(), (((float) Settings.WIDTH) / 2.0f) - (380.0f * Settings.scale), ((float) Settings.HEIGHT) / 2.0f));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(1).makeStatEquivalentCopy(), (((float) Settings.WIDTH) / 2.0f) + (0.0f * Settings.scale), ((float) Settings.HEIGHT) / 2.0f));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(2).makeStatEquivalentCopy(), (((float) Settings.WIDTH) / 2.0f) + (380.0f * Settings.scale), ((float) Settings.HEIGHT) / 2.0f));
            }
        }
        AbstractEvent.logMetric(ID, "GROW", null, null, null, cardMetrics, null, null, null, this.damage, 0, 0, 0, 0, 0);
    }


    @Override
    public void update() {
        super.update();
    }
}
