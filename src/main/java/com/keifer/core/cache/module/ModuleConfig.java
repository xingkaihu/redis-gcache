package com.keifer.core.cache.module;

import com.google.inject.Binder;

public interface ModuleConfig {

	void doBefore(Binder binder);

	void configure(Binder binder);

	void doAfter(Binder binder);

}
