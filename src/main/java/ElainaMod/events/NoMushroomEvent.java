package ElainaMod.events;

import ElainaMod.cards.WitnessOfFriendship;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class NoMushroomEvent extends AbstractImageEvent {
    public static final String ID = "Elaina:NoMushroom";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String DIALOG_1 = DESCRIPTIONS[desc.INTRO.ordinal()];
    private int healAmt;
    private enum CUR_SCREEN {
        INTRO,
        RESULT
    }
    private CUR_SCREEN screen = CUR_SCREEN.INTRO;
    private static enum desc {
        INTRO,

        DONT_EAT_RESULT,

        EAT_RESULT,

        SAYA_EAT_RESULT,
    }

    private static enum opt {
        DONT_EAT,
        EAT0,
        EAT1,
        SAYA_EAT,
        LEAVE
    }
    public NoMushroomEvent(){
        super(NAME, DIALOG_1, "ElainaMod/img/events/NoMushroom_mushroom.png");
        healAmt = AbstractDungeon.player.maxHealth / 2;

        this.imageEventText.setDialogOption(OPTIONS[opt.DONT_EAT.ordinal()]);
        this.imageEventText.setDialogOption(OPTIONS[opt.EAT0.ordinal()] + healAmt + OPTIONS[opt.EAT1.ordinal()]
                , CardLibrary.getCopy(Regret.ID));
        this.imageEventText.setDialogOption(OPTIONS[opt.SAYA_EAT.ordinal()],
                CardLibrary.getCopy(WitnessOfFriendship.ID));
        this.imageEventText.setDialogOption(OPTIONS[opt.LEAVE.ordinal()]);

    }

    @Override
    protected void buttonEffect(int i) {
        switch (screen) {
            case INTRO:
                switch (i) {
                    case 0:
                        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(
                                        AbstractDungeon.player.masterDeck.getPurgeableCards()),
                                1, OPTIONS[3], false, false, false,
                                true);

                        screen = CUR_SCREEN.RESULT;
                        imageEventText.clearAllDialogs();
                        imageEventText.setDialogOption(OPTIONS[opt.LEAVE.ordinal()]); //“离开”
                        this.imageEventText.updateBodyText(DESCRIPTIONS[desc.DONT_EAT_RESULT.ordinal()]);
                        this.imageEventText.loadImage("ElainaMod/img/events/NoMushroom_noeat.png");
                        return;
                    case 1:
                        AbstractDungeon.player.heal(healAmt);
                        AbstractEvent.logMetricHeal(ID, "Elaina:NoMushroom_Eat", healAmt);
                        AbstractCard c1 = new Regret();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCopy(c1.cardID),
                                (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                        screen = CUR_SCREEN.RESULT;
                        imageEventText.clearAllDialogs();
                        imageEventText.setDialogOption(OPTIONS[opt.LEAVE.ordinal()]); //“离开”
                        this.imageEventText.updateBodyText(DESCRIPTIONS[desc.EAT_RESULT.ordinal()]);
                        this.imageEventText.loadImage("ElainaMod/img/events/NoMushroom_eat.png");
                        return;
                    case 2:
                        AbstractCard c2 = new WitnessOfFriendship();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCopy(c2.cardID),
                                ((float) Settings.WIDTH) / 2.0f, ((float) Settings.HEIGHT) / 2.0f));

                        screen = CUR_SCREEN.RESULT;
                        imageEventText.clearAllDialogs();
                        imageEventText.setDialogOption(OPTIONS[opt.LEAVE.ordinal()]); //“离开”

                        this.imageEventText.updateBodyText(DESCRIPTIONS[desc.SAYA_EAT_RESULT.ordinal()]);
                        this.imageEventText.loadImage("ElainaMod/img/events/NoMushroom_sayaeat.png");
                }
            case RESULT:
                break;
            default:
                return;
        }
        if (i == 0) {
            openMap();
        }
    }

    @Override
    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            CardCrawlGame.sound.play("CARD_EXHAUST");
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) (Settings.WIDTH / 2),
                    (float) (Settings.HEIGHT / 2)));
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }
}
