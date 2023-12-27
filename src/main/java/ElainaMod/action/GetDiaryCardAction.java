package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.cards.IndelibleImprint;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetDiaryCardAction extends AbstractGameAction {
    public ElainaC p;
    public CardGroup g;
    private boolean toHand;
    public static final Logger logger = LogManager.getLogger(GetDiaryCardAction.class);
    public AbstractCard targetCard = null;
    public GetDiaryCardAction(ElainaC p){
        this.p = p;
        toHand = true;
        this.actionType = ActionType.CARD_MANIPULATION;
        g = p.DiaryGroup;
    }
    public GetDiaryCardAction(ElainaC p, boolean toHand){
        this.p = p;
        this.toHand = toHand;
        this.actionType = ActionType.CARD_MANIPULATION;
        g = p.DiaryGroup;
    }
    public GetDiaryCardAction(ElainaC p, boolean toHand, AbstractCard c){
        this.p = p;
        this.toHand = toHand;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.targetCard = c;
        g = p.DiaryGroup;
    }
    @Override
    public void update(){//将结语获取到手中，同时更新结语
        if(!g.isEmpty()){//如果调用p的方法，如p.getConclusion和p.getDiarySize就会报错Null，神奇
            // 不灭印记的特殊情况
            if(p.getConclusion() instanceof IndelibleImprint && !toHand){
                this.addToBot(
                        new DamageAction(
                                AbstractDungeon.getRandomMonster(),
                                new DamageInfo(p,p.getConclusion().magicNumber, DamageInfo.DamageType.THORNS)
                        )
                );
                p.getConclusion().flash();
                this.isDone = true;
                return;
            }

            // 拿结语
            if(targetCard == null || targetCard.equals(p.getConclusion())){
                targetCard = p.getConclusion();
                g.removeCard(g.getBottomCard());
                p.getConclusionOrb().syncConclusionWithDiary();
            }
            else {
                g.removeCard(targetCard);
                logger.info("Remove from Diary: "+targetCard.name);
            }

            if(toHand){
                ((AbstractElainaCard)targetCard).toHandfromDiary();
                if (p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                    g.moveToDiscardPile(targetCard);
                    this.p.createHandIsFullDialog();
                } else {
                    targetCard.untip();
                    targetCard.unhover();
                    targetCard.lighten(true);
                    targetCard.setAngle(0.0F);
                    targetCard.drawScale = 0.12F;
                    targetCard.targetDrawScale = 0.75F;
                    targetCard.current_x = p.getConclusionOrb().tX;
                    targetCard.current_y = p.getConclusionOrb().tY;
                    p.hand.addToTop(targetCard);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                }
            }else if(p.hasRelic("Elaina:TreasureWine") && p.getDiarySize()>0){
                p.getRelic("Elaina:TreasureWine").flash();
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, p.getRelic("Elaina:TreasureWine")));
                this.addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(p.getDiarySize(), true), DamageInfo.DamageType.THORNS, AttackEffect.BLUNT_HEAVY));
            }
        }
        logger.info("Now Diary size: "+p.getDiarySize());
        this.isDone=true;
    }
}
