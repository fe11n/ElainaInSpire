package ElainaMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class SpellReorganizationPower extends AbstractPower {
    public static final String POWER_ID = "Elaina:SpellReorganization";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(SpellReorganizationPower.class);
    public SpellReorganizationPower(AbstractCreature o,int magicNum){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = magicNum;
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/SpellReorganizationPower.png");
    }
    public void updateDescription(){this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];}

    public void atStartOfTurn(){
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.selfRetain == true || c.retain == true) {
                this.addToBot(new GainBlockAction(owner, owner, this.amount));
            }
        }
    }
}
