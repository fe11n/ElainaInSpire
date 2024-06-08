package ElainaMod.powers;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.ChangeMonthAction;
import ElainaMod.action.GetDiaryCardAction;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeGoesBackPower extends AbstractPower {
    public static final String POWER_ID = "Elaina:TimeGoesBack";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(TimeGoesBackPower.class);
    public TimeGoesBackPower(AbstractCreature o,int num){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = num;
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/TimeGoesBackPower.png");
    }
    public void updateDescription(){
        if(amount==1){
            this.description = DESCRIPTIONS[2];
        }else {
            this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
        }
    }
    public void onEnergyRecharge() {
        this.flash();
        if (!(owner instanceof ElainaC))
            return;
        this.addToBot(new ChangeMonthAction((ElainaC)owner,1));
        ElainaC p = (ElainaC)AbstractDungeon.player;
        if(p.getDiarySize()<=0){
        }
        else if(p.getDiarySize()==1){
            p.getConclusion().setCostForTurn(0);
            addToBot(new GetDiaryCardAction(p));
        }
        else {
            int a1 = AbstractDungeon.cardRandomRng.random(p.DiaryGroup.size()-1);
            int r = AbstractDungeon.cardRandomRng.random(p.DiaryGroup.size()-2);
            int a2 = (a1 + r + 1) % p.DiaryGroup.size();
            p.DiaryGroup.group.get(a1).setCostForTurn(0);
            p.DiaryGroup.group.get(a2).setCostForTurn(0);
            addToBot(new GetDiaryCardAction(p,true,p.DiaryGroup.group.get(a1)));
            addToBot(new GetDiaryCardAction(p,true,p.DiaryGroup.group.get(a2)));
        }
        if (this.amount <= 1) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Elaina:TimeGoesBack"));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, "Elaina:TimeGoesBack", 1));
        }

    }
}
