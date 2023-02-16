package org.pepstock.charba.showcase.client.cases.coloring;

import java.util.Collections;

import org.pepstock.charba.client.callbacks.DatasetContext;
import org.pepstock.charba.client.callbacks.FillCallback;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.items.FillBaseline;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class FillingBaselineCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, FillingBaselineCase> {
	}

	@UiField
	LineChartWidget chart;

	private LineDataset dataset = null;

	public FillingBaselineCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Setting filling colors on line chart");

		dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setData(getRandomDigits(months));
		dataset.setBorderWidth(0);
		dataset.setPointRadius(0);
		dataset.setFill(new FillCallback() {
			
			@Override
			public Object invoke(DatasetContext context) {
				double max = Collections.max(dataset.getData());
				double min = Collections.min(dataset.getData());
				return new FillBaseline((min + max) / 2);
			}
		});

		CartesianLinearAxis axis = new CartesianLinearAxis(chart);
		axis.getTicks().setAutoSkip(false);
		axis.getTicks().setMaxRotation(0);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);
		chart.getOptions().getScales().setAxes(axis);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		dataset.setData(getRandomDigits(months));
		double max = Collections.max(dataset.getData());
		double min = Collections.min(dataset.getData());
		dataset.setFillBaseline((min + max) / 2);
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
