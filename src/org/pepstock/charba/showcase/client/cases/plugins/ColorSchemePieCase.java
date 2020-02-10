package org.pepstock.charba.showcase.client.cases.plugins;

import java.util.List;

import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PieDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.PieChartWidget;
import org.pepstock.charba.client.impl.plugins.ColorScheme;
import org.pepstock.charba.client.impl.plugins.ColorSchemes;
import org.pepstock.charba.client.impl.plugins.ColorSchemesOptions;
import org.pepstock.charba.client.impl.plugins.enums.BrewerScheme;
import org.pepstock.charba.client.impl.plugins.enums.GoogleChartScheme;
import org.pepstock.charba.client.impl.plugins.enums.GwtMaterialScheme;
import org.pepstock.charba.client.impl.plugins.enums.OfficeScheme;
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

public class ColorSchemePieCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ColorSchemePieCase> {
	}

	@UiField
	PieChartWidget chart;

	@UiField
	ListBox category;

	@UiField
	ListBox name;

	@UiField
	CheckBox reverse;

	public ColorSchemePieCase() {
		initWidget(uiBinder.createAndBindUi(this));

		category.addItem("Brewer", "brewer");
		category.addItem("MS Office", "office");
		category.addItem("Tableau", "tableau");
		category.addItem("GWT material", "gwtmaterial");
		category.addItem("Google Chart", "googlechart");

		int index = 0;
		for (ColorScheme scheme : BrewerScheme.values()) {
			name.addItem(scheme.value(), scheme.value());
			if (BrewerScheme.PAIRED12.equals(scheme)) {
				name.setSelectedIndex(index);
			}
			index++;
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Coloring pie chart");

		PieDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setData(getRandomDigits(months, false));

		chart.getPlugins().add(ColorSchemes.get());

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		chart.getDatasetMeta(0);
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("add_dataset")
	protected void handleAddDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();
		PieDataset dataset = chart.newDataset();
		dataset.setLabel("dataset " + (datasets.size() + 1));
		dataset.setBackgroundColor(getSequenceColors(months, 1));
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
		if (months < 12) {
			chart.getData().getLabels().add(getLabel());
			months++;
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset ds : datasets) {
				PieDataset pds = (PieDataset) ds;
				pds.getData().add(getRandomDigit(false));
			}
			chart.update();
		}
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
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
		} else if ("googlechart".equalsIgnoreCase(selected)) {
			options.setScheme(Key.getKeyByValue(GoogleChartScheme.class, name.getSelectedValue()));
			options.setBackgroundColorAlpha(0.5D);
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

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
