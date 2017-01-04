package chucktcplugin;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PhraseGenerator implements IPhraseGenerator {

    private List<String> phrases;
    private IContentGenerator quotes;

    public PhraseGenerator(@NotNull List<String> phrases,
                           @NotNull IContentGenerator quotes ) {
        this.phrases = phrases;
        this.quotes = quotes;
    }


    public String GetApproveIntro() {
        return phrases.size() > 0 ? phrases.get(0) : "";
    }

    public String GetDisapproveIntro() {
        return phrases.size() > 1 ? phrases.get(1) : "";
    }

    public String GetQuotation() {

        String ret =  phrases.size() > 2 ? phrases.get(2) + " " : "";

        ret += quotes.GetRandomItem();

        return ret;
    }

}
