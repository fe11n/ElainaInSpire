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
    public boolean isInstant = false;
    public boolean isShorthand  = false;
    public static final Logger logger = LogManager.getLogger(AbstractElainaCard.class);
    public AbstractElainaCard(String ID, CardStrings strings, String IMG_PATH, int COST, CardType TYPE,
                              CardRarity RARITY, CardTarget TARGET){
        super(ID, strings.NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE,
                ElainaC.Enums.EXAMPLE_COLOR, RARITY, TARGET);
        this.strings = strings;
    }
    public AbstractElainaCard(String ID, CardStrings strings, String IMG_PATH, int COST, CardType TYPE,
                              CardRarity RARITY, CardTarget TARGET,CardColor color){
        super(ID, strings.NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE,
                color, RARITY, TARGET);
        this.strings = strings;
    }

    @Override
    public void upgrade() {
    }
    // 只有伊蕾娜能用的牌，重载这个函数。目前是过渡阶段，以后应考虑在具体的action中判断角色类型并进行保护
    public void BasicEffect(ElainaC p, AbstractMonster m){
    }
    // 伊蕾娜之外的角色可以用的牌，重载这个函数
    public void BasicEffect(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof ElainaC) {
            BasicEffect((ElainaC) p, m);
        }
        else {
            logger.info("Player is not ElainaC");
        }
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.isShorthand) {
            logger.info("Record by shorthand: " + this.name);
            this.addToBot(new RecordCardAction(this));
        }
        BasicEffect(p, m);
    }//使用
    public void triggerOnMonthChanged(int num,boolean isBack) {//由ChangeMonthAction调用
    }

    @Override
    public AbstractElainaCard makeStatEquivalentCopy() {
        AbstractElainaCard c = (AbstractElainaCard) super.makeStatEquivalentCopy();
        c.isInstant = this.isInstant;
        c.isShorthand = this.isShorthand;
        return c;
    }
}
