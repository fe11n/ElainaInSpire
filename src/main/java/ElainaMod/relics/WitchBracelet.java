package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.powers.SpellBoostPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class WitchBracelet extends CustomRelic {
    public static final String ID = "Elaina:WitchBracelet";
    public WitchBracelet() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/WitchBracelet.png"), RelicTier.BOSS, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        AbstractPlayer p = AbstractDungeon.player;
        if(targetCard.type == AbstractCard.CardType.SKILL){
            this.addToBot(new ApplyPowerAction(p,p,new SpellBoostPower(p,2)));
            this.addToTop(new RelicAboveCreatureAction(p, this));
        }
    }

}
