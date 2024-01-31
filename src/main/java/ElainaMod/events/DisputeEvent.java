package ElainaMod.events;

import ElainaMod.cards.IronWaveMagic;
import ElainaMod.cards.PerfectMagic;
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


public class DisputeEvent extends AbstractImageEvent {
    public static final String ID = "Elaina:Dispute";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String DIALOG_1 = DESCRIPTIONS[desc.INTRO.ordinal()];

    private enum CUR_SCREEN {
        INTRO,
        RESULT
    }
    private CUR_SCREEN screen = CUR_SCREEN.INTRO;
    private static enum desc {
        INTRO,

        LEFT_RESULT,

        RIGHT_RESULT,
        LEAVE_RESULT
    }

    private static enum opt {
        LEFT,
        RIGHT,
        LEAVE
    }
    public DisputeEvent(){
        super(NAME, DIALOG_1, "ElainaMod/img/events/NoMushroom_mushroom.png");

        this.imageEventText.setDialogOption(OPTIONS[opt.LEFT.ordinal()], CardLibrary.getCopy(IronWaveMagic.ID));
        this.imageEventText.setDialogOption(OPTIONS[opt.RIGHT.ordinal()], CardLibrary.getCopy(PerfectMagic.ID));
        this.imageEventText.setDialogOption(OPTIONS[opt.LEAVE.ordinal()]);

    }

    @Override
    protected void buttonEffect(int i) {
        switch (screen) {
            case INTRO:
                switch (i) {
                    case 0:
                        AbstractCard c2 = new IronWaveMagic();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCopy(c2.cardID),
                                ((float) Settings.WIDTH) / 2.0f, ((float) Settings.HEIGHT) / 2.0f));


                        screen = CUR_SCREEN.RESULT;
                        imageEventText.clearAllDialogs();
                        imageEventText.setDialogOption(OPTIONS[opt.LEAVE.ordinal()]); //“离开”
                        this.imageEventText.updateBodyText(DESCRIPTIONS[desc.LEFT_RESULT.ordinal()]);
                        this.imageEventText.loadImage("ElainaMod/img/events/Dispute_left.png");
                        return;
                    case 1:
                        AbstractCard c3 = new PerfectMagic();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCopy(c3.cardID),
                                ((float) Settings.WIDTH) / 2.0f, ((float) Settings.HEIGHT) / 2.0f));

                        screen = CUR_SCREEN.RESULT;
                        imageEventText.clearAllDialogs();
                        imageEventText.setDialogOption(OPTIONS[opt.LEAVE.ordinal()]); //“离开”
                        this.imageEventText.updateBodyText(DESCRIPTIONS[desc.RIGHT_RESULT.ordinal()]);
                        this.imageEventText.loadImage("ElainaMod/img/events/Dispute_right.png");
                        return;
                    case 2:
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

    @Override
    public void update() {
        super.update();
    }
}
