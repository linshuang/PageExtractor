package edu.bit.dlde.extractor.skeleton;

import java.io.Reader;
import java.util.List;

import javax.xml.xpath.XPathFactory;

import edu.bit.dlde.extractor.xpathcfg.Configuration;
import edu.bit.dlde.extractor.xpathcfg.Rule;

/**
 * This class is used to extract pages while given specific xpath expression in
 * the form of {@link Rule}. All of the critical fields come from
 * {@link PageExtractXML}. However, this is just abstract class.
 * And its subclass should override method
 * {@link PreciseExtractor#extract(String, String)}.
 * 
 * @author lins
 * @version 1.0
 * @see Rule
 * @see PageExtractXML
 * @since 1.6
 */
public abstract class PreciseExtractor implements IPageExtractor {

	protected String name;
	protected List<Rule> _rules;
	protected XPathFactory factory = XPathFactory.newInstance();
	protected Reader _reader;
	protected String _encoding = "UTF-8";

	public IPageExtractor setResource(Reader reader) {
		this._reader = reader;
		return this;
	}

	public Reader getResource() {
		return this._reader;
	}

	public IPageExtractor setEncoding(String encoding) {
		this._encoding = encoding;
		return this;
	}

	/**
	 * Configure this extractor.
	 */
	public PreciseExtractor configWith(Configuration cfg) {
		_rules = cfg.getConfig();
		return this;
	}
}
