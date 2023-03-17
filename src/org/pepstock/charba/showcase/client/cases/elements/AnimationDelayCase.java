package org.pepstock.charba.showcase.client.cases.elements;

import org.pepstock.charba.client.callbacks.DatasetContext;
import org.pepstock.charba.client.callbacks.DelayCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.Animation;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.StackedBarDataset;
import org.pepstock.charba.client.enums.ContextType;
import org.pepstock.charba.client.enums.DefaultTransitionMode;
import org.pepstock.charba.client.enums.DefaultInteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.events.AnimationCompleteEvent;
import org.pepstock.charba.client.events.AnimationCompleteEventHandler;
import org.pepstock.charba.client.gwt.widgets.StackedBarChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class AnimationDelayCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AnimationDelayCase> {
	}

	@UiField
	StackedBarChartWidget chart;

    private boolean delayed = false;

	public AnimationDelayCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Delay drawing on stacked bar chart");
		chart.getOptions().getTooltips().setMode(DefaultInteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(false);

		StackedBarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		dataset1.setData(getRandomDigits(months));

		StackedBarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);
		dataset2.setData(getRandomDigits(months));

		StackedBarDataset dataset3 = chart.newDataset();
		dataset3.setLabel("dataset 3");

		IsColor color3 = GoogleChartColor.values()[2];

		dataset3.setBackgroundColor(color3.alpha(0.2));
		dataset3.setBorderColor(color3.toHex());
		dataset3.setBorderWidth(1);
		dataset3.setData(getRandomDigits(months));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2, dataset3);
		
		chart.addHandler(new AnimationCompleteEventHandler() {
			
			@Override
			public void onComplete(AnimationCompleteEvent event) {
				delayed = true;
			}
		}, AnimationCompleteEvent.TYPE);

		Animation animation = chart.getOptions().getAnimation();
		animation.setDelay(new DelayCallback() {
			@Override
			public Integer invoke(DatasetContext context) {
				int delay = 0;
				if (ContextType.DATA.equals(context.getType()) && DefaultTransitionMode.DEFAULT.equals(context.getMode()) && !delayed) {
					delay = context.getDataIndex() * 300 + context.getDatasetIndex() * 100;
				}
				return delay;
			}

		});
		
	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		delayed = false;
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
