package ElainaMod.powers;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.GetDiaryCardAction;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class ContinuationPreparationPower extends AbstractPower {
    public static final String POWER_ID = "Elaina:ContinuationPreparation";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(ContinuationPreparationPower.class);
    public ContinuationPreparationPower(AbstractCreature o, int num){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = num;
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/ContinuationPreparationPower.png");
    }
    public void updateDescription(){this.description = DESCRIPTIONS[0]+ amount +DESCRIPTIONS[1];}
    public void atStartOfTurn() {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new ContinuationPower(this.owner, 6)));
        this.addToBot(new ReducePowerAction(this.owner, this.owner, ContinuationPreparationPower.POWER_ID, 1));
    }
}
