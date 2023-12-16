package ElainaMod.powers;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.WizardsWell;
import ElainaMod.orb.ConclusionOrb;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpellBoostPower extends AbstractPower {
    public static final String POWER_ID = "Elaina:SpellBoost";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(SpellBoostPower.class);
    public SpellBoostPower(AbstractCreature o,int num){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = num;
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/SpellBoostPower.png");
        if(owner.hasPower("Elaina:SpellLink")){
            owner.getPower("Elaina:SpellLink").flash();
            addToBot(new GainBlockAction(owner,num));
        }
    }
    public void updateDescription(){this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];}
    public void onUseCard(AbstractCard card, UseCardAction action){
        if(card.hasTag(ElainaC.Enums.MAGIC) && !(((ElainaC)AbstractDungeon.player).getConclusion()!=null && ((ElainaC)AbstractDungeon.player).getConclusion() instanceof WizardsWell)){
            int los = this.amount - this.amount /2;
            if(owner.hasPower("Elaina:SpellResonance")){
                owner.getPower("Elaina:SpellResonance").flash();
                addToBot(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(los, true), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.NONE, true));
            }
            this.amount /=2;
            if(this.amount<=0){
                addToBot(new RemoveSpecificPowerAction(owner,owner,"Elaina:SpellBoost"));
            }
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return card.hasTag(ElainaC.Enums.MAGIC)?damage+amount:damage;
    }
}
