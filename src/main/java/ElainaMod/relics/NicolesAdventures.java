package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.Strike;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class NicolesAdventures extends CustomRelic {
    public static final String ID = "Elaina:NicolesAdventures";
    AbstractPlayer p =AbstractDungeon.player;
    public ArrayList<AbstractCard> l = AbstractDungeon.actionManager.cardsPlayedThisTurn;
    public ArrayList<AbstractCard> g;
    public NicolesAdventures() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/NicolesAdventures.png"), RelicTier.STARTER, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    public void atPreBattle(){
        if(p instanceof ElainaC){
            g = ((ElainaC) p).DiaryGroup;
            g.add(new Strike());
        }
    }

    public void onPlayerEndTurn(){
        if(p instanceof ElainaC){
            g = ((ElainaC) p).DiaryGroup;
            g.add(l.get(l.size()-1));
        }
    }


    public AbstractRelic makeCopy(){
        return new NicolesAdventures();
    }

}
