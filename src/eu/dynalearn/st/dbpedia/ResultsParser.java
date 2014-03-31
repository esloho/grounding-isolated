package eu.dynalearn.st.dbpedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import eu.dynalearn.model.GroundingTerm;


public class ResultsParser {
	private List<String> resultList;
//	private List<DBPediaExtended> possibleGroundings = new ArrayList<DBPediaExtended>();
	private List<GroundingTerm> possibleGroundings = new ArrayList<GroundingTerm>();
	private Document dom;
	private String urlRequest;
	//private String filename;
	
	ResultsParser(String input) throws Exception {
		resultList = new Vector<String>();
		this.urlRequest = input;
//		this.filename = input;
//		runExample();
		parseXmlFile();
		if (dom!=null) parseDocument();

	}

	public List<String> getOutput() {
		return resultList;
	}
	
	public List<GroundingTerm> getResults() {
		return possibleGroundings;
	}
	
	private void parseXmlFile() throws Exception {
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		// DONT CATCH EXCEPTIONS IF they kill the program anyways!!!
		try 
		{
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
//			dom = db.parse(filename);
			dom = db.parse(urlRequest);	

		} 
		catch(Exception e) 
		{
			throw e;
		}
	}

	private void parseDocument() {
		Element docEle = dom.getDocumentElement();
		NodeList nl = docEle.getElementsByTagName("Result");
		
		if(nl != null && nl.getLength() > 0) 
		{
			for(int i=0 ;i<nl.getLength();i++) 
			{
				Element el = (Element)nl.item(i);
				GroundingTerm e = getResult(el);
				
				if (e != null) {
					possibleGroundings.add(e);
					resultList.add(e.toString());
				}
			}
		}
		
	}
	
	private GroundingTerm getResult(Element empEl) {
		final String label = getTextValue(empEl,"Label");
		final String desc = getTextValue(empEl,"Description");
		final String uri = getTextValue(empEl,"URI");
//		final String redirect = getTextValue(empEl,"Redirects");
		
		if (uri.length() > 0) {
			GroundingTerm result = new GroundingTerm();
			result.setLabel(label);
			result.setDescription(desc);
			result.setURI(uri);
			return result;
		}
		return null;
	}

	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		
		if(nl != null && nl.getLength() > 0) 
		{
			Element el = (Element)nl.item(0);
			if (el==null || el.getFirstChild()==null) return "";
			else return el.getFirstChild().getNodeValue();
		}
		return textVal;
	}
}
