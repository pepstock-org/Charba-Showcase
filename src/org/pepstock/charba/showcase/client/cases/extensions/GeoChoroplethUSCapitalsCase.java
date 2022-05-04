package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.Helpers;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.ScaleContext;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.dom.elements.Context2dItem;
import org.pepstock.charba.client.dom.enums.TextBaseline;
import org.pepstock.charba.client.enums.TextAlign;
import org.pepstock.charba.client.geo.ChoroplethDataPoint;
import org.pepstock.charba.client.geo.ChoroplethDataset;
import org.pepstock.charba.client.geo.ColorAxis;
import org.pepstock.charba.client.geo.CoordinatesPoint;
import org.pepstock.charba.client.geo.Feature;
import org.pepstock.charba.client.geo.GeoUtil;
import org.pepstock.charba.client.geo.ProjectionAxis;
import org.pepstock.charba.client.geo.callbacks.InterpolateCallback;
import org.pepstock.charba.client.geo.callbacks.QuantizeCallback;
import org.pepstock.charba.client.geo.enums.Align;
import org.pepstock.charba.client.geo.enums.Position;
import org.pepstock.charba.client.geo.enums.Projection;
import org.pepstock.charba.client.gwt.widgets.ChoroplethChartWidget;
import org.pepstock.charba.client.impl.plugins.enums.BrewerScheme;
import org.pepstock.charba.client.options.IsImmutableFont;
import org.pepstock.charba.client.plugins.AbstractPlugin;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.resources.MyResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class GeoChoroplethUSCapitalsCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GeoChoroplethUSCapitalsCase> {
	}
	
	private static final Map<String, Capital> CAPITALS = new HashMap<>();
	
	private static final List<IsColor> COLORS = BrewerScheme.ACCENT4.getColors();

	private static final Key NAME = Key.create("name");

	@UiField
	ChoroplethChartWidget chart;
	
	ChoroplethDataset dataset1;

	private final List<ChoroplethDataPoint> geodata = new LinkedList<>();

	public GeoChoroplethUSCapitalsCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		if (CAPITALS.isEmpty()) {
			parseAndLoad();
		}

		List<Feature> stateFeatures = GeoUtil.features(MyResources.INSTANCE.topojsonUS().getText(), "states");
		Feature outline = GeoUtil.features(MyResources.INSTANCE.topojsonUS().getText(), "nation").get(0);

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("US choropleth chart");

		Labels labels = GeoUtil.loadLabels(stateFeatures, NAME);

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
		
		chart.getPlugins().add(new AbstractPlugin("capitals") {
			
			final IsImmutableFont font = Helpers.get().toFont(Defaults.get().getGlobal().getFont());

			@Override
			public void onAfterDraw(IsChart passedChart) {
				Context2dItem ctx = chart.getCanvas().getContext2d();
				for (Capital capital : CAPITALS.values()) {
					ctx.beginPath();
					ctx.setStrokeColor(HtmlColor.BLACK);
					ctx.setLineWidth(2);
					CoordinatesPoint point = chart.projection(capital.latitude, capital.longitude);
					if (point.isConsistent()) {
						ctx.arc(point.getX(), point.getY(), 5, 0, 2 * Math.PI);
					}
					ctx.stroke();
					ctx.setFont(font.toCSSString());
					ctx.setTextAlign(TextAlign.CENTER);
					ctx.setTextBaseline(TextBaseline.MIDDLE);
					ctx.fillText(capital.capital, point.getX(), point.getY() - 15, point.getY() - (font.getLineHeight() / 2) - 15);
				}
			}
			
		});
		
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
