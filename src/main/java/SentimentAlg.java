import java.util.Properties;
import org.ejml.simple.SimpleMatrix;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class SentimentAlg {

    // build pipeline
    StanfordCoreNLP pipeline;

    public void initialize() {
        // set up pipeline properties
        Properties properties = new Properties();

        // set the list of annotators to run
        properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");

        pipeline = new StanfordCoreNLP(properties);

    }

    public SentimentPrint getSentimentResult(String text) {

        SentimentClassifier classification = new SentimentClassifier();

        SentimentPrint sentimentResult = new SentimentPrint();

        if (text != null && text.length() > 0) {

            Annotation annotation = pipeline.process(text);

            for(CoreMap sentence: annotation.get(CoreAnnotations.SentencesAnnotation.class)) {

                // System.out.println(sentence);

                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);

                //System.out.println(tree);

                SimpleMatrix simpleMatrix = RNNCoreAnnotations.getPredictions(tree);

                //System.out.println(simpleMatrix);

                classification.setVeryNegative((int)Math.round(simpleMatrix.get(0)*100d)); //indeks 0

                classification.setNegative((int)Math.round(simpleMatrix.get(1)*100d));

                classification.setNeutral((int)Math.round(simpleMatrix.get(2)*100d));

                classification.setPositive((int)Math.round(simpleMatrix.get(3)*100d));

                classification.setVeryPositive((int)Math.round(simpleMatrix.get(4)*100d));

                String setimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

                sentimentResult.setSentimentType(setimentType);

                sentimentResult.setSentimentClass(classification);

                sentimentResult.setSentimentScore(RNNCoreAnnotations.getPredictedClass(tree));

            }

        }

        return sentimentResult;

    }

}