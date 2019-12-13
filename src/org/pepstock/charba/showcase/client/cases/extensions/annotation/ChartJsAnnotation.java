package org.pepstock.charba.showcase.client.cases.extensions.annotation;

import java.util.List;

import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.commons.NativeObject;
import org.pepstock.charba.client.commons.NativeObjectContainer;

public class ChartJsAnnotation extends NativeObjectContainer {

	private enum Property implements Key {
        DRAW_TIME("drawTime"),
        ID("id"),
        TYPE("type"),
        MODE("mode"),
        SCALE_ID("scaleID"),
        VALUE("value"),
        END_VALUE("endValue"),
        BORDER_COLOR("borderColor"),
        BORDER_WIDTH("borderWidth"),
        BORDER_DASH("borderDash"),
        BORDER_DASH_OFFSET("borderDashOffset"),
        LABEL("label"),
        X_SCALE_ID("xScaleID"),
        Y_SCALE_ID("yScaleID"),
        X_MIN("xMin"),
        X_MAX("xMax"),
        Y_MAX("yMax"),
        Y_MIN("yMin"),
        BACKGROUND_COLOR("backgroundColor");

        private final String value;

        private Property(String value) {
            this.value = value;
        }

        @Override
        public String value() {
            return value;
        }
    }

    public NativeObject toNativeObject() {
        return getNativeObject();
    }

    public void setType(Type type) {
        setValue(Property.TYPE, type.toString());
    }

    public void setId(String id) {
        setValue(Property.ID, id);
    }

    public void setDrawTime(ChartJsAnnotationOptions.DrawTime drawTime) {
        setValue(Property.DRAW_TIME, drawTime.toString());
    }

    public void setMode(Mode mode) {
        setValue(Property.MODE, mode.toString());
    }

    public void setScaleId(String id) {
        setValue(Property.SCALE_ID, id);
    }

    public void setValue(long value) {
        setValue(Property.VALUE, value);
    }

    public void setEndValue(int endValue) {
        setValue(Property.END_VALUE, endValue);
    }

    public void setBorderColor(String color) {
        setValue(Property.BORDER_COLOR, color);
    }

    public void setBorderWidth(int width) {
        setValue(Property.BORDER_WIDTH, width);
    }

    public void setBorderDash(List<Integer> borderDash) {
        int[] arr = new int[borderDash.size()];
        for(int i = 0; i < borderDash.size(); i++)
        {
            arr[i] = borderDash.get(i);
        }
        setValueOrArray(Property.BORDER_DASH, arr);
    }

    public void setBorderDashOffset(int borderDashOffset) {
        setValue(Property.BORDER_DASH_OFFSET, borderDashOffset);
    }

    public void setLabel(ChartJsAnnotationLabel label) {
        setValue(Property.LABEL, label.toNativeObject());
    }

    public void setXScaleID(String xScaleID) {
        setValue(Property.X_SCALE_ID, xScaleID);
    }

    public void setYScaleID(String yScaleID) {
        setValue(Property.Y_SCALE_ID, yScaleID);
    }

    public void setXMin(int xMin) {
        setValue(Property.X_MIN, xMin);
    }

    public void setXMax(int xMax) {
        setValue(Property.X_MAX, xMax);
    }

    public void setYMax(int yMax) {
        setValue(Property.Y_MAX, yMax);
    }

    public void setYMin(int yMin) {
        setValue(Property.Y_MIN, yMin);
    }

    public void setBackgroundColor(String backgroundColor) {
        setValue(Property.BACKGROUND_COLOR, backgroundColor);
    }

    public enum Type {
        LINE("line"),
        BOX("box");

        private final String name;

        Type(String s) {
            name = s;
        }

        public String toString() {
            return this.name;
        }
    }

    public enum Mode {
        HORIZONTAL("horizontal"),
        VERTICAL("vertical");

        private final String name;

        Mode(String s) {
            name = s;
        }

        public String toString() {
            return this.name;
        }
    }


}
