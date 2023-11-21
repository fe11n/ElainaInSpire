package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AbstractElainaCard extends CustomCard {
    public AbstractElainaCard(String ID, CardStrings strings, String IMG_PATH, int COST, CardType TYPE,
                              CardRarity RARITY, CardTarget TARGET){
        super(ID, strings.NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE,
                CardColor.COLORLESS, RARITY, TARGET);
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


}
