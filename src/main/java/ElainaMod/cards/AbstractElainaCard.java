package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCardAction;
import ElainaMod.cardmods.toImageCardMod;
import ElainaMod.cardmods.toSeasonCardMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;


public class AbstractElainaCard extends CustomCard {
    public int NotedSeasonNum;

    public CardStrings strings;
    public ArrayList<String> ModStrings = new ArrayList<>();
    public int ExtendDamage[]={-1,-1,-1,-1};
    public int ExtendBlock[]={-1,-1,-1,-1};//为时令卡准备，时节变化时实现数值变化
    public int ExtendMagicNum[] = {-1,-1,-1,-1};
    public boolean ExtendExhaust[]={false,false,false,false};
    public boolean isInstant = false;
    public boolean isShorthand  = false;
    public static final Logger logger = LogManager.getLogger(AbstractElainaCard.class);
    public AbstractElainaCard(String ID, CardStrings strings, String IMG_PATH, int COST, CardType TYPE,
                              CardRarity RARITY, CardTarget TARGET){
        super(ID, strings.NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE,
                ElainaC.Enums.EXAMPLE_COLOR, RARITY, TARGET);
        NotedSeasonNum = -1;//初始设置一定与当前seasonnum不同的值，保证初始一定会调用upgrade函数
        this.strings = strings;
    }

    @Override
    public void upgrade() {
    }
    public void BasicEffect(ElainaC p, AbstractMonster m){
    }//基础效果，可以被使用和瞬发
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.isShorthand){
            logger.info("Record by shorthand: " + this.name);
            this.addToBot(new RecordCardAction(this));
        }
        BasicEffect((ElainaC) p,m);
    }//使用
    public void InstantUse(){
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        this.calculateCardDamage(m);
        BasicEffect((ElainaC) AbstractDungeon.player,m);
        logger.info("Modifier include toInstantCardMod: "+CardModifierManager.hasModifier(this,"toInstantCardMod"));
    }//瞬发
    public void triggerOnMonthChanged(int num,boolean isBack) {//由ChangeMonthAction调用
    }
    public static int getSeasonNum(){
        return ElainaC.getSeason();
    }
    public boolean isNotable(){return !(this.hasTag(ElainaC.Enums.UNNOTABLE) || this.type == CardType.POWER || this.exhaust == true || this.cost<0);}
    public boolean UpdateSeasonalDescription(){
        return UpdateSeasonalDescription(false);
    }
//    public void applyPowers(){
//        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
//        AbstractPlayer p = AbstractDungeon.player;
//        if(this.hasTag(ElainaC.Enums.MAGIC) && p.hasPower("Elaina:SpellBoost")){
//            int strAmt = p.getPower("Elaina:SpellBoost").amount;
//            if(strength == null){
//                p.powers.add(new StrengthPower(p,strAmt));
//            }
//            else {
//                strength.amount += strAmt;//这个函数在指向敌人时持续更新，如果直接让baseDamage自增，则会让它持续增加
//            }
//        }
//        super.applyPowers();
//        if(this.hasTag(ElainaC.Enums.MAGIC) && p.hasPower("Elaina:SpellBoost")){
//            strength = AbstractDungeon.player.getPower("Strength");
//            int strAmt = p.getPower("Elaina:SpellBoost").amount;
//            strength.amount -= strAmt;
//            if(strength.amount == 0){
//                p.powers.remove(p.getPower("Strength"));
//            }
//        }
//    }
    public boolean UpdateSeasonalDescription(boolean forceChange){//对于时令牌，时节变化时只更新数值和描述。打出效果只有一种：参数为当前时节的switch语句。
        if (NotedSeasonNum!=getSeasonNum() || forceChange){
            logger.info(this.name + ": is updating...");
            NotedSeasonNum = getSeasonNum();
            logger.info("SeasonNum: " + NotedSeasonNum);
            ArrayList<AbstractCardModifier> mods = new ArrayList<>();
            Iterator<AbstractCardModifier> it1 = CardModifierManager.modifiers(this).iterator();
            logger.info(CardModifierManager.modifiers(this));
            while(it1.hasNext()){
                AbstractCardModifier mod = it1.next();
                logger.info("mod name to add(1): " + mod.identifier(this));
                if(!mod.identifier(this).equals("toSeasonCardMod")){
                    logger.info("mod name to add(over): " + mod.identifier(this));
                    mods.add(mod.makeCopy());
                }
            }
            logger.info("mods size: " + mods.size());
            CardModifierManager.removeAllModifiers(this,true);
            logger.info("Is ethereal after remove modifiers: " + this.isEthereal);
            CardModifierManager.addModifier(this,new toSeasonCardMod());
            logger.info(this.name + ": " + this.rawDescription);
            Iterator<AbstractCardModifier> it2 = mods.iterator();
            while(it2.hasNext()){
                AbstractCardModifier m = it2.next();
                CardModifierManager.addModifier(this,m);
                logger.info("Moding: "+m.identifier(this));
            }
            return true;
        }
        else return false;
    }
    public void toHandfromDiary(){
        CardModifierManager.addModifier(this,new toImageCardMod());
    }

    @Override
    public AbstractCard makeCopy() {
        AbstractElainaCard c = (AbstractElainaCard) super.makeCopy();
        c.NotedSeasonNum=this.NotedSeasonNum;
        // 很奇怪，prebattle更新时令卡牌后，时令卡牌还会再实例化一次，
        // 这个时候新的卡牌notedseasonnum没更改，导致CardModifier的oninitialapp钩子更新数组越界，
        // 只好在实例化的时候复制notedseasonnum
        return c;
    }

//    public AbstractElainaCard makeInstanceCopy(){//这个方法不能复制mod
//        AbstractElainaCard c = (AbstractElainaCard) this.makeCopy();
//        if(this.upgraded){
//            c.upgrade();
//        }
//        c.NotedSeasonNum=this.NotedSeasonNum;
//        c.rawDescription=this.rawDescription;
//        c.exhaust = this.exhaust;
//        c.isEthereal = this.isEthereal;
//        c.tags = this.tags;
//        c.baseDamage = this.baseDamage;
//        c.baseBlock = this.baseBlock;
//        c.applyPowers();
//        return c;
//    }
}
