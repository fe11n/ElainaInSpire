package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.powers.ResidualMagicPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
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
                this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new ResidualMagicPower(m, AbstractDungeon.player, 3)));
            }
        }

    }

}
