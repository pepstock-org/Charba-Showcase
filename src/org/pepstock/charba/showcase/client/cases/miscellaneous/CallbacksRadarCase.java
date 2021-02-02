package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.RadiusCallback;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.RadarDataset;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.RadarChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class CallbacksRadarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, CallbacksRadarCase> {
	}

	@UiField
	RadarChartWidget chart;

	private final RadiusCallback radiusCallback = new RadiusCallback() {

		@Override
		public Double invoke(IsChart chart, ScriptableContext context) {
			int module = context.getDataIndex() % 2;
			return context.getDatasetIndex() % 2 == 0 ? module == 0 ? 50D : 25D : module == 0 ? 25D : 50D;
		}

	};

	private static final double MAX = 20D;

	private static final double MIN = 5D;

	public CallbacksRadarCase() {
		initWidget(uiBinder.createAndBindUi(this));
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Callbacks on radar chart dataset");

		chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(false);
		chart.getOptions().getHover().setMode(InteractionMode.NEAREST);
		chart.getOptions().getHover().setIntersect(true);

		List<Dataset> datasets = chart.getData().getDatasets(true);

		RadarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color.alpha(0.2D));
		dataset1.setBorderColor(color);
		dataset1.setPointBackgroundColor(color);
		dataset1.setPointRadius(new RadiusCallback() {

			@Override
			public Double invoke(IsChart chart, ScriptableContext context) {
				return (double) ((int) (Math.random() * (MAX - MIN))) + MIN;
			}
		});
		double[] values = getRandomDigits(months);
		dataset1.setPointHoverBackgroundColor(color);
		dataset1.setPointHoverRadius(radiusCallback);

		dataset1.setData(values);
		datasets.add(dataset1);

		chart.getData().setLabels(getLabels());
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		addData(chart);
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
