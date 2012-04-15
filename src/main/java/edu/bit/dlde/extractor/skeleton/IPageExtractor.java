package edu.bit.dlde.extractor.skeleton;

import java.io.Reader;
import java.util.HashMap;

public interface IPageExtractor {
	public IPageExtractor setResource(Reader reader);

	public Reader getResource();

	public IPageExtractor setEncoding(String encoding);
	
	public HashMap<String, String> extract();

}
