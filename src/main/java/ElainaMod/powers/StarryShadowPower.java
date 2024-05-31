package ElainaMod.powers;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.GetDiaryCardAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StarryShadowPower extends AbstractPower {
    public static final String POWER_ID = "Elaina:StarryShadow";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(StarryShadowPower.class);
    public StarryShadowPower(AbstractCreature o, int num){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = num;
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/StarryShadowPower.png");
    }
    public void updateDescription(){this.description = DESCRIPTIONS[0]+ amount +DESCRIPTIONS[1];}
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        ElainaC p = (ElainaC) AbstractDungeon.player;
        for(int i = 0;i<this.amount-p.hand.size();i++){
            if(p.getConclusion()==null) break;
            this.addToBot(new GetDiaryCardAction(p));
        }
    }
}
