package services;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.Book;

public class BaseRssService implements IRssService {
	
	private String URL_PATH = "https://vnexpress.net/rss/oto-xe-may.rss";
	

	@Override
	public ArrayList<Book> loadRss() {
		ArrayList<Book> result = new ArrayList<Book>();
		try {
		
		URL url = new URL(getRssUrl());
		 InputStream inputStream= url.openStream();
	        Reader reader = new InputStreamReader(inputStream,"UTF-8");
	        InputSource is = new InputSource(reader);
	        is.setEncoding("UTF-8");
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

			db = dbf.newDocumentBuilder();
			Document document = db.parse(is);
			
			NodeList nList = document.getElementsByTagName("item");
			
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					result.add(parseElement((Element)nNode));					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
		return result;
	}
	
	
	private Book parseElement(Element element) {
		String title = element.getElementsByTagName("title").item(0).getTextContent();
		String rawDescription = convertToUTF8(element.getElementsByTagName("description").item(0).getTextContent());
		String description = getDescription(rawDescription);
		String thumbnailSrc = getImageSrc(rawDescription);
		
		return new Book(title, description, thumbnailSrc);
	}
	
	// Helpers Methods
	private String convertToUTF8(String input) {
		ByteBuffer buffer = StandardCharsets.UTF_8.encode(input); 
		return StandardCharsets.UTF_8.decode(buffer).toString();
	}
	
	private String getImageSrc(String description) {
		Pattern pattern = Pattern.compile("src=\"(.*)\"");
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            return matcher.group(1);
        }
		return "";
	}
	
	private String getDescription(String description) {
		Pattern pattern = Pattern.compile("br>(.*)");
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
	}


	@Override
	public String getRssUrl() {
		return this.URL_PATH;
	}

}
