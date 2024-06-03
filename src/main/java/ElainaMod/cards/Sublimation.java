package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCardAction;
import ElainaMod.action.SublimationAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;
import java.util.Iterator;

public class Sublimation extends AbstractElainaCard {
    public static final String ID = "Elaina:Sublimation";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Sublimation.png";
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Sublimation() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
        this.selfRetain = true;
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
    public void BasicEffect(ElainaC p, AbstractMonster m) {
//        int num = 0;
//        Iterator it = p.hand.group.iterator();
//        while (it.hasNext()){
//            AbstractCard c = (AbstractCard) it.next();
//            if(c.selfRetain==true){
//                this.addToBot(new ExhaustSpecificCardAction(c,p.hand));
//                num++;
//            }
//        }
//        this.addToBot(new GainEnergyAction(num));
////        for(int i=0;i<num;i++){
////            this.addToBot(new FetchAction(p.discardPile,1));
////        }
//        for(int i = 0;i<num;i++){
//            if(p.discardPile.size()>i){
//                AbstractCard c = p.discardPile.group.get(p.discardPile.size()-1-i);
//                this.addToBot(new DiscardToHandAction(c));
//                if(upgraded){
//                    this.addToBot(new RecordCardAction(c));
//                }
//            }
//        }
        this.addToBot(new SublimationAction(p,this.upgraded));
    }
}
