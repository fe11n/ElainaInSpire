package ElainaMod.powers;

import ElainaMod.action.MagicDiffusionAction;
import ElainaMod.action.SelfDefenseAction;
import ElainaMod.orb.ConclusionOrb;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SelfDefensePower extends AbstractPower {
    public static final String POWER_ID = "Elaina:SelfDefense";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    ;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(SelfDefensePower.class);

    public SelfDefensePower(AbstractCreature o, int num) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = num;
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/SelfDefensePower.png");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(CardModifierManager.hasModifier(card,"toImageCardMod")){
            addToBot(new GainBlockAction(owner,amount));
        }
    }

    public void atStartOfTurnPostDraw() {
        this.flash();
        this.addToBot(new SelfDefenseAction((AbstractPlayer) owner));
    }
}
