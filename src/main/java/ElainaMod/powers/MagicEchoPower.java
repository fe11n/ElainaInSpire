package ElainaMod.powers;

import ElainaMod.cards.AbstractElainaCard;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class MagicEchoPower extends AbstractPower {
    public static final String POWER_ID = "Elaina:MagicEcho";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MagicEchoPower.class);
    public int cardsDoubledThisTurn = 0;
    public MagicEchoPower(AbstractCreature o){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = 1;
        this.type = PowerType.BUFF;
        logger.info(DESCRIPTIONS[0]);
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/MagicEchoPower.png");
    }
    public void atStartOfTurnPostDraw() {
        cardsDoubledThisTurn = 0;
    }

    public boolean isAble(){
        if(cardsDoubledThisTurn<amount){
            cardsDoubledThisTurn++;
            logger.info("Echo Ok!");
            return true;
        }
        logger.info("Echo False!");
        return false;
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
}
