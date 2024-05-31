package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.powers.MagicResiduePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Ignite extends AbstractElainaCard {
    public static final String ID = "Elaina:Ignite";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Ignite.png";
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Ignite() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID,CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET,CardColor.COLORLESS);
        this.magicNumber = this.baseMagicNumber = 2;
        this.selfRetain = true;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeMagicNumber(1);
        }
    }

    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    public void BasicEffect(ElainaC p, AbstractMonster m){
        this.addToBot(new ApplyPowerAction(m,p,new MagicResiduePower(m,p,this.magicNumber)));
    }//基础效果，可以被使用和瞬发
}
