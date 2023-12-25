package ElainaMod.cardmods;

import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.cards.AbstractSeasonCard;
import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class toSeasonCardMod extends AbstractCardModifier {

    public static final Logger logger = LogManager.getLogger(toSeasonCardMod.class);

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }//时令卡牌固有的

    public toSeasonCardMod(){
    }


    @Override
    public void onInitialApplication(AbstractCard card) {
//        logger.info("Card Name: " + card.name);
//        logger.info("SeasonNum: " + ((AbstractSeasonCard)card).NotedSeasonNum);
        card.exhaust = ((AbstractSeasonCard)card).ExtendExhaust[((AbstractSeasonCard)card).NotedSeasonNum];
        card.baseDamage = ((AbstractSeasonCard)card).ExtendDamage[((AbstractSeasonCard)card).NotedSeasonNum];
        card.baseBlock = ((AbstractSeasonCard)card).ExtendBlock[((AbstractSeasonCard)card).NotedSeasonNum];
        card.baseMagicNumber = ((AbstractSeasonCard)card).ExtendMagicNum[((AbstractSeasonCard)card).NotedSeasonNum];

        String d = CardCrawlGame.languagePack.getUIString(card.cardID).TEXT[((AbstractSeasonCard)card).NotedSeasonNum];
        card.rawDescription = d;
        card.applyPowers();
        card.initializeDescription();//写在modifyDescription里会和别的modifier冲突
    }

    @Override
    public String identifier(AbstractCard card) {
        return "toSeasonCardMod";
    }

    @Override//TODO: 这里写时令卡牌再对应时令的发光变色
    public Color getGlow(AbstractCard card){
        return super.getGlow(card);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new toSeasonCardMod();
    }

}
