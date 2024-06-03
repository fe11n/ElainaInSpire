package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class PortableWand extends CustomRelic {
    public static final String ID = "Elaina:PortableWand";
    public PortableWand() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/PortableWand.png"), RelicTier.COMMON, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    public void atTurnStart() {
        if( !(AbstractDungeon.player instanceof ElainaC))
            return;

        ElainaC p = (ElainaC) AbstractDungeon.player;
        if(p.getConclusion()!=null && p.isInstant(p.getConclusion())){//如果使用tag判断INSTANT会导致更改INSTANT时同类卡牌全部INSTANT被修改
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            p.InstantUse(p.getConclusion());
        }
    }

}
