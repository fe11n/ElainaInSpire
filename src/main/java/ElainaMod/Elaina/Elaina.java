package ElainaMod.Elaina;

import ElainaMod.Characters.ElainaC;
import ElainaMod.cards.*;
import ElainaMod.events.DisputeEvent;
import ElainaMod.events.NoMushroomEvent;
import ElainaMod.potions.TemperamentPotion;
import ElainaMod.potions.MagicPotion;
import ElainaMod.potions.TimePotion;
import ElainaMod.relics.*;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;

import java.nio.charset.StandardCharsets;

import static ElainaMod.Characters.ElainaC.Enums.EXAMPLE_COLOR;
import static ElainaMod.Characters.ElainaC.Enums.MY_CHARACTER;
import static com.megacrit.cardcrawl.core.Settings.language;

@SpireInitializer
public class Elaina implements EditStringsSubscriber,EditCardsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber
, EditKeywordsSubscriber, AddAudioSubscriber, PostInitializeSubscriber {
   private static final String MY_CHARACTER_BUTTON = "ElainaMod/img/char/Character_Button.png";
   // 娴滆櫣澧块柅澶嬪閻ｅ矂娼伴惃鍕彌缂?
   private static final String MY_CHARACTER_PORTRAIT = "ElainaMod/img/char/Character_Portrait.png";
   // 閺€璇插毊閻楀瞼娈戦懗灞炬珯閿涘牆鐨亸鍝勵嚟閿?
   private static final String BG_ATTACK_512 = "ElainaMod/img/512/bg_attack_512.png";
   // 閼宠棄濮忛悧宀€娈戦懗灞炬珯閿涘牆鐨亸鍝勵嚟閿?
   private static final String BG_POWER_512 = "ElainaMod/img/512/bg_power_512.png";
   // 閹垛偓閼崇晫澧濋惃鍕剹閺咁垽绱欑亸蹇撴槀鐎甸潻绱?
   private static final String BG_SKILL_512 = "ElainaMod/img/512/bg_skill_512.png";
   // 閸︺劌宕遍悧灞芥嫲闁澧块幓蹇氬牚娑擃厾娈戦懗浠嬪櫤閸ョ偓鐖?
   private static final String SMALL_ORB = "ElainaMod/img/char/small_orb.png";
   // 閺€璇插毊閻楀瞼娈戦懗灞炬珯閿涘牆銇囩亸鍝勵嚟閿?
   private static final String BG_ATTACK_1024 = "ElainaMod/img/1024/bg_attack.png";
   // 閼宠棄濮忛悧宀€娈戦懗灞炬珯閿涘牆銇囩亸鍝勵嚟閿?
   private static final String BG_POWER_1024 = "ElainaMod/img/1024/bg_power.png";
   // 閹垛偓閼崇晫澧濋惃鍕剹閺咁垽绱欐径褍鏄傜€甸潻绱?
   private static final String BG_SKILL_1024 = "ElainaMod/img/1024/bg_skill.png";
   // 閸︺劌宕遍悧宀勵暕鐟欏牏鏅棃銏㈡畱閼充粙鍣洪崶鐐垼
   private static final String BIG_ORB = "ElainaMod/img/char/card_orb.png";
   // 鐏忓繐鏄傜€靛摜娈戦懗浠嬪櫤閸ョ偓鐖ｉ敍鍫熷灛閺傛ぞ鑵戦敍宀€澧濋崼鍡涱暕鐟欏牞绱?
   private static final String ENERGY_ORB = "ElainaMod/img/char/cost_orb.png";
   public static final Color MY_COLOR = new Color(79.0F / 255.0F, 185.0F / 255.0F, 9.0F / 255.0F, 1.0F);
   public Elaina(){
      BaseMod.subscribe(this);
      // 鏉╂瑩鍣峰▔銊ュ斀妫版粏澹?
      BaseMod.addColor(EXAMPLE_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR,
              BG_ATTACK_512,BG_SKILL_512,BG_POWER_512, ENERGY_ORB,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,
              BIG_ORB,SMALL_ORB);
   }
   public static void initialize(){
      new Elaina();
   }

   @Override
   public void receiveEditCards(){
      //TODO 閸愭瑦鍧婇崝鐘插幢閻楀瞼娈戞禒锝囩垳
      BaseMod.addCard(new Accelerate());
      BaseMod.addCard(new Accumulation());
      BaseMod.addCard(new AdjustPace());
      BaseMod.addCard(new AdvancedMagic());
      BaseMod.addCard(new Anticipatory());
      BaseMod.addCard(new AutumnVigilance());
      BaseMod.addCard(new Awaken());
      BaseMod.addCard(new BasicMagic());
      BaseMod.addCard(new BestState());
      BaseMod.addCard(new BombardmentMagic());
      BaseMod.addCard(new BottledHappiness());
      BaseMod.addCard(new CharmMagic());
      BaseMod.addCard(new StarryShadow());
      BaseMod.addCard(new ConvergenceMagic());
      BaseMod.addCard(new CounterMagic());
      BaseMod.addCard(new Countermeasure());
      BaseMod.addCard(new DeepMemory());
      BaseMod.addCard(new Defend());
      BaseMod.addCard(new DejaVu());
      BaseMod.addCard(new DestructionMagic());
      BaseMod.addCard(new Deviation());
      BaseMod.addCard(new Drawup());
      BaseMod.addCard(new Echo());
//      BaseMod.addCard(new Eh());
      BaseMod.addCard(new EmergencyTreatment());
      BaseMod.addCard(new Endurance());
      BaseMod.addCard(new Gifts());
      BaseMod.addCard(new ExplosiveMagic());
      BaseMod.addCard(new FirstImpression());
      BaseMod.addCard(new FlashMagic());
      BaseMod.addCard(new FragmentMagic());
      BaseMod.addCard(new FragrantWind());
      BaseMod.addCard(new Glance());
      BaseMod.addCard(new GrowUp());
      BaseMod.addCard(new HypotheticalEnemy());
      BaseMod.addCard(new IceConeMagic());
      BaseMod.addCard(new IgnisFatuus());
      BaseMod.addCard(new Ignite());
      BaseMod.addCard(new IndelibleImprint());
      BaseMod.addCard(new IntensifyArray());
      BaseMod.addCard(new IronWaveMagic());
      BaseMod.addCard(new ItsMe());
      BaseMod.addCard(new LeavesMagic());
      BaseMod.addCard(new MagicAttachment());
      BaseMod.addCard(new MagicDiffusion());
      BaseMod.addCard(new MagicEcho());
      BaseMod.addCard(new MagicSurging());
      BaseMod.addCard(new MagicTrade());
      BaseMod.addCard(new MagicTurbulence());
      BaseMod.addCard(new MarblePhantasm());
      BaseMod.addCard(new Mutation());
      BaseMod.addCard(new Nausea());
      BaseMod.addCard(new NewClothes());
      BaseMod.addCard(new Outset());
      BaseMod.addCard(new Penoff());
      BaseMod.addCard(new PerfectMagic());
      BaseMod.addCard(new Ponder());
      BaseMod.addCard(new ProtectiveMagic());
      BaseMod.addCard(new PureMagic());
      BaseMod.addCard(new Quotation());
      BaseMod.addCard(new Reappear());
      BaseMod.addCard(new Recall());
      BaseMod.addCard(new Recollect());
      BaseMod.addCard(new Reconsitution());
//      BaseMod.addCard(new RecreateMagic());
      BaseMod.addCard(new Redistribution());
      BaseMod.addCard(new Repetition());
      BaseMod.addCard(new Reversal());
      BaseMod.addCard(new Review());
      BaseMod.addCard(new Rhetoric());
      BaseMod.addCard(new Rummage());
      BaseMod.addCard(new RuneMagic());
      BaseMod.addCard(new SelfDefense());
      BaseMod.addCard(new ShowWeakness());
      BaseMod.addCard(new SilentChant());
      BaseMod.addCard(new Spell());
      BaseMod.addCard(new SpellLink());
      BaseMod.addCard(new SpellReorganization());
      BaseMod.addCard(new SpellResonance());
      BaseMod.addCard(new SpringJoy());
      BaseMod.addCard(new Strike());
      BaseMod.addCard(new Sublimation());
      BaseMod.addCard(new SummerExcitement());
      BaseMod.addCard(new ThePerfectMe());
      BaseMod.addCard(new ThunderMagic());
      BaseMod.addCard(new TimeGoesBack());
      BaseMod.addCard(new TimeMagic());
      BaseMod.addCard(new Turnback());
      BaseMod.addCard(new Vibration());
      BaseMod.addCard(new Vision());
      BaseMod.addCard(new WinterPeace());
      BaseMod.addCard(new WitchRobe());
      BaseMod.addCard(new WitchsCauldron());
      BaseMod.addCard(new WitnessOfFriendship());
      BaseMod.addCard(new WizardsWell());
   }
   @Override
   public void receiveEditCharacters(){
      BaseMod.addCharacter(new ElainaC(CardCrawlGame.playerName),MY_CHARACTER_BUTTON,MY_CHARACTER_PORTRAIT,MY_CHARACTER);
   }
   @Override
   public void receiveEditRelics() {
      BaseMod.addRelicToCustomPool(new WanderingWitch(), EXAMPLE_COLOR); // RelicType鐞涖劎銇氶弰顖涘閺堝顫楅懝鏌ュ厴閼宠姤瀣侀崚鎵畱闁澧块敍宀冪箷閺勵垯绔存稉顏囶潡閼硅尙娈戦悪顒佹箒闁澧?
      BaseMod.addRelicToCustomPool(new PortableWand(), EXAMPLE_COLOR);
      BaseMod.addRelicToCustomPool(new StoveFire(), EXAMPLE_COLOR);
      BaseMod.addRelicToCustomPool(new TimeEngraving(), EXAMPLE_COLOR);
      BaseMod.addRelicToCustomPool(new AssociationEmblem(), EXAMPLE_COLOR);
      BaseMod.addRelicToCustomPool(new WitchBracelet(), EXAMPLE_COLOR);
      BaseMod.addRelicToCustomPool(new InscriptedRuins(), EXAMPLE_COLOR);
      BaseMod.addRelicToCustomPool(new LifePotion(), EXAMPLE_COLOR);
      BaseMod.addRelicToCustomPool(new TreasureWine(), EXAMPLE_COLOR);
      BaseMod.addRelicToCustomPool(new GreedyDoll(), EXAMPLE_COLOR);
      BaseMod.addRelicToCustomPool(new NicolesAdventure(), EXAMPLE_COLOR);
      BaseMod.addRelicToCustomPool(new ShinyMushroom(), EXAMPLE_COLOR);
   }

   private String getlang(){
      String lang;
      if (language == Settings.GameLanguage.ZHS) {
         lang = "ZHS"; // 婵″倹鐏夌拠顓♀枅鐠佸墽鐤嗘稉铏圭暆娴ｆ挷鑵戦弬鍥风礉閸掓瑥濮炴潪绲慔S閺傚洣娆㈡径鍦畱鐠у嫭绨?
      }
      else if(language == Settings.GameLanguage.ENG){
         lang = "ENG";
      }
      else {
         lang = "ENG"; // 婵″倹鐏夊▽鈩冩箒閻╃绨茬拠顓♀枅閻ㄥ嫮澧楅張顒婄礉姒涙顓婚崝鐘烘祰閼昏鲸鏋?
      }
      return lang;
   }


   public void receiveEditStrings() {
      String lang = getlang();
      BaseMod.loadCustomStringsFile(CardStrings.class, "ElainaMod/localization/" + lang + "/cards.json"); // 閸旂姾娴囬惄绋跨安鐠囶叀鈻堥惃鍕幢閻楀本婀伴崷鏉垮閸愬懎顔愰妴?
      // 婵″倹鐏夐弰顖欒厬閺傚浄绱濋崝鐘烘祰閻ㄥ嫬姘ㄩ弰?ExampleResources/localization/ZHS/cards.json"
      BaseMod.loadCustomStringsFile(CharacterStrings.class, "ElainaMod/localization/" + lang + "/characters.json");
      BaseMod.loadCustomStringsFile(RelicStrings.class, "ElainaMod/localization/" + lang + "/relics.json");
      BaseMod.loadCustomStringsFile(UIStrings.class,"ElainaMod/localization/" + lang + "/UIStrings.json");
      BaseMod.loadCustomStringsFile(PowerStrings.class,"ElainaMod/localization/" + lang + "/powers.json");
      BaseMod.loadCustomStringsFile(EventStrings.class,"ElainaMod/localization/" + lang + "/events.json");
      BaseMod.loadCustomStringsFile(PotionStrings.class,"ElainaMod/localization/" + lang + "/potions.json");

   }

   @Override
   public void receiveEditKeywords() {
      Gson gson = new Gson();

      String lang = getlang();

      String json = Gdx.files.internal("ElainaMod/localization/" + lang + "/keywords.json")
              .readString(String.valueOf(StandardCharsets.UTF_8));
      Keyword[] keywords = gson.fromJson(json, Keyword[].class);
      if (keywords != null) {
         for (Keyword keyword : keywords) {
            // 鏉╂瑤閲渋d鐟曚礁鍙忕亸蹇撳晸
            BaseMod.addKeyword("elainamod",
                    keyword.NAMES[0],
                    keyword.NAMES,
                    keyword.DESCRIPTION);
         }
      }

   }

   @Override
   public void receiveAddAudio() {
      BaseMod.addAudio("ELN_WADASHI", "ElainaMod/audio/sound/eln_wadashi.ogg");
   }


   @Override
   public void receivePostInitialize() {
      BaseMod.addEvent("Elaina:NoMushroom", NoMushroomEvent.class);
      BaseMod.addEvent("Elaina:Dispute", DisputeEvent.class);

      BaseMod.addPotion(MagicPotion.class,Color.BLUE,Color.BLUE,Color.BLUE,"Elaina:MagicPotion", MY_CHARACTER);
      BaseMod.addPotion(TemperamentPotion.class,Color.GOLD,Color.GOLD,Color.GOLD,"Elaina:TemperamentPotion", MY_CHARACTER);
      BaseMod.addPotion(TimePotion.class,Color.YELLOW,Color.YELLOW,Color.YELLOW,"Elaina:TimePotion", MY_CHARACTER);
   }
}
