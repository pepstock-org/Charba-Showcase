package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.geo.ChoroplethDataPoint;
import org.pepstock.charba.client.geo.ChoroplethDataset;
import org.pepstock.charba.client.geo.ColorLogarithmicAxis;
import org.pepstock.charba.client.geo.Feature;
import org.pepstock.charba.client.geo.GeoUtil;
import org.pepstock.charba.client.geo.ProjectionAxis;
import org.pepstock.charba.client.geo.enums.Interpolate;
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

public class GeoChoroplethLogarithmicCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GeoChoroplethLogarithmicCase> {
	}
	
	private static final Key NAME = Key.create("name");

	@UiField
	ChoroplethChartWidget chart;
	
	ChoroplethDataset dataset1;

	private final List<ChoroplethDataPoint> geodata = new LinkedList<>();

	public GeoChoroplethLogarithmicCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		List<Feature> stateFeatures = GeoUtil.features(MyResources.INSTANCE.topojsonUS().getText(), "states");
		Feature outline = GeoUtil.features(MyResources.INSTANCE.topojsonUS().getText(), "nation").get(0);

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Choropleth chart and logarithmic color axis");

		Labels labels = GeoUtil.loadLabels(stateFeatures, NAME);

		for (Feature f : stateFeatures) {
			geodata.add(new ChoroplethDataPoint(f, Math.random() * 1000));
		}
		
		dataset1 = chart.newDataset();
		dataset1.setLabel("States");
		dataset1.setOutline(outline);
		dataset1.setValues(geodata);
		
		ProjectionAxis axis1 = new ProjectionAxis(chart);
		axis1.setProjection(Projection.ALBERS_USA);
		
		ColorLogarithmicAxis axis2 = new ColorLogarithmicAxis(chart);
		axis2.setInterpolate(Interpolate.REDS);
		
		chart.getOptions().getScales().setAxes(axis1, axis2);

		chart.getData().setLabels(labels);
		chart.getData().setDatasets(dataset1);
		
	} 

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (ChoroplethDataPoint g : geodata) {
			g.setValue(Math.random() * 1000);
		}
		dataset1.setValues(geodata);
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
