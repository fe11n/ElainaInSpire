package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.ThePerfectMeAction;
import ElainaMod.cardmods.toImageCardMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.ArrayList;
import java.util.Iterator;

public class Awaken extends AbstractElainaCard {
    public static final String ID = "Elaina:Awaken";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Awaken.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Awaken() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
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
//        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
//        AbstractMonster mo;
//        while(var3.hasNext()) {
//            mo = (AbstractMonster)var3.next();
//            this.addToBot(new ApplyPowerAction(mo, p, new IntangiblePlayerPower(mo, 1), 1));
//        }
//        this.addToBot(new ApplyPowerAction(p,p,new IntangiblePlayerPower(p,1)));
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                Iterator it = p.hand.group.iterator();
                while(it.hasNext()){
                    AbstractCard c = (AbstractCard) it.next();
                    if(c instanceof AbstractElainaCard && !CardModifierManager.hasModifier(c,"toImageCardMod")){
                        c.flash();
                        logger.info("Changing card: " + c.name);
                        CardModifierManager.addModifier(c,new toImageCardMod());
                    }
                }
                it = p.drawPile.group.iterator();
                while(it.hasNext()){
                    AbstractCard c = (AbstractCard) it.next();
                    if(c instanceof AbstractElainaCard && !CardModifierManager.hasModifier(c,"toImageCardMod")){
                        logger.info("Changing card: " + c.name);
                        CardModifierManager.addModifier(c,new toImageCardMod());
                    }
                }
                it = p.discardPile.group.iterator();
                while(it.hasNext()){
                    AbstractCard c = (AbstractCard) it.next();
                    if(c instanceof AbstractElainaCard && !CardModifierManager.hasModifier(c,"toImageCardMod")){
                        logger.info("Changing card: " + c.name);
                        CardModifierManager.addModifier(c,new toImageCardMod());
                    }
                }
                it = p.DiaryGroup.iterator();
                while(it.hasNext()){
                    AbstractCard c = ((AbstractCard) it.next()).makeStatEquivalentCopy();
                    logger.info("Adding card: " + c.name);
                    addToBot(new MakeTempCardInDrawPileAction(c,1,true,true));
                }
                this.isDone = true;
            }
        });
    }
}
