package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.DatasetContext;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.geo.BubbleMapDataPoint;
import org.pepstock.charba.client.geo.BubbleMapDataset;
import org.pepstock.charba.client.geo.Feature;
import org.pepstock.charba.client.geo.GeoUtil;
import org.pepstock.charba.client.geo.ProjectionAxis;
import org.pepstock.charba.client.geo.SizeLogarithmicAxis;
import org.pepstock.charba.client.geo.enums.Projection;
import org.pepstock.charba.client.gwt.widgets.BubbleMapChartWidget;
import org.pepstock.charba.client.items.Undefined;
import org.pepstock.charba.showcase.client.Charba_Showcase;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.resources.MyResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class GeoBubbleMapLogarithmicCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GeoBubbleMapLogarithmicCase> {
	}

	private static final Map<String, Capital> CAPITALS = new HashMap<>();

	private static final Key NAME = Key.create("name");

	@UiField
	BubbleMapChartWidget chart;

	BubbleMapDataset dataset1;

	private final List<BubbleMapDataPoint> geodata = new LinkedList<>();

	public GeoBubbleMapLogarithmicCase() {
		initWidget(uiBinder.createAndBindUi(this));

		if (CAPITALS.isEmpty()) {
			parseAndLoad();
		}

		List<Feature> stateFeatures = GeoUtil.features(Charba_Showcase.US, "states");

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Bubble map chart and logarithmic size axis");
		chart.getOptions().getElements().getBubbleMapPoint().setOutlineBorderColor(HtmlColor.RED);

		Labels labels = Labels.build();

		for (Feature f : stateFeatures) {
			String state = f.getStringProperty(NAME);
			if (CAPITALS.containsKey(state)) {
				Capital c = CAPITALS.get(state);
				labels.add(c.capital);
				geodata.add(new BubbleMapDataPoint(c.latitude, c.longitude, getRandomDigit(0, 1000000)));
			}
		}

		dataset1 = chart.newDataset();
		dataset1.setLabel("States");
		dataset1.setOutline(stateFeatures);
		dataset1.setValues(geodata);
		dataset1.setBorderColor(HtmlColor.STEEL_BLUE);
		dataset1.setBackgroundColor(new ColorCallback<DatasetContext>() {
			
			@Override
			public Object invoke(DatasetContext context) {
				if (Undefined.is(context.getDataIndex())) {
					return null;
				}
				BubbleMapDataPoint dataPoint = dataset1.getValues().get(context.getDataIndex());
				return HtmlColor.STEEL_BLUE.alpha(dataPoint.getValue()/1000000);
			}
		});
		
		ProjectionAxis axis1 = new ProjectionAxis(chart);
		axis1.setProjection(Projection.ALBERS_USA);
		
		SizeLogarithmicAxis axis2 = new SizeLogarithmicAxis(chart);
		axis2.setDisplay(false);
		chart.getOptions().getScales().setAxes(axis1, axis2);

		chart.getData().setLabels(labels);
		chart.getData().setDatasets(dataset1);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (BubbleMapDataPoint g : geodata) {
			g.setValue(getRandomDigit(0, 1000000));
		}
		dataset1.setValues(geodata);
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

	private void parseAndLoad() {
		String[] records = MyResources.INSTANCE.dataUSCapitals().getText().split("\n");
		for (int i = 1; i < records.length; i++) {
			Capital c = new Capital(records[i]);
			CAPITALS.put(c.state, c);
		}
	}

	private static class Capital {

		private final String state;

		private final String capital;

		private final double latitude;

		private final double longitude;

		private Capital(String record) {
			String[] values = record.split(",");
			this.state = values[0];
			this.capital = values[1];
			this.latitude = Double.parseDouble(values[2]);
			this.longitude = Double.parseDouble(values[3]);
		}

	}
}
