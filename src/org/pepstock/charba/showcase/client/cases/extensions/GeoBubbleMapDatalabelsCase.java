package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.datalabels.DataLabelsContext;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.callbacks.FormatterCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.enums.Weight;
import org.pepstock.charba.client.geo.BubbleMapDataPoint;
import org.pepstock.charba.client.geo.BubbleMapDataset;
import org.pepstock.charba.client.geo.Feature;
import org.pepstock.charba.client.geo.GeoUtils;
import org.pepstock.charba.client.geo.ProjectionAxis;
import org.pepstock.charba.client.geo.SizeAxis;
import org.pepstock.charba.client.geo.enums.Position;
import org.pepstock.charba.client.geo.enums.Projection;
import org.pepstock.charba.client.gwt.widgets.BubbleMapChartWidget;
import org.pepstock.charba.client.items.DataItem;
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

public class GeoBubbleMapDatalabelsCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GeoBubbleMapDatalabelsCase> {
	}

	private static final Map<String, Capital> CAPITALS = new HashMap<>();

	private static final Key NAME = Key.create("name");

	@UiField
	BubbleMapChartWidget chart;

	BubbleMapDataset dataset1;

	private final List<BubbleMapDataPoint> geodata = new LinkedList<>();

	public GeoBubbleMapDatalabelsCase() {
		initWidget(uiBinder.createAndBindUi(this));

		if (CAPITALS.isEmpty()) {
			parseAndLoad();
		}

		List<Feature> stateFeatures = GeoUtils.features(Charba_Showcase.US, "states");

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("US bubble map chart");
		chart.getOptions().getElements().getBubbleMapPoint().setOutlineBorderColor(HtmlColor.RED);

		Labels labels = Labels.build();

		for (Feature f : stateFeatures) {
			String state = f.getStringProperty(NAME);
			if (CAPITALS.containsKey(state)) {
				Capital c = CAPITALS.get(state);
				labels.add(c.capital);
				geodata.add(new BubbleMapDataPoint(c.latitude, c.longitude, getRandomDigit(0, 1000)));
			}
		}

		dataset1 = chart.newDataset();
		dataset1.setLabel("States");
		dataset1.setOutline(stateFeatures);
		dataset1.setValues(geodata);
		dataset1.setBackgroundColor(HtmlColor.LIGHT_CORAL);

		ProjectionAxis axis1 = new ProjectionAxis(chart);
		axis1.setProjection(Projection.ALBERS_USA);
		
		SizeAxis axis2 = new SizeAxis(chart);
		axis2.getLegend().setAlign(org.pepstock.charba.client.geo.enums.Align.RIGHT);
		axis2.getLegend().setPosition(Position.BOTTOM_RIGHT);
		axis2.setRange(1, 20);
		chart.getOptions().getScales().setAxes(axis1, axis2);

		chart.getData().setLabels(labels);
		chart.getData().setDatasets(dataset1);

		DataLabelsOptions option = new DataLabelsOptions();
		option.setAlign(Align.TOP);
		option.setColor(HtmlColor.BLACK);
		option.getFont().setWeight(Weight.BOLD);
		option.setFormatter(new FormatterCallback() {
			
			@Override
			public String invoke(DataLabelsContext context, DataItem dataItem) {
				BubbleMapDataPoint bmp = dataItem.createDataPoint(BubbleMapDataPoint.FACTORY);
				return "v: ["+bmp.getValue()+"]";
			}
		});

		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (BubbleMapDataPoint g : geodata) {
			g.setValue(getRandomDigit(0, 1000));
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
