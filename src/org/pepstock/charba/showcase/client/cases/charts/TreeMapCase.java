package org.pepstock.charba.showcase.client.cases.charts;

import java.util.List;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.DatasetContext;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.gwt.widgets.TreeMapChartWidget;
import org.pepstock.charba.client.impl.plugins.enums.GwtMaterialScheme;
import org.pepstock.charba.client.treemap.TreeMapDataset;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TreeMapCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TreeMapCase> {
	}

	@UiField
	TreeMapChartWidget chart;

	public TreeMapCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Tree map chart");

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

		dataset1.setTree(getRandomDigits(months, false));
		
		chart.getData().setDatasets(dataset1);
		
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
