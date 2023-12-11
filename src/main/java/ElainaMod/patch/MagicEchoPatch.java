package ElainaMod.patch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;


public class MagicEchoPatch {
    public MagicEchoPatch(){
    }
    public static boolean isTarget = false;
    public static final Logger logger = LogManager.getLogger(MagicEchoPatch.class);
    @SpirePatch(
            clz = MakeTempCardInHandAction.class,
            method = "<ctor>",
            paramtypez = {AbstractCard.class,int.class}
    )
    public static class MagicEchoPatchInHandCheck1{
        @SpirePrefixPatch
        public static void Prefix(MakeTempCardInHandAction a, AbstractCard c,int i){
            logger.info("isTarget changing");
            if(c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS){
                isTarget = false;
                logger.info("isTarget change to false");
            }
            else {
                isTarget = true;
            }
        }
    }
    @SpirePatch(
            clz = MakeTempCardInHandAction.class,
            method = "<ctor>",
            paramtypez = {AbstractCard.class,boolean.class}
    )
    public static class MagicEchoPatchInHandCheck2{
        @SpirePrefixPatch
        public static void Prefix(MakeTempCardInHandAction a, AbstractCard c,boolean b){
            logger.info("isTarget changing");
            if(c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS){
                isTarget = false;
                logger.info("isTarget change to false");
            }
            else {
                isTarget = true;
            }
        }
    }
    @SpirePatch(
            clz = MakeTempCardInHandAction.class,
            method = "update"
    )
    public static class MagicEchoPatchInHand{
        @SpirePrefixPatch
        public static void Prefix(MakeTempCardInHandAction a){
            logger.info("isTarget now: "+isTarget);
            if(AbstractDungeon.player.hasPower("Elaina:MagicEcho") && isTarget){
                a.amount+=AbstractDungeon.player.getPower("Elaina:MagicEcho").amount;
            }
        }
    }

//    @SpirePatch(
//            clz = MakeTempCardInDrawPileAction.class,
//            method = "update"
//    )
//    public static class MagicEchoPatchInDrawPile{
//        @SpirePrefixPatch
//        public static void Prefix(MakeTempCardInDrawPileAction a){
//            if(AbstractDungeon.player.hasPower("Elaina:MagicEcho")){
//                a.amount+=1;
//            }
//        }
//    }

}
