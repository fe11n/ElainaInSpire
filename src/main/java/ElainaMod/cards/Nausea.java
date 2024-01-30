package ElainaMod.cards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

public class Nausea extends AbstractElainaCard {
    public static final String ID = "Elaina:Nausea";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Nausea.png";
    private static final int COST = -1;
    private static final CardType TYPE = CardType.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Nausea() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET,CardColor.CURSE);
        this.exhaust = true;
    }
}
