package edu.bit.dlde.extractor;

import java.util.HashMap;

import edu.bit.dlde.extractor.skeleton.PreciseExtractor;


/**
 * Subclass of {@link PreciseExtractor}. And this class is dedicated to news.
 * Strategy pattern is used here. Method
 * {@link PreciseExtractor#extract(String file, String charset)} should be override
 * in this class.
 * 
 * @author linss
 * @version 1.0
 * @see PreciseExtractor
 * @since 1.6
 */
public class NewsExtractor extends PreciseExtractor {

	public HashMap<String, String> extract() {
		return null;
	}

	

}
