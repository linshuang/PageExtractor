package edu.bit.dlde.extractor.skeleton;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathFactory;

import org.htmlcleaner.HtmlCleaner;

import edu.bit.dlde.extractor.xpathcfg.Configuration;
import edu.bit.dlde.extractor.xpathcfg.Rule;

/**
 * This class is used to extract pages while given specific xpath expression in
 * the form of {@link Rule}. All of the critical fields come from
 * {@link PageExtractXML}. However, this is just abstract class. And its
 * subclass should override method
 * {@link PreciseExtractor#extract(String, String)}.
 * 
 * @author lins
 * @version 1.0
 * @see Rule
 * @see PageExtractXML
 * @since 1.6
 */
public abstract class PreciseExtractor extends PageExtractor {
	protected HtmlCleaner cleaner = new HtmlCleaner();
	protected List<Rule> _rules = new ArrayList<Rule>();

	public abstract void addRules(ArrayList<Rule> rules);

	public Rule findFitRule() {
		for (int i = 0; i < _rules.size(); i++) {
			Rule rule = _rules.get(i);
			if (rule.isRuleFit(_uri))
				return rule;
		}
		return null;
	}

	public Rule findFitRule(String uri) {
		for (int i = 0; i < _rules.size(); i++) {
			Rule rule = _rules.get(i);
			if (rule.isRuleFit(uri))
				return rule;
		}
		return null;
	}
}
