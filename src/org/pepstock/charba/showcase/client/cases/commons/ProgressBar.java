package org.pepstock.charba.showcase.client.cases.commons;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Widget;

public class ProgressBar extends Widget {
    private static final String PERCENT_PATTERN = "#,##0%";
    
    private static final NumberFormat percentFormat = NumberFormat.getFormat(PERCENT_PATTERN);

    private static final String PROGRESS_TAG = "progress";

    private static final String PROGRESS_WIDTH_ATTRIBUTE = "width";
    
    private static final String PROGRESS_MAX_ATTRIBUTE = "max";
    
    private static final String PROGRESS_VALUE_ATTRIBUTE = "value";
    
    private static final double DEFAULT_MAX = 100D;
    
    private final Element progress;
    private final SpanElement percentageLabel;
    
    private double percentage;
    
    private double max;
    
    private double value;

    public ProgressBar() {
        progress = Document.get().createElement(PROGRESS_TAG);
        percentageLabel = Document.get().createSpanElement();
        percentage = value / max;
        percentageLabel.setInnerHTML(percentFormat.format(percentage));
        progress.appendChild(percentageLabel);
        setElement(progress);
        
        setValue(0);
        setMax(DEFAULT_MAX);
    }
    
	@Override
	public void setWidth(String width) {
		progress.getStyle().setProperty(PROGRESS_WIDTH_ATTRIBUTE, width);
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
		progress.setAttribute(PROGRESS_MAX_ATTRIBUTE, Double.toString(max));
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
		progress.setAttribute(PROGRESS_VALUE_ATTRIBUTE, Double.toString(value));
	}

	public void setProgress(double value) {
		setValue(value);
        percentage = value / max;
        percentageLabel.setInnerHTML(percentFormat.format(percentage));
    }

}