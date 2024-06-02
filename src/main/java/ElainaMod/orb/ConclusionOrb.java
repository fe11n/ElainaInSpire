package ElainaMod.orb;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCardAction;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.cards.AbstractSeasonCard;
import ElainaMod.relics.AbstractBookRelic;
import ElainaMod.relics.WanderingWitch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ElainaMod.Characters.ElainaC.Enums.SEASONAL;

public class ConclusionOrb extends AbstractOrb {
    public static final String ID = "Elaina:ConclusionOrb";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("Elaina:DiaryTips").TEXT;
    private AbstractCard prev_c; //这个只是为了移动卡牌的时候好看的。
    public AbstractCard c; //魔力增幅直接用了这个。
    private AbstractCard cardToRecord;
    private AbstractCard cardToRecordV;

    public static final Logger logger = LogManager.getLogger(ConclusionOrb.class);
    private static final Texture DiarySlotImg = ImageMaster.loadImage("ElainaMod/img/UI/Diary.png");
    private ConclusionOrb() {
        // 结语充能球一直存在，不在战斗中一直 new
        this.name = TEXT[0];
        this.description = TEXT[1];
    }
    private static ConclusionOrb instance = null;
    public static ConclusionOrb getInstance(){
        if (instance == null) {
            instance = new ConclusionOrb();
        }
        return instance;
    }
    public void pushConclusion(AbstractCard c_) {
        prev_c = c;
        c = c_;
    }
    public void setCurConclusion(AbstractCard c_){
        c = c_;
    }

    public void syncConclusionWithDiary(){
        // well, some kind of `popConclusion`
        if(((ElainaC)AbstractDungeon.player).getConclusion()!=null){
            AbstractCard c_ = ((ElainaC)AbstractDungeon.player).getConclusion();
            c_.current_x = c.current_x;
            c_.current_y = c.current_y;
            c = c_;
        }else {
            c = null;
        }
    }


    @Override
    public void onStartOfTurn(){
        prev_c = null; //这个只是为了移动卡牌的时候好看的。所以开局时清掉
        //实现瞬发机制
        if(c!= null && ElainaC.isInstant(c)){ //如果使用tag判断INSTANT会导致更改INSTANT时同类卡牌全部INSTANT被修改
            this.flashConclusion();
            ElainaC.InstantUse(c);
        }
    }

    @Override
    public void updateDescription() {
        this.description= TEXT[1];
    }
    private void updatePrev(){
        if (prev_c==null) {
            return;
        }
        prev_c.target_x = this.tX;
        prev_c.target_y = this.tY;
        prev_c.drawScale = 0.5F;
        prev_c.applyPowers();
        prev_c.update();
    }


    private void updateConclusion(){
        if (c == null) {
            return;
        }
        this.c.target_x = this.tX;
        this.c.target_y = this.tY;
        if (this.hb.hovered) {
            this.c.targetDrawScale = 1.0F;
        } else {
            this.c.targetDrawScale = 0.5F;
        }
        if(this.hb.hovered && (InputHelper.justClickedLeft || InputHelper.justClickedRight)){
            AbstractDungeon.gridSelectScreen.open(ElainaC.DiaryGroup,0,
                    TEXT[2], true);
        }
        this.c.applyPowers();
        this.c.update();
    }
    private void updateNext(){
        // cardToRecord 已经置空后，不再强制更新卡牌位置，以获得更好的动画效果
        if (cardToRecord == null) {
            return;
        }
        // 防止卡牌闪动做的临时存储。
        cardToRecordV = cardToRecord;
        cardToRecordV.target_x = this.tX - this.cardToRecordV.hb.width;
        cardToRecordV.target_y = this.tY;
        if (cardToRecordV.hb.hovered) {
            cardToRecordV.targetDrawScale = 1.0F;
        } else {
            cardToRecordV.targetDrawScale = 0.5F;
        }
        cardToRecordV.applyPowers();
        cardToRecordV.update();
    }
    public void update(){//更新充能球卡图
        super.update();
        this.setSlot(2,3);
        updatePrev();
        updateConclusion();
        updateNext();
    }

    @Override
    public void onEvoke() {
    }
    @Override
    public AbstractOrb makeCopy() {
        return null;
    }
    @Override
    public void render(SpriteBatch sb) {

        if (prev_c != null) {
            prev_c.render(sb);
        }
        // 避免 cardToRecord 置空后卡牌闪动，专用于渲染的卡牌
        if (cardToRecordV != null) {
            cardToRecordV.render(sb);
        }
        if (c!=null) {
            this.c.render(sb);
        } else {
            sb.setColor(Color.WHITE.cpy());
            int width = DiarySlotImg.getWidth();
            int height = DiarySlotImg.getHeight();
            sb.draw(DiarySlotImg,
                    this.cX - (float) width / 2,
                    this.cY - (float) height / 2,
                    (float) width / 2,
                    (float) height / 2,
                    (float) width * Settings.scale,
                    (float) height * Settings.scale,
                    1.0F,
                    1.0F,
                    0.0F,
                    0,
                    0,
                    width,
                    height,
                    false,
                    false);
            this.hb.render(sb);
        }
    }
    @Override
    public void playChannelSFX() {
    }
    public void applyFocus() {
    }


    public void removeConclusion() {
        this.c = null;
        this.prev_c = null;
        this.cardToRecord = null;
        // 如果要清除渲染，调用此函数将 cardToRecordV 置空
        this.cardToRecordV = null;
    }


    public void flashConclusion() {
        if(c!=null){
            c.flash();
        }
    }

    public void removeCardToRecord() {
        // TODO: 给点 exhuast 特效啊，不灭印记应该给个exhuast。但空想往前面插入。这两特殊情况都用这个来remove
        if (cardToRecord != null)
            cardToRecord = null;

        if (cardToRecordV != null)
            cardToRecordV = null;
    }

    public void updateSeasonalDecription() {
        if(c!=null && c.hasTag(SEASONAL)){
            ((AbstractSeasonCard)c).UpdateSeasonalDescription();
        }
        if(cardToRecord!=null && cardToRecord.hasTag(SEASONAL)){
            ((AbstractSeasonCard)cardToRecord).UpdateSeasonalDescription();
        }
        if(cardToRecordV!=null && cardToRecordV.hasTag(SEASONAL)){
            ((AbstractSeasonCard)cardToRecordV).UpdateSeasonalDescription();
        }
        if(prev_c!=null && prev_c.hasTag(SEASONAL)){
            ((AbstractSeasonCard)prev_c).UpdateSeasonalDescription();
        }
    }

    public void setCardToRecord(AbstractCard abstractCard) {
        cardToRecord = abstractCard;
    }

    @Override
    public void onEndOfTurn() {
        //回合结束时记录打出的最后一张卡牌
        super.onEndOfTurn();
        if (cardToRecord != null) {
            // 不 make_copy，牌的运动更符合直觉。
            AbstractGameAction a = new RecordCardAction(cardToRecord, false);
            AbstractDungeon.actionManager.addToTop(a);
            cardToRecord = null;
        }
    }
}
