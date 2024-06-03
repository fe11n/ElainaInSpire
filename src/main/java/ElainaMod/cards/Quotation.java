package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCardAction;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Quotation extends AbstractElainaCard {
    public static final String ID = "Elaina:Quotation";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Quotation.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Quotation() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 3;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    public ArrayList<AbstractCard> returnProphecy() {
        ArrayList<AbstractCard> list = new ArrayList();
        for(int i = 0;i<this.magicNumber;){
            AbstractElainaCard c = (AbstractElainaCard)AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
            logger.info("Show Card Name: "+c.name);
            if(ElainaC.isNotable(c)){
                list.add(c);
                i++;
            }
        }
        return list;
    }

    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    @Override
    public void BasicEffect(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> list = returnProphecy();
        Consumer<List<AbstractCard>> callback = new Consumer<List<AbstractCard>>() {
            @Override
            public void accept(List<AbstractCard> abstractCards) {
                logger.info("Show Card Name: "+abstractCards.get(0).name);
                list.remove(abstractCards.get(0));
                //list.add(abstractCards.get(0));
                Iterator it = list.iterator();
                while (it.hasNext()){
                    addToBot(new RecordCardAction((AbstractCard)it.next()));
                }
                addToBot(new RecordCardAction(abstractCards.get(0)));
            }
        };
        this.addToBot(new SelectCardsAction(list,CARD_STRINGS.EXTENDED_DESCRIPTION[0],callback));
    }
}
