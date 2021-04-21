package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PieDataset;
import org.pepstock.charba.client.enums.FontStyle;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.PieChartWidget;
import org.pepstock.charba.client.labels.Label;
import org.pepstock.charba.client.labels.LabelsOptions;
import org.pepstock.charba.client.labels.LabelsPlugin;
import org.pepstock.charba.client.labels.LabelsContext;
import org.pepstock.charba.client.labels.callbacks.RenderCallback;
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

public class LabelsUsingValueRenderCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LabelsUsingValueRenderCase> {
	}

	@UiField
	PieChartWidget chart;

	final MyRenderer renderer = new MyRenderer();

	final LabelsOptions option = new LabelsOptions();
	final Label label = option.createLabel("myLabel");

	public LabelsUsingValueRenderCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Using labels with values on pie chart");

		PieDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 1));
		dataset.setData(getRandomDigits(months, false));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);

		label.setRender(Render.VALUE);
		label.setPrecision(2);
		label.setColor(HtmlColor.WHITE);
		label.getFont().setSize(14);
		label.getFont().setStyle(FontStyle.BOLD);
		label.getFont().setFamily("'Lucida Console', Monaco, monospace");
		label.setOverlap(false);

		chart.getOptions().getPlugins().setOptions(LabelsPlugin.ID, option);
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
		PieDataset dataset = chart.newDataset();
		dataset.setLabel("dataset " + (datasets.size() + 1));
		dataset.setBackgroundColor(getSequenceColors(months, 1));
		dataset.setData(getRandomDigits(months, false));

		datasets.add(dataset);

		chart.update();

	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		removeDataset(chart);
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

	@UiHandler("format")
	protected void handleFormat(ClickEvent event) {
		boolean checked = ((CheckBox) event.getSource()).getValue();
		if (checked) {
			label.setRender(renderer);
		} else {
			label.setRender(Render.VALUE);
		}
		chart.getNode().getOptions().getPlugins().setOptions(LabelsPlugin.ID, option);
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

	static class MyRenderer implements RenderCallback {

		@Override
		public String invoke(LabelsContext item) {
			return "$$ " + item.getDataItem().getValue();
		}

	}
}
