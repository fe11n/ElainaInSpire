package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class PortableWand extends CustomRelic {
    public static final String ID = "Elaina:PortableWand";
    public static final Logger logger = LogManager.getLogger(PortableWand.class);
    ElainaC p;
    public ArrayList<AbstractCard> g = ElainaC.DiaryGroup.group;
    public PortableWand() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/PortableWand.png"), RelicTier.COMMON, LandingSound.FLAT);
        p =(ElainaC) AbstractDungeon.player;
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    public void atTurnStart() {
        if(p.getConclusion().isInstant){//如果使用tag判断INSTANT会导致更改INSTANT时同类卡牌全部INSTANT被修改
            this.flash();
            p.getConclusion().InstantUse();
        }
    }

}
