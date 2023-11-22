package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class AbstractElainaCard extends CustomCard {
    public int NotedSeasonNum;
    CardStrings strings;
    public int ExtendDamage[]={-1,-1,-1,-1};
    public int ExtendBlock[]={-1,-1,-1,-1};
    public AbstractElainaCard(String ID, CardStrings strings, String IMG_PATH, int COST, CardType TYPE,
                              CardRarity RARITY, CardTarget TARGET){
        super(ID, strings.NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE,
                CardColor.COLORLESS, RARITY, TARGET);
        NotedSeasonNum = 0;
        this.strings = strings;
    }

    @Override
    public void upgrade() {
    }
    public void BasicEffect(AbstractPlayer p, AbstractMonster m){
    }//基础效果，可以被使用和瞬发
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.hasTag(ElainaC.Enums.SHORTHAND)){
            this.addToBot(new RecordCard(this));
        }
        BasicEffect(p,m);
    }//使用
    public void InstantUse(){
        BasicEffect(AbstractDungeon.player,AbstractDungeon.getRandomMonster());
    }//瞬发
    public int getSeasonNum(){
        return ((ElainaC)(AbstractDungeon.player)).getSeason();
    }
    public void UpdateSeasonalDescription(){
        if (NotedSeasonNum!=getSeasonNum()){
            NotedSeasonNum = getSeasonNum();
            this.baseDamage = this.ExtendDamage[NotedSeasonNum];
            this.baseBlock = this.ExtendBlock[NotedSeasonNum];
            this.applyPowers();
            this.rawDescription = strings.EXTENDED_DESCRIPTION[NotedSeasonNum];
            this.initializeDescription();
        }
    }

}
