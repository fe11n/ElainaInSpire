package ElainaMod.relics;

import ElainaMod.cardmods.toRetainCardMod;
import ElainaMod.cards.AbstractElainaCard;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GreedyDoll extends CustomRelic {
    public static final String ID = "Elaina:GreedyDoll";
    public static final Logger logger = LogManager.getLogger(GreedyDoll.class);

    private static final int NUM_TURNS = 4;
    public GreedyDoll() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/GreedyDoll.png"), RelicTier.SHOP, LandingSound.FLAT);
    }
    public void onEquip() {
        this.counter = 0;
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    public void atTurnStartPostDraw() {
        if (this.counter == -1) {
            this.counter += 2;
        } else {
            ++this.counter;
        }
        if (this.counter == NUM_TURNS) {
            ++this.counter;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractPlayer p = AbstractDungeon.player;
                    for(AbstractCard c : p.hand.group){
                        logger.info("(1)Card: "+ c.name);
                        if(!c.selfRetain){
                            logger.info("(2)Card: "+ c.name);
                            c.flash();
                            CardModifierManager.addModifier(c,new toRetainCardMod());
                        }
                    }
                    this.isDone = true;
                }
            });
        }
    }
}
