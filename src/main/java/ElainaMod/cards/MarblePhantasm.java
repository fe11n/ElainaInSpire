package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.GetDiaryCardAction;
import ElainaMod.powers.SpellBoostPower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

public class MarblePhantasm extends AbstractElainaCard {
    public static final String ID = "Elaina:MarblePhantasm";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/MarblePhantasm.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public MarblePhantasm() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.isShorthand = true;
        this.isEthereal = true;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.isEthereal = false;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public void triggerOnGlowCheck() {
        if(CardModifierManager.hasModifier(this,"toImageCardMod")){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }
    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    public void BasicEffect(ElainaC p, AbstractMonster m){
        this.addToBot(new GetDiaryCardAction(p));
        if(CardModifierManager.hasModifier(this,"toImageCardMod")){
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    Iterator it = p.powers.iterator();
                    while(it.hasNext()){
                        AbstractPower po = (AbstractPower) it.next();
                        if(po.type == AbstractPower.PowerType.BUFF){
                            po.flash();
                            if(po instanceof SpellBoostPower){
                                this.addToBot(new ApplyPowerAction(p,p,new SpellBoostPower(p,po.amount)));
                            }
                            else {
                                po.amount += po.amount;
                                po.updateDescription();
                            }
                        }
                    }
                    this.isDone = true;
                }
            });
        }
    }//基础效果，可以被使用和瞬发
}
