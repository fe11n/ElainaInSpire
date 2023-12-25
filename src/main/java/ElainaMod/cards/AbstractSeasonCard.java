package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cardmods.toSeasonCardMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import basemod.abstracts.CustomCard;

import java.util.ArrayList;
import java.util.Iterator;


public abstract class AbstractSeasonCard extends AbstractElainaCard {

    public static final Logger logger = LogManager.getLogger(AbstractSeasonCard.class);

    private float rotationTimer;
    private int previewIndex;

    public int BestSeasonNum = 0;

    public int ExtendDamage[]={-1,-1,-1,-1};
    public int ExtendBlock[]={-1,-1,-1,-1};//为时令卡准备，时节变化时实现数值变化
    public int ExtendMagicNum[] = {-1,-1,-1,-1};
    public boolean ExtendExhaust[]={false,false,false,false};

    public int NotedSeasonNum;

    public AbstractSeasonCard(String ID, CardStrings strings, String IMG_PATH, int COST, CardType TYPE,
                              CardRarity RARITY, CardTarget TARGET) {
        super(ID, strings, IMG_PATH, COST, TYPE, RARITY, TARGET);
        NotedSeasonNum = 0;//初始设置一定与当前seasonnum不同的值，保证初始一定会调用upgrade函数
        if (
                    CardCrawlGame.dungeon != null//一局游戏已打开或可继续
                && AbstractDungeon.currMapNode != null//在一局游戏中
                && AbstractDungeon.getMonsters()!=null//在战斗房
                && !AbstractDungeon.getMonsters().monsters.isEmpty()//在战斗中
        ) {
            Iterator it = AbstractDungeon.getMonsters().monsters.iterator();
            while (it.hasNext()){
//                logger.info("Check MonsterHealth...");
                if(((AbstractMonster)it.next()).currentHealth!=0){
                    this.UpdateSeasonalDescription();//战斗外获得卡时，对卡组中的时令卡也生效。理想情况：战斗外获得卡不调用这个函数
                    break;
                }
            }
//            this.UpdateSeasonalDescription();//理想情况：战斗外创建卡不调用这个函数
        }
    }
    public int getSeasonNum(){
//        logger.info("Changing Season Num...");
//        if (
//                    CardCrawlGame.dungeon != null//一局游戏已打开或可继续
//                && AbstractDungeon.currMapNode != null//在一局游戏中
//                && AbstractDungeon.getMonsters()!=null//在战斗房
//                && !AbstractDungeon.getMonsters().monsters.isEmpty()//在战斗中
//                //TODO:战斗中打开卡组，循环预览对应的时节和主要卡面不同（循环预览显示的和手牌相同）
//        ) {
//            Iterator it = AbstractDungeon.getMonsters().monsters.iterator();
//            while (it.hasNext()){
//                AbstractMonster m = (AbstractMonster)it.next();
////                logger.info("Check MonsterHealth: "+m.name);
//                if(m.currentHealth!=0){
//                    if(AbstractDungeon.player.hasPower("Elaina:BestState")){
////                        logger.info("return BestSeasonNum 1: "+BestSeasonNum);
//                        return BestSeasonNum;
//                    }
//                    logger.info("return ElainaSeason: "+ElainaC.getSeason());
//                    return ElainaC.getSeason();
//                }
//            }
////            logger.info("return BestSeasonNum 2: "+BestSeasonNum);
//            return BestSeasonNum;
////            if(AbstractDungeon.player.hasPower("Elaina:BestState")){
////                logger.info("return BestSeasonNum 1: "+BestSeasonNum);
////                return BestSeasonNum;
////            }
////            logger.info("return ElainaSeason: "+ElainaC.getSeason());
////            return ElainaC.getSeason();
//        } else {
////            logger.info("return BestSeasonNum 2: "+BestSeasonNum);
//            return BestSeasonNum;
//        }
        if(AbstractDungeon.player.hasPower("Elaina:BestState")){
//                        logger.info("return BestSeasonNum 1: "+BestSeasonNum);
            return BestSeasonNum;
        }
//        logger.info("return ElainaSeason: "+ElainaC.getSeason());
        return ElainaC.getSeason();
    }
//    public void setPreviewCard(AbstractElainaCard c){
//        springCard = c;
//        summerCard = c;
//        autumnCard = c;
//        winterCard = c;
//    }
    public boolean UpdateSeasonalDescription(){
        return UpdateSeasonalDescription(false);
    }

