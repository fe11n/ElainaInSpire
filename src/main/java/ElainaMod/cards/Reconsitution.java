package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.GetDiaryCardAction;
import ElainaMod.powers.GetSpellBoostPower;
import ElainaMod.powers.MagicDiffusionPower;
import ElainaMod.powers.SpellBoostPower;
import ElainaMod.powers.TimeGoesBackPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.WellLaidPlans;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.DevotionPower;
import com.megacrit.cardcrawl.powers.watcher.ForesightPower;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;

import java.util.ArrayList;
import java.util.Iterator;

public class Reconsitution extends AbstractElainaCard {
    public static final String ID = "Elaina:Reconsitution";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/Reconsitution.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Reconsitution() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID,CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 0;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    @Override
    public void applyPowers() {
        this.baseMagicNumber = this.magicNumber = ((ElainaC)AbstractDungeon.player).DiaryGroup.size();
        super.applyPowers();
        this.rawDescription = this.upgraded?CARD_STRINGS.UPGRADE_DESCRIPTION:CARD_STRINGS.DESCRIPTION
                + CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }
    public void onMoveToDiscard() {
        this.rawDescription = this.upgraded?CARD_STRINGS.UPGRADE_DESCRIPTION:CARD_STRINGS.DESCRIPTION;
        this.initializeDescription();
    }
    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    public void BasicEffect(ElainaC p, AbstractMonster m){
        this.addToBot(new AbstractGameAction() {
            int m;
            public AbstractGameAction getMagicNum(int num){
                m = num;
                return this;
            }
            @Override
            public void update() {
                for(int i = 0;i<p.getDiarySize();i++){
                    this.addToBot(new GetDiaryCardAction(p,false));
                }
                for(int i = 0;i<m;i++){
                    this.addToBot(new ApplyPowerAction(p,p,
                            getPowers(p).get(
                                            AbstractDungeon.miscRng.random(getPowers(p).size()-1)
                                    )
                            )
                    );
                }
                this.isDone = true;
            }
        }.getMagicNum(
                this.upgraded?magicNumber*2:magicNumber*1
                ));
    }//基础效果，可以被使用和瞬发

    ArrayList<AbstractPower> getPowers(AbstractPlayer p){
        ArrayList<AbstractPower> g = new ArrayList();
        g.add(new BufferPower(p,1));
        g.add(new ArtifactPower(p,1));
        g.add(new StrengthPower(p,1));
        g.add(new DexterityPower(p,1));
        g.add(new SpellBoostPower(p,1));
        g.add(new MetallicizePower(p,1));
        g.add(new ThornsPower(p,1));
        g.add(new RegenPower(p,1));
        g.add(new DrawCardNextTurnPower(p,1));
        g.add(new EnergizedBluePower(p,1));
        g.add(new GetSpellBoostPower(p,1));
        g.add(new TimeGoesBackPower(p,1));
        g.add(new RitualPower(p,1,true));
        g.add(new BlurPower(p,1));
        g.add(new NoxiousFumesPower(p,1));
        g.add(new FreeAttackPower(p,1));
        g.add(new MayhemPower(p,1));
        g.add(new ToolsOfTheTradePower(p,1));
        g.add(new ForesightPower(p,1));
        g.add(new AfterImagePower(p,1));
        g.add(new DevotionPower(p,1));
        g.add(new EnvenomPower(p,1));
        g.add(new JuggernautPower(p,1));
        g.add(new InfiniteBladesPower(p,1));
        g.add(new ThousandCutsPower(p,1));
        g.add(new MagicDiffusionPower(p,1));
        g.add(new HeatsinkPower(p,1));
        g.add(new RetainCardPower(p,1));
        return g;
    }
}
