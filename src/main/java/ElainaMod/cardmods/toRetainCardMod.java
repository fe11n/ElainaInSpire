package ElainaMod.cardmods;

import ElainaMod.cards.AbstractElainaCard;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.Iterator;

public class toRetainCardMod extends AbstractCardModifier {
    public toRetainCardMod(){
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {//这里不要对rawDescription赋值，方便remove
        String d = card.rawDescription;
        Iterator<String> it = ((AbstractElainaCard)card).ModStrings.iterator();
        while (it.hasNext()){
            d = d + " NL " + it.next();
        }
        return d;
    }


    @Override
    public void onInitialApplication(AbstractCard card) {
        ((AbstractElainaCard)card).selfRetain = true;
        ((AbstractElainaCard)card).ModStrings.add(CardCrawlGame.languagePack.getUIString("Elaina:Retain").TEXT[0]);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new toRetainCardMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return "toInstantCardMod";
    }

    @Override
    public void onRemove(AbstractCard card) {
        ((AbstractElainaCard)card).selfRetain = false;//只允许对不保留的卡添加该mod
        ((AbstractElainaCard)card).ModStrings.remove(CardCrawlGame.languagePack.getUIString("Elaina:Retain").TEXT[0]);
    }
}
