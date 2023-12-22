package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.GetDiaryCardAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class WitnessOfFriendship extends AbstractSeasonCard {
    public static final String ID = "Elaina:WitnessOfFriendship";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/WitnessOfFriendship.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public WitnessOfFriendship() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 15;
        this.tags.add(ElainaC.Enums.SEASONAL);
        this.NotedSeasonNum = this.BestSeasonNum = 0;
        this.exhaust = true;
        this.ExtendExhaust[0]=this.ExtendExhaust[1]=true;
        this.ExtendMagicNum[0]=this.ExtendMagicNum[1]=15;
//        setPreviewCard(this);
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(10);
            this.ExtendMagicNum[0]=this.ExtendMagicNum[1]=25;
        }
    }

    public int getSeasonNum(){
//        if (
//                CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null
//        ) {
//            int m = ElainaC.getSeason();
//            if(AbstractDungeon.player.hasPower("Elaina:BestState")){
//                m = 1;
//            }
//            if(m == 1 && !ElainaC.UsedYear.contains((ElainaC.Month-1)/12)) return 0;
//            else return 1;
//        }else {
//            return BestSeasonNum;
//        }
        int m = ElainaC.getSeason();
        if(AbstractDungeon.player.hasPower("Elaina:BestState")){
            m = 1;
        }
        if(m == 1 && !ElainaC.UsedYear.contains((ElainaC.Month-1)/12)) return 0;
        else return 1;
    }
    public void update() {
        super.originUpdate();
        int season  = (NotedSeasonNum+1)%2;
        AbstractElainaCard c = (AbstractElainaCard) this.makeStatEquivalentCopy();
        c.rawDescription = CardCrawlGame.languagePack.getUIString(c.cardID).TEXT[season];
        c.magicNumber = c.baseMagicNumber = ExtendMagicNum[season];
        c.initializeDescription();
        this.cardsToPreview = c;
    }
//    @Override
//    public void renderCardTip(SpriteBatch sb) {
//        AbstractCard card5;
////        originRenderCardTip(sb);
//        if (this.isLocked) {
//            return;
//        }
//        NotedSeasonNum = getSeasonNum();
//        if (AbstractDungeon.player == null || (!AbstractDungeon.player.isDraggingCard &&
//                !AbstractDungeon.player.inSingleTargetMode)) {
//            // 0,0 是C位，其他不重要
//            if (!(this.springCardPreview == null || (card5 = this.springCardPreview.makeStatEquivalentCopy()) == null)) {
//                card5.rawDescription = CardCrawlGame.languagePack.getUIString(card5.cardID).TEXT[(NotedSeasonNum+1)%2];
//                card5.drawScale = (float) (0.75*drawScale);
//                card5.current_x = this.current_x - (float) 0.875*this.hb.width;
//                card5.current_y = this.current_y + (float) 0.125*this.hb.height;
//                card5.initializeDescription();
//
//                card5.render(sb);
//            }
//        }
//    }
    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    @Override
    public void BasicEffect(ElainaC p, AbstractMonster m) {
        switch (getSeasonNum()){
            case 0:
                this.addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractRelic r = AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON);
                        logger.info("P get relic: "+ r.name);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, r);
                        //r.atBattleStart();
                        ElainaC.UsedYear.add((ElainaC.Month -1)/12);
                        p.UpdateAllSeasonalDescription();
                        this.isDone = true;
                    }
                });
                break;
            case 1:
                this.addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        p.gainGold(magicNumber);
                        for(int i = 0; i < magicNumber; ++i) {
                            //AbstractDungeon.effectList.add(new GainPennyEffect(this.source, this.target.hb.cX, this.target.hb.cY, this.source.hb.cX, this.source.hb.cY, true));
                        }
                        this.isDone = true;
                    }
                });
                break;
        }

    }
}
