package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import basemod.abstracts.CustomCard;


public abstract class AbstractSeasonCard extends AbstractElainaCard {
//    public SeasonVars.Season season_;
    public AbstractElainaCard springCard;
    public AbstractElainaCard summerCard;
    public AbstractElainaCard autumnCard;
    public AbstractElainaCard winterCard;
//    public AbstractElainaCard toCard;
    public AbstractElainaCard springCardPreview;
    public AbstractElainaCard summerCardPreview;
    public AbstractElainaCard autumnCardPreview;
    public AbstractElainaCard winterCardPreview;

    public static final Logger logger = LogManager.getLogger(AbstractSeasonCard.class);

    public static int BestSeasonNum = 0;

    public AbstractSeasonCard(String ID, CardStrings strings, String IMG_PATH, int COST, CardType TYPE,
                              CardRarity RARITY, CardTarget TARGET) {
        super(ID, strings, IMG_PATH, COST, TYPE, RARITY, TARGET);
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null) {
            this.UpdateSeasonalDescription();
        }
    }
    public static int getSeasonNum(){
        if (
//                CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null
                AbstractDungeon.player != null
        ) {
            return ElainaC.getSeason();
        }else {
            return BestSeasonNum;
        }

    }
    public void setPreviewCard(AbstractElainaCard c){
        springCard = c;
        summerCard = c;
        autumnCard = c;
        winterCard = c;
    }
    public void setPreviewCard(AbstractElainaCard spring, AbstractElainaCard summer, AbstractElainaCard autumn,
                               AbstractElainaCard winter){
        springCard = spring;
        summerCard = summer;
        autumnCard = autumn;
        winterCard = winter;
    }
