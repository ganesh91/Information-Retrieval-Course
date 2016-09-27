package apcorpus.parser;

import java.util.List;

import org.w3c.dom.Document;

import apcorpus.parser.TrecParser;
import apcorpus.parser.GenerateIndex;

public class OrchestratorGeneratorIndex {
	public static void main(String args[]){
		TrecParser trec = new TrecParser();
		GenerateIndex GI = new GenerateIndex();
		GI.prepareIndexWriter("C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/index/");
		List<Document> trecdocs=trec.parseToDocument("C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/corpus1", true);
		for (Document doc: trecdocs){
			GI.documentToItemPair(doc);
		}
		GI.closeIndexWriter();
		GI.printStats("C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/index/");
	}
}
