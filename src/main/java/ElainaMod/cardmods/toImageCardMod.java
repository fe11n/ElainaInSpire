package ElainaMod.cardmods;

import ElainaMod.cards.AbstractElainaCard;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class toImageCardMod extends AbstractCardModifier {
    public toImageCardMod(){
    }//为方便remove，尽量不要使用CardMod直接修改卡牌值，虚无要修改，也要做好保留方便恢复
    private boolean isExhaustBefore = true;
    private boolean isEherealBefore = true;
    private boolean isSelfRetainBefore = true;
    private String postDescription;

    public static final Logger logger = LogManager.getLogger(toImageCardMod.class);

//    @Override
//    public String modifyDescription(String rawDescription, AbstractCard card) {//这里不要对rawDescription赋值，方便remove
//        logger.info(card.name + ": " + card.rawDescription);
//        postDescription = card.rawDescription;
//        String newDescription = postDescription
//                + " NL "
//                + CardCrawlGame.languagePack.getUIString("Elaina:Image").TEXT[0];
//        card.rawDescription = newDescription;
//        return newDescription;
//    }


    @Override
    public void onInitialApplication(AbstractCard card) {
        isEherealBefore = card.isEthereal;
        isExhaustBefore = card.exhaust;
        isSelfRetainBefore = card.selfRetain;
        card.isEthereal = true;
        card.exhaust = true;
        card.selfRetain = false;
        postDescription = card.rawDescription;
        String newDescription = postDescription
                + " NL "
                + CardCrawlGame.languagePack.getUIString("Elaina:Image").TEXT[0];
        card.rawDescription = newDescription;
        card.initializeDescription();
    }

    @Override
    public String identifier(AbstractCard card) {
        return "toImageCardMod";
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new toImageCardMod();
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.exhaust = isExhaustBefore;
        card.isEthereal = isEherealBefore;
        card.selfRetain = isSelfRetainBefore;
        card.rawDescription = postDescription;
    }
}
