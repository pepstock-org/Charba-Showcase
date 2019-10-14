package org.pepstock.charba.showcase.client.samples.annotation;

import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.commons.NativeObject;
import org.pepstock.charba.client.commons.NativeObjectContainer;

public class ChartJsAnnotationLabel extends NativeObjectContainer {

    public NativeObject toNativeObject() {
        return getNativeObject();
    }

    private enum Property implements Key {
        BACKGROUND_COLOR("backgroundColor"),
        FONT_FAMILY("fontFamily"),
        FONT_SIZE("fontSize"),
        FONT_STYLE("fontStyle"),
        FONT_COLOR("fontColor"),
        X_PADDING("xPadding"),
        Y_PADDING("yPadding"),
        CORNER_RADIUS("cornerRadius"),
        POSITION("position"),
        X_ADJUST("xAdjust"),
        Y_ADJUST("yAdjust"),
        ENABLED("enabled"),
        CONTENT("content");

        private final String value;

        private Property(String value) {
            this.value = value;
        }

        @Override
        public String value() {
            return value;
        }
    }

    public void setBackgroundColor(String backgroundColor) {
        setValue(Property.BACKGROUND_COLOR, backgroundColor);
    }

    public void setFontFamily(String fontFamily) {
        setValue(Property.FONT_FAMILY, fontFamily);
    }

    public void setFontSize(int fontSize) {
        setValue(Property.FONT_SIZE, fontSize);
    }

    public void setFontStyle(String fontStyle) {
        setValue(Property.FONT_STYLE, fontStyle);
    }

    public void setFontColor(String fontColor) {
        setValue(Property.FONT_COLOR, fontColor);
    }

    public void setXPadding(int xPadding) {
        setValue(Property.X_PADDING, xPadding);
    }

    public void setYPadding(int yPadding) {
        setValue(Property.Y_PADDING, yPadding);
    }

    public void setCornerRadius(int cornerRadius) {
        setValue(Property.CORNER_RADIUS, cornerRadius);
    }

    public void setPosition(String position) {
        setValue(Property.POSITION, position);
    }

    public void setXAdjust(int xAdjust) {
        setValue(Property.X_ADJUST, xAdjust);
    }

    public void setYAdjust(int yAdjust) {
        setValue(Property.Y_ADJUST, yAdjust);
    }

    public void setEnabled(boolean enabled) {
        setValue(Property.ENABLED, enabled);
    }

    public void setContent(String content) {
        setValue(Property.CONTENT, content);
    }
}
