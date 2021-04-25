package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.DoughnutDataset;
import org.pepstock.charba.client.data.PieDataset;
import org.pepstock.charba.client.gwt.widgets.DoughnutChartWidget;
import org.pepstock.charba.client.labels.Label;
import org.pepstock.charba.client.labels.LabelsOptions;
import org.pepstock.charba.client.labels.LabelsPlugin;
import org.pepstock.charba.client.labels.enums.Position;
import org.pepstock.charba.client.labels.enums.Render;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class LabelsPositioningCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LabelsPositioningCase> {
	}

	@UiField
	DoughnutChartWidget chart;

	final LabelsOptions options = new LabelsOptions();
	final Label label = options.createLabel("label");

	public LabelsPositioningCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLegend().setPosition(org.pepstock.charba.client.enums.Position.RIGHT);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Positioning labels");
		chart.getOptions().getTitle().getPadding().setBottom(32);

		DoughnutDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 1));
		dataset.setData(getRandomDigits(months, false));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);

		label.setRender(Render.LABEL);
		label.setColor(HtmlColor.WHITE);
		label.getFont().setSize(16);
		label.getFont().setFamily("'Lucida Console', Monaco, monospace");
		label.setArc(true);
		label.setPosition(Position.BORDER);
		label.setOverlap(false);

		chart.getOptions().getPlugins().setOptions(LabelsPlugin.ID, options);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		if (months < 12) {
			chart.getData().getLabels().add(getLabel());
			months++;
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset ds : datasets) {
				PieDataset pds = (PieDataset) ds;
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

	@UiHandler("outside")
	protected void handleOutside(ClickEvent event) {
		boolean checked = ((CheckBox) event.getSource()).getValue();
		label.setPosition(checked ? Position.OUTSIDE : Position.BORDER);
		label.setColor(checked ? HtmlColor.BLACK : HtmlColor.WHITE);
		chart.getNode().getOptions().getPlugins().setOptions(LabelsPlugin.ID, options);
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
