package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCardAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AbstractElainaCard extends CustomCard {
    public int NotedSeasonNum;

    public CardStrings strings;
    public int ExtendDamage[]={-1,-1,-1,-1};
    public int ExtendBlock[]={-1,-1,-1,-1};//为时令卡准备，时节变化时实现数值变化
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
        BasicEffect((ElainaC) AbstractDungeon.player,AbstractDungeon.getRandomMonster());
    }//瞬发
    public int getSeasonNum(){
        return ((ElainaC)(AbstractDungeon.player)).getSeason();
    }
    public boolean UpdateSeasonalDescription(){
        return UpdateSeasonalDescription(false);
    }

    public boolean UpdateSeasonalDescription(boolean forceChange){//对于时令牌，时节变化时只更新数值和描述。打出效果只有一种：参数为当前时节的switch语句。
        if (NotedSeasonNum!=getSeasonNum() || forceChange){
            logger.info(this.name + ": is updating...");
            NotedSeasonNum = getSeasonNum();
            this.exhaust = this.ExtendExhaust[NotedSeasonNum];
            this.baseDamage = this.ExtendDamage[NotedSeasonNum];
            this.baseBlock = this.ExtendBlock[NotedSeasonNum];
            this.applyPowers();
            this.rawDescription = strings.EXTENDED_DESCRIPTION[NotedSeasonNum];

            this.initializeDescription();
            return true;
        }
        else return false;
    }
    public void toHandfromDiary(){
        if(!this.exhaust){
            this.exhaust = true;
            this.rawDescription = this.rawDescription
                    +" NL "
                    + CardCrawlGame.languagePack.getUIString("Elaina:Exhaust").TEXT[0];
        }
        if(!this.isEthereal){
            this.isEthereal = true;
            this.rawDescription = this.rawDescription
                    +" NL "
                    + CardCrawlGame.languagePack.getUIString("Elaina:Ethereal").TEXT[0];
        }
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return super.makeCopy();
    }

    public AbstractElainaCard makeInstanceCopy(){
        AbstractElainaCard c = (AbstractElainaCard) this.makeCopy();
        if(this.upgraded){
            c.upgrade();
        }
        c.NotedSeasonNum=this.NotedSeasonNum;
        c.rawDescription=this.rawDescription;
        c.exhaust = this.exhaust;
        c.isEthereal = this.isEthereal;
        c.tags = this.tags;
        c.baseDamage = this.baseDamage;
        c.baseBlock = this.baseBlock;
        c.applyPowers();
        return c;
    }
}
