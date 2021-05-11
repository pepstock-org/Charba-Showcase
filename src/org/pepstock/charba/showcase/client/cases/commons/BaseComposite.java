package org.pepstock.charba.showcase.client.cases.commons;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.commons.Constants;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.utils.Console;
import org.pepstock.charba.showcase.client.Charba_Showcase;

public class BaseComposite extends AbstractComposite {
	
	private static final Set<String> COLORS = new HashSet<>();

	protected void removeDataset(IsChart chart) {
		List<Dataset> datasets = chart.getData().getDatasets();
		if (datasets.size() > 1) {
			datasets.remove(datasets.size() - 1);
			chart.update();
		}
	}

	protected void addData(IsChart chart) {
		addData(chart, true);
	}

	protected void addData(IsChart chart, boolean negative) {
		if (months < 12) {
			chart.getData().getLabels().add(getLabel());
			months++;
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset ds : datasets) {
				ds.getData().add(getRandomDigit(negative));
			}
			chart.update();
		}
	}

	protected void removeData(IsChart chart) {
		if (months > 1) {
			months--;
			chart.getData().setLabels(getLabels());
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset dataset : datasets) {
				dataset.getData().remove(dataset.getData().size() - 1);
			}
			chart.update();
		}
	}
	
	public final Set<String> getLabelColors(int count){
		if (COLORS.isEmpty() || COLORS.size() < count) {
			for (int i=0; i < count; i++) {
				COLORS.add(HtmlColor.values()[i].name().replace(Constants.UNDERSCORE, Constants.BLANK));
			}
		}
		return COLORS;
	}

	protected final String getUrl() {
		StringBuilder sb = new StringBuilder(Charba_Showcase.BASE_URL);
		return sb.append(this.getClass().getName().replace(".", "/")).append(".java").toString();
	}
	
	protected final void log(Object... objects) {
		Console.log(objects);
	}

}
