package ElainaMod.powers;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.MagicDiffusionAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResidualMagicPower extends AbstractPower {
    public static final String POWER_ID = "Elaina:ResidualMagic";
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    ;
    public static final String NAME = powerstrings.NAME;

    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(ResidualMagicPower.class);
    private AbstractCreature source;

    public ResidualMagicPower(AbstractCreature o, AbstractCreature s ,int num) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = o;
        this.source = s;
        this.amount = num;
        this.type = PowerType.DEBUFF;
        if (this.amount >= 9999) {
            this.amount = 9999;
        }
        this.updateDescription();
        this.img = new Texture("ElainaMod/img/powers/ResidualMagicPower.png");
        this.isTurnBased = true;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
    private void doDamage(AbstractPlayer p) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
                && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            int m = 0;
            for(AbstractCard c:p.hand.group){
                if(c.selfRetain){
                    m++;
                }
            }
            if(m>0){
//                // 用这个 hp 不会同步。
//                this.addToBot(new PoisonLoseHpAction(this.owner, p,
//                        this.amount*m, AbstractGameAction.AttackEffect.FIRE));

                // 如果不同步，不同主机生命值显示会不同
                this.addToBot(new DamageAction(owner, new DamageInfo(p, this.amount*m,
                        DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
    }

    public void atStartOfTurn() {
        // 为了兼容联机模组，区分玩家类
        if (source.getClass().getName().equals("spireTogether.monsters.playerChars.NetworkDefaultChar")) {
            // 实际联机的时候能力的 source 只有一个，除了第一个引发效果的人都会进这个分支
            logger.info("其他的 ResidualMagicPower "+ source);
            // 现在的逻辑是所有玩家都造成伤害
            doDamage(AbstractDungeon.player);
            return;
        }
        logger.info("初次引发效果的 ResidualMagicPower: "+ source);
        doDamage((AbstractPlayer) source);
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("ATTACK_MAGIC_FAST_1", 0.05f);
    }
}
