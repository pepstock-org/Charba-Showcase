package org.pepstock.charba.showcase.client.cases.charts;

import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.TooltipLabelCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarBorderRadius;
import org.pepstock.charba.client.data.BarBorderWidth;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.items.TooltipLabelColor;
import org.pepstock.charba.showcase.client.Charba_Showcase;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class BarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, BarCase> {
	}

	@UiField
	BarChartWidget chart;

	public BarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Bar chart");
		//chart.getOptions().getLegend().setRtl(true);
		
		chart.getOptions().getTooltips().getCallbacks().setLabelCallback(new TooltipLabelCallback() {

			@Override
			public TooltipLabelColor onLabelColor(IsChart chart, TooltipItem item) {
				return TooltipLabelCallback.super.onLabelColor(chart, item);
			}
			
		});
		
		chart.getOptions().getElements().getBar().setBackgroundColor(HtmlColor.GREEN);
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		
		//dataset1.setBorderWidth(4);
		
		BarBorderWidth b = new BarBorderWidth(3);
		b.setTop(10);
		b.setBottom(0);
		
		dataset1.setBorderWidth(b, new BarBorderWidth(3), new BarBorderWidth(6));

		//dataset1.setHoverBorderWidth(new BarBorderWidth(6), new BarBorderWidth(3));
	
		dataset1.setData(getRandomDigits(months));
		
//		dataset1.setBorderWidth(new BarBorderWidthCallback() {
//			
//			@Override
//			public Object invoke(IsChart chart, ScriptableContext context) {
//				return 6;
//			}
//		});
//		
//		dataset1.setHoverBorderWidth(new BarBorderWidthCallback() {
//			
//			@Override
//			public Object invoke(IsChart chart, ScriptableContext context) {
//				return 12;
//			}
//		});
//		
//		dataset1.setBorderRadius(new BorderRadiusCallback() {
//			
//			@Override
//			public Object invoke(IsChart chart, ScriptableContext context) {
//				return 20;
//			}
//		});
		
		//dataset1.setBorderSkipped(BorderSkipped.BOTTOM, BorderSkipped.TOP, BorderSkipped.FALSE, BorderSkipped.START, BorderSkipped.END, BorderSkipped.FALSE, BorderSkipped.LEFT);
		
		//chart.getOptions().getElements().getBar().setBorderRadius(20);
		
		dataset1.setBorderRadius(new BarBorderRadius(6));
		//chart.getOptions().getElements().getBar().setBorderRadius(10);
//		dataset1.setBorderRadius(new BorderRadiusCallback() {
//			
//			@Override
//			public Object invoke(IsChart chart, ScriptableContext context) {
//				BarBorderRadius result = new BarBorderRadius();
//				result.set(20);
//				return 40;
//			}
//		});
//		Charba_Showcase.LOG.info(dataset1.toJSON());
//		for (BarBorderWidth br : dataset1.getHoverBorderWidthAsObjects()) {
//			Charba_Showcase.LOG.info(br.toJSON());
//		}
//		for (BarBorderWidth br : dataset1.getBorderWidthAsObjects()) {
//			Charba_Showcase.LOG.info(br.toJSON());
//		}
		
		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);
		dataset2.setData(getRandomDigits(months));

		Charba_Showcase.LOG.info(chart.getOptions().toJSON());
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);

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
