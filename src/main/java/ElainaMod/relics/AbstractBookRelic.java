package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCardAction;
import ElainaMod.action.ShowDiaryAction;
import ElainaMod.cards.*;
import ElainaMod.orb.ConclusionOrb;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AbstractBookRelic extends CustomRelic {
    public int NotedMonth;
    private boolean Rclick = false;
    private boolean RclickStart = false;

    public static final Logger logger = LogManager.getLogger(AbstractBookRelic.class);

    public ArrayList<AbstractCard> g = ElainaC.DiaryGroup.group;

    public AbstractBookRelic(String id, Texture texture, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        super(id, texture, tier, sfx);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    //    public void onEnterRoom(AbstractRoom room){//进入房间后时节+1，更新计数器，此时卡牌未初始化，只能直接更新
    //        logger.info("Month before enter: "+p.Month);
    //        try {
    //            ElainaC.class.getMethod("ChangeMonth", int.class);
    //            p.ChangeMonth(p.Month+1,false);//通过这个函数调用UpgradeCounter不生效，神奇了
    //        } catch (NoSuchMethodException e) {
    //            logger.info("No method: ElainaC.ChangeMonth");
    //            throw new RuntimeException(e);
    //        }
    //        logger.info("Month after enter: "+p.Month);
    //        UpdateCounter();
    //        this.isDone = true;
    //    }
    public void onEnterRoom(AbstractRoom room) {
        logger.info("Month before enter: " + (AbstractDungeon.player != null ? ElainaC.Month : "null"));

        try {
            // TODO: 遗物不依赖人物存在，时令也不依赖人物存在。等着重构吧。
            if (AbstractDungeon.player != null) {
                ElainaC p = (ElainaC) AbstractDungeon.player;
                p.ChangeMonth(ElainaC.Month + 1, false);
                UpdateCounter();
            } else {
                logger.info("Player object (p) is null. Cannot change month.");
            }
        } catch (ClassCastException e) {
            logger.error("Failed to cast player object to ElainaC.", e);
            // 在这里添加适当的处理逻辑，或者记录其他信息
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            // 在这里添加适当的处理逻辑，或者记录其他信息
            throw new RuntimeException(e);
        }

        logger.info("Month after enter: " + (AbstractDungeon.player != null ? ElainaC.Month : "null"));
        this.isDone = true;
    }
    public void atPreBattle(){//战斗开始时记录卡牌（这个是遗物描述的），TODO 并且按季节更新所有卡牌描述（这个最好写到能力里）
        // 已经移动到 ElainaC applyPreCombatLogic 实现
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        // 已经移动到 ElainaC useCard 实现
    }
    public void onPlayerEndTurn(){
        // 移动到 ConclusionOrb onEndOfTurn 实现
    }

    public void UpdateCounter(){//更新计数器
        logger.info("Changing RelicCounter...");
        NotedMonth = (ElainaC.Month %12)<=0?(ElainaC.Month %12)+12:(ElainaC.Month %12);
        this.flash();
        logger.info("Noted Month: "+NotedMonth);
        this.counter = NotedMonth;
        this.description = DESCRIPTIONS[ElainaC.getSeason()+1];
        this.tips.clear();
        logger.info("Total Month: "+ ElainaC.Month);
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }


    public void onRightClick() {
        logger.info("onRightClick()");
        AbstractDungeon.actionManager.addToBottom(new ShowDiaryAction());
    }
    @Override
    public void update() {
        AbstractBookRelic.super.update();
        if (this.RclickStart && InputHelper.justReleasedClickRight) {
            if (this.hb.hovered) {
                this.Rclick = true;
            }
            this.RclickStart = false;
        }
        if (this.isObtained && this.hb != null && this.hb.hovered && InputHelper.justClickedRight) {
            this.RclickStart = true;
        }
        if (this.Rclick) {
            this.Rclick = false;
            onRightClick();
        }
    }
}
