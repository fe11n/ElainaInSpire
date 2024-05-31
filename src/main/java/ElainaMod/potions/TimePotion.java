//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ElainaMod.potions;

import ElainaMod.cards.Accelerate;
import ElainaMod.powers.SpellBoostPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class TimePotion extends AbstractPotion {
    public static final String POTION_ID = "Elaina:TimePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Elaina:TimePotion");

    public TimePotion() {
        super(potionStrings.NAME, "Elaina:TimePotion", PotionRarity.RARE, PotionSize.S, PotionColor.ENERGY);
        this.isThrown = false;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        this.description = potionStrings.DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            this.addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
            this.addToBot(new SkipEnemiesTurnAction());
            this.addToBot(new PressEndTurnButtonAction());
        }
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new TimePotion();
    }
}
