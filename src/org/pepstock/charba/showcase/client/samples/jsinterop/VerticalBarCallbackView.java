package org.pepstock.charba.showcase.client.samples.jsinterop;

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
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class VerticalBarCallbackView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, VerticalBarCallbackView> {
	}

	@UiField
	BarChart chart;
	
	public VerticalBarCallbackView() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Bar Chart");
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.80D));
		
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
