package ElainaMod.powers;

import ElainaMod.Characters.ElainaC;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProtectiveMagicPower extends AbstractPower {
    public static final String POWER_ID = "Elaina:ConvergenceMagic";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(ProtectiveMagicPower.class);
    public ProtectiveMagicPower(AbstractCreature o, int num){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = num;
        this.type = PowerType.BUFF;
        logger.info(DESCRIPTIONS[0]);
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/ProtectiveMagicPower.png");
    }
    public void updateDescription(){this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];}
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.hasTag(ElainaC.Enums.MAGIC)){
            this.flash();
            this.addToBot(new GainBlockAction(owner,amount));
        }
    }
    public void atEndOfTurn(boolean isPlayer) {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Elaina:ProtectiveMagic"));
    }
}
