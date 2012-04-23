package edu.bit.dlde.extractor.skeleton;

import java.util.Map;

public abstract class ImpreciseExtractor extends PageExtractor {

	@Override
	public abstract Map<String, String> extract();
}
