package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.logging.Logger;

import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.jsinterop.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.jsinterop.data.BarDataset;
import org.pepstock.charba.client.jsinterop.data.Dataset;
import org.pepstock.charba.client.jsinterop.enums.Position;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class HorizontalMyFlagsBarView extends BaseComposite{
	
	Logger LOG = Logger.getLogger("chart");
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	private static final String[] COUNTRIES = {"br","de","fr","gb","it","us"};
	
	interface ViewUiBinder extends UiBinder<Widget, HorizontalMyFlagsBarView> {
	}
	
	@UiField
	HorizontalBarMyChart chart;
	
	CartesianCategoryAxis axis;

	public HorizontalMyFlagsBarView() {
		initWidget(uiBinder.createAndBindUi(this));
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.right);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Horizontal Bar Chart with Flags");
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("Countries");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		dataset1.setData(getRandomDigits(COUNTRIES.length, false));

		axis = new CartesianCategoryAxis(chart);
		axis.setDisplay(true);
		axis.getScaleLabel().setDisplay(true);
//		axis.getScaleLabel().getPadding().setTop(100);

		chart.getData().setLabels(COUNTRIES);
		chart.getData().setDatasets(dataset1);

		chart.getOptions().getScales().setYAxes(axis);
	
	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}
	
}
