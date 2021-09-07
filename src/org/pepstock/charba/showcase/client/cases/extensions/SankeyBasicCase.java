package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Collections;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.TooltipTitleCallback;
import org.pepstock.charba.client.enums.FontStyle;
import org.pepstock.charba.client.gwt.widgets.SankeyChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.sankey.SankeyDataPoint;
import org.pepstock.charba.client.sankey.SankeyDataset;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class SankeyBasicCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, SankeyBasicCase> {
	}

	@UiField
	SankeyChartWidget chart;

	public SankeyBasicCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLayout().getPadding().setRight(26);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Basic sankey chart");
		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new TooltipTitleCallback() {

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				return Collections.emptyList();
			}

		});

		SankeyDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		dataset1.setNodeWidth(25);
		dataset1.getFont().setSize(32);
		dataset1.getFont().setStyle(FontStyle.BOLD);

		List<SankeyDataPoint> datapoints = dataset1.getDataPoints(true);
		datapoints.add(new SankeyDataPoint("a", "b", 20));
		datapoints.add(new SankeyDataPoint("c", "d", 10));
		datapoints.add(new SankeyDataPoint("c", "e", 5));

		chart.getData().setDatasets(dataset1);

	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
