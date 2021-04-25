package org.pepstock.charba.showcase.client.cases.plugins;

import java.util.List;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.BarBorderWidth;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.impl.plugins.ColorScheme;
import org.pepstock.charba.client.impl.plugins.ColorSchemes;
import org.pepstock.charba.client.impl.plugins.ColorSchemesOptions;
import org.pepstock.charba.client.impl.plugins.enums.BrewerScheme;
import org.pepstock.charba.client.impl.plugins.enums.GoogleChartScheme;
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

public class ColorSchemeBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ColorSchemeBarCase> {
	}

	@UiField
	BarChartWidget chart;

	@UiField
	CheckBox data;

	@UiField
	ListBox category;

	@UiField
	ListBox name;

	@UiField
	CheckBox reverse;

	public ColorSchemeBarCase() {

		initWidget(uiBinder.createAndBindUi(this));

		category.addItem("Brewer", "brewer");
		category.addItem("MS Office", "office");
		category.addItem("Tableau", "tableau");
		category.addItem("GWT material", "gwtmaterial");
		category.addItem("Google Chart", "googlechart");

		int index = 0;
		for (BrewerScheme scheme : BrewerScheme.values()) {
			name.addItem(scheme.value(), scheme.name());
			if (BrewerScheme.PAIRED12.equals(scheme)) {
				name.setSelectedIndex(index);
			}
			index++;
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Coloring bar chart");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		BarBorderWidth border = new BarBorderWidth();
		border.setTop(2);
		border.setLeft(2);
		border.setRight(2);

		dataset1.setBorderWidth(border);

		dataset1.setBorderColor(color1);
		dataset1.setData(getFixedDigits(months));

		ColorSchemesOptions options = new ColorSchemesOptions();
		options.setSchemeScope(SchemeScope.DATASET);

		chart.getOptions().getPlugins().setOptions(ColorSchemes.ID, options);
		chart.getPlugins().add(ColorSchemes.get());

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();

	}

	@UiHandler("add_dataset")
	protected void handleAddDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();

		BarDataset dataset = chart.newDataset();
		dataset.setLabel("dataset " + (datasets.size() + 1));

		IsColor color = GoogleChartColor.values()[datasets.size()];
		dataset.setBackgroundColor(color.alpha(0.2));
		dataset.setBorderColor(color.toHex());

		BarBorderWidth border = new BarBorderWidth();
		border.setTop(2);
		border.setLeft(2);
		border.setRight(2);

		dataset.setBorderWidth(border);

		dataset.setData(getRandomDigits(months, false));

		datasets.add(dataset);

		chart.update();
	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		removeDataset(chart);
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		addData(chart, false);
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
	}

	@UiHandler("data")
	protected void handleScope(ClickEvent event) {
		ColorSchemesOptions options = chart.getOptions().getPlugins().getOptions(ColorSchemes.ID, ColorSchemes.FACTORY);
		if (data.getValue()) {
			options.setSchemeScope(SchemeScope.DATA);
		} else {
			options.setSchemeScope(SchemeScope.DATASET);
		}
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
		} else if ("googlechart".equalsIgnoreCase(selected)) {
			name.clear();
			for (ColorScheme scheme : GoogleChartScheme.values()) {
				name.addItem(scheme.value(), scheme.value());
			}
			name.setSelectedIndex(0);
		}
		handleName(event);
	}

	@UiHandler("name")
	protected void handleName(ChangeEvent event) {
		ColorSchemesOptions options = chart.getOptions().getPlugins().getOptions(ColorSchemes.ID, ColorSchemes.FACTORY);
		String selected = category.getSelectedValue();
		if ("brewer".equalsIgnoreCase(selected)) {
			options.setScheme(Key.getKeyByValue(BrewerScheme.values(), name.getSelectedValue()));
			options.setBackgroundColorAlpha(0.5D);
		} else if ("office".equalsIgnoreCase(selected)) {
			options.setScheme(Key.getKeyByValue(OfficeScheme.values(), name.getSelectedValue()));
			options.setBackgroundColorAlpha(0.5D);
		} else if ("tableau".equalsIgnoreCase(selected)) {
			options.setScheme(Key.getKeyByValue(TableauScheme.values(), name.getSelectedValue()));
			options.setBackgroundColorAlpha(0.5D);
		} else if ("gwtmaterial".equalsIgnoreCase(selected)) {
			options.setScheme(Key.getKeyByValue(GwtMaterialScheme.values(), name.getSelectedValue()));
			options.setBackgroundColorAlpha(0.5D);
		} else if ("googlechart".equalsIgnoreCase(selected)) {
			options.setScheme(Key.getKeyByValue(GoogleChartScheme.values(), name.getSelectedValue()));
			options.setBackgroundColorAlpha(0.5D);
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