    public boolean UpdateSeasonalDescription(boolean forceChange){//对于时令牌，时节变化时只更新数值和描述。打出效果只有一种：参数为当前时节的switch语句。
//        logger.info("SeasonNum before change: " + NotedSeasonNum);
        if (NotedSeasonNum!=getSeasonNum() || forceChange){
//            this.flash();
//            logger.info(this.name + ": is updating...");
            NotedSeasonNum = getSeasonNum();
//            logger.info("SeasonNum: " + NotedSeasonNum);
            ArrayList<AbstractCardModifier> mods = new ArrayList<>();
            Iterator<AbstractCardModifier> it1 = CardModifierManager.modifiers(this).iterator();
//            logger.info(CardModifierManager.modifiers(this));
            while(it1.hasNext()){
                AbstractCardModifier mod = it1.next();
//                logger.info("mod name to add(1): " + mod.identifier(this));
                if(!mod.identifier(this).equals("toSeasonCardMod")){
//                    logger.info("mod name to add(over): " + mod.identifier(this));
                    mods.add(mod.makeCopy());
                }
            }
//            logger.info("mods size: " + mods.size());
            CardModifierManager.removeAllModifiers(this,true);
            CardModifierManager.addModifier(this,new toSeasonCardMod());
//            logger.info(this.name + ": " + this.rawDescription);
            Iterator<AbstractCardModifier> it2 = mods.iterator();
            while(it2.hasNext()){
                AbstractCardModifier m = it2.next();
                CardModifierManager.addModifier(this,m);
//                logger.info("Moding: "+m.identifier(this));
            }
            return true;
        }
        else return false;
    }
    public ArrayList<AbstractCard> returnProphecy() {
        ArrayList<AbstractCard> list = new ArrayList();
        int season  = BestSeasonNum;
        for(int i=1;i<5;i++){//显示全部4张
            AbstractElainaCard c = (AbstractElainaCard) this.makeStatEquivalentCopy();
            c.rawDescription = CardCrawlGame.languagePack.getUIString(c.cardID).TEXT[(season+i)%4];
            c.damage = c.baseDamage = ExtendDamage[(season+i)%4];
            c.magicNumber = c.baseMagicNumber = ExtendMagicNum[(season+i)%4];
            c.block = c.baseBlock = ExtendMagicNum[(season+i)%4];
            c.initializeDescription();
            list.add(c);
        }
        return list;
    }

    public void originUpdate(){
        super.update();
    }

