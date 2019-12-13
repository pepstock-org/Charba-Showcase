package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.PolarAreaChart;
import org.pepstock.charba.client.callbacks.BackgroundColorCallback;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.colors.Pattern;
import org.pepstock.charba.client.colors.tiles.Shape;
import org.pepstock.charba.client.colors.tiles.TilesFactory;
import org.pepstock.charba.client.configuration.RadialAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PolarAreaDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.impl.plugins.HtmlLegend;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class CallbacksPolarAreaCase extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, CallbacksPolarAreaCase> {
	}

	@UiField
	PolarAreaChart chart;

	public CallbacksPolarAreaCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.RIGHT);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Callbacks on polar area chart dataset");
		
		RadialAxis axis = new RadialAxis(chart);
		axis.getTicks().setBeginAtZero(true);
		axis.getTicks().setReverse(true);
		chart.getOptions().setAxis(axis);
		
		PolarAreaDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(new BackgroundColorCallback() {
			
			private final List<Pattern> patterns = new LinkedList<>();;

			@Override
			public Object invoke(IsChart chart, ScriptableContext context) {
				if (patterns.isEmpty()) {
					for (int i=0; i<months; i++) {
						Shape shape = Shape.values()[i];
						Pattern pattern = TilesFactory.createPattern(shape, Colors.ALL[i]);
						patterns.add(pattern);
					}
				}
				return patterns.get(context.getIndex());
			}			
		});

		dataset.setData(getRandomDigits(months, false));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);

		chart.getPlugins().add(HtmlLegend.get());

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
