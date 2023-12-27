package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.AddInstantAction;
import ElainaMod.action.RecordCardAction;
import ElainaMod.action.ShowDiaryAction;
import ElainaMod.cards.*;
import ElainaMod.orb.ConclusionOrb;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.megacrit.cardcrawl.helpers.input.InputHelper;


import java.util.ArrayList;

public class WanderingWitch extends AbstractBookRelic {
    public static final String ID = "Elaina:WanderingWitch";
    public static final Logger logger = LogManager.getLogger(WanderingWitch.class);

    public WanderingWitch() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/WanderingWitch.png"), RelicTier.STARTER, LandingSound.FLAT);
    }
    public void atPreBattle(){//战斗开始时记录卡牌（这个是遗物描述的），TODO 并且按季节更新所有卡牌描述（这个最好写到能力里）
        super.atPreBattle();
        switch (ElainaC.getSeason()){
            case 0:
                this.addToTop(new RecordCardAction(new WinterPeace()));
                break;
            case 1:
                this.addToTop(new RecordCardAction(new SpringJoy()));
                break;
            case 2:
                this.addToTop(new RecordCardAction(new SummerExcitement()));
                break;
            case 3:
                this.addToTop(new RecordCardAction(new AutumnVigilance()));
                break;
        }
    }
}
