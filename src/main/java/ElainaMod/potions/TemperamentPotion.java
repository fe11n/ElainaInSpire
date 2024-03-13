//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ElainaMod.potions;

import ElainaMod.cards.Accelerate;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

public class TemperamentPotion extends AbstractPotion {
    public static final String POTION_ID = "Elaina:TemperamentPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Elaina:TemperamentPotion");

    public TemperamentPotion() {
        super(potionStrings.NAME, "Elaina:TemperamentPotion", PotionRarity.UNCOMMON, PotionSize.FAIRY, PotionColor.GREEN);
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
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            this.addToBot(new MakeTempCardInHandAction(tempc.makeStatEquivalentCopy(), this.potency));
        }
    }

    public int getPotency(int ascensionLevel) {
        return 3;
    }

    public AbstractPotion makeCopy() {
        return new TemperamentPotion();
    }
}
