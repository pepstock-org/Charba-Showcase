package org.pepstock.charba.showcase.client.cases.elements;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.UpdateConfigurationBuilder;
import org.pepstock.charba.client.callbacks.TooltipLabelCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.enums.TooltipPosition;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.items.TooltipLabelColor;
import org.pepstock.charba.showcase.client.Charba_Showcase;
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

public class TooltipPositioningCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TooltipPositioningCase> {
	}

	@UiField
	LineChartWidget chart;

	@UiField
	ListBox position;

	private LineDataset dataset1 = null;

	private LineDataset dataset2 = null;

	public TooltipPositioningCase() {
		initWidget(uiBinder.createAndBindUi(this));

		for (TooltipPosition pos : TooltipPosition.values()) {
			position.addItem(pos.name(), pos.name());
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Tooltip positioning");
		chart.getOptions().getTooltips().setPosition(TooltipPosition.AVERAGE);
		chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(false);
		
//		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new TooltipTitleCallback() {
//
//			@Override
//			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
//				List<String> values = TooltipTitleCallback.super.onTitle(chart, items);
//				Charba_Showcase.LOG.info(values.toString());
//				return values;
//			}
//
//			@Override
//			public List<String> onAfterTitle(IsChart chart, List<TooltipItem> items) {
//				return null;
//			}
//
//			@Override
//			public List<String> onBeforeTitle(IsChart chart, List<TooltipItem> items) {
//				return null;
//			}
//			
//		});
		
//		chart.getOptions().getTooltips().getCallbacks().setBodyCallback(new TooltipBodyCallback() {
//
//			@Override
//			public List<String> onBeforeBody(IsChart chart, List<TooltipItem> items) {
//				return null;
//			}
//
//			@Override
//			public List<String> onAfterBody(IsChart chart, List<TooltipItem> items) {
//				return null;
//			}
//			
//		});
//		
		chart.getOptions().getTooltips().getCallbacks().setLabelCallback(new TooltipLabelCallback() {

//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.callbacks.TooltipLabelCallback#onLabel(org.pepstock.charba.client.IsChart, org.pepstock.charba.client.items.TooltipItem)
//			 */
//			@Override
//			public String onLabel(IsChart chart, TooltipItem item) {
//				return TooltipLabelCallback.super.onLabel(chart, item);
//			}
//
			/* (non-Javadoc)
			 * @see org.pepstock.charba.client.callbacks.TooltipLabelCallback#onLabelColor(org.pepstock.charba.client.IsChart, org.pepstock.charba.client.items.TooltipItem)
			 */
			@Override
			public TooltipLabelColor onLabelColor(IsChart chart, TooltipItem item) {
				TooltipLabelColor color = TooltipLabelCallback.super.onLabelColor(chart, item);
				
				Charba_Showcase.LOG.info(color.toJSON());
				
				return color;
			}
			
			
			

//			@Override
//			public TooltipLabelColor onLabelColor(IsChart chart, TooltipItem item) {
//				return null;
//			}
//
//			@Override
//			public IsColor onLabelTextColor(IsChart chart, TooltipItem item) {
//				return null;
//			}

			
		});
//		chart.getOptions().getTooltips().getCallbacks().setFooterCallback(new TooltipFooterCallback() {
//
//			@Override
//			public List<String> onBeforeFooter(IsChart chart, List<TooltipItem> items) {
//				return null;
//			}
//
//			@Override
//			public List<String> onFooter(IsChart chart, List<TooltipItem> items) {
//				return null;
//			}
//
//			@Override
//			public List<String> onAfterFooter(IsChart chart, List<TooltipItem> items) {
//				return null;
//			}
//			
//		});

		dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		IsColor color1 = GoogleChartColor.values()[0];
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(getRandomDigits(months));
		dataset1.setFill(Fill.FALSE);

		dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 1");
		IsColor color2 = GoogleChartColor.values()[1];
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(Fill.FALSE);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		dataset1.setData(getRandomDigits(months));
		dataset2.setData(getRandomDigits(months));
		chart.update();
	}

	@UiHandler("position")
	protected void handlePosition(ChangeEvent event) {
		String selected = position.getSelectedValue();
		for (TooltipPosition cPos : TooltipPosition.values()) {
			if (cPos.name().equalsIgnoreCase(selected)) {
				chart.getOptions().getTooltips().setPosition(cPos);
				chart.reconfigure(UpdateConfigurationBuilder.create().setDuration(1000).build());
				return;
			}
		}
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
