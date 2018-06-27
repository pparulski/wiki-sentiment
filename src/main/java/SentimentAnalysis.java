import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SentimentAnalysis {

    public static void main(String[] args) throws IOException {
        String url = "https://en.wikipedia.org/wiki/Walter_Burley";
        Connection connect = Jsoup.connect(url);
        Document document = connect.get();
        Elements allp = document.select("p");
        String allp1 = allp.text();
        System.out.println(allp1);

        String text = allp1;

        SentimentAlg sentimentAnalyzer = new SentimentAlg();

        sentimentAnalyzer.initialize();

        SentimentPrint sentimentResult = sentimentAnalyzer.getSentimentResult(text);


        System.out.println("Sentiments Classification:");

        System.out.println("Very positive: " + sentimentResult.getSentimentClass().getVeryPositive() + "%");

        System.out.println("Positive: " + sentimentResult.getSentimentClass().getPositive() + "%");

        System.out.println("Neutral: " + sentimentResult.getSentimentClass().getNeutral() + "%");

        System.out.println("Negative: " + sentimentResult.getSentimentClass().getNegative() + "%");

        System.out.println("Very negative: " + sentimentResult.getSentimentClass().getVeryNegative() + "%");

        System.out.println("\nSentiments result:");

        System.out.println("Sentiment Score: " + sentimentResult.getSentimentScore());

        System.out.println("Sentiment Type: " + sentimentResult.getSentimentType());


    }
}
