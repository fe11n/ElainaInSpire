package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ShowDiaryAction extends AbstractGameAction{
    public ElainaC p = (ElainaC) AbstractDungeon.player;
    public CardGroup g = ElainaC.DiaryGroup;
    public CardGroup diaryShow = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public static final Logger logger = LogManager.getLogger(ShowDiaryAction.class);
    public ShowDiaryAction(){
        logger.info("ShowDiaryAction()");
        diaryShow.group.addAll(g.group);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }
    @Override
    public void update() {
        if (diaryShow.group.isEmpty()) {
            this.isDone = true;
        }
        AbstractDungeon.gridSelectScreen.open(diaryShow, 1, true,"魔女日记");
        this.isDone = true;
    }
}
