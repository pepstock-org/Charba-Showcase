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
package org.pepstock.charba.showcase.client.samples.jsni;

import org.pepstock.charba.client.commons.JavaScriptObjectContainer;
import org.pepstock.charba.client.commons.Key;

import com.google.gwt.core.client.JavaScriptObject;

public final class PieceLabelOptions extends JavaScriptObjectContainer{
	
	public static final String ID = "pieceLabel"; 
	
	/**
	 * Name of fields of JavaScript object.
	 */
	private enum Property implements Key{
		render,
		precision,
		fontSize,
	    fontColor
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

	/**
	 * Returns the java script object of this options.
	 * 
	 * @return the java script object of this options.
	 */
	public final JavaScriptObject getObject(){
		return super.getJavaScriptObject();
	}

}
