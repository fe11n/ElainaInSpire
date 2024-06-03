package ElainaMod.powers;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.WizardsWell;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
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
        spellLinkGainBlock(num);
    }

    private void spellLinkGainBlock(int num) {
        if(owner.hasPower("Elaina:SpellLink")){
            if (isCallerTogetherInSpire()) {
                logger.info("跳过联机模组的调用，避免多次增加防御值");
                return;
            }
            owner.getPower("Elaina:SpellLink").flash();
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (StackTraceElement element : stackTrace) {
                logger.info(element.toString());
            }
            logger.info("gainblock "+num);
            addToBot(new GainBlockAction(owner,num));
        }
    }

    // Together In Spire 会清空所有 power 再重新加上，导致防御被多次添加。这里做判断，如果是那边调用就不加防御。
    private static boolean isCallerTogetherInSpire() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // Iterate through the stack trace elements
        for (StackTraceElement element : stackTrace) {
            // Check if the calling method is UpdatePowers in P2PPlayer class
            if (element.getClassName().startsWith("spireTogether")) {
                return true; // Caller is UpdatePowers
            }
        }
        return false; // Caller is not UpdatePowers
    }
    public void updateDescription(){this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];}
    public void onUseCard(AbstractCard card, UseCardAction action){
        int  los;
        if(card.hasTag(ElainaC.Enums.MAGIC)){
            this.flash();
            if(AbstractDungeon.player instanceof ElainaC &&
                    !(((ElainaC)AbstractDungeon.player).getConclusion()!=null &&
                    ((ElainaC)AbstractDungeon.player).getConclusion() instanceof WizardsWell)){
                los = this.amount - this.amount /2;
            }
            else {
                los = 1;
            }
            this.addToBot(new ReducePowerAction(owner,owner,"Elaina:SpellBoost",los));
            if(owner.hasPower("Elaina:SpellResonance")){
                owner.getPower("Elaina:SpellResonance").flash();
                addToBot(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(los, true), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.NONE, true));
            }
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return (card.hasTag(ElainaC.Enums.MAGIC) && !owner.hasPower("Elaina:AdvancedMagic"))?damage+amount:damage;
    }
    public float modifyBlock(float blockAmount,AbstractCard c) {
        if(c.hasTag(ElainaC.Enums.MAGIC)){
            return (blockAmount += (float)this.amount) < 0.0F ? 0.0F : blockAmount;
        }else return blockAmount;
    }
}
