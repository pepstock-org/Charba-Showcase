package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.geo.ChoroplethDataPoint;
import org.pepstock.charba.client.geo.ChoroplethDataset;
import org.pepstock.charba.client.geo.ColorAxis;
import org.pepstock.charba.client.geo.Feature;
import org.pepstock.charba.client.geo.GeoUtils;
import org.pepstock.charba.client.geo.ProjectionAxis;
import org.pepstock.charba.client.geo.enums.Interpolate;
import org.pepstock.charba.client.geo.enums.Projection;
import org.pepstock.charba.client.gwt.widgets.ChoroplethChartWidget;
import org.pepstock.charba.showcase.client.Charba_Showcase;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class GeoChoroplethItalyCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GeoChoroplethItalyCase> {
	}
	
	private static final Key NAME0 = Key.create("NAME_0");
	private static final Key NAME2 = Key.create("NAME_2");
	private static final Key GEOUNIT = Key.create("geounit");

	@UiField
	ChoroplethChartWidget chart;
	
	@UiField
	ListBox color;

	@UiField
	CheckBox graticule;

	ChoroplethDataset dataset1;
	
	ColorAxis axis2;

	private final List<ChoroplethDataPoint> geodata = new LinkedList<>();

	public GeoChoroplethItalyCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		color.addItem("Default", Interpolate.BLUES.name());
		for (Interpolate interpolate : Interpolate.values()) {
			color.addItem(interpolate.name(), interpolate.name());
		}
		
		List<Feature> stateFeatures = GeoUtils.features(Charba_Showcase.ITALY, "ITA_adm2", (element, index) -> "Italy".equalsIgnoreCase(element.getPropertyValue(NAME0, null)));
		Feature outline = GeoUtils.feature(Charba_Showcase.EUROPE, "continent_Europe_subunits", (element, index) -> "Italy".equalsIgnoreCase(element.getPropertyValue(GEOUNIT, null)) && element.hasGeometry());

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Italian provinces choropleth chart with selected interpolation");

		Labels labels = GeoUtils.loadLabels(stateFeatures, NAME2);

		for (Feature f : stateFeatures) {
			geodata.add(new ChoroplethDataPoint(f, getRandomDigit(0, 100)));
		}
		
		dataset1 = chart.newDataset();
		dataset1.setLabel("Italy");
		dataset1.setOutline(outline);
		dataset1.setValues(geodata);
		
		ProjectionAxis axis1 = new ProjectionAxis(chart);
		axis1.setProjection(Projection.EQUAL_EARTH);
		
		axis2 = new ColorAxis(chart);
		axis2.setInterpolate(Interpolate.BLUES);
		chart.getOptions().getScales().setAxes(axis1, axis2);

		chart.getData().setLabels(labels);
		chart.getData().setDatasets(dataset1);
		
	} 

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (ChoroplethDataPoint g : geodata) {
			g.setValue(getRandomDigit(0, 100));
		}
		dataset1.setValues(geodata);
		chart.update();
	}
	
	@UiHandler("color")
	protected void handleColors(ChangeEvent event) {
		String selected = color.getSelectedValue();
		Interpolate interpolate = Interpolate.valueOf(selected);
		axis2.setInterpolate(interpolate);
		chart.reconfigure();
	}


	@UiHandler("graticule")
	protected void handleGraticule(ClickEvent event) {
		dataset1.setShowGraticule(graticule.getValue());
		chart.reconfigure();
	}

	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
