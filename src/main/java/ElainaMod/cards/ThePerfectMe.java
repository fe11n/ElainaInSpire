package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.ThePerfectMeAction;
import ElainaMod.cardmods.toInstantCardMod;
import ElainaMod.powers.IntensifyArrayPower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;

public class ThePerfectMe extends AbstractElainaCard {
    public static final String ID = "Elaina:ThePerfectMe";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/ThePerfectMe.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public ThePerfectMe() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.tags.add(ElainaC.Enums.UNNOTABLE);
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
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
        this.addToBot(new ThePerfectMeAction(p));
    }
    public void triggerOnGlowCheck() {
        if(returnProphecy().isEmpty()){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
    public static ArrayList<AbstractCard> returnProphecy() {
        ArrayList<AbstractCard> list = new ArrayList();
        Iterator it = AbstractDungeon.player.discardPile.group.iterator();
        while(it.hasNext()){
            AbstractCard c = (AbstractCard)it.next();
            if(c instanceof AbstractElainaCard
                    && ((AbstractElainaCard)c).isInstant == false
                    && ((AbstractElainaCard)c).isNotable()){//没有瞬发且可被记录的伊蕾娜卡牌
                list.add(c);
            }
        }
        return list;
    }
}
