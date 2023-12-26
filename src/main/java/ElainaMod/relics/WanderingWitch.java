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

public class WanderingWitch extends CustomRelic {
    public static final String ID = "Elaina:WanderingWitch";
    public static final Logger logger = LogManager.getLogger(WanderingWitch.class);

    public ArrayList<AbstractCard> g = ElainaC.DiaryGroup.group;
    public int NotedMonth;
    public AbstractElainaCard cardToRecord;
    private boolean Rclick = false;
    private boolean RclickStart = false;

    public WanderingWitch() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/WanderingWitch.png"), RelicTier.STARTER, LandingSound.FLAT);
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

        UpdateCounter();
        this.isDone = true;
    }
    public void atPreBattle(){//战斗开始时记录卡牌（这个是遗物描述的），TODO 并且按季节更新所有卡牌描述（这个最好写到能力里）
        ElainaC.DiaryGroup.clear();//战斗开始时清空，不管sl了
        cardToRecord = null;
        ElainaC p = (ElainaC)AbstractDungeon.player;
        p.channelOrb(new ConclusionOrb());
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
        p.UpdateAllSeasonalDescription();
    }


    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        ElainaC p = (ElainaC)AbstractDungeon.player;
        // 在这里更新每回合结语卡。
        if (c instanceof AbstractElainaCard) {
            AbstractElainaCard ec =(AbstractElainaCard) c;
            // 考虑在这里判断一下 ec.isShortHand，不然速记一下，结语又记一下太多了。
            if (ec.isNotable()) {
                cardToRecord =(AbstractElainaCard) ec.makeStatEquivalentCopy();
            }else {
                cardToRecord = null;
                p.getConclusionOrb().removeCardToRecord();
            }
        }else {
            cardToRecord = null;
            p.getConclusionOrb().removeCardToRecord();
        }
    }
    public void onPlayerEndTurn(){//回合结束时记录打出的最后一张卡牌
        // EndTurn在胜利或者结束战斗时不会被调用，所以 preBattle需要清空 cardToRecord
        if (cardToRecord != null) {
            this.addToTop(new RecordCardAction(cardToRecord, false)); // 不 make_copy，牌的运动更符合直觉。
            cardToRecord = null;
        }
    }



    public void onVictory(){
        g.clear();
    }//TODO 战斗结束时清空日记，这个也最好写到能力里
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
    public AbstractRelic makeCopy(){
        return new WanderingWitch();
    }


    public void onRightClick() {
        logger.info("onRightClick()");
        AbstractDungeon.actionManager.addToBottom(new ShowDiaryAction());
    }
    @Override
    public void update() {
        WanderingWitch.super.update();
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
