package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class AssociationEmblem extends CustomRelic {
    public static final String ID = "Elaina:AssociationEmblem";
    public static final Logger logger = LogManager.getLogger(AssociationEmblem.class);
    ArrayList<AbstractCreature> priCr;
    public AssociationEmblem() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/AssociationEmblem.png"), RelicTier.RARE, LandingSound.FLAT);
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
    public void atTurnStart() {
        priCr = new ArrayList<>();
        ElainaC p =(ElainaC) AbstractDungeon.player;
        int num = 1;
        for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
            if(m.currentHealth>0){
                num++;
                this.priCr.add(m);
            }
        }
        int strenSum = 0;
        for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
            if(m.currentHealth>0 && m.hasPower("Strength")){
                int amt = m.getPower("Strength").amount;
                strenSum+=amt;
            }
        }
        if(p.hasPower("Strength")){
            int amt = p.getPower("Strength").amount;
            strenSum+=amt;
            this.priCr.add(p);
        }
        Collections.sort(priCr,new Comparator<AbstractCreature>(){
            @Override
            public int compare(AbstractCreature T, AbstractCreature o) {
                return (o.hasPower("Strength")?o.getPower("Strength").amount:0)
                        - (T.hasPower("Strength")?T.getPower("Strength").amount:0);
            }
        });
        if(strenSum>0){
            if(p.hasPower("Strength")){
                int amt = p.getPower("Strength").amount;
                this.addToBot(new ReducePowerAction(p,null,"Strength", amt));
            }
            if(strenSum/num>0){
                this.addToBot(new ApplyPowerAction(p,null,new StrengthPower(p,strenSum/num)));
                this.addToTop(new RelicAboveCreatureAction(p, this));
            }

            for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
                if(m.currentHealth>0){
                    if(m.hasPower("Strength")){
                        int amt = m.getPower("Strength").amount;
                        this.addToBot(new ReducePowerAction(m,null,"Strength", amt));
                    }
                    if(strenSum/num>0){
                        this.addToBot(new ApplyPowerAction(m,null,new StrengthPower(m,strenSum/num)));
                        this.addToTop(new RelicAboveCreatureAction(m, this));
                    }
                }
            }
            int los = strenSum%num;
            for(AbstractCreature cr:priCr){
                if(los>0){
//                    logger.info("Creature name added los: "+cr.id);
                    this.addToTop(new RelicAboveCreatureAction(cr, this));
                    this.addToBot(new ApplyPowerAction(cr,null,new StrengthPower(cr,1)));
                    los--;
                }
                else break;
            }
        }
    }
}
