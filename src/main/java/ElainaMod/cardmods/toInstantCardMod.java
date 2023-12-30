package ElainaMod.cardmods;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.Iterator;
import java.util.List;

public class toInstantCardMod extends AbstractCardModifier {
    private String postDescription;
    public toInstantCardMod(){
    }

//    @Override
//    public String modifyDescription(String rawDescription, AbstractCard card) {//这里不要对rawDescription赋值，方便remove
//        postDescription = card.rawDescription;
//        String newDescription = postDescription
//                + " NL "
//                + CardCrawlGame.languagePack.getUIString("Elaina:Instant").TEXT[0];
//        card.rawDescription = newDescription;
//        return newDescription;
//    }


    @Override
    public void onInitialApplication(AbstractCard card) {
        postDescription = card.rawDescription;
        String newDescription = postDescription
                + " NL "
                + CardCrawlGame.languagePack.getUIString("Elaina:Instant").TEXT[0];
        card.rawDescription = newDescription;
        ((AbstractElainaCard)card).isInstant = true;
        card.initializeDescription();
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
        card.rawDescription = postDescription;
    }
}
