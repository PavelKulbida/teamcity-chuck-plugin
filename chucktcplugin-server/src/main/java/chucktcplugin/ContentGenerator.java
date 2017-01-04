package chucktcplugin;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class ContentGenerator implements IContentGenerator {

    private List<String> content;
    private Random randomizer;

    public ContentGenerator(@NotNull List<String> content) {
        this.content = content;
        this.randomizer = new Random();
    }

    public boolean ExistData() { return !content.isEmpty(); }

    public String GetRandomItem(){

        String ret = "";

        if (!content.isEmpty())
        {
            ret = content.size() > 1
                    ? content.get(randomizer.nextInt(content.size()))
                    : content.get(0);
        }

        return ret;
    }

}
