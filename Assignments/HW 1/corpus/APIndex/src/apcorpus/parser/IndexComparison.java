package apcorpus.parser;

import java.util.List;

import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.w3c.dom.Document;

import apcorpus.parser.TrecParser;
import apcorpus.parser.GenerateIndex;

public class IndexComparison {
	public static void main(String args[]){
		TrecParser trec = new TrecParser();
		List<Document> trecdocs=trec.parseToDocument("C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/corpus1", true);
		
		// Print Standard Analyzer
		GenerateIndex GI = new GenerateIndex();
		GI.prepareIndexWriter("C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/index/");
		for (Document doc: trecdocs){
			GI.documentToItemPair(doc);
		}
		GI.closeIndexWriter();
		System.out.println("----Standard Analyzer-------");
		GI.printStats("C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/index/");
		
		// Print KeyWordAnalyser
		GenerateIndex kGI = new GenerateIndex();
		kGI.prepareIndexWriter("C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/index/"
				,new KeywordAnalyzer());
		for (Document doc: trecdocs){
			kGI.documentToItemPair(doc);
		}
		kGI.closeIndexWriter();
		System.out.println("----Keywords Analyzer-------");
		kGI.printStats("C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/index/");

		// Print Simple Analyzer
		GenerateIndex sGI = new GenerateIndex();
		sGI.prepareIndexWriter("C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/index/"
						,new SimpleAnalyzer());
		for (Document doc: trecdocs){
			sGI.documentToItemPair(doc);
		}
		sGI.closeIndexWriter();
		System.out.println("----Simple Analyzer-------");
		sGI.printStats("C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/index/");
		
		// Print Stop Analyzer
		GenerateIndex stGI = new GenerateIndex();
		stGI.prepareIndexWriter("C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/index/"
				,new StopAnalyzer());
		for (Document doc: trecdocs){
			stGI.documentToItemPair(doc);
		}
		stGI.closeIndexWriter();
		System.out.println("----Stop Analyzer-------");
		stGI.printStats("C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/index/");
	}
}
