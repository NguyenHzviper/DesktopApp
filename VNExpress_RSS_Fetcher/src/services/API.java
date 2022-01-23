package services;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class API {
	
	private String rssURL = "";

	
	public static void main(String[] args)  {

		try {
			testGetTitleFromXMLFile();
			
		}
		catch (Exception e) {
			
		}
		
		
	}
	
	public static void testGetTitleFromXMLFile() throws SAXException, IOException {
		
		URL url = new URL("https://vnexpress.net/rss/suc-khoe.rss");
		 InputStream inputStream= url.openStream();
	        Reader reader = new InputStreamReader(inputStream,"UTF-8");
	        InputSource is = new InputSource(reader);
	        is.setEncoding("UTF-8");
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document document = db.parse(is);
			
			NodeList nList = document.getElementsByTagName("item");
			
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					System.out.println("==================");
					Element yeah = (Element)nNode;
					String title = yeah.getElementsByTagName("description").item(0).getTextContent();
					ByteBuffer buffer = StandardCharsets.UTF_8.encode(title); 
					
					

					String utf8EncodedString = StandardCharsets.UTF_8.decode(buffer).toString();
					
					System.out.println(getImageUrlFromDescripion(title));

					//System.out.print(utf8EncodedString);
					
					
				}

				
				
			}
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
	}
	
	public static String getImageUrlFromDescripion(String str) {
		  Pattern pattern = Pattern.compile("src=\"(.*)\"");
	        Matcher matcher = pattern.matcher(str);
	        if (matcher.find()) {
	            return matcher.group(1);
	        }

		return "";
	}

	public static void loadXMLFromUrl() throws Exception {
		Document  doc = loadTestDocument("https://vnexpress.net/rss/oto-xe-may.rss");
        System.out.println(doc);
	}
	
	private static Document loadTestDocument(String url) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(new URL(url).openStream());
    }

}
