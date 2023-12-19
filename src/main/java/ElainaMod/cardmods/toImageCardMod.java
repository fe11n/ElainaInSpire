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
    }//为方便remove，尽量不要使用CardMod直接修改卡牌值，虚弱要修改，也要做好保留方便恢复
    private boolean isExhaustBefore = true;
    private boolean isEherealBefore = true;
    private boolean isSelfRetainBefore = true;

    public static final Logger logger = LogManager.getLogger(toSeasonCardMod.class);

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {//这里不要对rawDescription赋值，方便remove
        logger.info(card.name + ": " + card.rawDescription);
        String d = card.rawDescription;
        Iterator<String> it = ((AbstractElainaCard)card).ModStrings.iterator();
        while (it.hasNext()){
            d = d + " NL " + it.next();
        }
        return d;
    }


    @Override
    public void onInitialApplication(AbstractCard card) {
        isEherealBefore = card.isEthereal;
        isExhaustBefore = card.exhaust;
        isSelfRetainBefore = card.selfRetain;
        ((AbstractElainaCard)card).ModStrings.add(CardCrawlGame.languagePack.getUIString("Elaina:Image").TEXT[0]);
        card.isEthereal = true;
        card.exhaust = true;
        card.selfRetain = false;
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
        ((AbstractElainaCard)card).ModStrings.remove(CardCrawlGame.languagePack.getUIString("Elaina:Image").TEXT[0]);
    }
}
