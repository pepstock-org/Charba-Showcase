package org.pepstock.charba.showcase.client.cases.coloring;

import java.util.List;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.IsFill;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class FillingDatasetsOnLineCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, FillingDatasetsOnLineCase> {
	}

	private static final boolean[] HIDDENS = { true, false, true, false, false, false, false, false, true };

	private static final IsFill[] FILLS = { null, Fill.getFill("-1"), Fill.getFill(1), Fill.getFill("-1"), Fill.getFill("-1"), Fill.getFill("+2"), Fill.FALSE, Fill.getFill(8), Fill.END };

	@UiField
	LineChartWidget chart;

	@UiField
	CheckBox smooth;

	@UiField
	CheckBox propagate;

	public FillingDatasetsOnLineCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getLegend().setDisplay(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Filling datasets mode on line chart");
		chart.getOptions().getElements().getLine().setTension(0.000001D);
		chart.getOptions().setSpanGaps(false);
		chart.getOptions().getFiller().setPropagate(false);

		List<Dataset> datasets = chart.getData().getDatasets(true);

		for (int i = 0; i < 9; i++) {
			LineDataset dataset1 = chart.newDataset(HIDDENS[i]);
			dataset1.setLabel("dataset " + i);

			IsColor color1 = GoogleChartColor.values()[i];

			dataset1.setBackgroundColor(color1.alpha(0.2D));
			dataset1.setBorderColor(color1);
			IsFill fill = FILLS[i];
			if (fill != null) {
				dataset1.setFill(fill);
			}
			dataset1.setData(getRandomDigits(months, 5, 15));
			datasets.add(dataset1);
		}

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.setStacked(true);

		chart.getOptions().getScales().setAxes(axis1, axis2);

		chart.getData().setLabels(getLabels());
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, 5, 15));
		}
		chart.update();
	}

	@UiHandler("smooth")
	protected void handleSmooth(ClickEvent event) {
		double value = smooth.getValue() ? 0.4D : 0.000001D;
		chart.getOptions().getElements().getLine().setTension(value);
		chart.reconfigure();
	}

	@UiHandler("propagate")
	protected void handlePropagate(ClickEvent event) {
		chart.getOptions().getFiller().setPropagate(propagate.getValue());
		chart.reconfigure();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
