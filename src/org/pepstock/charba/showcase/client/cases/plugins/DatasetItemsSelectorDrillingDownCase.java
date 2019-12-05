package org.pepstock.charba.showcase.client.cases.plugins;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.TimeSeriesLineChart;
import org.pepstock.charba.client.callbacks.TimeTickCallback;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.Axis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.data.TimeSeriesItem;
import org.pepstock.charba.client.data.TimeSeriesLineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.impl.plugins.DatasetRangeSelectionEvent;
import org.pepstock.charba.client.impl.plugins.DatasetRangeSelectionEventHandler;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelector;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelectorOptions;
import org.pepstock.charba.client.items.TimeTickItem;
import org.pepstock.charba.showcase.client.Charba_Showcase;
import org.pepstock.charba.showcase.client.cases.commons.Colors;
import org.pepstock.charba.showcase.client.cases.jsinterop.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class DatasetItemsSelectorDrillingDownCase extends BaseComposite {
	
	private static final DateTimeFormat DAY_FORMAT = DateTimeFormat.getFormat("dd MMM yyyy");
	
	private static final DateTimeFormat HOUR_FORMAT = DateTimeFormat.getFormat("HH:mm");

	private static final DateTimeFormat DAY_HOUR_FORMAT = DateTimeFormat.getFormat("dd MMM yyyy HH:mm");

	private static final long MINUTE = 1000 * 60;
	
	private static final long HOUR = MINUTE * 60;
	
	private static final int AMOUNT_OF_POINTS = 6 * 24;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DatasetItemsSelectorDrillingDownCase> {
	}

	@UiField
	TimeSeriesLineChart chart;

	@UiField
	Button reset;

	TimeSeriesLineDataset dataset;
	
	CartesianTimeAxis axis;
		
	DatasetsItemsSelector plugin = new DatasetsItemsSelector();
	
	DatasetsItemsSelectorOptions pOptions = new DatasetsItemsSelectorOptions();
	
	public DatasetItemsSelectorDrillingDownCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Drilling down into dataset data on timeseries line chart");

		dataset = chart.newDataset();

		dataset.setLabel("dataset 1");
		dataset.setFill(Fill.FALSE);

		IsColor color1 = Colors.ALL[0];

		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());
		
		@SuppressWarnings("deprecation")
		Date myDate = new Date(119, 11, 1, 0, 0);
		long time = myDate.getTime();

		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false);
		List<TimeSeriesItem> data = new LinkedList<>();

		time = time + HOUR * AMOUNT_OF_POINTS;
		for (int i = AMOUNT_OF_POINTS; i >= 0; i--) {
			data.add(new TimeSeriesItem(new Date(time), xs1[i]));
			time = time - HOUR;
		}
		dataset.setTimeSeriesData(data);

		axis = chart.getOptions().getScales().getTimeAxis();
		axis.setDistribution(ScaleDistribution.SERIES);
		axis.getTime().setUnit(TimeUnit.DAY);
		axis.getTime().setStepSize(2);
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTicks().setCallback(new TimeTickCallback() {
			
			@SuppressWarnings("deprecation")
			@Override
			public String onCallback(Axis axis, Date value, String label, int index, List<TimeTickItem> values) {
				Charba_Showcase.LOG.info("TICK CALLBACK");
				boolean toPrintDate = index == 0 || index == (values.size()-1);
				if (reset.isEnabled()) {
					if (toPrintDate) {
						return DAY_HOUR_FORMAT.format(value);
					} else	if (value.getHours() == 0 && value.getMinutes() == 0) {
						return DAY_FORMAT.format(value);
					} else {
						return HOUR_FORMAT.format(value);
					}
				} else	if (toPrintDate || value.getHours() == 0) {
					return DAY_FORMAT.format(value);
				} else if (value.getHours() == 12) {
					return HOUR_FORMAT.format(value);
				}
				return "";
			}
		});
		
		CartesianLinearAxis axis2 = chart.getOptions().getScales().getLinearAxis();
		axis2.setDisplay(true);
		axis2.getTicks().setBeginAtZero(true);

		chart.getData().setDatasets(dataset);
		
		pOptions.setBorderWidth(2);
		pOptions.setBorderDash(6, 3, 6);
		pOptions.setBorderColor(HtmlColor.GREY);
		pOptions.setColor(HtmlColor.LIGHT_GOLDEN_ROD_YELLOW.alpha(DatasetsItemsSelectorOptions.DEFAULT_ALPHA));
		pOptions.setFireEventOnClearSelection(false);
		
		chart.getOptions().getPlugins().setOptions(DatasetsItemsSelector.ID, pOptions);
		chart.getPlugins().add(plugin);
		
		chart.addHandler(new DatasetRangeSelectionEventHandler() {
			
			@Override
			public void onSelect(DatasetRangeSelectionEvent event) {
				if (!reset.isEnabled()) {
					reset.setEnabled(true);
					List<TimeSeriesItem> items = dataset.getTimeSeriesData();
					TimeSeriesItem from = items.get(event.getFrom());
					TimeSeriesItem to = items.get(event.getTo());
					axis.getTicks().setMin(from.getTime());
					axis.getTicks().setMax(to.getTime());
					axis.getTime().setUnit(TimeUnit.HOUR);
					chart.getOptions().getPlugins().setEnabled(DatasetsItemsSelector.ID, false);
					plugin.onDestroy(chart);
					chart.reconfigure();
				}
			}
		}, DatasetRangeSelectionEvent.TYPE);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (TimeSeriesItem dp : dataset.getTimeSeriesData()) {
			dp.setValue(getRandomDigit(false));
		}
		chart.update();
	}

	@UiHandler("reset")
	protected void handleReset(ClickEvent event) {
		reset.setEnabled(false);
		axis.getTicks().setMin(null);
		axis.getTicks().setMax(null);
		axis.getTime().setUnit(TimeUnit.DAY);
		chart.getOptions().getPlugins().setOptions(DatasetsItemsSelector.ID, pOptions);
		chart.reconfigure();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
