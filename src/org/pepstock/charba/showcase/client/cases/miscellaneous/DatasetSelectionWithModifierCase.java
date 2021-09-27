package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.data.PieDataset;
import org.pepstock.charba.client.enums.ModifierKey;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.events.DatasetSelectionEventHandler;
import org.pepstock.charba.client.gwt.widgets.PieChartWidget;
import org.pepstock.charba.client.impl.plugins.ChartPointer;
import org.pepstock.charba.client.impl.plugins.ChartPointerOptions;
import org.pepstock.charba.client.impl.plugins.enums.PointerElement;
import org.pepstock.charba.client.utils.toast.enums.DefaultToastType;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class DatasetSelectionWithModifierCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DatasetSelectionWithModifierCase> {
	}

	@UiField
	PieChartWidget chart;
	
	@UiField
	HTMLPanel help;

	public DatasetSelectionWithModifierCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Selecting dataset on pie chart");

		chart.addHandler(new DatasetSelectionEventHandler() {

			@Override
			public void onSelect(DatasetSelectionEvent event) {
				if (!ModifierKey.ALT.isPressed(event)) {
					new Toast("Missing key!", "To select the dataset you must press "+ModifierKey.ALT.getElement().getInnerHTML()+" + click! ", DefaultToastType.WARNING).show();
					return;
				}
				IsChart chart = (IsChart) event.getSource();
				Labels labels = chart.getData().getLabels();
				List<Dataset> datasets = chart.getData().getDatasets();
				if (datasets != null && !datasets.isEmpty()) {
					StringBuilder sb = new StringBuilder();
					sb.append("Dataset index: <b>").append(event.getItem().getDatasetIndex()).append("</b><br>");
					sb.append("Dataset label: <b>").append(datasets.get(event.getItem().getDatasetIndex()).getLabel()).append("</b><br>");
					sb.append("Dataset data: <b>").append(datasets.get(event.getItem().getDatasetIndex()).getData().get(event.getItem().getIndex())).append("</b><br>");
					sb.append("Index: <b>").append(event.getItem().getIndex()).append("</b><br>");
					sb.append("Value: <b>").append(labels.getStrings(event.getItem().getIndex()).get(0)).append("</b><br>");
					new Toast("Dataset Selected!", sb.toString()).show();
				}

			}
		}, DatasetSelectionEvent.TYPE);

		PieDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 1));
		dataset.setData(getRandomDigits(months, false));

		ChartPointerOptions op = new ChartPointerOptions();
		op.setElements(PointerElement.DATASET);
		chart.getOptions().getPlugins().setOptions(ChartPointer.ID, op);
		chart.getPlugins().add(ChartPointer.get());

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);
		
		HTML html = new HTML("Press " + ModifierKey.ALT.getElement().getInnerHTML() + " + click to select");
		help.add(html);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
