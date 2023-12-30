package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.Elaina.Elaina;
import ElainaMod.cardmods.toInstantCardMod;
import ElainaMod.cards.AbstractElainaCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class ThePerfectMeAction extends AbstractGameAction {
    ElainaC p;
    public static final Logger logger = LogManager.getLogger(ThePerfectMeAction.class);
    public ThePerfectMeAction(AbstractPlayer p){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = (ElainaC) p;
    }
    @Override
    public void update(){
        if(returnProphecy().size()!=0){
            Iterator it = returnProphecy().iterator();
            while(it.hasNext()){
                CardModifierManager.addModifier((AbstractCard)it.next(),new toInstantCardMod());
            }
        }
        else {
            for(AbstractCard c:p.DiaryGroup.group){
                if(ElainaC.isInstant(c)){
//                    AbstractCard tmp = c.makeSameInstanceOf();
//                    AbstractDungeon.player.limbo.addToBottom(tmp);
//                    tmp.current_x = c.current_x;
//                    tmp.current_y = c.current_y;
//                    tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
//                    tmp.target_y = (float)Settings.HEIGHT / 2.0F;
                    ElainaC.InstantUse(c);
                }
            }
        }
        this.isDone = true;
    }
    public static ArrayList<AbstractCard> returnProphecy() {
        ArrayList<AbstractCard> list = new ArrayList();
        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
            if(ElainaC.isInstant(c) && ElainaC.isNotable(c)){//没有瞬发且可被记录的伊蕾娜卡牌
                list.add(c);
            }
        }
        return list;
    }
}
