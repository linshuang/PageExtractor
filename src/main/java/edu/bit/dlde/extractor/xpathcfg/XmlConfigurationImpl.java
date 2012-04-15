package edu.bit.dlde.extractor.xpathcfg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlConfigurationImpl implements Configuration {
	static Logger logger = Logger.getLogger(XmlConfigurationImpl.class);

	XPath xpath = XPathFactory.newInstance().newXPath();
	String _file;

	public XmlConfigurationImpl(String file) {
		_file = file;
	}

	public List<Rule> getConfig() {
		List<Rule> rules = new ArrayList<Rule>();

		File f = new File(_file);
		if (!f.exists()) {
			logger.info("Can't find file " + _file);
			throw new ConfigureFileNotFoundException(_file);
		}

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			docFactory.setNamespaceAware(true); // never forget this!
			Document doc = docFactory.newDocumentBuilder().parse(f);
			XPathExpression xPathExpr1 = xpath.compile("rules/rule");
			XPathExpression xPathExpr2 = xpath.compile("*/text()");

			NodeList pnodes = (NodeList) xPathExpr1.evaluate(doc,
					XPathConstants.NODESET);

			for (int i = 0; i < pnodes.getLength(); i++) {
				NodeList cnodes = (NodeList) xPathExpr2.evaluate(
						pnodes.item(i), XPathConstants.NODESET);
				Rule r = new Rule();
				for (int j = 0; j < cnodes.getLength(); j++) {
					String tag = cnodes.item(j).getParentNode().getNodeName();
					String value = cnodes.item(j).getNodeValue();
					logger.info("Xpath: " + tag + " - " + value);
					if (Rule.unExpr.contains(tag))
						r.addUnExpr(tag, value);
					else
						r.addExpr(cnodes.item(j).getParentNode().getNodeName(),
								cnodes.item(j).getNodeValue());
				}
				rules.add(r);
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return rules;
	}

	public String getFile() {
		return _file;
	}

	public void setFile(String file) {
		this._file = file;
	}

	public static void main(String[] args) {
		XmlConfigurationImpl cfg = new XmlConfigurationImpl(
				"src/main/java/xpath-cfg.xml");
		cfg.getConfig();
	}
}
