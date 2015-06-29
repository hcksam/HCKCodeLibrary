package pers.hck.readwrite;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {
	public Document document;
	public File file;

	public XMLReader(File file) {
		try {
			this.file = file;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.parse(file);
			document.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRootElementName() {
		return document.getChildNodes().item(0).getNodeName();
	}

	public NodeList getRootNodeList() {
		return document.getChildNodes().item(0).getChildNodes();
	}

	public static Node getSingleNode(Node inNode) {
		NodeList nodeList = inNode.getChildNodes();
		return (nodeList.getLength() >= 1) ? nodeList.item(0) : null;
	}

	public static String getSingleNodeValue(Node inNode) {
		Node node = getSingleNode(inNode);
		return (node != null) ? node.getNodeValue() : "";
	}

	public static Node getSingleNode(NodeList inNodeList) {
		ArrayList<Node> nodes = getNodes(inNodeList);
		if (nodes.size() >= 1) {
			return getSingleNode(nodes.get(0));
		} else {
			return null;
		}
	}

	public static String getSingleNodeValue(NodeList inNodeList) {
		ArrayList<Node> nodes = getNodes(inNodeList);
		if (nodes.size() >= 1) {
			return getSingleNodeValue(nodes.get(0));
		} else {
			return null;
		}
	}

	public static Node getSingleNode(NodeList inNodeList, int index) {
		ArrayList<Node> nodes = getNodes(inNodeList);
		if (nodes.size() > index) {
			return getSingleNode(nodes.get(index));
		} else {
			return null;
		}
	}

	public static String getSingleNodeValue(NodeList inNodeList, int index) {
		ArrayList<Node> nodes = getNodes(inNodeList);
		if (nodes.size() > index) {
			return getSingleNodeValue(nodes.get(index));
		} else {
			return null;
		}
	}

	public static ArrayList<Node> getNodes(NodeList inNodeList) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < inNodeList.getLength(); i++) {
			Node node = inNodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE)
				continue;
			nodes.add(node);
		}
		return nodes;
	}

	public static ArrayList<NodeList> getNodeLists(NodeList inNodeList) {
		ArrayList<NodeList> nodeLists = new ArrayList<NodeList>();
		ArrayList<Node> nodes = getNodes(inNodeList);
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			if (node.getChildNodes().getLength() > 1) {
				nodeLists.add(node.getChildNodes());
			}
		}
		return nodeLists;
	}

	public static ArrayList<String> getNodeValues(NodeList inNodeList) {
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<Node> nodes = getNodes(inNodeList);
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			if (node.getChildNodes().getLength() == 1) {
				values.add(getSingleNodeValue(node));
			}
		}
		return values;
	}

	public static String getSingleAttribute(Node inNode) {
		Node node = (inNode.getAttributes().getLength() >= 1) ? inNode
				.getAttributes().item(0) : null;
		return (node != null) ? node.getNodeName() : "";
	}

	public static String getSingleAttributeValue(Node inNode) {
		String value = getSingleAttributeValue(inNode,
				getSingleAttribute(inNode));
		return (value != null) ? value : "";
	}

	public static String getSingleAttributeValue(Node inNode,
			String AttributeName) {
		Node node = inNode.getAttributes().getNamedItem(AttributeName);
		return (node != null) ? getSingleNodeValue(node) : "";
	}

	public static String getSingleAttribute(Node inNode, int index) {
		Node node = (index < inNode.getAttributes().getLength()) ? inNode
				.getAttributes().item(index) : null;
		return (node != null) ? node.getNodeName() : null;
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
		XMLReader rixml = new XMLReader(new File(
				"C:/Temp/20130601/shop_location.xml"));
		System.out.println(rixml.getRootElementName());
		
		ArrayList<Node> shops = XMLReader.getNodes(rixml.getRootNodeList());
		for (int i = 0; i < shops.size(); i++) {
			Node shop = shops.get(i);
			System.out.println(shop.getNodeName());
			ArrayList<Node> shopInfos = XMLReader.getNodes(shop
					.getChildNodes());
			for (int j = 0; j < shopInfos.size(); j++) {
				Node shopInfo = shopInfos.get(j);
				String name = shopInfo.getNodeName();
				String value = "";

				if (shopInfo.getChildNodes().getLength() == 1) {
					name += ": ";
					value = XMLReader.getSingleNodeValue(shopInfo);
					System.out.println(name + value);
				} else {
					System.out.println(name + ": "
							+ XMLReader.getSingleAttributeValue(shopInfo));
					ArrayList<Node> shopDetails = XMLReader.getNodes(shopInfo
							.getChildNodes());
					for (int k = 0; k < shopDetails.size(); k++) {
						Node shopDetail = shopDetails.get(k);
						name = shopDetail.getNodeName() + ": ";
						value = XMLReader.getSingleNodeValue(shopDetail);
						System.out.println(name + value);
					}
				}
			}
			System.out.println();
		}
	}
}
