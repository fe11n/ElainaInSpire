package ElainaMod.cardmods;

import ElainaMod.action.RecordCardAction;
import ElainaMod.cards.AbstractElainaCard;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class toShorthandCardMod extends AbstractCardModifier {
    private String postDescription;
    public toShorthandCardMod(){
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        postDescription = card.rawDescription;
        String newDescription = postDescription
                + " NL "
                + CardCrawlGame.languagePack.getUIString("Elaina:Shorthand").TEXT[0];
        card.rawDescription = newDescription;
        if(card instanceof AbstractElainaCard){
            ((AbstractElainaCard)card).isShorthand = true;
        }
        card.initializeDescription();
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new toShorthandCardMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return "toShorthandCardMod";
    }

    @Override
    public void onRemove(AbstractCard card) {
        if(card instanceof AbstractElainaCard){
            ((AbstractElainaCard)card).isShorthand = false;
        }//只允许对不为速记的卡添加该mod
        card.rawDescription = postDescription;
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if(!(card instanceof AbstractElainaCard)){
            this.addToBot(new RecordCardAction(card));
        }
    }

    public boolean canApplyTo(AbstractCard c){
        return !CardModifierManager.hasModifier(c,"toShorthandCardMod")
                && !((c instanceof AbstractElainaCard)&&(((AbstractElainaCard)c).isShorthand));
    }

}
