package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.Helpers;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.dom.elements.Context2dItem;
import org.pepstock.charba.client.dom.enums.TextBaseline;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.enums.TextAlign;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.items.ChartElement;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.options.IsImmutableFont;
import org.pepstock.charba.client.plugins.SmartPlugin;
import org.pepstock.charba.client.plugins.hooks.AfterDatasetsDrawHook;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class SimpleLabelPluginOnBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, SimpleLabelPluginOnBarCase> {
	}

	@UiField
	BarChartWidget chart;

	public SimpleLabelPluginOnBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Simple label plugin on bar chart");

		final BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);

		dataset1.setData(getRandomDigits(months));

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);

		dataset2.setData(getRandomDigits(months));
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);
		
		SmartPlugin p = new SmartPlugin("simplelabel");
		p.setAfterDatasetsDrawHook(new AfterDatasetsDrawHook() {
			
			@Override
			public void onAfterDatasetsDraw(IsChart chart) {
				final IsImmutableFont font = Helpers.get().toFont(Defaults.get().getGlobal().getFont());
				final int padding = 5;
				final Context2dItem ctx = chart.getCanvas().getContext2d();

				List<Dataset> dss = chart.getData().getDatasets();
				for (int i = 0; i < dss.size(); i++) {
					DatasetItem item = chart.getDatasetItem(i);
					if (!item.isHidden()) {
						Dataset ds = dss.get(i);
						List<ChartElement> elements = item.getElements();
						for (int k = 0; k < elements.size(); k++) {
							ChartElement elem = elements.get(k);
							String dataString = ds.getData().get(k).toString();
							ctx.setFillColor("rgb(0, 0, 0)");
							ctx.setFont(font.toCSSString());
							ctx.setTextAlign(TextAlign.CENTER);
							ctx.setTextBaseline(TextBaseline.MIDDLE);
							ctx.fillText(dataString, elem.getX(), elem.getY() - (font.getLineHeight() / 2) - padding);
						}
					}
				}
			}
		});
		chart.getPlugins().add(p);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
