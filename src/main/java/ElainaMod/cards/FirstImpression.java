package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.FirstImpressionAction;
import ElainaMod.action.ItsMeAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FirstImpression extends AbstractElainaCard {
    public static final String ID = "Elaina:FirstImpression";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/FirstImpression.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public FirstImpression() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.tags.add(ElainaC.Enums.UNNOTABLE);
        magicNumber = baseMagicNumber = 3;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void BasicEffect(ElainaC p, AbstractMonster m) {
        this.addToBot(new DrawCardAction(1));
        this.addToBot(new FirstImpressionAction(p,magicNumber));
    }
}
