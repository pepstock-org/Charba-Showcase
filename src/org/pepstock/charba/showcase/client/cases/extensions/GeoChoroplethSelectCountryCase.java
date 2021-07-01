package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.events.DatasetSelectionEventHandler;
import org.pepstock.charba.client.geo.ChoroplethDataPoint;
import org.pepstock.charba.client.geo.ChoroplethDataset;
import org.pepstock.charba.client.geo.Feature;
import org.pepstock.charba.client.geo.GeoUtils;
import org.pepstock.charba.client.geo.ProjectionAxis;
import org.pepstock.charba.client.geo.enums.Projection;
import org.pepstock.charba.client.gwt.widgets.ChoroplethChartWidget;
import org.pepstock.charba.client.impl.plugins.ChartPointer;
import org.pepstock.charba.client.items.DatasetReference;
import org.pepstock.charba.showcase.client.Charba_Showcase;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class GeoChoroplethSelectCountryCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GeoChoroplethSelectCountryCase> {
	}

	private static final Key NAME = Key.create("name");

	@UiField
	ChoroplethChartWidget chart;

	ChoroplethDataset dataset1;

	private final List<ChoroplethDataPoint> geodata = new LinkedList<>();

	public GeoChoroplethSelectCountryCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Earth choropleth chart");

		chart.addHandler(new DatasetSelectionEventHandler() {

			@Override
			public void onSelect(DatasetSelectionEvent event) {
				DatasetReference dataset = event.getItem();
				int index = dataset.getIndex();
				if (index >= 0 && index < geodata.size()) {
					ChoroplethDataPoint dp = geodata.get(index);
					String name = dp.getFeature().getStringProperty(NAME);
					StringBuilder sb = new StringBuilder();
					sb.append("Index: <b>").append(index).append("</b><br>");
					sb.append("Country: <b>").append(name).append("</b><br>");
					sb.append("Value: <b>").append(dp.getValue()).append("</b><br>");
					new Toast("Country Selected!", sb.toString()).show();
				}
			}
		} , DatasetSelectionEvent.TYPE);

		Labels labels = GeoUtils.loadLabels(Charba_Showcase.EARTH_FEATURES, NAME);

		for (Feature f : Charba_Showcase.EARTH_FEATURES) {
			geodata.add(new ChoroplethDataPoint(f, getRandomDigit(0, 100)));
		}

		dataset1 = chart.newDataset();
		dataset1.setLabel("Countries");
		dataset1.setValues(geodata);
		dataset1.setShowOutline(true);

		ProjectionAxis axis1 = new ProjectionAxis(chart);
		axis1.setProjection(Projection.EQUAL_EARTH);

		chart.getOptions().getScales().setAxes(axis1);

		chart.getData().setLabels(labels);
		chart.getData().setDatasets(dataset1);
		
		chart.getPlugins().add(ChartPointer.get());

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
