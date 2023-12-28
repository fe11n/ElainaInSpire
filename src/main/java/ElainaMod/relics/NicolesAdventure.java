package ElainaMod.relics;

import ElainaMod.Characters.ElainaC;
import ElainaMod.action.RecordCardAction;
import ElainaMod.cardmods.toInstantCardMod;
import ElainaMod.cards.*;
import ElainaMod.orb.ConclusionOrb;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.GamblingChipAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class NicolesAdventure extends AbstractBookRelic {
    public static final String ID = "Elaina:NicolesAdventure";
    private boolean activated = false;
    public NicolesAdventure() {
        super(ID, ImageMaster.loadImage("ElainaMod/img/relics/NicolesAdventure.png"), RelicTier.BOSS, LandingSound.FLAT);
    }
    public void onEnterRoom(AbstractRoom room) {
        logger.info("Month before enter: " + (AbstractDungeon.player != null ? ElainaC.Month : "null"));

        try {
            // TODO: 遗物不依赖人物存在，时令也不依赖人物存在。等着重构吧。
            if (AbstractDungeon.player != null && room.phase == AbstractRoom.RoomPhase.COMBAT) {
                ElainaC p = (ElainaC) AbstractDungeon.player;
                p.ChangeMonth(ElainaC.Month + 2, false);
                UpdateCounter();
            } else {
                logger.info("Player object (p) is null. Cannot change month.");
            }
        } catch (ClassCastException e) {
            logger.error("Failed to cast player object to ElainaC.", e);
            // 在这里添加适当的处理逻辑，或者记录其他信息
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            // 在这里添加适当的处理逻辑，或者记录其他信息
            throw new RuntimeException(e);
        }

        logger.info("Month after enter: " + (AbstractDungeon.player != null ? ElainaC.Month : "null"));
        this.isDone = true;
    }
    public void atBattleStartPreDraw() {
        this.activated = false;
    }

    public void atTurnStartPostDraw() {
        if (!this.activated) {
            this.activated = true;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    for(AbstractCard c:AbstractDungeon.player.hand.group){
                        if(c instanceof AbstractElainaCard){
                            AbstractElainaCard ec = (AbstractElainaCard) c;
                            if(!ec.isInstant){
                                CardModifierManager.addModifier(ec,new toInstantCardMod());
                            }
                        }
                        this.addToBot(new RecordCardAction(c));
                    }
                    this.isDone = true;
                }
            });
        }
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {}
    public void obtain() {
        if (AbstractDungeon.player.hasRelic("Elaina:WanderingWitch")) {
            this.instantObtain(AbstractDungeon.player, 0, false);
        } else {
            super.obtain();
        }

    }
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic("Elaina:WanderingWitch");
    }
}
