package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.FontCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.FloatingData;
import org.pepstock.charba.client.enums.DataType;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.items.FontItem;
import org.pepstock.charba.client.labels.Label;
import org.pepstock.charba.client.labels.LabelsContext;
import org.pepstock.charba.client.labels.LabelsOptions;
import org.pepstock.charba.client.labels.LabelsPlugin;
import org.pepstock.charba.client.labels.callbacks.RenderCallback;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class LabelsBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LabelsBarCase> {
	}

	@UiField
	BarChartWidget chart;

	public LabelsBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Labels on bar chart");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1);

		double[] values = getRandomDigits(months);
		double[] gaps = getRandomDigits(months, false);

		List<FloatingData> data = dataset1.getFloatingData(true);
		for (int i=0; i<months; i++) {
			data.add(new FloatingData(values[i], values[i] + gaps[i]));
		}
		
		LabelsOptions options = new LabelsOptions();
		Label label = options.createLabel("bar");
		label.setRender(new RenderCallback() {

			@Override
			public String invoke(LabelsContext context) {
				double value = 0;
				if (DataType.ARRAYS.equals(context.getDataItem().getDataType())) {
					value = context.getDataItem().getValueAsFloatingData().getAbsValue();
				} else {
					value = context.getDataItem().getValue();
				}
				return "$$ " + value;
			}
		});
		label.setColor(new ColorCallback<LabelsContext>() {

			@Override
			public IsColor invoke(LabelsContext context) {
			if (DataType.ARRAYS.equals(context.getDataItem().getDataType())) {
					return context.getDataItem().getValueAsFloatingData().getAbsValue() > 25 ? HtmlColor.RED : HtmlColor.BLACK;
				} else {
					return context.getDataItem().getValue() > 25 ? HtmlColor.RED : HtmlColor.BLACK;
				}
			}
		});

		label.setFont(new FontCallback<LabelsContext>() {

			private final FontItem font = new FontItem();
			
			@Override
			public FontItem invoke(LabelsContext context) {
				font.setSize(16);
				return font;
			}
		});

		chart.getOptions().getPlugins().setOptions(LabelsPlugin.ID, options);

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

	@UiHandler("add_dataset")
	protected void handleAddDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();

		BarDataset dataset = chart.newDataset();
		dataset.setLabel("dataset " + (datasets.size() + 1));

		IsColor color = GoogleChartColor.values()[datasets.size()];
		dataset.setBackgroundColor(color.alpha(0.2));
		dataset.setBorderColor(color.toHex());
		dataset.setBorderWidth(1);
		dataset.setData(getRandomDigits(months));

		datasets.add(dataset);

		chart.update();
	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		removeDataset(chart);
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		addData(chart);
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
	
}
