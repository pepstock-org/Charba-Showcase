package org.pepstock.charba.showcase.client.cases.elements;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.enums.AxisPosition;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class StackedAxesCase extends BaseComposite {
	
	private static final String[] VALUES = {"ON", "OFF"}; 

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, StackedAxesCase> {
	}

	@UiField
	LineChartWidget chart;
	
	private final LineDataset dataset1; 

	private final LineDataset dataset2; 

	public StackedAxesCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Stacked axes on line chart");

		CartesianLinearAxis axis1 = new CartesianLinearAxis(chart, "y-axis-1");
		axis1.setDisplay(true);
		axis1.setPosition(AxisPosition.LEFT);
		axis1.setStack("myStack");
		axis1.setStackWeight(2);
		axis1.setMin(0);
		axis1.setMax(100);
		axis1.getGrid().setBorderColor(HtmlColor.RED);
		axis1.getGrid().setBorderWidth(3);
		axis1.getGrid().setZ(-1);
		
		CartesianCategoryAxis axis2 = new CartesianCategoryAxis(chart, "y-axis-2", AxisKind.Y);
		axis2.setDisplay(true);
		axis2.setLabels(VALUES);
		axis2.setPosition(AxisPosition.LEFT);
		axis2.setOffset(true);
		axis2.setStack("myStack");
		axis2.setStackWeight(1);
		axis2.getGrid().setBorderColor(HtmlColor.BLUE);
		axis2.getGrid().setBorderWidth(3);
		axis2.getGrid().setZ(-1);

		chart.getOptions().getScales().setAxes(axis1, axis2);

		dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		dataset1.setBorderColor(HtmlColor.ORANGE_RED);
		dataset1.setPointBackgroundColor(HtmlColor.ORANGE_RED);
		dataset1.setData(getRandomDigits(months, 0, 100));
		dataset1.setYAxisID("y-axis-1");

		dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		dataset2.setBorderColor(HtmlColor.CORNFLOWER_BLUE);
		dataset2.setPointBackgroundColor(HtmlColor.CORNFLOWER_BLUE);
		dataset2.setDataString(getRandomData());
		dataset2.setYAxisID("y-axis-2");
		dataset2.setStepped(true);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		dataset1.setData(getRandomDigits(months, 0, 100));
		dataset2.setDataString(getRandomData());
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
	
	private List<String> getRandomData(){
		List<String> result = new LinkedList<>();
		for (int i=0;i<months; i++) {
			int index = Math.random() <= 0.5 ? 0 : 1;
			result.add(VALUES[index]);
		}
		return result;
	}
}