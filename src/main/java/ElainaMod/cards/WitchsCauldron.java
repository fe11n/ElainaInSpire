package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class WitchsCauldron extends AbstractElainaCard {
    public static final String ID = "Elaina:WitchsCauldron";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/WitchsCauldron.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private float rotationTimer;
    private int previewIndex;

    public WitchsCauldron() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.exhaust = true;
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }
    public static ArrayList<AbstractCard> returnProphecy() {
        ArrayList<AbstractCard> list = new ArrayList();
        list.add(new Penoff());
        list.add(new Echo());
        list.add(new ItsMe());
        list.add(new Repetition());
        list.add(new Accelerate());
        list.add(new Deviation());
        list.add(new Spell());
        list.add(new Reappear());
        list.add(new FragmentMagic());
        list.add(new Reversal());
        list.add(new Mutation());
        list.add(new Insight());
        list.add(new Smite());
        list.add(new Safety());
        list.add(new Miracle());
        list.add(new ThroughViolence());
        return list;
    }

    public void update() {
        super.update();
        if (this.hb.hovered) {
            if (this.rotationTimer <= 0.0F) {
                this.rotationTimer = 0.6F;
                this.cardsToPreview = returnProphecy().get(this.previewIndex).makeCopy();
                if (this.previewIndex == returnProphecy().size() - 1) {
                    this.previewIndex = 0;
                } else {
                    ++this.previewIndex;
                }
            } else {
                this.rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }
    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    @Override
    public void BasicEffect(ElainaC p, AbstractMonster m) {
        for(int i = 0;i<this.magicNumber;i++){
            this.addToBot(new MakeTempCardInHandAction(
                    returnProphecy().get(
                            AbstractDungeon.cardRandomRng.random(returnProphecy().size()-1)
                    )
                )
            );
        }
    }
}
