package ElainaMod.powers;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.GetDiaryCardAction;
import ElainaMod.cards.WizardsWell;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BestStatePower extends AbstractPower {
    public static final String POWER_ID = "Elaina:BestState";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(BestStatePower.class);
    public BestStatePower(AbstractCreature o){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = 1;
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/BestStatePower.png");
    }
    public void updateDescription(){this.description = DESCRIPTIONS[0]+ amount + DESCRIPTIONS[1];}
    public void onUseCard(AbstractCard card, UseCardAction action){
        if(card.hasTag(ElainaC.Enums.SEASONAL)){
            this.addToBot(new ReducePowerAction(owner,owner,"Elaina:BestState",1));
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    ((ElainaC)owner).UpdateAllSeasonalDescription();
                    this.isDone = true;
                }
            });
        }
    }
}
