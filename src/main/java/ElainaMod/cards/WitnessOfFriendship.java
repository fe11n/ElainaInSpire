package ElainaMod.cards;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.GetDiaryCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class WitnessOfFriendship extends AbstractElainaCard {
    public static final String ID = "Elaina:WitnessOfFriendship";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String IMG_PATH = "ElainaMod/img/cards/WitnessOfFriendship.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public WitnessOfFriendship() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, CARD_STRINGS, IMG_PATH, COST, TYPE, RARITY, TARGET);
        this.baseMagicNumber = 8;
        this.tags.add(ElainaC.Enums.SEASONAL);
    }

    @Override
    public void upgrade() { // 升级调用的方法
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public int getSeasonNum(){
        if(!this.upgraded){
            if(((ElainaC)(AbstractDungeon.player)).Month==1){
                return 0;
            }
            else return 1;
        }
        else {
            if(((ElainaC)(AbstractDungeon.player)).Month==1 || ((ElainaC)(AbstractDungeon.player)).Month==2 ){
                return 0;
            }
            else return 1;
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
        switch (this.getSeasonNum()){
            case 0:
                this.addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        p.getRelic(AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON).relicId);
                        this.isDone = true;
                    }
                });
                break;
            case 1:
                this.addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        p.gainGold(magicNumber);
                        this.isDone = true;
                    }
                });
                break;
        }

    }
}
