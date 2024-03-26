//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ElainaMod.potions;

import ElainaMod.cards.Accelerate;
import ElainaMod.powers.SpellBoostPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

public class TimePotion extends AbstractPotion {
    public static final String POTION_ID = "Elaina:TimePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Elaina:TimePotion");

    public TimePotion() {
        super(potionStrings.NAME, "Elaina:TimePotion", PotionRarity.RARE, PotionSize.S, PotionColor.ENERGY);
        this.isThrown = false;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        AbstractCard tempc = new Accelerate();
        tempc.setCostForTurn(0);
        tempc.upgrade();
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            this.addToBot(new MakeTempCardInHandAction(tempc.makeStatEquivalentCopy(), this.potency));
        }
    }

    public int getPotency(int ascensionLevel) {
        return 3;
    }

    public AbstractPotion makeCopy() {
        return new TimePotion();
    }
}
