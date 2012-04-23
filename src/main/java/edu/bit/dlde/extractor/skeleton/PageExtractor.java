package edu.bit.dlde.extractor.skeleton;

import java.io.Reader;
import java.util.Map;

public abstract class PageExtractor {
	protected Reader _reader;
	protected String _uri;

	/**
	 * @param reader
	 *            is the only way we put the input to extractor
	 * @param uri
	 *            should be specified when u use {@link PreciseExtractor} or its
	 *            sub-class. however u may offer null in
	 *            {@link ImpreciseExtractor} or its sub-class.
	 */
	public PageExtractor setResource(Reader reader, String uri) {
		this._reader = reader;
		this._uri = uri;
		return this;
	}

	public Reader getResource() {
		return this._reader;
	}

	/**
	 * @return this method returns the {name, value} pairs for instances
	 *         {"title", "this is a title"}
	 */
	public abstract Map<String, String> extract();

}
