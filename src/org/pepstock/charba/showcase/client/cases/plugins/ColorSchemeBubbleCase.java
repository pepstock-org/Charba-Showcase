package org.pepstock.charba.showcase.client.cases.plugins;

import java.util.List;
import java.util.Random;

import org.pepstock.charba.client.BubbleChart;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.BubbleDataset;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.impl.plugins.ColorScheme;
import org.pepstock.charba.client.impl.plugins.ColorSchemes;
import org.pepstock.charba.client.impl.plugins.ColorSchemesOptions;
import org.pepstock.charba.client.impl.plugins.enums.BrewerScheme;
import org.pepstock.charba.client.impl.plugins.enums.GwtMaterialScheme;
import org.pepstock.charba.client.impl.plugins.enums.OfficeScheme;
import org.pepstock.charba.client.impl.plugins.enums.SchemeScope;
import org.pepstock.charba.client.impl.plugins.enums.TableauScheme;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ColorSchemeBubbleCase extends BaseComposite{
	
	private static final int AMOUNT_OF_POINTS = 16;
	private static final int MIN_XY = -150;
	private static final int MAX_XY = 100;
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ColorSchemeBubbleCase> {
	}

	@UiField
	BubbleChart chart;
	
	@UiField
	CheckBox data;

	@UiField
	ListBox category;

	@UiField
	ListBox name;
	
	@UiField
	CheckBox reverse;
	
	public ColorSchemeBubbleCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		category.addItem("Brewer", "brewer");
		category.addItem("MS Office", "office");
		category.addItem("Tableau", "tableau");
		category.addItem("GWT material", "gwtmaterial");
		
		int index = 0;
		for (ColorScheme scheme : BrewerScheme.values()) {
			name.addItem(scheme.value(), scheme.value());
			if (BrewerScheme.PAIRED12.equals(scheme)) {
				name.setSelectedIndex(index);
			}
			index++;
		}
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(true);
		chart.getOptions().getLegend().getLabels().setUsePointStyle(true);
		chart.getOptions().getLegend().getLabels().setFontSize(18);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Coloring buble chart");
		
		BubbleDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		String[] colors = new String[AMOUNT_OF_POINTS];
		String[] hcolors = new String[AMOUNT_OF_POINTS];
		String[] bcolors = new String[AMOUNT_OF_POINTS];
		int[] bwidth = new int[AMOUNT_OF_POINTS];
		int[] hbwidth = new int[AMOUNT_OF_POINTS];
		
		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i=0; i<AMOUNT_OF_POINTS; i++){
			dp1[i] = new DataPoint();
			dp1[i].setX(getData());
			dp1[i].setY(getData());
			dp1[i].setR(getData(0, 50));
			colors[i] = colorize(false, dp1[i]);
			bcolors[i] = colorize(true, dp1[i]);
			bwidth[i] = Math.min(Math.max(1, i + 1), 5);
			hcolors[i] = "transparent"; 
			hbwidth[i] = (int)Math.round(8 * dp1[i].getR() / 1000);
		}
		dataset1.setBackgroundColor(colors);
		dataset1.setBorderColor(bcolors);
		dataset1.setBorderWidth(bwidth);
		dataset1.setHoverBackgroundColor(hcolors);
		dataset1.setHoverBorderWidth(hbwidth);
		dataset1.setDataPoints(dp1);

		chart.getPlugins().add(ColorSchemes.get());
		chart.getData().setDatasets(dataset1);
		

	}

	private int getData(){
		return getData(MIN_XY, MAX_XY);
	}

	private int getData(int min, int max){
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

		for (Dataset dataset : chart.getData().getDatasets()){
			BubbleDataset bDataset = (BubbleDataset)dataset;
			int i = 0;
			for (DataPoint dp : bDataset.getDataPoints()){
				dp.setX(getData());
				dp.setY(getData());
				dp.setR(getData(0, 50));
				colors[i] = colorize(false, dp);
				bcolors[i] = colorize(true, dp);
				bwidth[i] = Math.min(Math.max(1, i + 1), 5);
				hbwidth[i] = (int)Math.round(8 * dp.getR() / 1000);
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
		dataset.setLabel("dataset "+(datasets.size()+1));
		
		String[] colors = new String[AMOUNT_OF_POINTS];
		String[] hcolors = new String[AMOUNT_OF_POINTS];
		String[] bcolors = new String[AMOUNT_OF_POINTS];
		int[] bwidth = new int[AMOUNT_OF_POINTS];
		int[] hbwidth = new int[AMOUNT_OF_POINTS];
		
		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i=0; i<AMOUNT_OF_POINTS; i++){
			dp1[i] = new DataPoint();
			dp1[i].setX(getData());
			dp1[i].setY(getData());
			dp1[i].setR(getData(0, 50));
			colors[i] = colorize(false, dp1[i]);
			bcolors[i] = colorize(true, dp1[i]);
			bwidth[i] = Math.min(Math.max(1, i + 1), 5);
			hcolors[i] = "transparent"; 
			hbwidth[i] = (int)Math.round(8 * dp1[i].getR() / 1000);
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
	
	@UiHandler("data")
	protected void handleScope(ClickEvent event) {
		ColorSchemesOptions options = chart.getOptions().getPlugins().getOptions(ColorSchemes.ID, ColorSchemes.FACTORY);
		if (data.getValue()) {
			options.setSchemeScope(SchemeScope.DATA);
		} else {
			options.setSchemeScope(SchemeScope.DATASET);
		}
		chart.getOptions().getPlugins().setOptions(ColorSchemes.ID, options);
		chart.update();
	}
	
	@UiHandler("reverse")
	protected void handleReverse(ClickEvent event) {
		ColorSchemesOptions options = chart.getOptions().getPlugins().getOptions(ColorSchemes.ID, ColorSchemes.FACTORY);
		options.setReverse(reverse.getValue());
		chart.getOptions().getPlugins().setOptions(ColorSchemes.ID, options);
		chart.update();
	}
	
	@UiHandler("category")
	protected void handleCategory(ChangeEvent event) {
		String selected = category.getSelectedValue();
		if ("brewer".equalsIgnoreCase(selected)) {
			name.clear();
			int index = 0;
			for (ColorScheme scheme : BrewerScheme.values()) {
				name.addItem(scheme.value(), scheme.value());
				if (BrewerScheme.PAIRED12.equals(scheme)) {
					name.setSelectedIndex(index);
				}
				index++;
			}
		} else if ("office".equalsIgnoreCase(selected)) {
			name.clear();
			for (ColorScheme scheme : OfficeScheme.values()) {
				name.addItem(scheme.value(), scheme.value());
			}
			name.setSelectedIndex(0);	
		} else if ("tableau".equalsIgnoreCase(selected)) {
			name.clear();
			for (ColorScheme scheme : TableauScheme.values()) {
				name.addItem(scheme.value(), scheme.value());
			}
			name.setSelectedIndex(0);
		} else if ("gwtmaterial".equalsIgnoreCase(selected)) {
			name.clear();
			for (ColorScheme scheme : GwtMaterialScheme.values()) {
				name.addItem(scheme.value(), scheme.value());
			}
			name.setSelectedIndex(0);	
		}
		handleName(event);
		chart.update();
	}

	@UiHandler("name")
	protected void handleName(ChangeEvent event) {
		ColorSchemesOptions options = chart.getOptions().getPlugins().getOptions(ColorSchemes.ID, ColorSchemes.FACTORY);
		String selected = category.getSelectedValue();
		if ("brewer".equalsIgnoreCase(selected)) {
			options.setScheme(Key.getKeyByValue(BrewerScheme.class, name.getSelectedValue()));
			options.setBackgroundColorAlpha(0.5D);
		} else if ("office".equalsIgnoreCase(selected)) {
			options.setScheme(Key.getKeyByValue(OfficeScheme.class, name.getSelectedValue()));
			options.setBackgroundColorAlpha(0.5D);
		} else if ("tableau".equalsIgnoreCase(selected)) {
			options.setScheme(Key.getKeyByValue(TableauScheme.class, name.getSelectedValue()));
			options.setBackgroundColorAlpha(0.5D);
		} else if ("gwtmaterial".equalsIgnoreCase(selected)) {
			options.setScheme(Key.getKeyByValue(GwtMaterialScheme.class, name.getSelectedValue()));
			options.setBackgroundColorAlpha(0.95D);
		} 
		chart.getOptions().getPlugins().setOptions(ColorSchemes.ID, options);
		chart.update();
	}
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}


}
