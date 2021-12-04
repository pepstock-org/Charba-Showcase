package org.pepstock.charba.showcase.client.views;

import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.TickCallback;
import org.pepstock.charba.client.colors.Color;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.Axis;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.events.DatasetSelectionEventHandler;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.impl.plugins.ChartPointer;
import org.pepstock.charba.client.impl.plugins.ChartPointerOptions;
import org.pepstock.charba.client.impl.plugins.enums.PointerElement;
import org.pepstock.charba.showcase.client.Charba_Showcase;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class HomeView extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, HomeView> {
	}

	@UiField
	LineChartWidget chart;

	@UiField
	CheckBox loading;

	private static final String LINK_GITHUB_VERSION = "https://github.com/pepstock-org/Charba/releases/tag/";

	private static final String DEFAULT_FORMAT = "#0.#";

	private static final String[] LABELS = { "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "2.0", "2.1", "2.2", "2.3", "2.4", "2.5", "2.6", "2.7", "2.8", "3.0", "3.1",  "3.2", "3.3", "4.0", "4.1", "4.2", "5.0"};

	private static final double[] VALUES_GWT = { 746, 760, 763, 832, 861, 863, 1200, 1550, 1710, 1720, 1910, 1950, 2040, 2334, 2536, 3064, 3091, 3125, 3125, 4098, 4541, 4843, 4924};
	
	private static final double[] VALUES_J2CL = { Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, 2881, 2910, 2941, 2942, 3933, 4336, 4697, 4775};

	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getFormat(DEFAULT_FORMAT);

	public HomeView() {
		initWidget(uiBinder.createAndBindUi(this));

		loading.setValue(Charba_Showcase.isDeferred);

		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getLegend().setDisplay(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba artifacts");
		chart.getOptions().getTooltips().setEnabled(false);

		LineDataset datasetGwt = chart.newDataset();
		datasetGwt.setLabel("GWT");

		datasetGwt.setBackgroundColor(Color.CHARBA);
		datasetGwt.setBorderColor(Color.CHARBA);
		datasetGwt.setBorderWidth(4);
		datasetGwt.setPointBackgroundColor(HtmlColor.WHITE);
		datasetGwt.setPointBorderColor(Color.CHARBA);
		datasetGwt.setPointBorderWidth(1);
		datasetGwt.setPointRadius(4);
		datasetGwt.setPointHoverRadius(4);
		datasetGwt.setPointHoverBorderWidth(1);
		datasetGwt.setPointHitRadius(4);
		datasetGwt.setFill(false);
		datasetGwt.setData(VALUES_GWT);

		LineDataset datasetJ2CL = chart.newDataset();
		datasetJ2CL.setLabel("J2CL");

		datasetJ2CL.setBackgroundColor(HtmlColor.CORNFLOWER_BLUE);
		datasetJ2CL.setBorderColor(HtmlColor.CORNFLOWER_BLUE);
		datasetJ2CL.setBorderWidth(4);
		datasetJ2CL.setPointBackgroundColor(HtmlColor.WHITE);
		datasetJ2CL.setPointBorderColor(HtmlColor.CORNFLOWER_BLUE);
		datasetJ2CL.setPointBorderWidth(1);
		datasetJ2CL.setPointRadius(4);
		datasetJ2CL.setPointHoverRadius(4);
		datasetJ2CL.setPointHoverBorderWidth(1);
		datasetJ2CL.setPointHitRadius(4);
		datasetJ2CL.setFill(false);
		datasetJ2CL.setData(VALUES_J2CL);
		chart.addHandler(new DatasetSelectionEventHandler() {

			@Override
			public void onSelect(DatasetSelectionEvent event) {
				IsChart chart = (IsChart) event.getSource();
				Labels labels = chart.getData().getLabels();
				String version = labels.getString(event.getItem().getIndex());
				StringBuilder sb = new StringBuilder(LINK_GITHUB_VERSION);
				sb.append(version);
				Window.open(sb.toString(), "_blank", "");
			}

		}, DatasetSelectionEvent.TYPE);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getTitle().setDisplay(true);
		axis1.getTitle().setText("Charba version");
		axis1.getTitle().setColor(HtmlColor.BLACK);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getGrid().setZ(-1);
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

		axis2.getTitle().setDisplay(true);
		axis2.getTitle().setText("JAR size");
		axis2.getTitle().setColor(HtmlColor.BLACK);

		chart.getOptions().getScales().setAxes(axis1, axis2);

		chart.getData().setLabels(LABELS);
		chart.getData().setDatasets(datasetGwt, datasetJ2CL);
		
		ChartPointerOptions options = new ChartPointerOptions();
		options.setElements(PointerElement.DATASET, PointerElement.LEGEND);
		chart.getOptions().getPlugins().setOptions(ChartPointer.ID, options);

		chart.getPlugins().add(ChartPointer.get());
	}

	@UiHandler("loading")
	protected void handleLoading(ClickEvent event) {
		String href = Window.Location.getHref();
		StringBuilder cleanHref = null;
		if (href.contains("?")) {
			cleanHref = new StringBuilder(href.substring(0, href.indexOf("?")));
		} else {
			cleanHref = new StringBuilder(href);
		}
		cleanHref.append("?").append(Charba_Showcase.LOADING_PARAM).append("=").append(loading.getValue() ? Charba_Showcase.LOADING_DEFERRED : Charba_Showcase.LOADING_EMBEDDED);
		Window.Location.replace(cleanHref.toString());
	}

}
