package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.callbacks.FontCallback;
import org.pepstock.charba.client.callbacks.ScaleContext;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.RadialAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PolarAreaDataset;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gradient.Colors;
import org.pepstock.charba.client.gradient.GradientOptions;
import org.pepstock.charba.client.gradient.PropertyOptions;
import org.pepstock.charba.client.gwt.widgets.PolarAreaChartWidget;
import org.pepstock.charba.client.impl.plugins.enums.TableauScheme;
import org.pepstock.charba.client.items.FontItem;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class GradientPolarAreaCase extends BaseComposite {
	
	private static final int MIN = 0;
	
	private static final int MAX = 160;
	
	private static final List<IsColor> COLORS = TableauScheme.JEWEL_BRIGHT9.getColors();

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GradientPolarAreaCase> {
	}

	@UiField
	PolarAreaChartWidget chart;
	
	RadialAxis axis;
	
	public GradientPolarAreaCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.RIGHT);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Using GRADIENT plugin on radial linear axis");

		PolarAreaDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setData(getRandomDigits(months, MIN, MAX));
		
		axis = new RadialAxis(chart);
		axis.setBeginAtZero(true);
		axis.setReverse(false);
		axis.getTicks().setZ(1);
		axis.getTicks().setBackdropColor(HtmlColor.TRANSPARENT);
		axis.getTicks().setColor(HtmlColor.BLACK);
		axis.getGrid().setCircular(true);
		axis.getAngleLines().setDisplay(true);
		axis.getPointLabels().setDisplay(true);
		axis.getPointLabels().setFont(new FontCallback<ScaleContext>() {
			
			@Override
			public FontItem invoke(ScaleContext context) {
				FontItem fo = new FontItem();
				fo.setSize(16);
				return fo;
			}
		});
		chart.getOptions().getScales().setAxes(axis);
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);
		
		GradientOptions options = new GradientOptions();
		PropertyOptions propOptions = options.getBackgroundColor();
		propOptions.setAxis(AxisKind.R);
		Colors stopColors = propOptions.getColors();
		int index = 0;
		for (int i = MIN; i<MAX; i++) {
			if (i % 30 == 0) {
				stopColors.setColor(i, COLORS.get(index));
				index++;
			}
		}
		dataset.setOptions(options);
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
		if (months < 12) {
			chart.getData().getLabels().add(getLabel());
			months++;
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset ds : datasets) {
				PolarAreaDataset pds = (PolarAreaDataset) ds;
				pds.getData().add(getRandomDigit(MIN, MAX));
			}
			chart.update();
		}
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
