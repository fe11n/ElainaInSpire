package ElainaMod.cardmods;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.Iterator;
import java.util.List;

public class toInstantCardMod extends AbstractCardModifier {
    public toInstantCardMod(){
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
        ((AbstractElainaCard)card).isInstant = true;
        ((AbstractElainaCard)card).ModStrings.add(CardCrawlGame.languagePack.getUIString("Elaina:Instant").TEXT[0]);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new toInstantCardMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return "toInstantCardMod";
    }

    @Override
    public void onRemove(AbstractCard card) {
        ((AbstractElainaCard)card).isInstant = false;//只允许对不为瞬发的卡添加该mod
        ((AbstractElainaCard)card).ModStrings.remove(CardCrawlGame.languagePack.getUIString("Elaina:Instant").TEXT[0]);
    }
}
