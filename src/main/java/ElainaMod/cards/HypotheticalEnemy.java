package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.ChangeMonthAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.sun.org.apache.xpath.internal.functions.FuncFalse;

import java.util.ArrayList;
import java.util.Iterator;

public class HypotheticalEnemy extends AbstractElainaCard {
    public static final String ID = "Elaina:HypotheticalEnemy";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/HypotheticalEnemy.png";
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public HypotheticalEnemy() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    @Override
    public void BasicEffect(AbstractPlayer p, AbstractMonster m) {
        // AbstractCard中实现了addToBot方法，它的效果和AbstractDungeon.actionManager.addToBottom相同
        this.addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.magicNumber), this.magicNumber));
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        int hasPower = 0;
        AbstractMonster mo;
        while(var3.hasNext()) {
            mo = (AbstractMonster)var3.next();
            if(mo.hasPower("Strength")){
                int strAmt = mo.getPower("Strength").amount;
                if(strAmt>0){
                    hasPower += 1;
                }
                this.addToBot(new ApplyPowerAction(mo, p,new StrengthPower(mo, strAmt), strAmt));
            }
        }
        if(hasPower>0){
            AbstractCard c = this.makeStatEquivalentCopy();
            c.cost = cost + 1;
            c.costForTurn = cost + 1;
            c.isCostModified = true;
            this.addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
            if(this.upgraded){
                this.addToBot(new MakeTempCardInDrawPileAction(c,1,true,true));
            }else {
                this.addToBot(new MakeTempCardInDiscardAction(c,1));
            }
        }
//        ArrayList<AbstractCard> stanceChoices = new ArrayList();
//        stanceChoices.add(new Fight());
//        stanceChoices.add(new Rush());
//        stanceChoices.add(new Asylum());
//        if (this.upgraded) {
//            Iterator var4 = stanceChoices.iterator();
//
//            while(var4.hasNext()) {
//                AbstractCard c = (AbstractCard)var4.next();
//                c.upgrade();
//            }
//        }
//        this.addToBot(new ChooseOneAction(stanceChoices));
    }
    public void applyPowers() {
    }
}
