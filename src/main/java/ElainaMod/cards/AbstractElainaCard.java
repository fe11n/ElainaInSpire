package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCardAction;
import ElainaMod.cardmods.toImageCardMod;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


public class AbstractElainaCard extends CustomCard {

    public CardStrings strings;
    public ArrayList<String> ModStrings = new ArrayList<>();
    public boolean isInstant = false;
    public boolean isShorthand  = false;
    public static final Logger logger = LogManager.getLogger(AbstractElainaCard.class);
    public AbstractElainaCard(String ID, CardStrings strings, String IMG_PATH, int COST, CardType TYPE,
                              CardRarity RARITY, CardTarget TARGET){
        super(ID, strings.NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE,
                ElainaC.Enums.EXAMPLE_COLOR, RARITY, TARGET);
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
    public boolean isNotable(){return !(this.hasTag(ElainaC.Enums.UNNOTABLE) || this.type == CardType.POWER || this.exhaust == true || this.cost<0);}

    public void toHandfromDiary(){
        CardModifierManager.addModifier(this,new toImageCardMod());
        this.current_x = ((ElainaC) AbstractDungeon.player).getConclusionOrb().tX;
        this.current_y = ((ElainaC) AbstractDungeon.player).getConclusionOrb().tY;
    }

    @Override
    public AbstractElainaCard makeStatEquivalentCopy() {
        AbstractElainaCard c = (AbstractElainaCard) super.makeStatEquivalentCopy();
        c.isInstant = this.isInstant;
        c.isShorthand = this.isShorthand;
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
