package ElainaMod.powers;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.MagicDiffusionAction;
import ElainaMod.cards.Ignite;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IgnisFatuusPower extends AbstractPower {
    public static final String POWER_ID = "Elaina:IgnisFatuus";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    ;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(IgnisFatuusPower.class);

    public IgnisFatuusPower(AbstractCreature o, int num) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = num;
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/IgnisFatuusPower.png");
    }

    public void updateDescription() {
        if(amount==1){
            this.description = DESCRIPTIONS[2];
        }else {
            this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
        }
    }

    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            this.addToBot(new MakeTempCardInHandAction(new Ignite(),this.amount));
        }
    }
}
