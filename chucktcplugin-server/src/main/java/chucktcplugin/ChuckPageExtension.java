package chucktcplugin;

import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.SimplePageExtension;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ChuckPageExtension extends SimplePageExtension {

    protected SBuildServer buildServer;
    protected PluginDescriptor pluginDescriptor;

    protected IPhraseGenerator chuckSay;
    protected IContentGenerator chuckHappyImages;
    protected IContentGenerator chuckSadImages;
    protected IContentGenerator chuckMusic;

    public ChuckPageExtension(@NotNull PagePlaces pagePlaces,
                              @NotNull PlaceId placeId,
                              @NotNull @NonNls java.lang.String id,
                              PluginDescriptor pluginDescriptor,
                              SBuildServer buildServer) {
        super(pagePlaces, placeId, id, pluginDescriptor.getPluginResourcesPath("Chuck.jsp"));
        register();
        this.buildServer = buildServer;
        this.pluginDescriptor = pluginDescriptor;

        String lang = GetCurrentLanguage();

        chuckHappyImages = GetContentGenerator("/buildServerResources/chuckHappyImages.txt");
        chuckSadImages = GetContentGenerator("/buildServerResources/chuckSadImages.txt");
        chuckMusic = GetContentGenerator(String.format("/buildServerResources/musics_%1$s.txt", lang));

        List<String> phrases = GetResource(String.format("/buildServerResources/phrases_%1$s.txt", lang));
        IContentGenerator quotes = GetContentGenerator(String.format("/buildServerResources/quotes_%1$s.txt", lang));
        phrases = FilterPhrasesList(phrases);
        chuckSay = new PhraseGenerator(phrases, quotes);
    }

    private String GetCurrentLanguage(){

        String ret = "";

        try {
            ret = IOUtils.toString(getClass().getResourceAsStream("/buildServerResources/LanguageSettings.txt"), "UTF-8");
        } catch (IOException e) {
            Loggers.SERVER.error("Failed to load languages settings", e);
        }

        return ret;
    }

    protected List<String> FilterPhrasesList(List<String> listPhrases) {

        return listPhrases;
    }

    private IContentGenerator GetContentGenerator(String pathFile) {

        List<String> contentList = GetResource(pathFile);

        return new ContentGenerator(contentList);
    }


    private List<String> GetResource(String pathFile) {

        List<String> ret = new ArrayList<String>();

        try {
            ret = (List<String>)IOUtils.readLines(getClass().getResourceAsStream(pathFile), "UTF-8");
        } catch (IOException e) {
            Loggers.SERVER.error("Failed to load - " + pathFile, e);
        }

        return ret;
    }

}
