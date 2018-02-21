package search.analyzers;

import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import misc.exceptions.NotYetImplementedException;
import search.models.Webpage;

import java.net.URI;

/**
 * This class is responsible for computing how "relevant" any given document is
 * to a given search query.
 *
 * See the spec for more details.
 */
public class TfIdfAnalyzer {
    // This field must contain the IDF score for every single word in all
    // the documents.
    private IDictionary<String, Double> idfScores;

    // This field must contain the TF-IDF vector for each webpage you were given
    // in the constructor.
    //
    // We will use each webpage's page URI as a unique key.
    private IDictionary<URI, IDictionary<String, Double>> documentTfIdfVectors;

    // Feel free to add extra fields and helper methods.
    
    private IDictionary<URI, IDictionary<String, Double>> documentTfVectors;

    public TfIdfAnalyzer(ISet<Webpage> webpages) {
        // Implementation note: We have commented these method calls out so your
        // search engine doesn't immediately crash when you try running it for the
        // first time.
        //
        // You should uncomment these lines when you're ready to begin working
        // on this class.
        this.documentTfVectors = this.computeAllDocumentTfVectors(webpages);
        this.idfScores = this.computeIdfScores(webpages);
        this.documentTfIdfVectors = this.computeAllDocumentTfIdfVectors(webpages);
    }

    // Note: this method, strictly speaking, doesn't need to exist. However,
    // we've included it so we can add some unit tests to help verify that your
    // constructor correctly initializes your fields.
    public IDictionary<URI, IDictionary<String, Double>> getDocumentTfIdfVectors() {
        return this.documentTfIdfVectors;
    }

    // Note: these private methods are suggestions or hints on how to structure your
    // code. However, since they're private, you're not obligated to implement exactly
    // these methods: feel free to change or modify these methods however you want. The
    // important thing is that your 'computeRelevance' method ultimately returns the
    // correct answer in an efficient manner.

    /**
     * Return a dictionary mapping every single unique word found
     * in every single document to their IDF score.
     */
    private IDictionary<String, Double> computeIdfScores(ISet<Webpage> pages) {
        IDictionary<String, Integer> totalOccurrence = new ChainedHashDictionary<String, Integer>();
        int totalPages = 0;
        for (Webpage page : pages) {
            totalPages++;
            for (KVPair<String, Double> pair : documentTfVectors.get(page.getUri())) {
                if (!totalOccurrence.containsKey(pair.getKey())) {
                    totalOccurrence.put(pair.getKey(), 1);
                }else {
                    totalOccurrence.put(pair.getKey(), (totalOccurrence.get(pair.getKey()) + 1) );
                }
            }
            }
        System.out.println("Total Pages = " + totalPages);
        IDictionary<String, Double> IdfScores = new ChainedHashDictionary<String, Double>();
        for (KVPair<String, Integer> pair : totalOccurrence) {
            IdfScores.put(pair.getKey(), (Math.log( (double)totalPages / (double)pair.getValue() ) ) );
            System.out.println("'" + pair.getKey() + "' has count " + pair.getValue());
            System.out.println("'" + pair.getKey() + "' has multiplier " + ((double)totalPages)/(double)pair.getValue());
            System.out.println("'" + pair.getKey() + "' has value " + IdfScores.get(pair.getKey()));
            
            }
        return IdfScores;
        }
    
    

    /**
     * Returns a dictionary mapping every unique word found in the given list
     * to their term frequency (TF) score.
     *
     * The input list represents the words contained within a single document.
     */
    private IDictionary<String, Double> computeTfScores(IList<String> words) {
         IDictionary<String, Integer> wordCount = new ChainedHashDictionary<String, Integer>();
         int totalWords = 0;
         for (String word : words) {
             totalWords++;
             if (wordCount.containsKey(word)) {
                 wordCount.put(word, (wordCount.get(word) + 1));
             }else {
                 wordCount.put(word, 1);
             }
         }
         IDictionary<String, Double> TfScores = new ChainedHashDictionary<String, Double>();
        for (KVPair<String, Integer> pair : wordCount) {
            TfScores.put(pair.getKey(), ((double)pair.getValue()/(double)totalWords));
            }
        return TfScores;
    }

    /**
     * See spec for more details on what this method should do.
     */
    private IDictionary<URI, IDictionary<String, Double>> computeAllDocumentTfVectors(ISet<Webpage> pages) {
        // Hint: this method should use the idfScores field and
        // call the computeTfScores(...) method.
        IDictionary<URI, IDictionary<String, Double>> allTf = new ChainedHashDictionary<URI, IDictionary<String, Double>>();
        for (Webpage page : pages) {
            IDictionary<String, Double> TfScore = computeTfScores(page.getWords());
            allTf.put(page.getUri(), TfScore);
        }
        return allTf;
    }
    
    private IDictionary<URI, IDictionary<String, Double>> computeAllDocumentTfIdfVectors(ISet<Webpage> pages) {
        // Hint: this method should use the idfScores field and
        // call the computeTfScores(...) method.
        IDictionary<URI, IDictionary<String, Double>> allTfIdf = new ChainedHashDictionary<URI, IDictionary<String, Double>>();
        for (Webpage page : pages) {
            IDictionary<String, Double> TfIdfScore = new ChainedHashDictionary<String, Double>();
            for (KVPair<String, Double> pair : documentTfVectors.get(page.getUri())) {
                Double TfIdf = pair.getValue() * idfScores.get(pair.getKey());
                TfIdfScore.put(pair.getKey(), TfIdf);
            }
            allTfIdf.put(page.getUri(), TfIdfScore);
        }
        return allTfIdf;
    }

    /**
     * Returns the cosine similarity between the TF-IDF vector for the given query and the
     * URI's document.
     *
     * Precondition: the given uri must have been one of the uris within the list of
     *               webpages given to the constructor.
     */
    public Double computeRelevance(IList<String> query, URI pageUri) {
        // Note: The pseudocode we gave you is not very efficient. When implementing,
        // this method, you should:
        //
        // 1. Figure out what information can be precomputed in your constructor.
        //    Add a third field containing that information.
        //
        // 2. See if you can combine or merge one or more loops.
        IDictionary<String, Double> documentVector = documentTfIdfVectors.get(pageUri);
        IDictionary<String, Double> queryVector = new ChainedHashDictionary<String, Double>();
        IDictionary<String, Double> queryTf = computeTfScores(query);
        Double numerator = 0.0;
        for (KVPair<String, Double> pair : queryTf) {
            if(idfScores.containsKey(pair.getKey())) {
                Double TfIdf = pair.getValue() * idfScores.get(pair.getKey());
                queryVector.put(pair.getKey(), TfIdf);  
            }else {
                queryVector.put(pair.getKey(), 0.0);
            }
        }
        for (String word : query) {
            Double docWordScore;
            if (documentVector.containsKey(word)) {
                docWordScore = documentVector.get(word);
            }else {
                docWordScore = 0.0;
            }
            Double queryWordScore = queryVector.get(word);
            numerator += docWordScore * queryWordScore;
        }
        Double denominator = norm(documentVector) * norm(queryVector);
        if (denominator != 0) {
            return numerator/denominator;
        }else {
            return 0.0;
        }
    }
    
    private Double norm(IDictionary<String, Double> vector) {
        Double output = 0.0;
        for (KVPair<String, Double> pair : vector) {
            Double score = pair.getValue();
            output += score * score;
        }
        return Math.sqrt(output);
    }
    
}
