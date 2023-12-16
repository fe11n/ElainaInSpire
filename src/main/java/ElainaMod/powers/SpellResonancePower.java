package ElainaMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpellResonancePower extends AbstractPower {
    public static final String POWER_ID = "Elaina:SpellResonance";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(SpellResonancePower.class);
    public SpellResonancePower(AbstractCreature o){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.type = PowerType.BUFF;
        logger.info(DESCRIPTIONS[0]);
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/SpellResonancePower.png");
    }
    public void updateDescription(){this.description = DESCRIPTIONS[0];}
}
