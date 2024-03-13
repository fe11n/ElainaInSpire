package ElainaMod.cards;

import ElainaMod.powers.MagicResiduePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fight extends AbstractElainaCard {
    public static final String ID = "Elaina:Fight";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Fight.png";
    private static final int COST = -2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Fight() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET,CardColor.COLORLESS);
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        for(AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters){
            if(mo.hasPower(MagicResiduePower.POWER_ID)){
                int am = mo.getPower(MagicResiduePower.POWER_ID).amount;
                this.addToBot(new ApplyPowerAction(mo,p,new MagicResiduePower(mo,p,am)));
                if(this.upgraded){
                    this.addToBot(new ApplyPowerAction(mo,p,new MagicResiduePower(mo,p,am)));
                }
            }
        }
    }

}
