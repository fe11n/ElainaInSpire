package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.Iterator;

public class CharmMagic extends AbstractElainaCard {
    public static final String ID = "Elaina:CharmMagic";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/CharmMagic.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public CharmMagic() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID,CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.damage = this.baseDamage = 7;
        this.magicNumber = this.baseMagicNumber = 2;
        this.isMultiDamage = true;
        this.tags.add(ElainaC.Enums.MAGIC);

    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeDamage(3); // 将该卡牌的伤害提高2点。
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
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if(!p.DiaryGroup.isEmpty() && p.getConclusion().isInstant){
            Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            AbstractMonster mo;
            while(var3.hasNext()) {
                mo = (AbstractMonster)var3.next();
                this.addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, magicNumber, false), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
        }

    }//基础效果，可以被使用和瞬发
}
