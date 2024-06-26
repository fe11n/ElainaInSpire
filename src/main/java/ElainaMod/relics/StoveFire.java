package ElainaMod.relics;

import ElainaMod.powers.MagicResiduePower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class StoveFire extends CustomRelic {
    public static final String ID = "Elaina:StoveFire";
    public StoveFire() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/StoveFire.png"), RelicTier.UNCOMMON, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        this.flash();
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            if (!m.isDead && !m.isDying) {
                this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new MagicResiduePower(m, AbstractDungeon.player, 3)));
            }
        }

    }

}
