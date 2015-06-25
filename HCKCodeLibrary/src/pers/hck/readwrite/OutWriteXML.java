package pers.hck.readwrite;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OutWriteXML {
	public Document document;
	public File file;

	public OutWriteXML(File outFile, Document document) {
		this.document = document;
		this.file = outFile;
	}

	public OutWriteXML(File inFile, File outFile) {
		document = new ReadInXML(inFile).getDocument();
		this.file = outFile;
	}

	public void write() {
		if (writeXML()) {
			System.out
					.println("Write file: " + file.getPath() + " successful!");
		} else {
			System.out.println("Write file: " + file.getPath() + " fail!");
		}
	}

	public boolean writeXML() {
		try {
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadInXML rixml = new ReadInXML(new File(
				"C:/Temp/20130607/Counter_Locations.xml"));
		Document document = rixml.getDocument();
		NodeList shops = document.getElementsByTagName("counter_location");
		for (int i = 0; i < shops.getLength(); i++) {
			NodeList shopInfs = shops.item(i).getChildNodes();
			for (int j = 0; j < shopInfs.getLength(); j++) {
				Node shopInf = shopInfs.item(j);
				NodeList details = shopInf.getChildNodes();
				if (shopInf.getNodeName().equalsIgnoreCase("details")) {
					for (int k = 0; k < details.getLength(); k++) {
						Node detail = details.item(k);
						System.out.println(detail.getNodeName()+": "+detail.getNodeValue());
					}
				}
			}
		}
//		OutWriteXML owxml = new OutWriteXML(new File(
//				"C:/Temp/20130607/shop_location.xml"), document);
//		owxml.write();
	}

}
