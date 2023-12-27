package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cardmods.toSeasonCardMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public int NotedSeasonNum = -1;//初始设置一定与当前seasonnum不同的值，保证初始一定会调用upgrade函数


    public AbstractSeasonCard(String ID, CardStrings strings, String IMG_PATH, int COST, CardType TYPE,
                              CardRarity RARITY, CardTarget TARGET) {
        super(ID, strings, IMG_PATH, COST, TYPE, RARITY, TARGET);
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
                    this.UpdateSeasonalDescription();//理想情况：战斗外获得卡不调用这个函数
                    break;
                }
            }
//            this.UpdateSeasonalDescription();//理想情况：战斗外创建卡不调用这个函数
        }

    }
    public int getSeasonNum(){
        if(AbstractDungeon.player.hasPower("Elaina:BestState")||AbstractDungeon.player.hasRelic("Elaina:StoveFire")){
                        logger.info("return BestSeasonNum 1: "+BestSeasonNum);
            return BestSeasonNum;
        }
        // logger.info("return ElainaSeason: "+ElainaC.getSeason()); 太吵了
        return ElainaC.getSeason();
    }
    public boolean UpdateSeasonalDescription(){
        return UpdateSeasonalDescription(false);
    }

    public boolean UpdateSeasonalDescription(boolean forceChange){
        //对于时令牌，时节变化时只更新数值和描述。打出效果只有一种：参数为当前时节的switch语句。
//        logger.info(this.name + ": is updating...");
//        logger.info("SeasonNum before change: " + NotedSeasonNum);
        if (
                true
                        ||NotedSeasonNum!=getSeasonNum()
                        || forceChange
        ){
//            this.flash();
            NotedSeasonNum = getSeasonNum();
//            logger.info("SeasonNum: " + NotedSeasonNum);
            ArrayList<AbstractCardModifier> mods = new ArrayList<>();
            //            logger.info(CardModifierManager.modifiers(this));
            for (AbstractCardModifier mod : CardModifierManager.modifiers(this)) {
                //                logger.info("mod name to add(1): " + mod.identifier(this));
                if (!mod.identifier(this).equals("toSeasonCardMod")) {
//                    logger.info("mod name to add(over): " + mod.identifier(this));
                    mods.add(mod.makeCopy());
                }
            }
//            logger.info("mods size: " + mods.size());
            CardModifierManager.removeAllModifiers(this,true);
            CardModifierManager.addModifier(this,new toSeasonCardMod());
//            logger.info(this.name + ": " + this.rawDescription);
            for (AbstractCardModifier m : mods) {
                CardModifierManager.addModifier(this, m);
//                logger.info("Moding: "+m.identifier(this));
            }
//            this.applyPowers();
//            this.initializeDescription();
            return true;
        }
        else return false;
    }
    public ArrayList<AbstractCard> returnProphecy() {
        ArrayList<AbstractCard> list = new ArrayList();
        int season  = BestSeasonNum;
        for(int i=1;i<5;i++){//显示全部4张
            AbstractElainaCard c = (AbstractElainaCard) this.makeCopy();
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
    @Override
    public AbstractCard makeCopy() {
        AbstractSeasonCard c = (AbstractSeasonCard) super.makeCopy();
        c.NotedSeasonNum=this.NotedSeasonNum;
        // 很奇怪，prebattle更新时令卡牌后，时令卡牌还会再实例化一次，
        // 这个时候新的卡牌notedseasonnum没更改，导致CardModifier的oninitialapp钩子更新数组越界，
        // 只好在实例化的时候复制notedseasonnum
        return c;
    }
    @Override
    public AbstractSeasonCard makeStatEquivalentCopy() {
        AbstractSeasonCard c = (AbstractSeasonCard) super.makeStatEquivalentCopy();
        c.BestSeasonNum =  BestSeasonNum;
        c.ExtendDamage = ExtendDamage;
        c.ExtendBlock = ExtendBlock;
        c.ExtendMagicNum = ExtendMagicNum;
        c.ExtendExhaust = ExtendExhaust;
        c.NotedSeasonNum = NotedSeasonNum;
        return c;
    }

}
