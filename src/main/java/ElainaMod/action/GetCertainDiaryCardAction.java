package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.orb.ConclusionOrb;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Consumer;

public class GetCertainDiaryCardAction extends AbstractGameAction {
    public ElainaC p;
    private boolean toHand;
    public static final Logger logger = LogManager.getLogger(GetCertainDiaryCardAction.class);
    public GetCertainDiaryCardAction(ElainaC p){
        this.p = p;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    @Override
    public void update(){//将结语获取到手中，同时更新结语
        CardGroup g = ElainaC.DiaryGroup;
        Consumer<List<AbstractCard>> toHand = new Consumer<List<AbstractCard>>() {
            @Override
            public void accept(List<AbstractCard> abstractCards) {
                ElainaC.toHand(abstractCards.get(0));

                if (g.isEmpty()) {
                    logger.info("g is empty");
                    ConclusionOrb.getInstance().removeConclusion();
                } else if (!g.getBottomCard().equals(p.getConclusionOrb().c)){
                    logger.info("syncConclusion");
                    p.getConclusionOrb().syncConclusionWithDiary();
                }
            }
        };

        if(!g.isEmpty()){//如果调用p的方法，如p.getConclusion和p.getDiarySize就会报错Null，神奇
            addToBot(new FetchAction(g,toHand));
        }
        logger.info("Now Diary size: "+p.getDiarySize());
        this.isDone=true;
    }
}
