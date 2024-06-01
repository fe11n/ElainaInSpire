package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.GetDiaryCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Repetition extends AbstractElainaCard {
    public static final String ID = "Elaina:Repetition";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Repetition.png";
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Repetition() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID,CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.selfRetain = true;
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
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
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(p.getConclusion()!=null){
                    for(int i = 0;i<magicNumber;i++){
                        p.InstantUse(p.getConclusion());
                    }
                }
                this.isDone = true;
            }
        });
    }//基础效果，可以被使用和瞬发

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = ((ElainaC)p).getConclusion();
        // 这里瞬发判断要改，别直接强转类型。
        if(((ElainaC)p).getConclusion() == null || !((AbstractElainaCard)c).isInstant){
            return false;
        }
        else return super.canUse(p,m);
    }
}
