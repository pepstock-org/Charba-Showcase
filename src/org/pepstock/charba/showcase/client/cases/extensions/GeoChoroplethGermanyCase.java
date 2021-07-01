package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.geo.ChoroplethDataPoint;
import org.pepstock.charba.client.geo.ChoroplethDataset;
import org.pepstock.charba.client.geo.ColorAxis;
import org.pepstock.charba.client.geo.Feature;
import org.pepstock.charba.client.geo.GeoUtils;
import org.pepstock.charba.client.geo.ProjectionAxis;
import org.pepstock.charba.client.geo.callbacks.InterpolateCallback;
import org.pepstock.charba.client.geo.enums.Projection;
import org.pepstock.charba.client.gwt.widgets.ChoroplethChartWidget;
import org.pepstock.charba.client.impl.plugins.enums.TableauScheme;
import org.pepstock.charba.showcase.client.Charba_Showcase;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class GeoChoroplethGermanyCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GeoChoroplethGermanyCase> {
	}
	
	private static final Key NAME0 = Key.create("NAME_0");
	private static final Key NAME2 = Key.create("NAME_2");
	private static final Key GEOUNIT = Key.create("geounit");

	private static final List<IsColor> COLORS = TableauScheme.CLASSIC_AREA_RED_GREEN21.getColors();

	@UiField
	ChoroplethChartWidget chart;
	
	ChoroplethDataset dataset1;

	private final List<ChoroplethDataPoint> geodata = new LinkedList<>();

	public GeoChoroplethGermanyCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		List<Feature> stateFeatures = GeoUtils.features(Charba_Showcase.GERMANY, "DEU_adm2", (element, index) -> "Germany".equalsIgnoreCase(element.getPropertyValue(NAME0, null)));
		Feature outline = GeoUtils.feature(Charba_Showcase.EUROPE, "continent_Europe_subunits", (element, index) -> "Germany".equalsIgnoreCase(element.getPropertyValue(GEOUNIT, null)) && element.hasGeometry());

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("German regions choropleth chart with custom interpolation");

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
		
		ColorAxis axis2 = new ColorAxis(chart);
		axis2.setQuantize(COLORS.size());
		axis2.setInterpolate(new InterpolateCallback() {
			
			@Override
			public Object interpolate(double normalizedValue) {
				int index = (int)Math.round(normalizedValue * (COLORS.size() - 1));
				return COLORS.get(Math.min(index, COLORS.size() - 1));
			}
		});
		
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
