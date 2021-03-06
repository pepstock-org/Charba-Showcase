package org.pepstock.charba.showcase.client.cases.charts;

import java.util.List;

import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.DoughnutDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.DoughnutChartWidget;
import org.pepstock.charba.client.items.OptionsNode;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class DoughnutCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DoughnutCase> {
	}

	@UiField
	DoughnutChartWidget chart;
	
	@UiField
	CheckBox semiCircle;

	public DoughnutCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Doughnut chart");

		DoughnutDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 1));

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

	@UiHandler("add_dataset")
	protected void handleAddDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();
		DoughnutDataset dataset = chart.newDataset();
		dataset.setLabel("dataset " + (datasets.size() + 1));
		dataset.setBackgroundColor(getSequenceColors(months, 1));
		dataset.setData(getRandomDigits(months, false));
		datasets.add(dataset);
		chart.update();
		semiCircle.setValue(false);
		semiCircle.setEnabled(false);
	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		if (chart.getData().getDatasets().size() > 1){
			removeDataset(chart);
			if (chart.getData().getDatasets().size() == 1){
				semiCircle.setValue(false);
				semiCircle.setEnabled(true);
			}
		}
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		if (months < 12) {
			chart.getData().getLabels().add(getLabel());
			months++;
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset ds : datasets) {
				DoughnutDataset pds = (DoughnutDataset) ds;
				pds.setBackgroundColor(getSequenceColors(months, 1));
				pds.getData().add(getRandomDigit(false));
			}
			chart.update();
		}
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
	}

	@UiHandler("semiCircle")
	protected void handleSemiCircle(ClickEvent event) {
		OptionsNode options = chart.getNode().getOptions();
		if (options.getCircumference() == 180D) {
			options.setCircumference(360D);
			options.setRotation(-45D);
		} else {
			options.setCircumference(180D);
			options.setRotation(-90D);
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
