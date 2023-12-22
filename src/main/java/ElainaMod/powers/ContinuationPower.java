package ElainaMod.powers;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.GetDiaryCardAction;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class ContinuationPower extends AbstractPower {
    public static final String POWER_ID = "Elaina:Continuation";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(ContinuationPower.class);
    public ContinuationPower(AbstractCreature o,int num){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = num;
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/ContinuationPower.png");
    }
    public void updateDescription(){this.description = DESCRIPTIONS[0]+ amount +DESCRIPTIONS[1];}
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(CardModifierManager.hasModifier(card,"toImageCardMod")){
            this.flash();
            this.addToBot(new GetDiaryCardAction((ElainaC) AbstractDungeon.player));
        }
    }
    public void atEndOfTurn(boolean isPlayer) {
        int sum = 0;
        Iterator it = ((AbstractPlayer)owner).hand.group.iterator();
        while (it.hasNext()){
            AbstractCard c = (AbstractCard) it.next();
            if(CardModifierManager.hasModifier(c,"toImageCardMod")){
                sum++;
            }
        }
        sum*=amount;
        it = ((AbstractPlayer)owner).drawPile.group.iterator();
        while (sum>0 && it.hasNext()){
            AbstractCard c = (AbstractCard) it.next();
            if(c.canUpgrade()){
                c.upgrade();
                sum--;
            }
        }
        it = ((AbstractPlayer)owner).hand.group.iterator();
        while (sum>0 && it.hasNext()){
            AbstractCard c = (AbstractCard) it.next();
            if(c.canUpgrade()){
                c.upgrade();
                sum--;
            }
        }
        it = ((AbstractPlayer)owner).discardPile.group.iterator();
        while (sum>0 && it.hasNext()){
            AbstractCard c = (AbstractCard) it.next();
            if(c.canUpgrade()){
                c.upgrade();
                sum--;
            }
        }
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Elaina:Continuation"));
    }
}
