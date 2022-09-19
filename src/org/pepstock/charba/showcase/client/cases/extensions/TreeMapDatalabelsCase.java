package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.DatasetContext;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.datalabels.DataLabelsContext;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.callbacks.FormatterCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.datalabels.enums.Anchor;
import org.pepstock.charba.client.enums.Weight;
import org.pepstock.charba.client.gwt.widgets.TreeMapChartWidget;
import org.pepstock.charba.client.impl.plugins.enums.GwtMaterialScheme;
import org.pepstock.charba.client.items.DataItem;
import org.pepstock.charba.client.treemap.TreeMapDataset;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TreeMapDatalabelsCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TreeMapDatalabelsCase> {
	}

	@UiField
	TreeMapChartWidget chart;

	public TreeMapDatalabelsCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Tree map chart using Datalabels plugin");

		TreeMapDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		dataset1.setBackgroundColor(new ColorCallback<DatasetContext>() {
			
			private List<IsColor> colors = GwtMaterialScheme.RED.getColors();

			@Override
			public Object invoke(DatasetContext context) {
				if (context.getDataIndex() < 0) {
					return null;
				}
				int index = months - context.getDataIndex() - 1;
				return colors.get(index);
			}
			
		});

		IsColor color1 = GoogleChartColor.values()[0];
		dataset1.setBorderColor(color1.toHex());
		
		DataLabelsOptions optionds = new DataLabelsOptions();
		optionds.setAlign(Align.CENTER);
		optionds.setAnchor(Anchor.CENTER);
		optionds.setColor(HtmlColor.BLACK);
		optionds.getFont().setSize(24);
		optionds.getFont().setWeight(Weight.BOLD);
		optionds.setFormatter(new FormatterCallback() {
			
			private final Key value = Key.create("v");
			
			@Override
			public String invoke(DataLabelsContext context, DataItem dataItem) {
				DataPoint p = dataItem.getValueAsDataPoint();
				return String.valueOf(p.getAttribute(value));
			}
		});
		dataset1.setOptions(DataLabelsPlugin.ID, optionds);

		dataset1.setTree(getRandomDigits(months, false));
		
		chart.getData().setDatasets(dataset1);
		
		DataLabelsOptions option = new DataLabelsOptions();
		option.setDisplay(true);
		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			TreeMapDataset tds = (TreeMapDataset)dataset;
			tds.setTree(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
	
}
