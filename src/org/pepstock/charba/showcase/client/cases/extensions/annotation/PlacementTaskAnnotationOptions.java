package org.pepstock.charba.showcase.client.cases.extensions.annotation;

import java.util.Arrays;

public class PlacementTaskAnnotationOptions extends ChartJsAnnotationOptions {

    public PlacementTaskAnnotationOptions(Long initialValue) {
        ChartJsAnnotation annotation = new ChartJsAnnotation();
        annotation.setType(ChartJsAnnotation.Type.LINE);
        annotation.setMode(ChartJsAnnotation.Mode.HORIZONTAL);
        annotation.setValue(40L);
        annotation.setBorderWidth(5);
        annotation.setBorderColor("black");
        annotation.setBorderDash(Arrays.asList(5, 5));
        
        annotation.setScaleId("y-axis-0");
        ChartJsAnnotationLabel label = new ChartJsAnnotationLabel();
        label.setContent("Label");
        label.setEnabled(true);
        label.setBackgroundColor("rgba(255,255,255,0.5)");
        label.setFontColor("rgba(105,105,105)");
        label.setPosition("right");
        label.setYAdjust(-10);
        annotation.setLabel(label);
        this.setAnnotation(annotation);
    }
}