    public void update() {
        super.update();
        if (this.hb.hovered) {
            if (this.rotationTimer <= 0.0F) {
                this.rotationTimer = 1F;
                this.cardsToPreview = returnProphecy().get(this.previewIndex);
                if (this.previewIndex == returnProphecy().size() - 1) {
                    this.previewIndex = 0;
                } else {
                    ++this.previewIndex;
                }
            } else {
                this.rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

//    @Override
//    public void hover() {
//        try {
//            this.springCardPreview = this.springCard;
//            this.summerCardPreview = this.summerCard;
//            this.autumnCardPreview = this.autumnCard;
//            this.winterCardPreview = this.winterCard;
//            if (this.upgraded) {
//                this.springCardPreview.upgrade();
//                this.summerCardPreview.upgrade();
//                this.autumnCardPreview.upgrade();
//                this.winterCardPreview.upgrade();
//            }
//        } catch (Throwable e) {
//            logger.warn(e.toString());
//        }
//        super.hover();
//    }
//    @Override
//    public void unhover() {
//        super.unhover();
//        this.springCardPreview = null;
//        this.summerCardPreview = null;
//        this.autumnCardPreview = null;
//        this.winterCardPreview = null;
//    }
//    public void originRenderCardTip(SpriteBatch sb) {
//        super.renderCardTip(sb);
//    }
//
//    @Override
//    public void renderCardTip(SpriteBatch sb) {
//        AbstractCard card2;
//        AbstractCard card3;
//        AbstractCard card4;
////        AbstractCard card5;
//        super.renderCardTip(sb);
//        int season  = getSeasonNum();
//        if (this.isLocked) {
//            return;
//        }
//        if (AbstractDungeon.player == null || (!AbstractDungeon.player.isDraggingCard &&
//                !AbstractDungeon.player.inSingleTargetMode)) {
//            float [] seasonX = {
//                    this.current_x - (float) 0.875*this.hb.width,
//                    this.current_x - (float) 0.875*this.hb.width,
//                    this.current_x - (float) 0.125*this.hb.width,
//            };
//            float [] seasonY = {
//                    this.current_y + (float) 0.125*this.hb.height,
//                    this.current_y + (float) 0.875*this.hb.height,
//                    this.current_y + (float) 0.875*this.hb.height,
//            };
//            // 0,0 是C位，其他不重要
////            if (!(this.springCardPreview == null || (card5 = this.springCardPreview.makeStatEquivalentCopy()) == null)) {
////                card5.rawDescription = CardCrawlGame.languagePack.getUIString(card5.cardID).TEXT[season];
////                card5.drawScale = drawScale;
////                card5.current_x = seasonX[0];
////                card5.current_y = seasonY[0];
////                card5.initializeDescription();
////
////                card5.render(sb);
////            }
//            if (!(this.summerCardPreview == null || (card4 = this.summerCardPreview.makeStatEquivalentCopy()) == null)) {
//                card4.rawDescription = CardCrawlGame.languagePack.getUIString(card4.cardID).TEXT[(season+1)%4];
//                card4.drawScale = 0.75f;
//                card4.current_x = seasonX[0];
//                card4.current_y = seasonY[0];
//                card4.damage = card4.baseDamage = ((AbstractSeasonCard)card4).ExtendDamage[(season+1)%4];
//                card4.magicNumber = card4.baseMagicNumber = ((AbstractSeasonCard)card4).ExtendMagicNum[(season+1)%4];
//                card4.block = card4.baseBlock = ((AbstractSeasonCard)card4).ExtendMagicNum[(season+1)%4];
////                if(CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null){
////                    card4.applyPowers();
////                }
//                card4.initializeDescription();
//                card4.render(sb);
//            }
//            if (!(this.autumnCardPreview == null || (card3 = this.autumnCardPreview.makeStatEquivalentCopy()) == null)) {
//                card3.rawDescription = CardCrawlGame.languagePack.getUIString(card3.cardID).TEXT[(season+2)%4];
//                card3.drawScale = 0.75f;
//                card3.current_x = seasonX[1];
//                card3.current_y = seasonY[1];
//                card3.damage = card3.baseDamage = ((AbstractSeasonCard)card3).ExtendDamage[(season+2)%4];
//                card3.magicNumber = card3.baseMagicNumber = ((AbstractSeasonCard)card3).ExtendMagicNum[(season+2)%4];
//                card3.block = card3.baseBlock = ((AbstractSeasonCard)card3).ExtendMagicNum[(season+2)%4];
////                if(CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null){
////                    card3.applyPowers();
////                }
//                card3.initializeDescription();
//                card3.render(sb);
//            }
//            if (!(this.winterCardPreview == null || (card2 = this.winterCardPreview.makeStatEquivalentCopy()) == null)) {
//                card2.rawDescription = CardCrawlGame.languagePack.getUIString(card2.cardID).TEXT[(season+3)%4];
//                card2.drawScale = (float) (0.75*drawScale);
//                card2.current_x = seasonX[2];
//                card2.current_y = seasonY[2];
//                card2.damage = card2.baseDamage = ((AbstractSeasonCard)card2).ExtendDamage[(season+3)%4];
//                card2.magicNumber = card2.baseMagicNumber = ((AbstractSeasonCard)card2).ExtendMagicNum[(season+3)%4];
//                card2.block = card2.baseBlock = ((AbstractSeasonCard)card2).ExtendMagicNum[(season+3)%4];
////                if(CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null){
////                    card2.applyPowers();
////                }
//                card2.initializeDescription();
//                card2.render(sb);
//            }
//        }
//    }
    @Override
    public AbstractCard makeCopy() {
        AbstractSeasonCard c = (AbstractSeasonCard) super.makeCopy();
        c.NotedSeasonNum=this.NotedSeasonNum;
        // 很奇怪，prebattle更新时令卡牌后，时令卡牌还会再实例化一次，
        // 这个时候新的卡牌notedseasonnum没更改，导致CardModifier的oninitialapp钩子更新数组越界，
        // 只好在实例化的时候复制notedseasonnum
        return c;
    }

}
