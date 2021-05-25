package org.pepstock.charba.showcase.client.cases.elements;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.DatasetContext;
import org.pepstock.charba.client.callbacks.DelayCallback;
import org.pepstock.charba.client.callbacks.FromCallback;
import org.pepstock.charba.client.callbacks.NativeCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.configuration.Animations;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.enums.ContextType;
import org.pepstock.charba.client.enums.DefaultAnimationPropertyKey;
import org.pepstock.charba.client.enums.DefaultScaleId;
import org.pepstock.charba.client.enums.Easing;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.items.DatasetElement;
import org.pepstock.charba.client.items.ScaleItem;
import org.pepstock.charba.client.options.AnimationCollection;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class AnimationProgressiveLineCase extends BaseComposite {

	private static final int DURATION = 10000;

	private static final int AMOUNT = 1000;

	private static final int DELAY_BETWEEN_POINTS = DURATION / AMOUNT;

	private static final NativeCallback NATIVE_DELAY_X = NativeCallback.create("if (context.type !== 'data' || context.xStarted) { return 0; } context.xStarted = true; return context.index * "+DELAY_BETWEEN_POINTS+";");
	
	private static final NativeCallback NATIVE_DELAY_Y = NativeCallback.create("if (context.type !== 'data' || context.yStarted) { return 0; } context.yStarted = true; return context.index * "+DELAY_BETWEEN_POINTS+";");
	
	private static final NativeCallback NATIVE_FROM_Y = NativeCallback.create("return context.index === 0 ? context.chart.scales.y.getPixelForValue(100) : context.chart.getDatasetMeta(context.datasetIndex).data[context.index - 1].getProps(['y'], true).y;");
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AnimationProgressiveLineCase> {
	}

	@UiField
	LineChartWidget chart;
	
	@UiField
	CheckBox nativeCallback;

    private Map<Integer, Set<Integer>> xStarted = new HashMap<>();

	private Map<Integer, Set<Integer>> yStarted = new HashMap<>();

	public AnimationProgressiveLineCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Progressive line");
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getInteraction().setIntersect(false);
		chart.getOptions().getElements().getLine().setTension(0);

		xStarted.put(0, new HashSet<>());
		xStarted.put(1, new HashSet<>());
		yStarted.put(0, new HashSet<>());
		yStarted.put(1, new HashSet<>());

		Animations animations = chart.getOptions().getAnimations();
		AnimationCollection x = animations.create(DefaultAnimationPropertyKey.X);
		x.setEasing(Easing.LINEAR);
		x.setDuration(DELAY_BETWEEN_POINTS);
		x.setFrom(Double.NaN);
		x.setDelay(new DelayCallback() {
			@Override
			public Integer invoke(DatasetContext context) {
				if (!ContextType.DATA.equals(context.getType()) || xStarted.get(context.getDatasetIndex()).contains(context.getDataIndex())) {
					return 0;
				}
				xStarted.get(context.getDatasetIndex()).add(context.getDataIndex());
				return context.getDataIndex() * DELAY_BETWEEN_POINTS;
			}

		});

		AnimationCollection y = animations.create(DefaultAnimationPropertyKey.Y);
		y.setEasing(Easing.LINEAR);
		y.setDuration(DELAY_BETWEEN_POINTS);
		y.setDelay(new DelayCallback() {
			@Override
			public Integer invoke(DatasetContext context) {
				if (!ContextType.DATA.equals(context.getType()) || yStarted.get(context.getDatasetIndex()).contains(context.getDataIndex())) {
					return 0;
				}
				yStarted.get(context.getDatasetIndex()).add(context.getDataIndex());
				return context.getDataIndex() * DELAY_BETWEEN_POINTS;
			}

		});
		
		y.setFrom(new FromCallback() {
			private Map<Integer, List<DatasetElement>> elements = new HashMap<Integer, List<DatasetElement>>();
			
			@Override
			public Double invoke(DatasetContext context) {
				IsChart chart = context.getChart();
				if (context.getDataIndex() <= 0) {
					ScaleItem scale = chart.getNode().getScales().getItems().get(DefaultScaleId.Y.value());
					return scale.getPixelForValue(100);
				}
				DatasetElement element = elements.computeIfAbsent(context.getDatasetIndex(), mapKey -> chart.getDatasetItem(context.getDatasetIndex()).getElements()).get(context.getDataIndex() - 1);
				return element.getY();
			}			
		});

		List<DataPoint> datapoint1 = new LinkedList<>();
		List<DataPoint> datapoint2 = new LinkedList<>();

		List<Dataset> datasets = chart.getData().getDatasets(true);
		
		double prev = 100;
		double prev2 = 80;
		for (int i = 0; i < AMOUNT; i++) {
			prev += 5 - Math.random() * 10;

			DataPoint dp1 = new DataPoint();
			dp1.setX(i);
			dp1.setY(prev);
			datapoint1.add(dp1);
			prev2 += 5 - Math.random() * 10;

			DataPoint dp2 = new DataPoint();
			dp2.setX(i);
			dp2.setY(prev2);
			datapoint2.add(dp2);
		}

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setBorderColor(GoogleChartColor.values()[0]);
		dataset1.setFill(false);
		dataset1.setDataPoints(datapoint1);
		dataset1.setPointRadius(0);
		datasets.add(dataset1);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		dataset2.setBorderColor(GoogleChartColor.values()[1]);
		dataset2.setFill(false);
		dataset2.setDataPoints(datapoint2);
		dataset2.setPointRadius(0);
		datasets.add(dataset2);

		CartesianLinearAxis axis1 = new CartesianLinearAxis(chart, AxisKind.X);
		axis1.setDisplay(true);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);

		chart.getOptions().getScales().setAxes(axis1, axis2);
		
	}

	@UiHandler("nativeCallback")
	protected void handleNativeCallback(ClickEvent event) {
		xStarted.get(0).clear();
		xStarted.get(1).clear();
		yStarted.get(0).clear();
		yStarted.get(1).clear();
		Animations animations = chart.getOptions().getAnimations();
		AnimationCollection x = animations.get(DefaultAnimationPropertyKey.X);
		AnimationCollection y = animations.get(DefaultAnimationPropertyKey.Y);
		if (nativeCallback.getValue()) {
			x.setDelay(NATIVE_DELAY_X);
			y.setFrom(NATIVE_FROM_Y);
			y.setDelay(NATIVE_DELAY_Y);
		} else {
			x.setDelay(new DelayCallback() {
				@Override
				public Integer invoke(DatasetContext context) {
					if (!ContextType.DATA.equals(context.getType()) || xStarted.get(context.getDatasetIndex()).contains(context.getDataIndex())) {
						return 0;
					}
					xStarted.get(context.getDatasetIndex()).add(context.getDataIndex());
					return context.getDataIndex() * DELAY_BETWEEN_POINTS;
				}

			});
			y.setDelay(new DelayCallback() {
				@Override
				public Integer invoke(DatasetContext context) {
					if (!ContextType.DATA.equals(context.getType()) || yStarted.get(context.getDatasetIndex()).contains(context.getDataIndex())) {
						return 0;
					}
					yStarted.get(context.getDatasetIndex()).add(context.getDataIndex());
					return context.getDataIndex() * DELAY_BETWEEN_POINTS;
				}

			});
			y.setFrom(new FromCallback() {
				private Map<Integer, List<DatasetElement>> elements = new HashMap<Integer, List<DatasetElement>>();
				
				@Override
				public Double invoke(DatasetContext context) {
					IsChart chart = context.getChart();
					if (context.getDataIndex() <= 0) {
						ScaleItem scale = chart.getNode().getScales().getItems().get(DefaultScaleId.Y.value());
						return scale.getPixelForValue(100);
					}
					DatasetElement element = elements.computeIfAbsent(context.getDatasetIndex(), mapKey -> chart.getDatasetItem(context.getDatasetIndex()).getElements()).get(context.getDataIndex() - 1);
					return element.getY();
				}			
			});

		}
		chart.reconfigure();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}