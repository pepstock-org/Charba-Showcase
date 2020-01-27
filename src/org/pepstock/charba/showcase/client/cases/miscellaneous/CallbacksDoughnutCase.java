package org.pepstock.charba.showcase.client.cases.miscellaneous;

import org.pepstock.charba.client.DoughnutChart;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.BackgroundColorCallback;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.DoughnutDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.items.OptionsNode;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class CallbacksDoughnutCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, CallbacksDoughnutCase> {
	}

	@UiField
	DoughnutChart chart;

	public CallbacksDoughnutCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Callbacks on bar chart dataset");

		DoughnutDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");

		dataset.setBackgroundColor(new BackgroundColorCallback() {

			@Override
			public IsColor invoke(IsChart chart, ScriptableContext context) {
				int size = GoogleChartColor.values().length - 1;
				return GoogleChartColor.values()[size - context.getIndex()];
			}

		});

		dataset.setData(getRandomDigits(months, false));

		chart.getData().setLabels(getLabels(months));
		chart.getData().setDatasets(dataset);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("semiCircle")
	protected void handleSemiCircle(ClickEvent event) {
		OptionsNode options = chart.getNode().getOptions();
		if (options.getCircumference() == Math.PI) {
			options.setCircumference(2 * Math.PI);
			options.setRotation(-Math.PI / 2);
		} else {
			options.setCircumference(Math.PI);
			options.setRotation(-Math.PI);
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
