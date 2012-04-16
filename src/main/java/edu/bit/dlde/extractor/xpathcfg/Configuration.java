package edu.bit.dlde.extractor.xpathcfg;

import java.util.List;

/**
 * Interface to parse configuration. {@link XmlConfigurationImpl} is its impl
 * with xml as cofigure file.
 */
public interface Configuration {
	public List<Rule> getConfig();
}
