package org.pepstock.charba.showcase.client.cases.miscellaneous;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.BackgroundColorCallback;
import org.pepstock.charba.client.callbacks.BorderColorCallback;
import org.pepstock.charba.client.callbacks.BorderSkippedCallback;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarBorderWidth;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.BorderSkipped;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class CallbacksBarCase extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, CallbacksBarCase> {
	}

	@UiField
	BarChart chart;
	
	public CallbacksBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Callbacks on bar chart dataset");
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		BarBorderWidth border = new BarBorderWidth();
		border.setTop(2);
		border.setLeft(2);
		border.setRight(2);
		dataset1.setBorderWidth(border);
		
		dataset1.setBackgroundColor(new BackgroundColorCallback() {

			@Override
			public IsColor invoke(IsChart chart, ScriptableContext context) {
				return Colors.ALL[context.getIndex()+1];
			}
			
		});
		dataset1.setBorderColor(new BorderColorCallback() {

			@Override
			public IsColor invoke(IsChart chart, ScriptableContext context) {
				return Colors.ALL[context.getIndex()+5];
			}

		});
		dataset1.setBorderSkipped(new BorderSkippedCallback() {

			@Override
			public BorderSkipped invoke(IsChart chart, ScriptableContext context) {
				return BorderSkipped.BOTTOM;
			}
			
		});

		dataset1.setData(getFixedDigits(months));
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
