package org.pepstock.charba.showcase.client.samples;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;

import com.google.gwt.user.client.ui.Composite;

public class BaseComposite extends Composite{
	
	protected static final String[] MONTHS = new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	protected int months = 7;
	
	protected String[] getLabels(){
		String[] values = new String[months];
		for (int i=0; i<months; i++){
			values[i] = MONTHS[i];
		}
		return values;
	}

	protected String getLabel(){
		return MONTHS[months];
	}

	protected double[] getRandomDigits(int length){
		return getRandomDigits(length, true);
	}
	
	protected double[] getRandomDigits(int length, boolean negative){
		double[] values = new double[length];
		for(int i=0;i<length;i++){
			if (negative){
				boolean neg = Math.random() < 0.2 ? true : false;
				values[i] = neg ? Math.round(Math.random()*-100) : Math.round(Math.random()*100);
			} else {
				values[i] = Math.round(Math.random()*100);
			}
		}
		return values;
	}

	protected double getRandomDigit(){
		return getRandomDigit(true);
	}

	protected double getRandomDigit(boolean negative){
		if (negative){
			boolean neg = Math.random() < 0.2 ? true : false;
			return neg ? Math.round(Math.random()*-100) : Math.round(Math.random()*100);
		} else {
			return Math.round(Math.random()*100);
		}
	}

	protected double[] getRandomDigitsLog(int length){
		double[] values = new double[length];
		for(int i=0;i<length;i++){
			values[i] = (Math.ceil(Math.random()*10)) * (Math.pow(10, Math.ceil(Math.random() * 5)));
		}
		return values;
	}

	protected IsColor[] getSequenceColors(int length, double alpha){
		IsColor[] values = new IsColor[length];
		for(int i=0;i<length;i++){
			values[i] = Colors.ALL[i].alpha(alpha);
		}
		return values;
		
	}

	protected void removeDataset(AbstractChart<?, ?> chart) {
		List<Dataset> datasets = chart.getData().getDatasets();
		datasets.remove(datasets.size()-1);
		chart.update();
	}

	protected void addData(AbstractChart<?, ?> chart) {
		addData(chart, true);
	}

	protected void addData(AbstractChart<?, ?> chart, boolean negative) {
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

	protected void removeData(AbstractChart<?, ?> chart) {
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

	
}
