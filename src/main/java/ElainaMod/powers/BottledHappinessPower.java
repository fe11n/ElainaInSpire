package ElainaMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Iterator;

public class BottledHappinessPower extends AbstractPower {
    public static final String POWER_ID = "Elaina:BottledHappiness";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public BottledHappinessPower(AbstractCreature o, int magicNumber){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = magicNumber;
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/BottledHappinessPower.png");
    }
    public void updateDescription(){this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];}

    public void atEndOfTurn(boolean isPlayer) {
        Iterator it  = AbstractDungeon.getMonsters().monsters.iterator();
        while(it.hasNext()){
            AbstractMonster m = (AbstractMonster)it.next();
            if(m.hasPower("Strength")){
                int strAmt = m.getPower("Strength").amount;
                if(strAmt>0){
                    this.addToBot(new DamageAction(m, new DamageInfo(owner, strAmt*amount, DamageInfo.DamageType.HP_LOSS)));
                }
            }
        }
    }
}
