/**
    Copyright 2017 Andrea "Stock" Stocchero

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

	    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.commons.NativeObject;
import org.pepstock.charba.client.commons.NativeObjectContainer;

public final class PieceLabelOptions  extends NativeObjectContainer {
	
	public static final String ID = "pieceLabel"; 
	
	/**
	 * Name of properties of native object.
	 */
	private enum Property implements Key
	{
		render,
		precision,
		fontSize,
	    fontColor
	}

	/**
	 * 
	 */
	public PieceLabelOptions() {
	}

	/**
	 * @param nativeObject
	 */
	public PieceLabelOptions(NativeObject nativeObject) {
		super(nativeObject);
	}

	public void setRender(String render){
		setValue(Property.render, render);
	}

	public void setPrecision(int precision){
		setValue(Property.precision, precision);
	}

	public void setFontSize(int size){
		setValue(Property.fontSize, size);
	}

	public void setFontColor(String color){
		setValue(Property.fontColor, color);
	}

}
