/*
 * Lucene Index Generator for AP89 Corpus with Standard Analyser.
 * Author : Ganesh Nagarajan, gnagaraj@indiana.edu
 * Version 1, 8/27/2016
 */
package apcorpus.parser;

import java.io.IOException;
import java.nio.file.Paths;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class GenerateIndex {
	// GenerateIndex accepts a index path to write index, then processes XML document object
	// and parses the file by extracting DOCNO,HEAD,BYLINE,DATELINE and TEXT tags.
	// If there are multiple tags, each tag is traversed and then concatinated.
	// Current solution offers a O(n) object generation (String Contenation)
	// The Tag is added to the index only if it is present, i.e tag !=""
	
	public IndexWriter indWriter;
	
	public void prepareIndexWriter(String indexPath){
		/*
		 * Accept a index path and create index folder using default standard Analyser
		 */
		try {
			Directory dir=FSDirectory.open(Paths.get(indexPath));
			Analyzer analyser = new StandardAnalyzer();
			IndexWriterConfig iwc=new IndexWriterConfig(analyser);
			iwc.setOpenMode(OpenMode.CREATE);
			indWriter = new IndexWriter(dir, iwc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prepareIndexWriter(String indexPath,KeywordAnalyzer analyser){
		/*
		 * Over rides prepareIndexWriter with Keyword Analyzer
		 */
		try {
			Directory dir=FSDirectory.open(Paths.get(indexPath));
			IndexWriterConfig iwc=new IndexWriterConfig(analyser);
			iwc.setOpenMode(OpenMode.CREATE);
			indWriter = new IndexWriter(dir, iwc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prepareIndexWriter(String indexPath,SimpleAnalyzer analyser){
		/*
		 * Over rides prepareIndexWriter with Simple Analyzer
		 */
		try {
			Directory dir=FSDirectory.open(Paths.get(indexPath));
			IndexWriterConfig iwc=new IndexWriterConfig(analyser);
			iwc.setOpenMode(OpenMode.CREATE);
			indWriter = new IndexWriter(dir, iwc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prepareIndexWriter(String indexPath,StopAnalyzer analyser){
		/*
		 * Over rides prepareIndexWriter with Stop Analyzer
		 */
		try {
			Directory dir=FSDirectory.open(Paths.get(indexPath));
			IndexWriterConfig iwc=new IndexWriterConfig(analyser);
			iwc.setOpenMode(OpenMode.CREATE);
			indWriter = new IndexWriter(dir, iwc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeIndexWriter(){
		/*
		 * Close index writer
		 */
		try {
			indWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void documentToItemPair(Document document){
		/*
		 * Traverse the Document by Tags. For multiple leaf tags, concatenate them.
		 * If the elements are not empty, add them to the lucene document
		 * and Index it.
		 */
		
		NodeList docList = document.getElementsByTagName("DOC");
		for (int i=0; i < docList.getLength(); i++){
			Node doc=docList.item(i);
			if(doc.getNodeType() == Node.ELEMENT_NODE){
				Element eElement = (Element) doc;
				String docno=eElement.getElementsByTagName("DOCNO").item(0).getTextContent();
				NodeList heads = eElement.getElementsByTagName("HEAD");
				String head="";
				for (int j=0;j<heads.getLength();j++){
					Element el=((Element) heads.item(j));
					el.normalize();
					head+=el.getTextContent().trim();
				}
				head=head.replace("\\n","");
				NodeList bylines = eElement.getElementsByTagName("BYLINE");
				String byline="";
				for (int j=0;j<bylines.getLength();j++){
					byline+=((Element) bylines.item(j)).getTextContent().trim();
				}
				byline=byline.replace("\\n","");
				String dateline="";
				NodeList DL = eElement.getElementsByTagName("DATELINE");
				for (int j=0;j<DL.getLength();j++){
					dateline+=((Element) DL.item(j)).getTextContent().trim();
				}
				String txt=eElement.getElementsByTagName("TEXT").item(0).getTextContent();
				
				org.apache.lucene.document.Document luceneDoc = new org.apache.lucene.document.Document();
				luceneDoc.add(new StringField("DOCNO", docno, Field.Store.YES));
				
				//Check for tag presence
				if (!head.equals("")){
					luceneDoc.add(new TextField("HEAD", head, Field.Store.YES));
				}
				if (!byline.equals("")){
					luceneDoc.add(new TextField("BYLINE", byline, Field.Store.YES));
				}
				if (!dateline.equals("")){
					luceneDoc.add(new TextField("DATELINE", dateline, Field.Store.YES));
				}
				if (!txt.equals("")){
					luceneDoc.add(new TextField("TEXT", txt, Field.Store.YES));
				}
				try {
					// Add the DOcument to Lucene Index
					indWriter.addDocument(luceneDoc);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void printStats(String pathToIndex){
		/*
		 * Print Lucene Index stats
		 */
		try{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(pathToIndex)));
		System.out.println("Total number of documents in the corpus:"+reader.maxDoc());
		Terms vocabulary = MultiFields.getTerms(reader, "TEXT");
		System.out.println("Size of the vocabulary for this field:"+vocabulary.size());
		System.out.println("Number of tokens for this field:"+vocabulary.getSumTotalTermFreq());
		System.out.println("Number of postings for this field:"+vocabulary.getSumDocFreq());
		TermsEnum iterator = vocabulary.iterator();
		BytesRef byteRef = null;
		int distinctTerms=0;
		while((byteRef = iterator.next()) != null) {
			distinctTerms+=1;
		}
		System.out.println("Size of the vocabulary for this field (Counted):"+distinctTerms);
		reader.close();}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
