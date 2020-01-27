package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.callbacks.LegendLabelsCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.PointStyle;
import org.pepstock.charba.client.items.LegendLabelItem;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ApplyingPointStylesOnLineCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ApplyingPointStylesOnLineCase> {
	}

	@UiField
	LineChart chart;

	@UiField
	ListBox pointStyles;

	LineDataset dataset = null;

	IsColor color = null;

	public ApplyingPointStylesOnLineCase() {
		initWidget(uiBinder.createAndBindUi(this));

		for (PointStyle myPointStyle : PointStyle.values()) {
			pointStyles.addItem(myPointStyle.name(), myPointStyle.name());
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(true);
		chart.getOptions().getLegend().getLabels().setUsePointStyle(true);
		chart.getOptions().getLegend().getLabels().setFontSize(15);
		chart.getOptions().getLegend().getLabels().setLabelsCallback(new LegendLabelsCallback() {

			@Override
			public List<LegendLabelItem> generateLegendLabels(IsChart chart, List<LegendLabelItem> defaultLabels) {
				String selectedPointStyle = pointStyles.getSelectedValue();
				for (LegendLabelItem item : defaultLabels) {
					item.setPointStyle(PointStyle.valueOf(selectedPointStyle));
					item.setStrokeStyle(color);
					item.setFillStyle(color);
				}
				return defaultLabels;
			}
		});
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Applying point styles on line chart");

		dataset = chart.newDataset();
		dataset.setLabel("My dataset");

		color = GoogleChartColor.values()[0];
		dataset.setBackgroundColor(color.toHex());
		dataset.setBorderColor(color.toHex());
		dataset.setData(getRandomDigits(months));
		dataset.setFill(Fill.FALSE);
		dataset.setPointRadius(10D);
		dataset.setPointHoverRadius(20D);
		dataset.setShowLine(false);
		dataset.setPointStyle(PointStyle.CIRCLE);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		dataset.setData(getRandomDigits(months));
		chart.update();
	}

	@UiHandler("pointStyles")
	protected void handlePointStyle(ChangeEvent event) {
		String selectedPointStyle = pointStyles.getSelectedValue();
		int index = 0;
		for (PointStyle style : PointStyle.values()) {
			if (style.name().equalsIgnoreCase(selectedPointStyle)) {
				color = GoogleChartColor.values()[index];
				dataset.setPointStyle(style);
				dataset.setBackgroundColor(color.toHex());
				dataset.setBorderColor(color.toHex());
				chart.update();
				return;
			}
			index++;
		}
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
