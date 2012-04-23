package edu.bit.dlde.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.bit.dlde.extractor.skeleton.PageExtractor;
import edu.bit.dlde.extractor.skeleton.PreciseExtractor;
import edu.bit.dlde.extractor.xpathcfg.Configuration;
import edu.bit.dlde.extractor.xpathcfg.Rule;

/**
 * we recommend to use CombinedPreciseExtractor instead of
 * {@link ForumExtractor} and {@link NewsExtractor}. It needs only one copy of
 * configuration of both forum and news and can automatically dispatch tasks to
 * the suitable extractor.
 */
public class CombinedPreciseExtractor extends PageExtractor {
	protected List<Rule> _rules = new ArrayList<Rule>();

	protected PreciseExtractor _fe = new ForumExtractor();
	protected PreciseExtractor _ne = new NewsExtractor();

	/**
	 * extract contents from given reader. 
	 * @see edu.bit.dlde.extractor.skeleton.PageExtractor#extract()
	 */
	public Map<String, String> extract() {
		if (_reader == null)
			return null;

		if (_fe.findFitRule(_uri) != null) {
			_fe.setResource(_reader, _uri);
			return _fe.extract();
		} else if (_ne.findFitRule(_uri) != null) {
			_ne.setResource(_reader, _uri);
			return _ne.extract();
		}

		return null;
	}

	/**
	 * Configure sub-extractors with {@link Configuration}
	 */
	public CombinedPreciseExtractor configWith(Configuration cfg) {
		_rules = cfg.getConfig();
		_fe.addRules(new ArrayList<Rule>(_rules));
		_ne.addRules(new ArrayList<Rule>(_rules));
		return this;
	}
}
