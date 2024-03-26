package ElainaMod.powers;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.MagicDiffusionAction;
import ElainaMod.cardmods.toInstantCardMod;
import ElainaMod.cards.*;
import ElainaMod.orb.ConclusionOrb;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class MagicDiffusionPower extends AbstractPower {
    public static final String POWER_ID = "Elaina:MagicDiffusion";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    ;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MagicDiffusionPower.class);

    public MagicDiffusionPower(AbstractCreature o, int num) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = num;
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/MagicDiffusionPower.png");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void atStartOfTurnPostDraw() {
        if(((ElainaC)owner).getConclusion()!=null && ElainaC.isInstant(((ElainaC)owner).getConclusion())){
            this.flash();
            this.addToBot(new MagicDiffusionAction((AbstractPlayer) owner, amount));
        }
    }
}
