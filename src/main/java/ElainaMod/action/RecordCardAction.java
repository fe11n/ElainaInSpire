package ElainaMod.action;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.AbstractElainaCard;
import ElainaMod.cards.IndelibleImprint;
import ElainaMod.cards.MarblePhantasm;
import ElainaMod.orb.ConclusionOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecordCardAction extends AbstractGameAction {
    public AbstractCard c;
    private boolean isNotable = false;
    public CardGroup g;
    public static final Logger logger = LogManager.getLogger(RecordCardAction.class);
    public RecordCardAction(AbstractCard c){
        this.actionType = ActionType.CARD_MANIPULATION;
        isNotable = ElainaC.isNotable(c);
        if(isNotable){
            this.c = c.makeStatEquivalentCopy();
        }
    }
    public RecordCardAction(AbstractCard c, boolean make_copy){
        this.actionType = ActionType.CARD_MANIPULATION;
        isNotable = ElainaC.isNotable(c);
        if(isNotable){
            if (make_copy) {
                this.c = c.makeStatEquivalentCopy();
            } else {
                this.c = c;
            }
        }
    }
    @Override
    public void update() {
        if (!(AbstractDungeon.player instanceof ElainaC)) {
            // 记牌是伊蕾娜的人物能力，其他人不能记牌。
            this.isDone = true;
            return;
        }

        if (isNotable) {
            g = ElainaC.DiaryGroup;
            ElainaC p=(ElainaC)AbstractDungeon.player;

            // 不灭印记的特殊情况，不考虑写出去？
            if(!g.isEmpty() && p.getConclusion() instanceof IndelibleImprint){
                c = p.getConclusion();
                ConclusionOrb orb = p.getConclusionOrb();
                orb.flashConclusion();
                orb.removeCardToRecord();
                this.addToBot(
                        new DamageAction(
                                AbstractDungeon.getRandomMonster(),
                                new DamageInfo(p,c.magicNumber, DamageInfo.DamageType.THORNS)
                        )
                );

                this.isDone=true;
                return;
            }

            logger.info("Record in Diary: "+c.name);
            // 空想具现化的特殊情况，作为结语记录也记录在开头？
            if(c instanceof MarblePhantasm){
                g.addToTop(c);
                logger.info("Diary size after record: "+g.size());
                // 如果是唯一一张
                ConclusionOrb orb = ConclusionOrb.getInstance();
                if(g.size()==1){
                    orb.setCurConclusion(c);
                } else {
                    orb.removeCardToRecord(); //清理本来显示的
                }
                this.isDone=true;
                return;
            }

            // 正常情况。
            g.addToBottom(c.makeStatEquivalentCopy()); // 这里 make copy 是为了避免日记和结语槽抢卡牌渲染。
            ConclusionOrb orb = ConclusionOrb.getInstance();
            orb.pushConclusion(c);
            orb.removeCardToRecord();
            logger.info("Diary size after record: "+g.size());
        }
        this.isDone=true;
    }
}
