package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.GetDiaryCardAction;
import ElainaMod.action.RecordCardAction;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Rummage extends AbstractElainaCard {
    public static final String ID = "Elaina:Rummage";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Rummage.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Rummage() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;//要给base值和原值同时赋值
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
    protected void onRightClick() {

    }
    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    @Override
    public void BasicEffect(ElainaC p, AbstractMonster m) {
        int num = BaseMod.MAX_HAND_SIZE - p.hand.size() - this.magicNumber;
        this.addToBot(new DrawCardAction(this.magicNumber));
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(num>=0){
                    for(int i = 0;i<magicNumber;i++){
                        this.addToBot(new RecordCardAction(p.hand.group.get(p.hand.group.size()-magicNumber+i)));
                    }
                }else {
                    for(int i = 0;i<magicNumber+num+1;i++){
                        this.addToBot(new RecordCardAction(p.hand.group.get(p.hand.group.size()-magicNumber+i)));
                    }
                }
                this.isDone = true;
            }
        });
    }
}
