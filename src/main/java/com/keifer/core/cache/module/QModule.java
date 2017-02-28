package com.keifer.core.cache.module;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.keifer.core.extension.Scope;
import com.keifer.core.extension.Spi;

@Spi(scope = Scope.SINGLETON)
public interface QModule extends Module {

	void doBefore(Binder binder);

	void doAfter(Binder binder);
}
