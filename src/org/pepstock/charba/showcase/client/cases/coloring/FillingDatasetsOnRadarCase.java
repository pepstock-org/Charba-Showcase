package org.pepstock.charba.showcase.client.cases.coloring;

import java.util.List;

import org.pepstock.charba.client.RadarChart;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.RadarDataset;
import org.pepstock.charba.client.enums.DefaultPlugin;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.IsFill;
import org.pepstock.charba.client.plugins.AbstractPluginOptions;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class FillingDatasetsOnRadarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, FillingDatasetsOnRadarCase> {
	}

	private static final boolean[] HIDDENS = {false, true, false, false, false, false};
	
	private static final  IsFill[] FILLS = {null, Fill.getFill("-1"), Fill.getFill(1), Fill.FALSE, Fill.getFill("-1"), Fill.getFill("-1")};

	@UiField
	RadarChart chart;
	
	@UiField
	CheckBox smooth;

	@UiField
	CheckBox propagate;
	
	private final FillerOptions options = new FillerOptions();
	
	public FillingDatasetsOnRadarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getLegend().setDisplay(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Filling datasets mode on radar chart");
		chart.getOptions().getElements().getLine().setTension(0.000001D);
		chart.getOptions().setSpanGaps(false);
		
		List<Dataset> datasets = chart.getData().getDatasets(true);
		
		// radar chart doesn't support stacked values, let's do it manually
		double increment = 10;
		double max = 0;
		
		for (int i=0; i<6; i++) {
			RadarDataset dataset1 = chart.newDataset();
			dataset1.setLabel("dataset "+i);

			IsColor color1 = GoogleChartColor.values()[i];

			dataset1.setBackgroundColor(color1.alpha(0.2D));
			dataset1.setBorderColor(color1);
			IsFill fill = FILLS[i];
			if (fill != null) {
				dataset1.setFill(fill);
			}
			dataset1.setHidden(HIDDENS[i]);
			// radar chart doesn't support stacked values, let's do it manually
			dataset1.setData(getRandomDigits(months, max+1, max + increment));
			datasets.add(dataset1);
			max += increment;
		}

		chart.getData().setLabels(getLabels());
		
		options.setPropagate(false);
		chart.getOptions().getPlugins().setOptions(DefaultPlugin.FILLER.value(), options);
		
	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		// radar chart doesn't support stacked values, let's do it manually
		double increment = 10;
		double max = 0;
		for (Dataset dataset : chart.getData().getDatasets()) {
			// radar chart doesn't support stacked values, let's do it manually
			dataset.setData(getRandomDigits(months, max+1, max + increment));
			max += increment;
		}
		chart.update();
	}
	
	@UiHandler("smooth")
	protected void handleSmooth(ClickEvent event) {
		double value = smooth.getValue() ? 0.4D : 0.000001D;
		chart.getOptions().getElements().getLine().setTension(value);
		chart.reconfigure();
	}

	@UiHandler("propagate")
	protected void handlePropagate(ClickEvent event) {
		options.setPropagate(propagate.getValue());
		chart.getOptions().getPlugins().setOptions(DefaultPlugin.FILLER.value(), options);
		chart.reconfigure();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
		
	private static class FillerOptions extends AbstractPluginOptions{
		
		private Key propagate = Key.create("propagate");
		
		/**
		 * @param pluginId
		 */
		FillerOptions() {
			super(DefaultPlugin.FILLER.value());
		}

		void setPropagate(boolean prop) {
			setValue(propagate, prop);
		}

		
	}
}
