//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ElainaMod.potions;

import ElainaMod.powers.ResidualMagicPower;
import ElainaMod.powers.SpellBoostPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

public class FlashPotion extends AbstractPotion {
    public static final String POTION_ID = "Elaina:FlashPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Elaina:FlashPotion");

    public FlashPotion() {
        super(potionStrings.NAME, "Elaina:FlashPotion", PotionRarity.COMMON, PotionSize.FAIRY, PotionColor.GREEN);
        this.isThrown = true;
        this.targetRequired = true;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            this.addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new ResidualMagicPower(target, AbstractDungeon.player,this.potency), this.potency));
        }
    }

    public int getPotency(int ascensionLevel) {
        return 5;
    }

    public AbstractPotion makeCopy() {
        return new FlashPotion();
    }
}
