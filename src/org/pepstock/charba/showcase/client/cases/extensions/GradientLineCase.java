package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.enums.DefaultInteractionMode;
import org.pepstock.charba.client.gradient.Colors;
import org.pepstock.charba.client.gradient.GradientOptions;
import org.pepstock.charba.client.gradient.PropertyOptions;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.impl.plugins.enums.TableauScheme;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class GradientLineCase extends BaseComposite {
	
	private static final int MIN = -100;
	
	private static final int MAX = 100;
	
	private static final List<IsColor> COLORS = TableauScheme.JEWEL_BRIGHT9.getColors();

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GradientLineCase> {
	}

	@UiField
	LineChartWidget chart;
	
	LineDataset dataset1;

	public GradientLineCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Using GRADIENT plugin on cartesian linear axis");
		chart.getOptions().getTooltips().setMode(DefaultInteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(false);

		List<Dataset> datasets = chart.getData().getDatasets(true);

		dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		dataset1.setFill(true);
		dataset1.setBorderWidth(0);
		
		double[] values = getRandomDigits(months, MIN, MAX);
		List<Double> data = dataset1.getData(true);
		for (int i = 0; i < values.length; i++) {
			data.add(values[i]);
		}
		datasets.add(dataset1);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getTitle().setDisplay(true);
		axis1.getTitle().setText("Month");

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getTitle().setDisplay(true);
		axis2.getTitle().setText("Value");

		chart.getOptions().getScales().setAxes(axis1, axis2);
		chart.getData().setLabels(getLabels());
		
		GradientOptions options = new GradientOptions();
		PropertyOptions propOptions = options.getBackgroundColor();
		propOptions.setAxis(AxisKind.Y);
		Colors stopColors = propOptions.getColors();
		int index = 0;
		for (int i = MIN; i<MAX; i++) {
			if (i % 30 == 0) {
				stopColors.setColor(i, COLORS.get(index));
				index++;
			}
		}

		Labels labels = chart.getData().getLabels();
		PropertyOptions prop1Options = options.getBorderColor();
		prop1Options.setAxis(AxisKind.X);
		Colors stop1Colors = prop1Options.getColors();
		for (int i=0; i<labels.size(); i++) {
			stop1Colors.setColor(i * 1.5, GoogleChartColor.values()[i]);
		}
		dataset1.setOptions(options);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, MIN, MAX));
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
	
	@UiHandler("fill")
	protected void handleOutside(ClickEvent event) {
		boolean checked = ((CheckBox) event.getSource()).getValue();
		if (checked) {
			dataset1.setFill(true);
			dataset1.setBorderWidth(0);
		} else {
			dataset1.setFill(false);
			dataset1.setBorderWidth(5);
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
