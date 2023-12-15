package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Iterator;

public class EmergencyTreatment extends AbstractElainaCard {
    public static final String ID = "Elaina:EmergencyTreatment";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/EmergencyTreatment.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public EmergencyTreatment() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 7;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
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
        AbstractCard c = this.makeCopy();
        if(this.upgraded){
            c.upgrade();
        }
        this.addToBot(new MakeTempCardInHandAction(c));
        this.addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,magicNumber)));
        int count = 0;
        Iterator it = p.drawPile.group.iterator();
        while(it.hasNext()){
            if(it.next() instanceof EmergencyTreatment){
                count++;
            }
        }
        it = p.discardPile.group.iterator();
        while(it.hasNext()){
            if(it.next() instanceof EmergencyTreatment){
                count++;
            }
        }
        if(count>0){
            it = AbstractDungeon.getMonsters().monsters.iterator();
            while(it.hasNext()){
                AbstractMonster mo = (AbstractMonster) it.next();
                this.addToBot(new ApplyPowerAction(mo, p,new StrengthPower(mo, count), count));
        }
        }
    }
}
