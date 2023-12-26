package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.cards.IndelibleImprint;
import ElainaMod.orb.ConclusionOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetDiaryCardAction extends AbstractGameAction {
    public ElainaC p;
    public CardGroup g;
    private boolean toHand;
    public static final Logger logger = LogManager.getLogger(GetDiaryCardAction.class);
    public AbstractCard targetCard = null;
    int cardIndex;
    public GetDiaryCardAction(ElainaC p){
        this.p = p;
        toHand = true;
        this.actionType = ActionType.CARD_MANIPULATION;
        g = p.DiaryGroup;
        this.cardIndex = -1;
    }
    public GetDiaryCardAction(ElainaC p, boolean toHand){
        this.p = p;
        this.toHand = toHand;
        this.actionType = ActionType.CARD_MANIPULATION;
        g = p.DiaryGroup;
        this.cardIndex = -1;//实际update时再赋值，防止同一卡牌的其它动作改变序列
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
            if(targetCard == null || targetCard.equals(p.getConclusion())){
                targetCard = p.getConclusion();
                this.cardIndex = g.size()-1;
                g.removeCard(g.getBottomCard());
                if(!g.isEmpty()){
                    ConclusionOrb orb = (ConclusionOrb) p.orbs.get(0);
                    orb.setCurConclusion(p.getConclusion());
                }
                else{
                    ConclusionOrb.removeConclusion();
                }
            }
            else {
                g.removeCard(targetCard);
                logger.info("Remove from Diary: "+targetCard.name);
            }

            if(toHand){
                ((AbstractElainaCard)targetCard).toHandfromDiary();
                if(p.hand.size()<10){
                    p.hand.addToHand(targetCard);
                }
                else {
                    p.discardPile.addToTop(targetCard);
                }
            }
        }
        logger.info("Now Diary size: "+p.getDiarySize());
        this.isDone=true;
    }
}
