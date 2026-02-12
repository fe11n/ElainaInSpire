package ElainaMod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Nausea extends AbstractElainaCard {
    public static final String ID = "Elaina:Nausea";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "ElainaMod/img/cards/Nausea.png";
    private static final int COST = -2;
    private static final CardType TYPE = CardType.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Nausea() {
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET,CardColor.CURSE);
        this.retain = true;
    }
    
    // Removed canUse() as per NEW version logic (effectively letting AbstractCard decide or default behavior)
}
