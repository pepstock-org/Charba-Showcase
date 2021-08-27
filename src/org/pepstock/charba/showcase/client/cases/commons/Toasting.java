package org.pepstock.charba.showcase.client.cases.commons;

import org.pepstock.charba.client.commons.NativeObject;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, name = "toasting", namespace = JsPackage.GLOBAL)
final class Toasting {

	private Toasting() {}

	@JsMethod
	private static native void create(NativeObject options);

	@JsOverlay
	static void show(ToastingOptions options) {
		if (options != null) {
			create(options.nativeObject());
		}
	}

}
