package org.pepstock.charba.showcase.client.cases.jsinterop;

import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.showcase.client.Charba_Showcase;
import org.pepstock.charba.showcase.client.cases.commons.AbstractComposite;

public class BaseComposite extends AbstractComposite{

	protected void removeDataset(IsChart chart) {
		List<Dataset> datasets = chart.getData().getDatasets();
		if (datasets.size() > 1) {
			datasets.remove(datasets.size()-1);
			chart.update();
		}
	}

	protected void addData(IsChart chart) {
		addData(chart, true);
	}

	protected void addData(IsChart chart, boolean negative) {
		if (months < 12){
			chart.getData().getLabels().add(getLabel());
			months++;
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset ds : datasets){
				ds.getData().add(getRandomDigit(negative));
			}
			chart.update();
		}
	}

	protected void removeData(IsChart chart) {
		if (months > 1){
			months--;
			chart.getData().setLabels(getLabels());
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset dataset : datasets){
				dataset.getData().remove(dataset.getData().size()-1);
			}
			chart.update();
		}
	}

	protected String getUrl() {
		StringBuilder sb = new StringBuilder(Charba_Showcase.BASE_URL);
		return sb.append(this.getClass().getName().replace(".", "/")).append(".java").toString();
	}
	
}
