package org.pepstock.charba.showcase.client.cases.charts;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.callbacks.ScaleContext;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.geo.ChoroplethDataPoint;
import org.pepstock.charba.client.geo.ChoroplethDataset;
import org.pepstock.charba.client.geo.ColorAxis;
import org.pepstock.charba.client.geo.Feature;
import org.pepstock.charba.client.geo.GeoUtils;
import org.pepstock.charba.client.geo.ProjectionAxis;
import org.pepstock.charba.client.geo.callbacks.QuantizeCallback;
import org.pepstock.charba.client.geo.enums.Align;
import org.pepstock.charba.client.geo.enums.Position;
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

public class GeoChoroplethUSCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GeoChoroplethUSCase> {
	}
	
	private static final Key NAME = Key.create("name");

	@UiField
	ChoroplethChartWidget chart;
	
	ChoroplethDataset dataset1;

	private final List<ChoroplethDataPoint> geodata = new LinkedList<>();

	public GeoChoroplethUSCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		List<Feature> stateFeatures = GeoUtils.features(MyResources.INSTANCE.topojsonUS().getText(), "states");
		Feature outline = GeoUtils.features(MyResources.INSTANCE.topojsonUS().getText(), "nation").get(0);

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("US choropleth chart");

		Labels labels = GeoUtils.loadLabels(stateFeatures, NAME);

		for (Feature f : stateFeatures) {
			geodata.add(new ChoroplethDataPoint(f, getRandomDigit(0, 100)));
		}
		
		dataset1 = chart.newDataset();
		dataset1.setLabel("States");
		dataset1.setOutline(outline);
		dataset1.setValues(geodata);
		
		ProjectionAxis axis1 = new ProjectionAxis(chart);
		axis1.setProjection(Projection.ALBERS_USA);
		
		ColorAxis axis2 = new ColorAxis(chart);
		axis2.setQuantize(new QuantizeCallback() {
			
			@Override
			public Integer invoke(ScaleContext context) {
				return 5;
			}
		});
		axis2.getLegend().setPosition(Position.TOP_RIGHT);
		axis2.getLegend().setAlign(Align.TOP);
		
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

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
