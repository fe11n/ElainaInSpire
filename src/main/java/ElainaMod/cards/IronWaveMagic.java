package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.powers.ProtectiveMagicPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IronWaveMagic extends AbstractElainaCard {
    public static final String ID = "Elaina:IronWaveMagic";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/IronWaveMagic.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public IronWaveMagic() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET,CardColor.COLORLESS);
        this.block = this.baseBlock = 5;
        this.damage = this.baseDamage = 5;
        this.selfRetain = true;
        this.tags.add(ElainaC.Enums.MAGIC);
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            this.upgradeDamage(2);
        }
    }
    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    @Override
    public void BasicEffect(ElainaC p, AbstractMonster m) {
        // AbstractCard中实现了addToBot方法，它的效果和AbstractDungeon.actionManager.addToBottom相同
        this.addToBot(new GainBlockAction(p,p,this.block));
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SMASH));
    }
}
