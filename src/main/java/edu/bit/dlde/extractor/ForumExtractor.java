package edu.bit.dlde.extractor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import edu.bit.dlde.extractor.skeleton.PreciseExtractor;

/**
 * Subclass of {@link PreciseExtractor}. And this class is dedicated to forum.
 * Strategy pattern is used here. Method
 * {@link PreciseExtractor#extract(String file, String charset)} should be
 * override in this class.
 * 
 * @author linss
 * @version 1.0
 * @see PreciseExtractor
 * @since 1.6
 */
public class ForumExtractor extends PreciseExtractor {
	static Logger logger = Logger.getLogger(ForumExtractor.class);
	long _id;
	HtmlCleaner cleaner = new HtmlCleaner();

	public void resetId() {
		_id = 0;
	}

	/**
	 * Extract the page whose type is forum.
	 * 
	 * @param file
	 *            content of the file to be extracted
	 * @throws XPatherException
	 *             if any errors occurs
	 * @throws IOException
	 *             if any errors occurs
	 * @see PreciseExtractor.crawl.extractor.classic.xpath.IPageExtractor#extract(java.lang.String,
	 *      java.lang.String)
	 */
	public HashMap<String, String> extract(String file) {
		HashMap<String, String> c2v = new HashMap<String, String>();

		TagNode tagNode;
		tagNode = cleaner.clean(file);

		Object[] nodes;
		String xPathExpression;
		xPathExpression = _rules.get(0).getExpression();
		Object[] frames = null;
		TagNode frameNode;
		try {
			frames = tagNode.evaluateXPath(xPathExpression);
		} catch (XPatherException e1) {
			System.out.println(xPathExpression);
			e1.printStackTrace();
		}
		// System.out.println(frames[0].toString());
		for (Object frame : frames) {
			// System.out.println("12819281937");
			frameNode = (TagNode) frame;
			for (int i = 1; i < _rules.size(); i++) {
				xPathExpression = _rules.get(i).getExpression();
				try {
					nodes = frameNode.evaluateXPath(xPathExpression);

					for (Object node : nodes) {
						TagNode n = (TagNode) node;
						String content = n.getText().toString();
						c2v.put(_rules.get(i).getName() + _id, content
								.replaceAll("&nbsp;", " ").replace("\n", ""));
						logger.info("Extract result: "
								+ _rules.get(i).getName()
								+ ":"
								+ content.replaceAll("&nbsp;", " ").replace(
										"\n", ""));
					}
				} catch (NullPointerException e) {
					System.out.println("ERROR: XPathExpression is "
							+ xPathExpression);
				} catch (XPatherException e) {
					System.out.println("XPather ERROR: XPathExpression is "
							+ xPathExpression);
					e.printStackTrace();
				}
			}
			_id++;
		}
		return c2v;
	}

	public HashMap<String, String> extract() {
		return null;
	}
}
