package ElainaMod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ModRecordedCard extends AbstractCardModifier {
    public ModRecordedCard(){
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription
                + " NL "
                + CardCrawlGame.languagePack.getUIString("Elaina:Ethereal").TEXT[0]
                +" NL "
                + CardCrawlGame.languagePack.getUIString("Elaina:Exhaust").TEXT[0];
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.exhaust = true;
        card.isEthereal = true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ModRecordedCard();
    }

}
