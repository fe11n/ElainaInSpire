package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.GetDiaryCardAction;
import ElainaMod.powers.GetSpellBoostPower;
import ElainaMod.powers.MagicDiffusionPower;
import ElainaMod.powers.SpellBoostPower;
import ElainaMod.powers.TimeGoesBackPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.DevotionPower;
import com.megacrit.cardcrawl.powers.watcher.ForesightPower;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;

import java.util.ArrayList;

public class Review extends AbstractElainaCard {
    public static final String ID = "Elaina:Review";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Review.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Review() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID,CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.block = this.baseBlock = 5;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
        }
    }
    @Override
    public void applyPowers() {
        this.baseMagicNumber = this.magicNumber = ((ElainaC)AbstractDungeon.player).DiaryGroup.size();
        this.rawDescription = CARD_STRINGS.DESCRIPTION + CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        super.applyPowers();
        this.initializeDescription();
    }
    public void onMoveToDiscard() {
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        this.initializeDescription();
    }
    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    public void BasicEffect(ElainaC p, AbstractMonster m){
        this.addToBot(new AbstractGameAction() {
            int m;
            public AbstractGameAction getMagicNum(int num){
                m = num;
                return this;
            }
            @Override
            public void update() {
                for(int i = 0;i<p.getDiarySize();i++){
                    this.addToBot(new GetDiaryCardAction(p,false));
                }
                for(int i = 0;i<m;i++){
                    this.addToBot(new GainBlockAction(p,block));
                }
                this.isDone = true;
            }
        }.getMagicNum(
                magicNumber
                ));
    }//基础效果，可以被使用和瞬
}
