package org.pepstock.charba.showcase.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface MyResources extends ClientBundle {

	public static final MyResources INSTANCE = GWT.create(MyResources.class);

	@Source("js/chartjs-plugin-stacked100.js")
	TextResource chartJsStacked100Source();

	@Source("css/Legend.css")
	TextResource legend();

	@Source("topojson/europe.json")
	TextResource topojsonEurope();

	@Source("topojson/italy-provinces.json")
	TextResource topojsonItaly();

	@Source("topojson/countries-50m.json")
	TextResource topojsonEarth();
	
	@Source("topojson/states-10m.json")
	TextResource topojsonUS();
	
	@Source("topojson/us-capitals.data")
	TextResource dataUSCapitals();

}