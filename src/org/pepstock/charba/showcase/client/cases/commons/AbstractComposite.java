package org.pepstock.charba.showcase.client.cases.commons;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.Gradient;
import org.pepstock.charba.client.colors.GradientScope;
import org.pepstock.charba.client.colors.GradientType;
import org.pepstock.charba.client.colors.GwtMaterialColor;
import org.pepstock.charba.client.colors.IsColor;

import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractComposite extends Composite {

	protected static final String[] MONTHS = new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

	protected static final double[] FIXED = new double[] { 20, 10, 40, 35, 50, 70, 80, 30, 55, 15, 100, 90 };

	protected int months = 7;

	protected String[] getLabels() {
		return getLabels(months);
	}

	protected String[] getLabels(int size) {
		String[] values = new String[size];
		for (int i = 0; i < size; i++) {
			values[i] = MONTHS[i];
		}
		return values;
	}

	protected String getLabel() {
		return MONTHS[months];
	}

	protected double[] getRandomDigits(int length) {
		return getRandomDigits(length, true);
	}

	protected double[] getRandomDigits(int length, boolean negative) {
		double[] values = new double[length];
		for (int i = 0; i < length; i++) {
			if (negative) {
				boolean neg = Math.random() < 0.2 ? true : false;
				values[i] = neg ? Math.round(Math.random() * -100) : Math.round(Math.random() * 100);
			} else {
				values[i] = Math.round(Math.random() * 100);
			}
		}
		return values;
	}

	protected double getRandomDigit() {
		return getRandomDigit(true);
	}

	protected double getRandomDigit(boolean negative) {
		if (negative) {
			boolean neg = Math.random() < 0.2 ? true : false;
			return neg ? Math.round(Math.random() * -100) : Math.round(Math.random() * 100);
		} else {
			return Math.round(Math.random() * 100);
		}
	}

	protected double[] getRandomDigits(int length, double min, double max) {
		double[] values = new double[length];
		for (int i = 0; i < length; i++) {
			values[i] = (double) ((int) (Math.random() * (max - min))) + min;
		}
		return values;
	}

	protected double[] getRandomDigitsLog(int length) {
		double[] values = new double[length];
		for (int i = 0; i < length; i++) {
			values[i] = (Math.ceil(Math.random() * 10)) * (Math.pow(10, Math.ceil(Math.random() * 5)));
		}
		return values;
	}

	protected IsColor[] getSequenceColors(int length, double alpha) {
		IsColor[] values = new IsColor[length];
		for (int i = 0; i < length; i++) {
			values[i] = GoogleChartColor.values()[i].alpha(alpha);
		}
		return values;
	}

	protected Gradient[] getRadialGradients(int length) {
		IsColor[] colors = GwtMaterialColor.values();
		Gradient[] gradients = new Gradient[length];
		for (int i = 0; i < length; i++) {
			Gradient gradient = new Gradient(GradientType.RADIAL, GradientScope.CHART);
			int index = i * 14;
			gradient.addColorsStartStop(colors[index + 7], colors[index + 2]);
			gradients[i] = gradient;
		}
		return gradients;
	}

	protected double[] getFixedDigits(int length) {
		double[] values = new double[length];
		for (int i = 0; i < length; i++) {
			values[i] = FIXED[i];
		}
		return values;
	}

}
