package org.pepstock.charba.showcase.client.cases.plugins;

import java.util.List;
import java.util.Random;

import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.BubbleDataset;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.dom.elements.Div;
import org.pepstock.charba.client.dom.enums.IsKeyboardKey;
import org.pepstock.charba.client.dom.enums.KeyboardUiKey;
import org.pepstock.charba.client.events.DatasetRangeCleanSelectionEvent;
import org.pepstock.charba.client.events.DatasetRangeCleanSelectionEventHandler;
import org.pepstock.charba.client.events.DatasetRangeSelectionEvent;
import org.pepstock.charba.client.events.DatasetRangeSelectionEventHandler;
import org.pepstock.charba.client.gwt.widgets.BubbleChartWidget;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelector;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelectorOptions;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class DatasetItemsSelectorBubbleCase extends BaseComposite {

	private static final int AMOUNT_OF_POINTS = 16;
	private static final int MIN_XY = -150;
	private static final int MAX_XY = 100;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DatasetItemsSelectorBubbleCase> {
	}

	@UiField
	BubbleChartWidget chart;
	
	@UiField
	HTMLPanel help;

	public DatasetItemsSelectorBubbleCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Dataset items selector plugin on bubble chart");

		BubbleDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		String[] colors = new String[AMOUNT_OF_POINTS];
		String[] hcolors = new String[AMOUNT_OF_POINTS];
		String[] bcolors = new String[AMOUNT_OF_POINTS];
		int[] bwidth = new int[AMOUNT_OF_POINTS];
		int[] hbwidth = new int[AMOUNT_OF_POINTS];

		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			dp1[i] = new DataPoint();
			dp1[i].setX(getData());
			dp1[i].setY(getData());
			dp1[i].setR(getData(0, 50));
			colors[i] = colorize(false, dp1[i]);
			bcolors[i] = colorize(true, dp1[i]);
			bwidth[i] = Math.min(Math.max(1, i + 1), 5);
			hcolors[i] = "transparent";
			hbwidth[i] = (int) Math.round(8 * dp1[i].getR() / 1000);
		}
		dataset1.setBackgroundColor(colors);
		dataset1.setBorderColor(bcolors);
		dataset1.setBorderWidth(bwidth);
		dataset1.setHoverBackgroundColor(hcolors);
		dataset1.setHoverBorderWidth(hbwidth);
		dataset1.setDataPoints(dp1);

		chart.getData().setDatasets(dataset1);
		
		DatasetsItemsSelectorOptions pOptions = new DatasetsItemsSelectorOptions();
		pOptions.setBorderWidth(2);
		pOptions.setBorderDash(6, 3, 6);
		pOptions.setBorderColor(HtmlColor.GREY);
		pOptions.getSelectionCleaner().setDisplay(false);
		pOptions.setColor(HtmlColor.LIGHT_PINK.alpha(DatasetsItemsSelectorOptions.DEFAULT_ALPHA));

		chart.getOptions().getPlugins().setOptions(DatasetsItemsSelector.ID, pOptions);
		chart.getPlugins().add(DatasetsItemsSelector.get());

		chart.addHandler(new DatasetRangeCleanSelectionEventHandler() {

			@Override
			public void onClean(DatasetRangeCleanSelectionEvent event) {
				StringBuilder sb = new StringBuilder();
				sb.append("<b>Clear selection event</b>");
				new Toast("Dataset Range Clean Selection!", sb.toString()).show();
			}
		}, DatasetRangeCleanSelectionEvent.TYPE);
		
		chart.addHandler(new DatasetRangeSelectionEventHandler() {

			@Override
			public void onSelect(DatasetRangeSelectionEvent event) {
				StringBuilder sb = new StringBuilder();
				sb.append("Dataset from: <b>").append(event.getFrom().getLabel()).append("</b><br>");
				sb.append("Dataset to: <b>").append(event.getTo().getLabel()).append("</b><br>");
				new Toast("Dataset Range Selected!", sb.toString()).show();
			}
		}, DatasetRangeSelectionEvent.TYPE);
		
		Div kkey = IsKeyboardKey.getElement(KeyboardUiKey.ESCAPE);
		HTML html = new HTML("Press " + kkey.getInnerHTML() + " to clear selection");
		help.add(html);
	}

	private int getData() {
		return getData(MIN_XY, MAX_XY);
	}

	private int getData(int min, int max) {
		Random random = new Random();
		return random.nextInt(max + 1 - min) + min;
	}

	private String colorize(boolean opaque, DataPoint value) {
		double x = value.getX() / 100;
		double y = value.getY() / 100;
		int r = x < 0 && y < 0 ? 250 : x < 0 ? 150 : y < 0 ? 50 : 0;
		int g = x < 0 && y < 0 ? 0 : x < 0 ? 50 : y < 0 ? 150 : 250;
		int b = x < 0 && y < 0 ? 0 : x > 0 && y > 0 ? 250 : 150;
		double a = opaque ? 1 : 0.2 * value.getR() / 50;

		return "rgba(" + r + "," + g + "," + b + "," + a + ")";
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		String[] colors = new String[AMOUNT_OF_POINTS];
		String[] bcolors = new String[AMOUNT_OF_POINTS];
		int[] bwidth = new int[AMOUNT_OF_POINTS];
		int[] hbwidth = new int[AMOUNT_OF_POINTS];

		for (Dataset dataset : chart.getData().getDatasets()) {
			BubbleDataset bDataset = (BubbleDataset) dataset;
			int i = 0;
			for (DataPoint dp : bDataset.getDataPoints()) {
				dp.setX(getData());
				dp.setY(getData());
				dp.setR(getData(0, 50));
				colors[i] = colorize(false, dp);
				bcolors[i] = colorize(true, dp);
				bwidth[i] = Math.min(Math.max(1, i + 1), 5);
				hbwidth[i] = (int) Math.round(8 * dp.getR() / 1000);
				i++;
			}
			bDataset.setBackgroundColor(colors);
			bDataset.setBorderColor(bcolors);
			bDataset.setBorderWidth(bwidth);
		}

		chart.update();
	}

	@UiHandler("add_dataset")
	protected void handleAddDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();
		BubbleDataset dataset = chart.newDataset();
		dataset.setLabel("dataset " + (datasets.size() + 1));

		String[] colors = new String[AMOUNT_OF_POINTS];
		String[] hcolors = new String[AMOUNT_OF_POINTS];
		String[] bcolors = new String[AMOUNT_OF_POINTS];
		int[] bwidth = new int[AMOUNT_OF_POINTS];
		int[] hbwidth = new int[AMOUNT_OF_POINTS];

		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			dp1[i] = new DataPoint();
			dp1[i].setX(getData());
			dp1[i].setY(getData());
			dp1[i].setR(getData(0, 50));
			colors[i] = colorize(false, dp1[i]);
			bcolors[i] = colorize(true, dp1[i]);
			bwidth[i] = Math.min(Math.max(1, i + 1), 5);
			hcolors[i] = "transparent";
			hbwidth[i] = (int) Math.round(8 * dp1[i].getR() / 1000);
		}
		dataset.setBackgroundColor(colors);
		dataset.setBorderColor(bcolors);
		dataset.setBorderWidth(bwidth);
		dataset.setHoverBackgroundColor(hcolors);
		dataset.setHoverBorderWidth(hbwidth);
		dataset.setDataPoints(dp1);

		datasets.add(dataset);

		chart.update();
	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		removeDataset(chart);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
