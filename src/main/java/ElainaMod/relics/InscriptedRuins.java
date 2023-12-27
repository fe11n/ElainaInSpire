package ElainaMod.relics;

import ElainaMod.powers.SpellBoostPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class InscriptedRuins extends CustomRelic {
    public static final String ID = "Elaina:InscriptedRuins";
    private boolean triggeredThisTurn = false;
    public InscriptedRuins() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/InscriptedRuins.png"), RelicTier.BOSS, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    public void atTurnStart() {
        this.triggeredThisTurn = false;
    }
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        AbstractPlayer p = AbstractDungeon.player;
        if(!this.triggeredThisTurn && targetCard.exhaust){
            this.addToBot(new GainEnergyAction(1));
            this.triggeredThisTurn = true;
            this.addToTop(new RelicAboveCreatureAction(p, this));
        }
    }

}
