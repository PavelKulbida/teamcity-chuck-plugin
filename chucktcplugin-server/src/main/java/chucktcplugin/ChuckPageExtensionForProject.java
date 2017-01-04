package chucktcplugin;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ChuckPageExtensionForProject extends ChuckPageExtension {

    public ChuckPageExtensionForProject(PagePlaces pagePlaces, PluginDescriptor pluginDescriptor, SBuildServer buildServer) {
        super(pagePlaces, PlaceId.PROJECT_FRAGMENT, "chucktcpluginProject", pluginDescriptor, buildServer);
    }

    @Override
    protected List<String> FilterPhrasesList(List<String> listPhrases) {

        List<String> buildPhrases = new ArrayList<String>();

        if (listPhrases.size() >= 4){
            buildPhrases.add(listPhrases.get(2));
            buildPhrases.add(listPhrases.get(3));
            buildPhrases.add(listPhrases.get(4));
        }

        return buildPhrases;
    }

    @Override
    public boolean isAvailable(@NotNull HttpServletRequest request) {
        return true;
    }

    @Override
    public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request) {

        Set<String> fails = Sets.newHashSet();
        String projectId = request.getParameter("projectId");
        SProject project = buildServer.getProjectManager().findProjectByExternalId(projectId);
        for (SBuildType buildType : project.getBuildTypes()) {
            List<SFinishedBuild> builds = buildType.getHistory();
            if (!builds.isEmpty()) {
                SFinishedBuild lastBuild = builds.iterator().next();
                if (!lastBuild.getBuildStatus().isSuccessful()) {
                     fails.add(buildType.getName());
                }
            }
        }

        model.put("chuckHappy", fails.isEmpty());
        model.put("sadImage", pluginDescriptor.getPluginResourcesPath(this.chuckSadImages.GetRandomItem()));
        model.put("happyImage", pluginDescriptor.getPluginResourcesPath(this.chuckHappyImages.GetRandomItem()));

        String chuckMessage;
        if (fails.isEmpty()) {
            chuckMessage = String.format("%1$s %2$s", this.chuckSay.GetApproveIntro(), project.getName());
        } else {
            chuckMessage = String.format("%1$s %2$s", this.chuckSay.GetDisapproveIntro(), Joiner.on(", ").join(fails));

        }
        chuckMessage += " " + this.chuckSay.GetQuotation();

        model.put("message", chuckMessage);

        model.put("existMusic", this.chuckMusic.ExistData());
        model.put("music", pluginDescriptor.getPluginResourcesPath(this.chuckMusic.GetRandomItem()));
    }
}