//    public void changeSeason(){
//        if (!this.isLocked){
//            this.season_ = SeasonVars.getSeasonEnum();
//            transform();
//        }
//    }
//    public void transform(){
//        if (this.season_ == SeasonVars.Season.SPRING) {
//            toCard = this.springCard;
//        } else if (this.season_== SeasonVars.Season.SUMMER) {
//            toCard = this.summerCard;
//        }else if (this.season_ == SeasonVars.Season.AUTUMN) {
//            toCard = this.autumnCard;
//        } else if (this.season_ == SeasonVars.Season.WINTER) {
//            toCard = this.winterCard;
//        } else {
//            // fallback to spring
//            logger.warn("时令卡无对应时令，使用春季卡属性");
//            toCard = this.springCard;
//        }
//        if (toCard != null){
//            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(this.toCard.cardID).DESCRIPTION;
//            if (this.upgraded) {
//                this.toCard.upgrade();
//                if (CardCrawlGame.languagePack.getCardStrings(this.toCard.cardID).UPGRADE_DESCRIPTION != null) {
//                    this.rawDescription =
//                            CardCrawlGame.languagePack.getCardStrings(this.toCard.cardID).UPGRADE_DESCRIPTION;
//                }
//            }
//            this.name = this.toCard.name;
//            this.target = this.toCard.target;
//            this.cost = this.toCard.cost;
//            this.costForTurn = this.toCard.costForTurn;
//            this.isCostModified = false;
//            this.isCostModifiedForTurn = false;
//            this.energyOnUse = this.toCard.energyOnUse;
//            this.freeToPlayOnce = this.toCard.freeToPlayOnce;
//            this.exhaust = this.toCard.exhaust;
//            this.retain = this.toCard.retain;
//            this.purgeOnUse = this.toCard.purgeOnUse;
//            this.baseDamage = this.toCard.baseDamage;
//            this.baseBlock = this.toCard.baseBlock;
//            this.baseDraw = this.toCard.baseDraw;
//            this.baseMagicNumber = this.toCard.baseMagicNumber;
//            this.baseHeal = this.toCard.baseHeal;
//            this.baseDiscard = this.toCard.baseDiscard;
//            this.isLocked = this.toCard.isLocked;
//            this.misc = this.toCard.misc;
//            loadCardImage(this.toCard.textureImg);
//            initializeDescription();
//        }
//    }
//    @Override
//    public void upgrade() {
//        if (!this.upgraded) {
//            upgradeName();
//            transform();
//        }
//    }
//    @Override
//    public void atTurnStart() {
//        super.atTurnStart();
//        if (this.toCard != null) {
//            this.toCard.atTurnStart();
//        }
//    }
    @Override
    public void hover() {
        try {
            this.springCardPreview = this.springCard;
            this.summerCardPreview = this.summerCard;
            this.autumnCardPreview = this.autumnCard;
            this.winterCardPreview = this.winterCard;
            if (this.upgraded) {
                this.springCardPreview.upgrade();
                this.summerCardPreview.upgrade();
                this.autumnCardPreview.upgrade();
                this.winterCardPreview.upgrade();
            }
        } catch (Throwable e) {
            logger.warn(e.toString());
        }
        super.hover();
    }
    @Override
    public void unhover() {
        super.unhover();
        this.springCardPreview = null;
        this.summerCardPreview = null;
        this.autumnCardPreview = null;
        this.winterCardPreview = null;
    }
    public void originRenderCardTip(SpriteBatch sb) {
        super.renderCardTip(sb);
    }

    @Override
    public void renderCardTip(SpriteBatch sb) {
        AbstractCard card2;
        AbstractCard card3;
        AbstractCard card4;
//        AbstractCard card5;
        super.renderCardTip(sb);
        if (this.isLocked) {
            return;
        }
        if (AbstractDungeon.player == null || (!AbstractDungeon.player.isDraggingCard &&
                !AbstractDungeon.player.inSingleTargetMode)) {
            int season = getSeasonNum();
            float [] seasonX = {
                    this.current_x - (float) 0.875*this.hb.width,
                    this.current_x - (float) 0.875*this.hb.width,
                    this.current_x - (float) 0.125*this.hb.width,
            };
            float [] seasonY = {
                    this.current_y + (float) 0.125*this.hb.height,
                    this.current_y + (float) 0.875*this.hb.height,
                    this.current_y + (float) 0.875*this.hb.height,
            };
            // 0,0 是C位，其他不重要
//            if (!(this.springCardPreview == null || (card5 = this.springCardPreview.makeStatEquivalentCopy()) == null)) {
//                card5.rawDescription = CardCrawlGame.languagePack.getUIString(card5.cardID).TEXT[season];
//                card5.drawScale = drawScale;
//                card5.current_x = seasonX[0];
//                card5.current_y = seasonY[0];
//                card5.initializeDescription();
//
//                card5.render(sb);
//            }
            if (!(this.summerCardPreview == null || (card4 = this.summerCardPreview.makeStatEquivalentCopy()) == null)) {
                card4.rawDescription = CardCrawlGame.languagePack.getUIString(card4.cardID).TEXT[(season+1)%4];
                card4.drawScale = (float) (0.75*drawScale);
                card4.current_x = seasonX[0];
                card4.current_y = seasonY[0];
                card4.initializeDescription();
                card4.render(sb);
            }
            if (!(this.autumnCardPreview == null || (card3 = this.autumnCardPreview.makeStatEquivalentCopy()) == null)) {
                card3.rawDescription = CardCrawlGame.languagePack.getUIString(card3.cardID).TEXT[(season+2)%4];
                card3.drawScale = (float) (0.75*drawScale);
                card3.current_x = seasonX[1];
                card3.current_y = seasonY[1];
                card3.initializeDescription();
                card3.render(sb);
            }
            if (!(this.winterCardPreview == null || (card2 = this.winterCardPreview.makeStatEquivalentCopy()) == null)) {
                card2.rawDescription = CardCrawlGame.languagePack.getUIString(card2.cardID).TEXT[(season+3)%4];
                card2.drawScale = (float) (0.75*drawScale);
                card2.current_x = seasonX[2];
                card2.current_y = seasonY[2];
                card2.initializeDescription();
                card2.render(sb);
            }
        }
    }

}
