package ElainaMod.patch;

import ElainaMod.Characters.ElainaC;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class SaveLoadPatch {
    private static final String KEY_DATE_ARRAY = "ElainaMod:DateArray";
    private static final String KEY_USED_YEAR = "ElainaMod:UsedYear";
    private static final String LEGACY_KEY_DATE_ARRAY = "DateArray";
    private static final String LEGACY_KEY_USED_YEAR = "UsedYear";

    private static final String LEGACY_PLAYER_CLASS_NAME = "MY_CHARACTER";
    private static final String PLAYER_CLASS_NAME = "ELAINA";

    public static int[] DateArray = new int[2];
    public static ArrayList<Integer> UsedYear = new ArrayList<>();

    public SaveLoadPatch() {
    }
    public static final Logger logger = LogManager.getLogger(SaveLoadPatch.class);

    @SpirePatch(
            clz = SaveFile.class,
            method = "<ctor>",
            paramtypez = {SaveFile.SaveType.class}
    )
    public static class SaveTheSaveData {
        public SaveTheSaveData() {
        }

        @SpirePostfixPatch
        public static void saveAllTheSaveData(SaveFile __instance, SaveFile.SaveType type) {
            if(AbstractDungeon.player != null && AbstractDungeon.player instanceof ElainaC){
                logger.info("get data start*************");
                ElainaC p = (ElainaC)AbstractDungeon.player;
                DateArray[0] = p.Month;
                DateArray[1] = p.FarYear;
                UsedYear = new ArrayList<>(p.UsedYear);
                logger.info("get data over*************");
            }
        }
    }

    @SpirePatch(
            clz = SaveAndContinue.class,
            method = "save",
            paramtypez = {SaveFile.class}
    )
    public static class SaveDataToFile {
        public SaveDataToFile() {
        }

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"params"}
        )
        public static void addCustomSaveData(SaveFile save, HashMap<Object, Object> params) {
            if(!(AbstractDungeon.player instanceof ElainaC)){
                return;
            }
            params.put(KEY_DATE_ARRAY, DateArray);
            params.put(KEY_USED_YEAR, UsedYear);
            logger.info("save data to file over*************");
        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(GsonBuilder.class, "create");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = SaveAndContinue.class,
            method = "loadSaveFile",
            paramtypez = {String.class}
    )
    public static class LoadDataFromFile {
        public LoadDataFromFile() {
        }

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"gson", "savestr"}
        )
        public static void loadCustomSaveData(String path, Gson gson, @ByRef String[] savestr) {
            try {
                if(savestr != null && savestr.length > 0 && savestr[0] != null){
                    savestr[0] = migrateLegacyPlayerClassIfNeeded(savestr[0]);
                    loadCustomSaveDataFromJson(savestr[0]);
                }
                logger.info("load data from file over*************");
            } catch (Exception var4) {
                var4.printStackTrace();
            }

        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Gson.class, "fromJson");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "loadSave"
    )
    public static class loadSave {
        public loadSave() {
        }

        @SpirePostfixPatch
        public static void loadSave(AbstractDungeon __instance, SaveFile file) {
            if(AbstractDungeon.player instanceof ElainaC){
                ElainaC p = (ElainaC)AbstractDungeon.player;
                p.Month = DateArray[0];
                p.FarYear = DateArray[1];
                p.UsedYear = UsedYear;
                logger.info("send data to Elaina over*************");
            }
        }
    }

    private static void loadCustomSaveDataFromJson(String savestr) {
        JsonObject saveObj = new JsonParser().parse(savestr).getAsJsonObject();

        int[] loadedDateArray = tryGetIntArray(saveObj, KEY_DATE_ARRAY);
        if(loadedDateArray == null){
            loadedDateArray = tryGetIntArray(saveObj, LEGACY_KEY_DATE_ARRAY);
        }
        if(loadedDateArray != null && loadedDateArray.length >= 2){
            DateArray = loadedDateArray;
        }

        ArrayList<Integer> loadedUsedYear = tryGetIntList(saveObj, KEY_USED_YEAR);
        if(loadedUsedYear == null){
            loadedUsedYear = tryGetIntList(saveObj, LEGACY_KEY_USED_YEAR);
        }
        if(loadedUsedYear != null){
            UsedYear = loadedUsedYear;
        }
    }

    private static int[] tryGetIntArray(JsonObject saveObj, String key) {
        if(!saveObj.has(key)){
            return null;
        }
        JsonElement element = saveObj.get(key);
        if(!(element instanceof JsonArray)){
            return null;
        }
        JsonArray array = element.getAsJsonArray();
        int[] result = new int[array.size()];
        for(int i = 0; i < array.size(); i++){
            result[i] = array.get(i).getAsInt();
        }
        return result;
    }

    private static ArrayList<Integer> tryGetIntList(JsonObject saveObj, String key) {
        if(!saveObj.has(key)){
            return null;
        }
        JsonElement element = saveObj.get(key);
        if(!(element instanceof JsonArray)){
            return null;
        }
        JsonArray array = element.getAsJsonArray();
        ArrayList<Integer> result = new ArrayList<>();
        for(JsonElement entry : array){
            result.add(entry.getAsInt());
        }
        return result;
    }

    private static String migrateLegacyPlayerClassIfNeeded(String savestr) {
        if(!savestr.contains("\"" + LEGACY_PLAYER_CLASS_NAME + "\"")){
            return savestr;
        }
        if(!looksLikeElainaSave(savestr)){
            return savestr;
        }
        return savestr.replace("\"" + LEGACY_PLAYER_CLASS_NAME + "\"", "\"" + PLAYER_CLASS_NAME + "\"");
    }

    private static boolean looksLikeElainaSave(String savestr) {
        if(savestr.contains("\"Elaina:WanderingWitch\"")){
            return true;
        }
        return countOccurrences(savestr, "\"Elaina:") >= 8;
    }

    private static int countOccurrences(String haystack, String needle) {
        int count = 0;
        int index = 0;
        while((index = haystack.indexOf(needle, index)) >= 0){
            count++;
            index += needle.length();
        }
        return count;
    }
}
