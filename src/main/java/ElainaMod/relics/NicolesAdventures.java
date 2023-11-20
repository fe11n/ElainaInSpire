package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.GetDiaryCard;
import ElainaMod.cards.Strike;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NicolesAdventures extends CustomRelic {
    public static final String ID = "Elaina:NicolesAdventures";
    public static final Logger logger = LogManager.getLogger(GetDiaryCard.class);
    public NicolesAdventures() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/NicolesAdventures.png"), RelicTier.STARTER, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    AbstractPlayer p =AbstractDungeon.player;
    public void atPreBattle(){
        if(p instanceof ElainaC){
            ((ElainaC) p).DiaryGroup.add(new Strike());
            logger.info(
                    "start num: "+((ElainaC) p).DiaryGroup.size()
            );
        }
    }

    public AbstractRelic makeCopy(){
        return new NicolesAdventures();
    }

}
