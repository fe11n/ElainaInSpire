package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import java.util.ArrayList;

public class GetDiaryCard extends AbstractGameAction {
    public AbstractPlayer p;
    public ArrayList<AbstractCard> g;
    public GetDiaryCard(AbstractPlayer p){
        this.p = p;
    }
    @Override
    public void update(){
        if(this.p instanceof ElainaC){
            g = ((ElainaC)p).DiaryGroup;
            if(g.size()>0){
                p.hand.addToHand(g.get(0));
            }
        }
        this.isDone=true;
    }
}
