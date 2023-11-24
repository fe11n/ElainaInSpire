package ElainaMod.cardmods;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class toInstantCardMod extends AbstractCardModifier {
    public toInstantCardMod(){
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription
                + " NL "
                + CardCrawlGame.languagePack.getUIString("Elaina:Instant").TEXT[0];
    }


    @Override
    public void onInitialApplication(AbstractCard card) {
        ((AbstractElainaCard)card).isInstant = true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new toInstantCardMod();
    }

}
