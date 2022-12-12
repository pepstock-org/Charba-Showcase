package org.pepstock.charba.showcase.client.cases.plugins;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.callbacks.MinMaxCallback;
import org.pepstock.charba.client.callbacks.ScaleContext;
import org.pepstock.charba.client.callbacks.TimeTickCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.Axis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeSeriesAxis;
import org.pepstock.charba.client.data.TimeSeriesItem;
import org.pepstock.charba.client.data.TimeSeriesLineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.events.DatasetRangeSelectionEvent;
import org.pepstock.charba.client.events.DatasetRangeSelectionEventHandler;
import org.pepstock.charba.client.gwt.widgets.TimeSeriesLineChartWidget;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelector;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelectorOptions;
import org.pepstock.charba.client.items.ScaleTickItem;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

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
	TimeSeriesLineChartWidget chart;

	@UiField
	Button reset;

	TimeSeriesLineDataset dataset;
	
	CartesianTimeSeriesAxis axis;

	private Date minDate = null;

	private Date maxDate = null;

	public DatasetItemsSelectorDrillingDownCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Drilling down into dataset data on timeseries line chart");

		dataset = chart.newDataset();

		dataset.setLabel("dataset 1");
		dataset.setFill(Fill.FALSE);

		IsColor color1 = GoogleChartColor.values()[0];

		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());

		@SuppressWarnings("deprecation")
		Date myDate = new Date(119, 11, 1, 0, 0);
		long time = myDate.getTime();

		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false);
		List<TimeSeriesItem> data = new LinkedList<>();

		time = time + HOUR * AMOUNT_OF_POINTS;
		for (int i = AMOUNT_OF_POINTS - 1; i >= 0; i--) {
			data.add(new TimeSeriesItem(new Date(time), xs1[i]));
			time = time - HOUR;
		}
		dataset.setTimeSeriesData(data);

		axis = chart.getOptions().getScales().getTimeAxis();
		axis.getTicks().setStepSize(1);
		axis.getTime().setUnit(TimeUnit.DAY);
		axis.setMin(new MinMaxCallback<Date>() {
			
			@Override
			public Date invoke(ScaleContext context) {
				return minDate;
			}
		});
		axis.setMax(new MinMaxCallback<Date>() {
			
			@Override
			public Date invoke(ScaleContext context) {
				return maxDate;
			}
		});
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTicks().setCallback(new TimeTickCallback() {

			@SuppressWarnings("deprecation")
			@Override
			public String onCallback(Axis axis, Date value, String label, int index, List<ScaleTickItem> values) {
				boolean toPrintDate = index == 0 || index == (values.size() - 1);
				if (reset.isEnabled()) {
					if (toPrintDate) {
						return DAY_HOUR_FORMAT.format(value);
					} else if (value.getHours() == 0 && value.getMinutes() == 0) {
						return DAY_FORMAT.format(value);
					} else {
						return HOUR_FORMAT.format(value);
					}
				} else if (toPrintDate || value.getHours() == 0) {
					return DAY_FORMAT.format(value);
				} else if (value.getHours() == 12) {
					return HOUR_FORMAT.format(value);
				}
				return "";
			}
		});

		CartesianLinearAxis axis2 = chart.getOptions().getScales().getLinearAxis();
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);

		chart.getData().setDatasets(dataset);

		DatasetsItemsSelectorOptions pOptions = new DatasetsItemsSelectorOptions();
		pOptions.setBorderWidth(2);
		pOptions.setBorderDash(6, 3, 6);
		pOptions.setBorderColor(HtmlColor.GREY);
		pOptions.setColor(HtmlColor.LIGHT_GOLDEN_ROD_YELLOW.alpha(DatasetsItemsSelectorOptions.DEFAULT_ALPHA));

		chart.getOptions().getPlugins().setOptions(DatasetsItemsSelector.ID, pOptions);
		chart.getPlugins().add(DatasetsItemsSelector.get());

		chart.addHandler(new DatasetRangeSelectionEventHandler() {
			
			@Override
			public void onSelect(DatasetRangeSelectionEvent event) {
				if (!reset.isEnabled()) {
					dataset.setBackgroundColor(HtmlColor.DARK_MAGENTA);
					dataset.setBorderColor(HtmlColor.DARK_MAGENTA);
					minDate = event.getFrom().getValueAsDate();
					maxDate = event.getTo().getValueAsDate();
					axis.getTime().setUnit(TimeUnit.HOUR);
					DatasetsItemsSelectorOptions pOptions = chart.getOptions().getPlugins().getOptions(DatasetsItemsSelector.FACTORY);
					pOptions.setEnabled(false);
				    chart.reconfigure();
					reset.setEnabled(true);
				}
			}
		}, DatasetRangeSelectionEvent.TYPE);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (TimeSeriesItem dp : dataset.getTimeSeriesData()) {
			dp.setValue(getRandomDigit(false));
		}
		handleReset(null);
	}

	@UiHandler("reset")
	protected void handleReset(ClickEvent event) {
		IsColor color1 = GoogleChartColor.values()[0];
		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());
		minDate = null;
		maxDate = null;
		axis.getTime().setUnit(TimeUnit.DAY);
		DatasetsItemsSelectorOptions pOptions = chart.getOptions().getPlugins().getOptions(DatasetsItemsSelector.FACTORY);
		pOptions.setEnabled(true);
		chart.reconfigure();
		reset.setEnabled(false);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
