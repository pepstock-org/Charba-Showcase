package org.pepstock.charba.showcase.client.cases.annotation;

import java.util.List;

import org.pepstock.charba.client.commons.ArrayObjectContainerList;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.commons.NativeObjectContainer;

public class ChartJsAnnotationOptions extends NativeObjectContainer {
    public static final String ID = "annotation";

    private enum Property implements Key {
        DRAW_TIME("drawTime"),
        EVENTS("events"),
        DBL_CLICK_SPEED("dblClickSpeed"),
        ANNOTATIONS("annotations");

        private final String value;

        private Property(String value) {
            this.value = value;
        }

        @Override
        public String value() {
            return value;
        }
    }

    public void setDrawTime(DrawTime drawTime) {
        setValue(Property.DRAW_TIME, drawTime.toString());
    }

    public void setEvents(List<String> events) {
        String[] arr = events.toArray(new String[0]);
        setValueOrArray(Property.EVENTS, arr);
    }

    public void setDoubleClickSpeed(int dblClickSpeedMs) {
        setValue(Property.DBL_CLICK_SPEED, dblClickSpeedMs);
    }

    public void setAnnotation(ChartJsAnnotation annotation) {
        ArrayObjectContainerList<ChartJsAnnotation> arr = new ArrayObjectContainerList<>();
        arr.add(annotation);

        setAnnotations(arr);
    }

    public void setAnnotations(ArrayObjectContainerList<ChartJsAnnotation> annotations) {
        setArrayValue(Property.ANNOTATIONS, annotations);
    }

    public enum DrawTime {
        AFTER_DRAW("afterDraw"),
        AFTER_DATASETS_DRAW("afterDataSetsDraw"),
        BEFORE_DATASETS_DRAW("beforeDataSetsDraw");

        private final String name;

        DrawTime(String s) {
            name = s;
        }

        public String toString() {
            return this.name;
        }
    }
}
