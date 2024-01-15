package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCardAction;
import ElainaMod.cardmods.toRetainCardMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class MagicTrade extends AbstractElainaCard {
    public static final String ID = "Elaina:MagicTrade";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/MagicTrade.png";
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public MagicTrade() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
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
        int num = 0;
        Iterator it = p.hand.group.iterator();
        while (it.hasNext()){
            AbstractCard c = (AbstractCard) it.next();
            if(c.selfRetain==true){
                this.addToBot(new DiscardSpecificCardAction(c));
                num++;
            }
        }
        final int n = num;
        this.addToBot(new DrawCardAction(n));
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for(int i = 0;i<n;i++){
                    AbstractCard c = p.hand.group.get(p.hand.group.size()-n+i) ;
                    if(!c.selfRetain){
                        CardModifierManager.addModifier(c,new toRetainCardMod());
                    }
                }
                this.isDone = true;
            }
        });
    }
}
