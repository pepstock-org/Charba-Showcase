package org.pepstock.charba.showcase.client.cases.charts;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.geo.ChoroplethDataPoint;
import org.pepstock.charba.client.geo.ChoroplethDataset;
import org.pepstock.charba.client.geo.Feature;
import org.pepstock.charba.client.geo.GeoUtils;
import org.pepstock.charba.client.geo.ProjectionAxis;
import org.pepstock.charba.client.geo.enums.Projection;
import org.pepstock.charba.client.gwt.widgets.ChoroplethChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.resources.MyResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class GeoChoroplethCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GeoChoroplethCase> {
	}
	
	private static final Key NAME = Key.create("name");

	@UiField
	ChoroplethChartWidget chart;
	
	ChoroplethDataset dataset1;

	private final List<ChoroplethDataPoint> geodata = new LinkedList<>();

	public GeoChoroplethCase() {
		initWidget(uiBinder.createAndBindUi(this));
	
		List<Feature> stateFeatures = GeoUtils.features(MyResources.INSTANCE.topojsonEarth().getText(), "countries");
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Earth choropleth chart");

		chart.getOptions().setShowGraticule(true);
		chart.getOptions().setShowOutline(true);
		
		Labels labels = GeoUtils.loadLabels(stateFeatures, NAME);

		for (Feature f : stateFeatures) {
			geodata.add(new ChoroplethDataPoint(f, getRandomDigit(0, 100)));
		}
		
		dataset1 = chart.newDataset();
		dataset1.setLabel("Countries");
		dataset1.setValues(geodata);
		
		ProjectionAxis axis1 = new ProjectionAxis(chart);
		axis1.setProjection(Projection.EQUAL_EARTH);
		
		chart.getOptions().getScales().setAxes(axis1);

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

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
