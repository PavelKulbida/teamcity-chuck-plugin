package chucktcplugin;

import jetbrains.buildServer.controllers.BuildDataExtensionUtil;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ChuckPageExtensionForBuild extends ChuckPageExtension {

    public ChuckPageExtensionForBuild(PagePlaces pagePlaces, PluginDescriptor pluginDescriptor, SBuildServer buildServer) {
        super(pagePlaces, PlaceId.BUILD_RESULTS_FRAGMENT, "chucktcpluginBuild", pluginDescriptor, buildServer);
    }

    @Override
    protected List<String> FilterPhrasesList(List<String> listPhrases) {

        List<String> buildPhrases = new ArrayList<String>();

        if (listPhrases.size() >= 4){
            buildPhrases.add(listPhrases.get(0));
            buildPhrases.add(listPhrases.get(1));
            buildPhrases.add(listPhrases.get(4));
        }

        return buildPhrases;
    }


    @Override
    public boolean isAvailable(@NotNull HttpServletRequest request) {
        SBuild build = getBuild(request);
        return build != null && build.isFinished();
    }

    @Override
    public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request) {

        SBuild build = getBuild(request);
        model.put("buildId", build.getBuildNumber());
        model.put("chuckHappy", build.getBuildStatus().isSuccessful());
        model.put("sadImage", pluginDescriptor.getPluginResourcesPath(this.chuckSadImages.GetRandomItem()));
        model.put("happyImage", pluginDescriptor.getPluginResourcesPath(this.chuckHappyImages.GetRandomItem()));

        String chuckMessage;
        if (build.getBuildStatus().isSuccessful()) {
            chuckMessage = this.chuckSay.GetApproveIntro();
        } else {
            chuckMessage = this.chuckSay.GetDisapproveIntro();
        }
        chuckMessage = String.format("%1$s #%2$s %3$s", chuckMessage, build.getBuildId(), this.chuckSay.GetQuotation());

        model.put("message", chuckMessage);

        model.put("existMusic", this.chuckMusic.ExistData());
        model.put("music", pluginDescriptor.getPluginResourcesPath(this.chuckMusic.GetRandomItem()));
    }

    private SBuild getBuild(HttpServletRequest request) {
        return BuildDataExtensionUtil.retrieveBuild(request, buildServer);
    }
}
