package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cardmods.toInstantCardMod;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.powers.TimeGoesBackPower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.CollectPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class TimeGoesBackAction extends AbstractGameAction {
    public AbstractPlayer p;
    public AbstractElainaCard c;
    public ArrayList<AbstractElainaCard> g;
    public static final Logger logger = LogManager.getLogger(TimeGoesBackAction.class);
    private int amount;
    private boolean upgraded;
    private boolean freeToPlayOnce = false;
    public TimeGoesBackAction(AbstractPlayer p, int num,boolean upgraded,boolean freeToPlayOnce){
       this.p = p;
       this.amount = num;
       this.upgraded = upgraded;
       this.duration = Settings.ACTION_DUR_XFAST;
       this.actionType = ActionType.CARD_MANIPULATION;
       this.freeToPlayOnce = freeToPlayOnce;
    }
    @Override
    public void update(){
        int effect = EnergyPanel.totalCount;
        if (this.amount != -1) {
            effect = this.amount;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if(effect > 0){
            if (p instanceof ElainaC)
                this.addToBot(new ChangeMonthAction((ElainaC) p,effect,true));
        }

        if (this.upgraded) {
            ++effect;
        }

        if (effect > 0) {
            this.addToBot(new ApplyPowerAction(this.p, this.p, new TimeGoesBackPower(this.p, effect), effect));
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}
