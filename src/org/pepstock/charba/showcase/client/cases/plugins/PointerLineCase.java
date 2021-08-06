package org.pepstock.charba.showcase.client.cases.plugins;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.UpdateConfigurationBuilder;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.dom.enums.CursorType;
import org.pepstock.charba.client.events.AxisClickEvent;
import org.pepstock.charba.client.events.AxisClickEventHandler;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.events.DatasetSelectionEventHandler;
import org.pepstock.charba.client.events.SubtitleClickEvent;
import org.pepstock.charba.client.events.SubtitleClickEventHandler;
import org.pepstock.charba.client.events.TitleClickEvent;
import org.pepstock.charba.client.events.TitleClickEventHandler;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.impl.plugins.ChartPointer;
import org.pepstock.charba.client.impl.plugins.ChartPointerOptions;
import org.pepstock.charba.client.impl.plugins.enums.PointerElement;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class PointerLineCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, PointerLineCase> {
	}

	@UiField
	LineChartWidget chart;

	@UiField
	ListBox cursor;

	@UiField
	CheckBox dataset;

	@UiField
	CheckBox legend;

	@UiField
	CheckBox title;

	@UiField
	CheckBox subtitle;

	@UiField
	CheckBox axes;

	private final ChartPointerOptions options = new ChartPointerOptions();

	private final List<PointerElement> elements = new LinkedList<>();

	public PointerLineCase() {
		initWidget(uiBinder.createAndBindUi(this));

		cursor.addItem("Default", CursorType.POINTER.name());
		for (CursorType myC : CursorType.values()) {
			cursor.addItem(myC.name(), myC.name());
		}

		for (PointerElement element : PointerElement.values()) {
			elements.add(element);
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getLegend().setDisplay(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Setting cursors on line chart");
		chart.getOptions().getSubtitle().setDisplay(true);
		chart.getOptions().getSubtitle().setText("Subtitle: setting cursors on line chart");
		chart.getOptions().getTooltips().setEnabled(false);

		List<Dataset> datasets = chart.getData().getDatasets(true);

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setFill(false);
		double[] values = getRandomDigits(months);
		List<Double> data = dataset1.getData(true);
		for (int i = 0; i < values.length; i++) {
			data.add(values[i]);
		}
		datasets.add(dataset1);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(false);
		datasets.add(dataset2);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getTitle().setDisplay(true);
		axis1.getTitle().setText("Month");

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getTitle().setDisplay(true);
		axis2.getTitle().setText("Value");

		chart.getOptions().getScales().setAxes(axis1, axis2);

		chart.getData().setLabels(getLabels());

		chart.addHandler(new DatasetSelectionEventHandler() {

			@Override
			public void onSelect(DatasetSelectionEvent event) {
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

		chart.addHandler(new TitleClickEventHandler() {

			@Override
			public void onClick(TitleClickEvent event) {
				IsChart chart = (IsChart) event.getSource();
				List<String> values = chart.getOptions().getTitle().getText();
				StringBuilder title = new StringBuilder();
				if (!values.isEmpty()) {
					for (String value : values) {
						title.append(value).append(" ");
					}
				}
				StringBuilder sb = new StringBuilder();
				sb.append("Title: <b>").append(title.toString()).append("</b><br>");
				new Toast("Title Selected!", sb.toString()).show();
			}
		}, TitleClickEvent.TYPE);

		chart.addHandler(new SubtitleClickEventHandler() {

			@Override
			public void onClick(SubtitleClickEvent event) {
				IsChart chart = (IsChart) event.getSource();
				List<String> values = chart.getOptions().getTitle().getText();
				StringBuilder title = new StringBuilder();
				if (!values.isEmpty()) {
					for (String value : values) {
						title.append(value).append(" ");
					}
				}
				StringBuilder sb = new StringBuilder();
				sb.append("Subtitle: <b>").append(title.toString()).append("</b><br>");
				new Toast("Subtitle Selected!", sb.toString()).show();
			}
		}, SubtitleClickEvent.TYPE);

		chart.addHandler(new AxisClickEventHandler() {

			@Override
			public void onClick(AxisClickEvent event) {
				StringBuilder sb = new StringBuilder();
				sb.append("Axis: value: <b>").append(event.getValue().getLabel()).append("</b><br>");
				new Toast("Axis Selected!", sb.toString()).show();
			}
		}, AxisClickEvent.TYPE);

		chart.getPlugins().add(ChartPointer.get());

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

	@UiHandler("cursor")
	protected void handleCursor(ChangeEvent event) {
		String selected = cursor.getSelectedValue();
		CursorType myC = CursorType.valueOf(selected);
		options.setCursorPointer(myC);
		chart.getOptions().getPlugins().setOptions(ChartPointer.ID, options);
		chart.reconfigure(UpdateConfigurationBuilder.create().setDuration(1000).build());
	}

	@UiHandler(value = { "dataset", "legend", "title", "subtitle", "axes" })
	protected void handleElement(ClickEvent event) {
		checkElement(dataset, PointerElement.DATASET);
		checkElement(legend, PointerElement.LEGEND);
		checkElement(title, PointerElement.TITLE);
		checkElement(subtitle, PointerElement.SUBTITLE);
		checkElement(axes, PointerElement.AXES);
		options.setElements(elements.toArray(new PointerElement[0]));
		chart.getOptions().getPlugins().setOptions(ChartPointer.ID, options);
		chart.reconfigure(UpdateConfigurationBuilder.create().setDuration(1000).build());
	}

	private void checkElement(CheckBox status, PointerElement element) {
		if (status.getValue() && !elements.contains(element)) {
			elements.add(element);
		} else if (!status.getValue()) {
			elements.remove(element);
		}
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
