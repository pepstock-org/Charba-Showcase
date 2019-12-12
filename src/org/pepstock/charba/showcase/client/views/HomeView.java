package org.pepstock.charba.showcase.client.views;

import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.callbacks.TickCallback;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.Axis;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.events.DatasetSelectionEventHandler;
import org.pepstock.charba.client.impl.plugins.ChartPointer;
import org.pepstock.charba.client.impl.plugins.ChartPointerOptions;
import org.pepstock.charba.client.impl.plugins.enums.PointerElement;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class HomeView extends BaseComposite{

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, HomeView> {
	}

	@UiField
	LineChart chart;

	private static final String LINK_GITHUB_VERSION = "https://github.com/pepstock-org/Charba/releases/tag/";
	
	private static final String DEFAULT_FORMAT = "#0.#";

	private static final String COLOR = "#f27173";
	
	private static final String[] LABELS = {"", "1.0", "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "2.0", "2.1", "2.2", "2.3", "2.4", "2.5", "2.6", "2.7", ""};
	
	private static final double[] VALUES = {Double.NaN, Double.NaN, Double.NaN, 746, 760, 763, 832, 861, 863, 1200, 1550, 1710, 1720, 1910, 1950, 2040, 2328, Double.NaN};

	// it formats the number of ticks
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getFormat(DEFAULT_FORMAT);
	
	public HomeView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("charba-[version.release].jar");
		chart.getOptions().getTooltips().setEnabled(false);
		
		LineDataset dataset = chart.newDataset();
		dataset.setLabel("JAR dimension");

		dataset.setBackgroundColor(COLOR);
		dataset.setBorderColor(COLOR);
		dataset.setBorderWidth(5);
		dataset.setPointBackgroundColor(HtmlColor.WHITE);
		dataset.setPointBorderColor(COLOR);
		dataset.setPointBorderWidth(1);
		dataset.setPointRadius(4);
		dataset.setPointHoverRadius(4);
		dataset.setPointHoverBorderWidth(1);
		dataset.setPointHitRadius(4);
		dataset.setFill(false);
		dataset.setData(VALUES);
		
		chart.addHandler(new DatasetSelectionEventHandler() {
			
			@Override
			public void onSelect(DatasetSelectionEvent event) {
				IsChart chart = (IsChart)event.getSource();
				Labels labels = chart.getData().getLabels();
				String version = labels.getString(event.getItem().getIndex());
				StringBuilder sb = new StringBuilder(LINK_GITHUB_VERSION);
				sb.append(version);
				Window.open(sb.toString(), "_blank", "");
			}

		}, DatasetSelectionEvent.TYPE);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Charba version");
		axis1.getScaleLabel().setFontColor(HtmlColor.BLACK);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getTicks().setCallback(new TickCallback() {
			
			@Override
			public String onCallback(Axis axis, double value, int index, List<Double> values) {
				if (value >= 1000D) {
					double thousands = value / 1000D;
					return NUMBER_FORMAT.format(thousands) + " MB";
					
				}
				return value + " KB";
			}
		});
		
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Dimension");
		axis2.getScaleLabel().setFontColor(HtmlColor.BLACK);
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);

		chart.getData().setLabels(LABELS);
		chart.getData().setDatasets(dataset);

		ChartPointerOptions options = new ChartPointerOptions();
		options.setElements(PointerElement.DATASET);
		chart.getOptions().getPlugins().setOptions(ChartPointer.ID, options);

		chart.getPlugins().add(ChartPointer.get());

	}

}
