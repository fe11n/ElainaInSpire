package ElainaMod.Elaina;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.*;
import ElainaMod.relics.WanderingWitch;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.graphics.Color;

import static ElainaMod.Characters.ElainaC.Enums.*;

@SpireInitializer
public class Elaina implements EditStringsSubscriber,EditCardsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber {
   private static final String MY_CHARACTER_BUTTON = "ElainaMod/img/char/Character_Button.png";
   // 人物选择界面的立绘
   private static final String MY_CHARACTER_PORTRAIT = "ElainaMod/img/char/Character_Portrait.png";
   // 攻击牌的背景（小尺寸）
   private static final String BG_ATTACK_512 = "ElainaMod/img/512/bg_attack_512.png";
   // 能力牌的背景（小尺寸）
   private static final String BG_POWER_512 = "ElainaMod/img/512/bg_power_512.png";
   // 技能牌的背景（小尺寸）
   private static final String BG_SKILL_512 = "ElainaMod/img/512/bg_skill_512.png";
   // 在卡牌和遗物描述中的能量图标
   private static final String SMALL_ORB = "ElainaMod/img/char/small_orb.png";
   // 攻击牌的背景（大尺寸）
   private static final String BG_ATTACK_1024 = "ElainaMod/img/1024/bg_attack.png";
   // 能力牌的背景（大尺寸）
   private static final String BG_POWER_1024 = "ElainaMod/img/1024/bg_power.png";
   // 技能牌的背景（大尺寸）
   private static final String BG_SKILL_1024 = "ElainaMod/img/1024/bg_skill.png";
   // 在卡牌预览界面的能量图标
   private static final String BIG_ORB = "ElainaMod/img/char/card_orb.png";
   // 小尺寸的能量图标（战斗中，牌堆预览）
   private static final String ENEYGY_ORB = "ElainaMod/img/char/cost_orb.png";
   public static final Color MY_COLOR = new Color(79.0F / 255.0F, 185.0F / 255.0F, 9.0F / 255.0F, 1.0F);
   public Elaina(){
      BaseMod.subscribe(this);
      // 这里注册颜色
      BaseMod.addColor(EXAMPLE_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR,BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,ENEYGY_ORB,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,BIG_ORB,SMALL_ORB);
   }
   public static void initialize(){
      new Elaina();
   }

   @Override
   public void receiveEditCards(){
      //TODO 写添加卡牌的代码
      BaseMod.addCard(new BombardmentMagic());
      BaseMod.addCard(new CharmMagic());
      BaseMod.addCard(new Defend());
      BaseMod.addCard(new DestructionMagic());
      BaseMod.addCard(new GrowUp());
      BaseMod.addCard(new IceConeMagic());
      BaseMod.addCard(new IndelibleImprint());
      BaseMod.addCard(new IntensifyArray());
      BaseMod.addCard(new Recall());
      BaseMod.addCard(new Recollect());
      BaseMod.addCard(new RecreateMagic());
      BaseMod.addCard(new Rush());
      BaseMod.addCard(new Strike());
      BaseMod.addCard(new WitchRobe());
      BaseMod.addCard(new WitnessOfFriendship());
   }
   @Override
   public void receiveEditCharacters(){
      BaseMod.addCharacter(new ElainaC(CardCrawlGame.playerName),MY_CHARACTER_BUTTON,MY_CHARACTER_PORTRAIT,MY_CHARACTER);
   }
   @Override
   public void receiveEditRelics() {
      BaseMod.addRelic(new WanderingWitch(), RelicType.SHARED); // RelicType表示是所有角色都能拿到的遗物，还是一个角色的独有遗物
   }
   public void receiveEditStrings() {
      String lang;
      if (Settings.language == Settings.GameLanguage.ZHS) {
         lang = "ZHS"; // 如果语言设置为简体中文，则加载ZHS文件夹的资源
      } else {
         lang = "ENG"; // 如果没有相应语言的版本，默认加载英语
      }
      BaseMod.loadCustomStringsFile(CardStrings.class, "ElainaMod/localization/" + lang + "/cards.json"); // 加载相应语言的卡牌本地化内容。
      // 如果是中文，加载的就是"ExampleResources/localization/ZHS/cards.json"
      BaseMod.loadCustomStringsFile(CharacterStrings.class, "ElainaMod/localization/" + lang + "/characters.json");
      BaseMod.loadCustomStringsFile(RelicStrings.class, "ElainaMod/localization/" + lang + "/relics.json");
      BaseMod.loadCustomStringsFile(UIStrings.class,"ElainaMod/localization/" + lang + "/UIStrings.json");
      BaseMod.loadCustomStringsFile(PowerStrings.class,"ElainaMod/localization/" + lang + "/powers.json");
   }
}
