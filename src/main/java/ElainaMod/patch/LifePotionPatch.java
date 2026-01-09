package ElainaMod.patch;

import ElainaMod.Characters.ElainaC;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;


public class LifePotionPatch {
    public LifePotionPatch(){
    }
    public static final Logger logger = LogManager.getLogger(LifePotionPatch.class);
    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = "update"
    )
    public static class LifePotionDrawCard{
        @SpirePrefixPatch
        public static void Prefix(ApplyPowerAction a) throws NoSuchFieldException, IllegalAccessException {
            Class<?> ApplyPowerActionClass = ApplyPowerAction.class;
            Field powerField = ApplyPowerActionClass.getDeclaredField("powerToApply");
            powerField.setAccessible(true);
            AbstractPower po = (AbstractPower) powerField.get(a);

            if(     a.target!=null && a.target instanceof AbstractPlayer
                    && AbstractDungeon.player.hasRelic("Elaina:LifePotion")
                    && !a.target.hasPower(po.ID)){
                AbstractPlayer p = AbstractDungeon.player;
                int num = ((p.powers.size()+1) > BaseMod.MAX_HAND_SIZE?BaseMod.MAX_HAND_SIZE:(p.powers.size()+1)) - p.hand.size();
                logger.info("power size: "+p.powers.size()+1);
                logger.info("hand size: "+p.hand.size());
                if(num>0){
                    AbstractDungeon.actionManager.addToTop(new DrawCardAction(num));
                    AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(p, p.getRelic("Elaina:LifePotion")));
                }
            }

            powerField.setAccessible(false);
        }
    }
}
