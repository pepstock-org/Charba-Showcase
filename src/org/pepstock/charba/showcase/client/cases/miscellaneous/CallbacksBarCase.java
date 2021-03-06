package org.pepstock.charba.showcase.client.cases.miscellaneous;

import org.pepstock.charba.client.callbacks.BorderSkippedCallback;
import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.DatasetContext;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.BorderSkipped;
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

public class CallbacksBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, CallbacksBarCase> {
	}

	@UiField
	BarChartWidget chart;

	public CallbacksBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Callbacks on bar chart dataset");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setBorderWidth(5);

		dataset1.setBackgroundColor(new ColorCallback<DatasetContext>() {

			@Override
			public IsColor invoke(DatasetContext context) {
				return GoogleChartColor.values()[context.getDataIndex() + 1];
			}

		});
		dataset1.setBorderColor(new ColorCallback<DatasetContext>() {

			@Override
			public IsColor invoke(DatasetContext context) {
				return GoogleChartColor.values()[context.getDataIndex() + 5];
			}

		});
		dataset1.setBorderSkipped(new BorderSkippedCallback() {

			@Override
			public BorderSkipped invoke(DatasetContext context) {
				return BorderSkipped.BOTTOM;
			}

		});

		dataset1.setData(getFixedDigits(months));
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
