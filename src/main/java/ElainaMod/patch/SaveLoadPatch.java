package ElainaMod.patch;

import ElainaMod.Characters.ElainaC;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class SaveLoadPatch {
    public static int[] DateArray = new int[2];

    public SaveLoadPatch() {
    }
    public static final Logger logger = LogManager.getLogger(SaveLoadPatch.class);

    public class ElainaSaveFile extends SaveFile{
        public int[] DateArray = new int[2];
    }

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
            params.put("DateArray", DateArray);
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
        public static void loadCustomSaveData(String path, Gson gson, String savestr) {
            try {
                ElainaSaveFile data = gson.fromJson(savestr, ElainaSaveFile.class);
                DateArray = data.DateArray;
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
                logger.info("send data to Elaina over*************");
            }
        }
    }
}
