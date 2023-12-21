package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCardAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AnticipatoryMagic extends AbstractElainaCard {
    public static final String ID = "Elaina:AnticipatoryMagic";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/AnticipatoryMagic.png";
    private static final int COST = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public AnticipatoryMagic() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.damage = this.baseDamage = 15;
        this.magicNumber = this.baseMagicNumber = 3;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeMagicNumber(1);
            this.upgradeDamage(3);
        }
    }

    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    public void BasicEffect(ElainaC p, AbstractMonster m){
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageType.NORMAL)));
        for(int i = 0;i<magicNumber;i++){
            if(p.drawPile.size()>i){
                AbstractCard c = p.drawPile.group.get(p.drawPile.size()-1-i);
                if(c instanceof AbstractElainaCard && ((AbstractElainaCard)c).isNotable()){
                    this.addToBot(new RecordCardAction(c));
                }else {
                    this.addToBot(new GainEnergyAction(1));
                }
            }
        }
    }
}
