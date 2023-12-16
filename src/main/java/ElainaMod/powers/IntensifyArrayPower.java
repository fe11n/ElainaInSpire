package ElainaMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class IntensifyArrayPower extends AbstractPower {
    public static final String POWER_ID = "Elaina:IntensifyArray";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public IntensifyArrayPower(AbstractCreature o){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/IntensifyArrayPower.png");
    }
    public void updateDescription(){this.description = DESCRIPTIONS[0];}
    public void atStartOfTurn(){
        this.addToBot(new GainEnergyAction(1));
        this.addToBot(new DrawCardAction(1));
        AbstractMonster mo = AbstractDungeon.getRandomMonster();
        this.addToBot(new ApplyPowerAction(mo,owner,new StrengthPower(mo, 1), 1));
    }
}
