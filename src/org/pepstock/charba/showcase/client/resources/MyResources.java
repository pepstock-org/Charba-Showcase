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

	@Source("topojson/germany-regions.json")
	TextResource topojsonGermany();

	@Source("topojson/countries-50m.json")
	TextResource topojsonEarth();
	
	@Source("topojson/states-10m.json")
	TextResource topojsonUS();
	
	@Source("topojson/us-capitals.data")
	TextResource dataUSCapitals();

	@Source("data/sankey-energy-category.json")
	TextResource energyCategory();

	@Source("data/sankey-energy-data.json")
	TextResource energyData();

	@Source("data/sankey-energy-data2.json")
	TextResource energyData2();

	@Source("data/sankey-countries-data.json")
	TextResource countriesData();

	@Source("data/sankey-tree-data.json")
	TextResource treeData();

	@Source("data/sankey-tree-colors.json")
	TextResource treeColors();

}