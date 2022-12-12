package org.pepstock.charba.showcase.client.cases.plugins;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.enums.AxisPosition;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.impl.plugins.Crosshair;
import org.pepstock.charba.client.impl.plugins.CrosshairOptions;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class CrosshairStackedAxesCase extends BaseComposite {
	
	private static final String[] VALUES = {"ON", "OFF"}; 

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, CrosshairStackedAxesCase> {
	}

	@UiField
	LineChartWidget chart;
	
	@UiField
	CheckBox upperScale;
	
	private final LineDataset dataset1; 

	private final LineDataset dataset2;
	
	private final CartesianLinearAxis axis1;
	
	private final CartesianCategoryAxis axis2;

	public CrosshairStackedAxesCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Crosshair on stacked axes");

		axis1 = new CartesianLinearAxis(chart, "y-axis-1");
		axis1.setDisplay(true);
		axis1.setPosition(AxisPosition.LEFT);
		axis1.setStack("myStack");
		axis1.setStackWeight(2);
		axis1.setMin(0);
		axis1.setMax(100);
		axis1.getGrid().setZ(-1);
		axis1.getBorder().setColor(HtmlColor.RED);
		axis1.getBorder().setWidth(3);
		
		axis2 = new CartesianCategoryAxis(chart, "y-axis-2", AxisKind.Y);
		axis2.setDisplay(true);
		axis2.setLabels(VALUES);
		axis2.setPosition(AxisPosition.LEFT);
		axis2.setOffset(true);
		axis2.setStack("myStack");
		axis2.setStackWeight(1);
		axis2.getGrid().setZ(-1);
		axis2.getBorder().setColor(HtmlColor.BLUE);
		axis2.getBorder().setWidth(3);

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

		CrosshairOptions options = new CrosshairOptions(); 
		options.setYScaleID(axis1.getId());
		chart.getOptions().getPlugins().setOptions(Crosshair.ID, options);
		
		chart.getPlugins().add(Crosshair.get());
	}

	@UiHandler("upperScale")
	protected void handleUpperScale(ClickEvent event) {
		CrosshairOptions options = chart.getOptions().getPlugins().getOptions(Crosshair.ID, Crosshair.FACTORY);
		options.setYScaleID(upperScale.getValue() ? axis2.getId() : axis1.getId());
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
