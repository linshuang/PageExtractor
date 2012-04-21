package edu.bit.dlde.extractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import edu.bit.dlde.extractor.skeleton.PreciseExtractor;
import edu.bit.dlde.extractor.xpathcfg.Rule;

/**
 * Subclass of {@link PreciseExtractor}. And this class is dedicated to news.
 * Strategy pattern is used here. Method
 * {@link PreciseExtractor#extract(String file, String charset)} should be
 * override in this class.
 * 
 * @author linss
 * @version 1.0
 * @see PreciseExtractor
 * @since 1.6
 */
public class NewsExtractor extends PreciseExtractor {
	static Logger logger = Logger.getLogger(PreciseExtractor.class);

	public LinkedHashMap<String, String> extract() {
		if (_reader == null)
			return null;

		LinkedHashMap<String, String> c2v = new LinkedHashMap<String, String>();
		try {
			TagNode root = cleaner.clean(_reader);
			CleanerProperties props = cleaner.getProperties();
			props.setNamespacesAware(false);
			// get suitable rule;
			Rule rule = findFitRule();
			if (rule == null)
				return null;

			for (int i = 0; i < rule.getExprsSize(); i++) {
				String name = rule.getExprName(i);
				String value = rule.getExprValue(i);
				Object[] nodes = root.evaluateXPath(value);

				int id = 0;
				for (Object node : nodes) {
					TagNode n = (TagNode) node;
					String content = n.getText().toString();
					c2v.put(id + "-" + name, content);
					logger.info("Extract result: " + name + ":" + content);
					id++;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return c2v;
	}

	public void addRules(ArrayList<Rule> rules) {
		for (Rule r : rules) {
			if (r._siteType.equals("news"))
				_rules.add(r);
		}
	}

}
