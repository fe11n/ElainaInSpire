package ElainaMod.cardmods;

import ElainaMod.cards.AbstractElainaCard;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.Iterator;

public class toRetainCardMod extends AbstractCardModifier {
    private String postDescription;
    public toRetainCardMod(){
    }

//    @Override
//    public String modifyDescription(String rawDescription, AbstractCard card) {//这里不要对rawDescription赋值，方便remove
//        postDescription = card.rawDescription;
//        String newDescription = postDescription
//                + " NL "
//                + CardCrawlGame.languagePack.getUIString("Elaina:Retain").TEXT[0];
//        card.rawDescription = newDescription;
//        return newDescription;
//    }


    @Override
    public void onInitialApplication(AbstractCard card) {
        postDescription = card.rawDescription;
        String newDescription = postDescription
                + " NL "
                + CardCrawlGame.languagePack.getUIString("Elaina:Retain").TEXT[0];
        card.rawDescription = newDescription;
        card.selfRetain = true;
        card.initializeDescription();
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new toRetainCardMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return "toRetainCardMod";
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.selfRetain = false;//只允许对不保留的卡添加该mod
        card.rawDescription = postDescription;
    }
}
