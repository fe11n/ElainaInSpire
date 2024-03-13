package ElainaMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MagicResiduePower extends AbstractPower {
    public static final String POWER_ID = "Elaina:MagicResidue";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    ;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MagicResiduePower.class);
    private AbstractCreature source;

    public MagicResiduePower(AbstractCreature o, AbstractCreature s , int num) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.source = s;
        this.amount = num;
        this.type = PowerType.DEBUFF;
        if (this.amount >= 9999) {
            this.amount = 9999;
        }
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/MagicResiduePower.png");
        this.isTurnBased = true;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
                && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            AbstractPlayer p = (AbstractPlayer)this.source;//这里联机的时候可能出问题？
            int m = 0;
            for(AbstractCard c:p.hand.group){
                if(c.selfRetain){
                    m++;
                }
            }
            if(m>0){
                this.addToBot(new PoisonLoseHpAction(this.owner, this.source,
                        this.amount*m, AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("ATTACK_MAGIC_FAST_1", 0.05f);
    }
}
