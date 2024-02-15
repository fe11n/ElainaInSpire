package ElainaMod.cardmods;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class toMagicCardMod extends AbstractCardModifier {
    public toMagicCardMod(){
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
        card.tags.add(ElainaC.Enums.MAGIC);
    }

    public String modifyName(String cardName, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString("Elaina:Magic").TEXT[0]
                + cardName;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new toMagicCardMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return "toMagicCardMod";
    }

    @Override
    public void onRemove(AbstractCard card) {
    }

    public boolean canApplyTo(AbstractCard c){
        return c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)||c.hasTag(AbstractCard.CardTags.STARTER_DEFEND);
    }
}
