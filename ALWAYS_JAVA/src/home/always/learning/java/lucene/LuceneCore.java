/**
 * 
 */
package home.always.learning.java.lucene;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;

import com.google.gson.Gson;

/**
 * @author ttrehan
 *
 */
public class LuceneCore {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//Map<String, String> metricsMap = GenericUtils.getMetricsAsJson("/Users/ttrehan/git/ALWAYS_JAVA/ALWAYS_JAVA/Input/Metadata/", "searchMetricFeed.csv", ",");
		// TODO : Split by comma causing concerns.
		List<PostDetailsBean> reportMetricsLst = LuceneUtils.readMetricsXls("/Users/ttrehan/Desktop/", "searchUSPortal.xlsx");
		//List<ReportMetricDetails> reportMetricsLst = GenericUtils.readMetricsXls("/Users/ttrehan/git/ALWAYS_JAVA/ALWAYS_JAVA/Input/Metadata/", "searchMetrics.xlsx");
		addReportsToIdx(reportMetricsLst);
		
	}
	
	public static void addReportsToIdx(List<PostDetailsBean> reportMetricsLst) {
        // Construct a RAMDirectory to hold the in-memory representation
        // of the index.
		Gson gsonCore = new Gson();
        RAMDirectory idx = new RAMDirectory();
        Analyzer analyzer = new StandardAnalyzer();

        try {
        	IndexWriterConfig idxWriterConfig = new IndexWriterConfig(analyzer);
        	//IndexWriter idxWriter = new IndexWriter(arg0, arg1)
            // Make an writer to create the index
            IndexWriter writer = 
                new IndexWriter(idx, idxWriterConfig);

            for (PostDetailsBean reportMetricDetails : reportMetricsLst) {
            	writer.addDocument(createDocument(reportMetricDetails.getReportMetrics(),gsonCore.toJson(reportMetricDetails)));
			}
 
            writer.commit();
            System.out.println("Index count is:"+writer.numDocs());
            
            DirectoryReader ireader = DirectoryReader.open(idx);
            IndexSearcher isearcher = new IndexSearcher(ireader);
            
         // Parse a simple query that searches for "text":
            QueryParser parser = new QueryParser("metrics_idx", analyzer);
            Query query = parser.parse("accounts");
            TopDocs hits = isearcher.search(query, 100);
            		//(query, null, 1000).scoreDocs;
            System.out.println(hits.scoreDocs.length);
            
            for (int i = 0; i < hits.scoreDocs.length; i++) {
                Document hitDoc = isearcher.doc(hits.scoreDocs[i].doc);
                //System.out.println("Hit Doc:"+hitDoc);
                System.out.println("Hit Doc Val:"+hitDoc.get("content"));
                /*for (Iterator iterator = hitDoc.getFields().iterator(); iterator.hasNext();) {
					String jsonFieldVal = (String) iterator.next();
					System.out.println("Result Field value:"+jsonFieldVal);
					
				}*/
                //System.out.println("Document is:"+hitDoc);
              }
            
            writer.close();
            ireader.close();
            idx.close();
            

        }
        catch(Exception ioe) {
            // In this example we aren't really doing an I/O, so this
            // exception should never actually be thrown.
            ioe.printStackTrace();
        }
    }
	
	public static void addMetricsToIndex(Map<String,String> metricsMapObj) {
        // Construct a RAMDirectory to hold the in-memory representation
        // of the index.
        RAMDirectory idx = new RAMDirectory();
        Analyzer analyzer = new StandardAnalyzer();

        try {
        	IndexWriterConfig idxWriterConfig = new IndexWriterConfig(analyzer);
        	//IndexWriter idxWriter = new IndexWriter(arg0, arg1)
            // Make an writer to create the index
            IndexWriter writer = 
                new IndexWriter(idx, idxWriterConfig);

            Set<String> mapKeySet = metricsMapObj.keySet();
            
            for (String keyVal : mapKeySet) {
            	System.out.println("Iterating hashmap."+keyVal+"--Value--"+metricsMapObj.get(keyVal));
            	String jsonContentVal = metricsMapObj.get(keyVal);
            	writer.addDocument(createDocument(keyVal,jsonContentVal));
			}
            
            // Add some Document objects containing quotes
            /*writer.addDocument(createDocument("Theodore Roosevelt", 
                "It behooves every man to remember that the work of the " +
                "critic, is of altogether secondary importance, and that, " +
                "in the end, progress is accomplished by the man who does " +
                "things."));
            writer.addDocument(createDocument("Friedrich Hayek",
                "The case for individual freedom rests largely on the " +
                "recognition of the inevitable and universal ignorance " +
                "of all of us concerning a great many of the factors on " +
                "which the achievements of our ends and welfare depend."));
            writer.addDocument(createDocument("Ayn Rand",
                "There is nothing to take a man's freedom away from " +
                "him, save other men. To be free, a man must be free " +
                "of his brothers."));
            writer.addDocument(createDocument("Mohandas Gandhi",
                "Freedom is not worth having if it does not connote " +
                "freedom to err."));*/

            // Optimize and close the writer to finish building the index
            //writer.optimize();
            writer.commit();
            writer.close();
            
            
            DirectoryReader ireader = DirectoryReader.open(idx);
            IndexSearcher isearcher = new IndexSearcher(ireader);
            
         // Parse a simple query that searches for "text":
            QueryParser parser = new QueryParser("fieldname", analyzer);
            Query query = parser.parse("trehan");
            TopDocs hits = isearcher.search(query, 100);
            		//(query, null, 1000).scoreDocs;
            System.out.println(hits.scoreDocs.length);
            
            for (int i = 0; i < hits.scoreDocs.length; i++) {
                Document hitDoc = isearcher.doc(hits.scoreDocs[i].doc);
                System.out.println("Hit Doc:"+hitDoc);
                System.out.println("Hit Doc Val:"+hitDoc.get("fieldname"));
                /*for (Iterator iterator = hitDoc.getFields().iterator(); iterator.hasNext();) {
					String jsonFieldVal = (String) iterator.next();
					System.out.println("Result Field value:"+jsonFieldVal);
					
				}*/
                //System.out.println("Document is:"+hitDoc);
              }
            
            ireader.close();
            idx.close();
            

        }
        catch(Exception ioe) {
            // In this example we aren't really doing an I/O, so this
            // exception should never actually be thrown.
            ioe.printStackTrace();
        }
    }

    /**
     * Make a Document object with an un-indexed title field and an
     * indexed content field.
     */ 
    private static Document createDocument(String title, String content) {
        Document doc = new Document();
        
        //Field contentField = new Field(title, content,Field.Store.YES);
        
        
        // Add the title as an unindexed field...
        //doc.add(contentField);
        doc.add(new Field("content", content, TextField.TYPE_STORED));
        doc.add(new Field("metrics_idx", title, TextField.TYPE_STORED));
        //doc.add(new StringField("fieldname", content, Field.Store.YES));

        // ...and the content as an indexed field. Note that indexed
        // Text fields are constructed using a Reader. Lucene can read
        // and index very large chunks of text, without storing the
        // entire content verbatim in the index. In this example we
        // can just wrap the content string in a StringReader.
        //doc.add(Field.Text("content", new StringReader(content)));

        return doc;
    }

    /**
     * Searches for the given string in the "content" field
     */
    /*private static void search(Searcher searcher, String queryString) 
        throws ParseException, IOException {

        // Build a Query object
        Query query = QueryParser.parse(
            queryString, "content", new StandardAnalyzer());

        // Search for the query
        Hits hits = searcher.search(query);

        // Examine the Hits object to see if there were any matches
        int hitCount = hits.length();
        if (hitCount == 0) {
            System.out.println(
                "No matches were found for \"" + queryString + "\"");
        }
        else {
            System.out.println("Hits for \"" + 
                queryString + "\" were found in quotes by:");

            // Iterate over the Documents in the Hits object
            for (int i = 0; i < hitCount; i++) {
                Document doc = hits.doc(i);

                // Print the value that we stored in the "title" field. Note
                // that this Field was not indexed, but (unlike the 
                // "contents" field) was stored verbatim and can be
                // retrieved.
                System.out.println("  " + (i + 1) + ". " + doc.get("title"));
            }
        }
        System.out.println();
    }*/
}

