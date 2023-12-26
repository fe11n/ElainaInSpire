package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class AssociationEmblem extends CustomRelic {
    public static final String ID = "Elaina:AssociationEmblem";
    public AssociationEmblem() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/AssociationEmblem.png"), RelicTier.UNCOMMON, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    public void atTurnStart() {
        ElainaC p =(ElainaC) AbstractDungeon.player;
        int num = 1;
        Iterator it = AbstractDungeon.getMonsters().monsters.iterator();
        while (it.hasNext()){
            if(((AbstractMonster)it.next()).currentHealth!=0){
                num++;
            }
        }
        int strenSum = 0;
        it = AbstractDungeon.getMonsters().monsters.iterator();
        while (it.hasNext()){
            AbstractMonster m = (AbstractMonster)it.next();
            if(m.currentHealth!=0 && m.hasPower("Strength")){
                int amt = m.getPower("Strength").amount;
                if(amt>num){
                    strenSum+=amt-amt%num;
                    this.addToBot(new ReducePowerAction(m,m,"Strength", amt-amt%num));
                }
            }
        }
        if(p.hasPower("Strength") && p.getPower("Strength").amount>num){
            int amt = p.getPower("Strength").amount;
            strenSum+=amt-amt%num;
            this.addToBot(new ReducePowerAction(p,p,"Strength", amt -amt%num));
        }
        if(strenSum>0){
            this.addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,strenSum/num)));
            this.addToTop(new RelicAboveCreatureAction(p, this));
            it = AbstractDungeon.getMonsters().monsters.iterator();
            while (it.hasNext()){
                AbstractMonster m = (AbstractMonster)it.next();
                if(m.currentHealth!=0){
                    this.addToBot(new ApplyPowerAction(m,p,new StrengthPower(m,strenSum/num)));
                    this.addToTop(new RelicAboveCreatureAction(m, this));
                }
            }
        }
    }
}
