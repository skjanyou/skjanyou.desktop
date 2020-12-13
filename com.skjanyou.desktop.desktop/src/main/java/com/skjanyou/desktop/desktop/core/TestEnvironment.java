package com.skjanyou.desktop.desktop.core;

import java.io.InputStream;


class TestEnvironment implements Environment {

	@Override
	public InputStream getInputStream(String path) {
		return TestEnvironment.class.getResourceAsStream(path);
	}

}
