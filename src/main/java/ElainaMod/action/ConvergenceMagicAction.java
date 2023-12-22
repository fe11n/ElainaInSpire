package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cardmods.toInstantCardMod;
import ElainaMod.cards.AbstractElainaCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class ConvergenceMagicAction extends AbstractGameAction {
    ElainaC p;
    public static final Logger logger = LogManager.getLogger(ConvergenceMagicAction.class);
    public ConvergenceMagicAction(AbstractPlayer p){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = (ElainaC) p;
    }
    @Override
    public void update(){
        CardGroup g = p.DiaryGroup;
        int sum = 0;
        for(int i = g.size()-1;i>=0 && sum<10-p.hand.size();i--){
            if(g.group.get(i).hasTag(ElainaC.Enums.MAGIC)){
                sum+=1;
                addToBot(new GetDiaryCardAction(p,true,g.group.get(i)));//从下向上再现，防止序列改变
            }
        }
        this.isDone = true;
    }
}
