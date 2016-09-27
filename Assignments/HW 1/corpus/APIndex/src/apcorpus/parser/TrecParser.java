package apcorpus.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.ByteArrayInputStream;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

public class TrecParser {
	/*
	 * TrecParser accepts trec file directory. Since TrecFiles doesn't have a
	 * root node, a <root> and </root> is added programmatically and the resulting
	 * fully formed XML is parsed by a SAX parser as DOM Object.
	 * Returns a XML Document for all trec files.
	 */
	public List<Document> parseToDocument(String URI,Boolean isDirectory){
		List<Document> trecfiles = new ArrayList<Document>();
		if(isDirectory){
			File[] dirFiles= new File(URI).listFiles();
			for (File file : dirFiles){
				try {
					FileInputStream fis=new FileInputStream(file);
//					System.out.println(file.getName());
					List<InputStream> streams = Arrays.asList(
							new ByteArrayInputStream("<root>".getBytes()),
							fis,
							new ByteArrayInputStream("</root>".getBytes()));
					InputStream cntr = 
							new SequenceInputStream(Collections.enumeration(streams));
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(cntr);
					trecfiles.add(doc);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
				}
				
			}
		}
		else{
			//Later refactor the code to include single files.
		}
		return(trecfiles);
	}
}
