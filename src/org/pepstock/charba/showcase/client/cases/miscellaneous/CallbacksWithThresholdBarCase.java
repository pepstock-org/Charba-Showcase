package org.pepstock.charba.showcase.client.cases.miscellaneous;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.BackgroundColorCallback;
import org.pepstock.charba.client.callbacks.BorderColorCallback;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.BarBorderWidth;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class CallbacksWithThresholdBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, CallbacksWithThresholdBarCase> {
	}

	@UiField
	BarChartWidget chart;

	private static final IsColor INFO_COLOR = HtmlColor.GREEN;

	private static final double WARNING = 60D;

	private static final IsColor WARNING_COLOR = HtmlColor.ORANGE;

	private static final double ERROR = 85D;

	private static final IsColor ERROR_COLOR = HtmlColor.RED;

	public CallbacksWithThresholdBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Callbacks on bar chart dataset");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		BarBorderWidth border = new BarBorderWidth();
		border.setTop(2);
		border.setLeft(2);
		border.setRight(2);
		dataset1.setBorderWidth(border);

		dataset1.setBackgroundColor(new BackgroundColorCallback() {

			@Override
			public IsColor invoke(IsChart chart, ScriptableContext context) {
				Dataset dataset = chart.getData().getDatasets().get(context.getDatasetIndex());
				Double value = dataset.getData().get(context.getIndex());
				if (value >= ERROR) {
					return ERROR_COLOR.alpha(0.2D);
				} else if (value >= WARNING) {
					return WARNING_COLOR.alpha(0.2D);
				}
				return INFO_COLOR.alpha(0.2D);
			}

		});
		dataset1.setBorderColor(new BorderColorCallback() {

			@Override
			public IsColor invoke(IsChart chart, ScriptableContext context) {
				Dataset dataset = chart.getData().getDatasets().get(context.getDatasetIndex());
				Double value = dataset.getData().get(context.getIndex());
				if (value >= ERROR) {
					return ERROR_COLOR;
				} else if (value >= WARNING) {
					return WARNING_COLOR;
				}
				return INFO_COLOR;
			}

		});
		dataset1.setData(getRandomDigits(months, 0, 100));

		CartesianLinearAxis axis = new CartesianLinearAxis(chart);
		axis.setMax(100);
		axis.setMin(0);
		axis.setBeginAtZero(true);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, 0, 100));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
