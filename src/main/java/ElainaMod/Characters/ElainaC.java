package ElainaMod.Characters;

import ElainaMod.Elaina.Elaina;
import ElainaMod.action.RecordCardAction;
import ElainaMod.cardmods.toImageCardMod;
import ElainaMod.cards.*;
import ElainaMod.orb.ConclusionOrb;
import ElainaMod.relics.AbstractBookRelic;
import ElainaMod.relics.WanderingWitch;
import basemod.abstracts.CustomPlayer;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static ElainaMod.Characters.ElainaC.Enums.*;

public class ElainaC extends CustomPlayer{
    private static final String MY_CHARACTER_SHOULDER_1 = "ElainaMod/img/char/shoulder.png";
    // 火堆的人物立绘（行动后）
    private static final String MY_CHARACTER_SHOULDER_2 = "ElainaMod/img/char/shoulder2.png";
    // 人物死亡图像
    private static final String CORPSE_IMAGE = "ElainaMod/img/char/corpse.png";
    // 战斗界面左下角能量图标的每个图层
    private static final String[] ORB_TEXTURES = new String[]{
            "ElainaMod/img/UI/orb/layer5.png",
            "ElainaMod/img/UI/orb/layer4.png",
            "ElainaMod/img/UI/orb/layer3.png",
            "ElainaMod/img/UI/orb/layer2.png",
            "ElainaMod/img/UI/orb/layer1.png",
            "ElainaMod/img/UI/orb/layer6.png",
            "ElainaMod/img/UI/orb/layer5d.png",
            "ElainaMod/img/UI/orb/layer4d.png",
            "ElainaMod/img/UI/orb/layer3d.png",
            "ElainaMod/img/UI/orb/layer2d.png",
            "ElainaMod/img/UI/orb/layer1d.png"
    };
    // 每个图层的旋转速度
    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};
    // 人物的本地化文本，如卡牌的本地化文本一样，如何书写见下
    private static final CharacterStrings characterStrings =
            CardCrawlGame.languagePack.getCharacterString("Elaina:ElainaC");
    public static CardGroup DiaryGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);//存储日记的抽象数组
    public static int Month = new Random().nextInt(12)+1;//时节变量
    //public static int Month = 12;//时节变量
    public static int FarYear = 0;
    public static final Logger logger = LogManager.getLogger(ElainaC.class);
    public static ArrayList<Integer> UsedYear = new ArrayList<>();

    public ElainaC(String name) {
        super(name,
                MY_CHARACTER,
                ORB_TEXTURES,
                "ElainaMod/img/UI/orb/vfx.png",
                LAYER_SPEED,
                null,
                null);

        // 人物对话气泡的大小，如果游戏中尺寸不对在这里修改（libgdx的坐标轴左下为原点）
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);


        // 初始化你的人物，如果你的人物只有一张图，那么第一个参数填写你人物图片的路径。
        this.initializeClass(
                "ElainaMod/img/char/character.png", // 人物图片
                MY_CHARACTER_SHOULDER_2, MY_CHARACTER_SHOULDER_1,
                CORPSE_IMAGE, // 人物死亡图像
                this.getLoadout(),
                0.0F, 0.0F,
                200.0F, 220.0F, // 人物碰撞箱大小，越大的人物模型这个越大
                new EnergyManager(3) // 初始每回合的能量
        );
        if (!CardCrawlGame.loadingSave && AbstractDungeon.floorNum < 2) {
            Month = new Random().nextInt(12)+1;
            UsedYear = new ArrayList<>();
            FarYear = 0;
        }


        // 如果你的人物没有动画，那么这些不需要写
        // this.loadAnimation("ExampleModResources/img/char/character.atlas", "ExampleModResources/img/char/character.json", 1.8F);
        // AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        // e.setTime(e.getEndTime() * MathUtils.random());
        // e.setTimeScale(1.2F);

        // 初始卡组的ID，可直接写或引用变量

    }

    public static boolean isInstant(AbstractCard c){
        if (c==null) {
            return false;
        }
        if(c instanceof AbstractElainaCard) return ((AbstractElainaCard) c).isInstant;
        else return CardModifierManager.hasModifier(c,"toInstantCardMod");
    }
    public static boolean isNotable(AbstractCard c){
        return !(   c.exhaust
                || c.type == AbstractCard.CardType.POWER
                || c.hasTag(ElainaC.Enums.UNNOTABLE)
                || c.cost < 0 );
    }
    public static void InstantUse(AbstractCard c){
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        c.calculateCardDamage(m);
        c.use(AbstractDungeon.player,m);
    }
    public static void toHand(AbstractCard c){
        CardModifierManager.addModifier(c, new toImageCardMod());
    }
    public static int getSeason(){
        return (Month%12)>=0?(Month%12)/3:(Month%12+12)/3;
    }//0，1，2，3分别表示冬，春，夏，秋
    public void ChangeMonth(int num, boolean upgradeDeck){
        Month = num;
        logger.info("Change to: "+num);
        if((Month-1)/12>FarYear){
            FarYear = (Month-1)/12;
            if(this.hasRelic("Elaina:WanderingWitch")){
                AbstractDungeon.player.gainGold(50);
                logger.info("Get 50 gold.");
            }else if(this.hasRelic("Elaina:NicolesAdventure")){
                AbstractDungeon.player.increaseMaxHp(4,true);
                logger.info("Get 4 MaxHp.");
            }
        }
        if(upgradeDeck){
            UpdateAllSeasonalDescription();
            if( this.relics.get(0) instanceof AbstractBookRelic){
                ((AbstractBookRelic) this.relics.get(0)).UpdateCounter();
            }
        }
    }
    public void ChangeMonth(int num){//更改月份调用该函数，更新遗物并更新卡牌描述
        ChangeMonth(num,true);
    }
    public void UpdateAllSeasonalDescription(){
        UpdateSeasonalDescription(this.discardPile);
        UpdateSeasonalDescription(this.drawPile);
        UpdateSeasonalDescription(this.hand);
        UpdateSeasonalDescription(DiaryGroup,true);
        this.getConclusionOrb().updateSeasonalDecription();

    }
    private void UpdateSeasonalDescription(CardGroup g){
        UpdateSeasonalDescription(g,false);
    }

    private void UpdateSeasonalDescription(CardGroup g, boolean isDiary){
        Iterator it = g.group.iterator();
        while (it.hasNext()){
            AbstractCard ca = (AbstractCard) it.next();
            if(ca instanceof AbstractElainaCard){ //防止状态、诅咒牌引起报错
                AbstractElainaCard c = (AbstractElainaCard) ca;
                if(c.hasTag(SEASONAL)){
                    if(((AbstractSeasonCard)c).UpdateSeasonalDescription() && isDiary && !(it.hasNext())){
                        //在Diary结语位置且需要更新时
                        ConclusionOrb orb = (ConclusionOrb) this.orbs.get(0);
                        orb.setCurConclusion(c);
                    }
                }
            }
        }
    }

    @Override
    public void applyPreCombatLogic() {
        ElainaC.DiaryGroup.clear();//战斗开始时清空，不管sl了
        ElainaC p = (ElainaC)AbstractDungeon.player;
        ConclusionOrb.getInstance().removeConclusion();
        p.channelOrb(ConclusionOrb.getInstance());
        p.UpdateAllSeasonalDescription();
        super.applyPreCombatLogic();
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        super.useCard(c, monster, energyOnUse);
        if(AbstractDungeon.player.hasRelic("Elaina:NicolesAdventure"))return;
        // 在这里更新每回合结语卡。
        if (ElainaC.isNotable(c)) {
            ConclusionOrb.getInstance().setCardToRecord(c.makeStatEquivalentCopy());
        } else {
            ConclusionOrb.getInstance().removeCardToRecord();
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        DiaryGroup.group.clear();
    }


    public AbstractCard getConclusion(){
        if(!DiaryGroup.isEmpty()){
            return (DiaryGroup.getBottomCard()).makeStatEquivalentCopy(); // 避免抢渲染，返回拷贝。
        }
        else return null;
    }
    public ConclusionOrb getConclusionOrb(){
        return ConclusionOrb.getInstance();
    }
    public int getDiarySize(){
        return DiaryGroup.size();
    }
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for(int x = 0; x<4; x++) {
            retVal.add(Strike.ID);
        }
        for(int x = 0; x<4; x++) {
            retVal.add(Defend.ID);
        }
        retVal.add(Rhetoric.ID);
        retVal.add(BasicMagic.ID);
        return retVal;
    }

    // 初始遗物的ID，可以先写个原版遗物凑数
    public ArrayList<String> getStartingRelics(){
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(WanderingWitch.ID);
        return retVal;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                characterStrings.NAMES[0], // 人物名字
                characterStrings.TEXT[0], // 人物介绍
                75, // 当前血量
                75, // 最大血量
                1, // 初始充能球栏位
                50, // 初始携带金币
                5, // 每回合抽牌数量
                this, // 别动
                this.getStartingRelics(), // 初始遗物
                this.getStartingDeck(), // 初始卡组
                false // 别动
        );
    }

    // 人物名字（出现在游戏左上角）
    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    // 你的卡牌颜色（这个枚举在最下方创建）
    @Override
    public AbstractCard.CardColor getCardColor() {
        return EXAMPLE_COLOR;
    }

    // 翻牌事件出现的你的职业牌（一般设为打击）
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    // 卡牌轨迹颜色
    @Override
    public Color getCardTrailColor() {
        return Elaina.MY_COLOR;
    }

    // 高进阶带来的生命值损失
    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    // 卡牌的能量字体，没必要修改
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    // 人物选择界面点击你的人物按钮时触发的方法，这里为屏幕轻微震动
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playV("ELN_WADASHI", 1);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    // 碎心图片
    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        // 有两个参数的，第二个参数表示出现图片时播放的音效
        panels.add(new CutscenePanel("ElainaMod/img/char/Victory1.png", "ATTACK_MAGIC_FAST_1"));
        panels.add(new CutscenePanel("ElainaMod/img/char/Victory2.png"));
        panels.add(new CutscenePanel("ElainaMod/img/char/Victory3.png"));
        return panels;
    }

    // 自定义模式选择你的人物时播放的音效
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ELN_WADASHI";
    }

    // 游戏中左上角显示在你的名字之后的人物名称
    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    // 创建人物实例，照抄
    @Override
    public AbstractPlayer newInstance() {
        return new ElainaC(this.name);
    }

    // 第三章面对心脏说的话（例如战士是“你握紧了你的长刀……”之类的）
    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    // 打心脏的颜色，不是很明显
    @Override
    public Color getSlashAttackColor() {
        return Elaina.MY_COLOR;
    }

    // 吸血鬼事件文本，主要是他（索引为0）和她（索引为1）的区别（机器人另外）
    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }

    // 卡牌选择界面选择该牌的颜色
    @Override
    public Color getCardRenderColor() {
        return Elaina.MY_COLOR;
    }

    // 第三章面对心脏造成伤害时的特效
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    public void doDeltaConclusionCostForTurn(int i) {
        if(!DiaryGroup.isEmpty()) {
            AbstractCard c = DiaryGroup.getBottomCard();
            c.setCostForTurn(c.costForTurn + i);
        }
    }

    // 为原版人物枚举、卡牌颜色枚举扩展的枚举，需要写，接下来要用
    // ***填在SpireEnum中的name需要一致***
    public static class Enums {
        @SpireEnum
        public static PlayerClass MY_CHARACTER;

        @SpireEnum(name = "ELAINA")
        public static AbstractCard.CardColor EXAMPLE_COLOR;

        @SpireEnum(name = "ELAINA")
        public static CardLibrary.LibraryType EXAMPLE_LIBRARY;
        @SpireEnum
        public static AbstractCard.CardTags MAGIC;//魔法
        @SpireEnum
        public static AbstractCard.CardTags SEASONAL;//时令
        @SpireEnum
        public static AbstractCard.CardTags UNNOTABLE;//不可被记录
    }
}

