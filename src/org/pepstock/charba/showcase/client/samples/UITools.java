package org.pepstock.charba.showcase.client.samples;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * Common helper methods for UI widgets styles 
 * 
 * @author Andrea Stock Stocchero
 *
 */
public final class UITools {

	public static final String PX = "px";
	public static final String HUNDRED_PERCENT = "100%";
	public static final String PLACEHOLDER = "placeholder";
	public static final String MAX_WIDTH = "maxWidth";

	/**
	 * A date and time formatter, with this pattern: yyyy-MM-dd HH:mm:ss
	 */
	public static final DateTimeFormat DATE_TIME_FULL = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**
	 * A number format for currency (based on locale)
	 */
	public static final NumberFormat CURRENCY = NumberFormat.getCurrencyFormat();
	
	public static final NumberFormat currency() {
		return CURRENCY;
	}
	
	/**
	 * To avoid any instantiation
	 */
	private UITools() {
	}
	
	/**
	 * Set styles to FlexTable
	 * @param t
	 * @param oddRowStyle
	 * @param evenRowStyle
	 * @param keyStyle
	 */
	public static void setFlexTableStyles(FlexTable t, String oddRowStyle, String evenRowStyle, String keyStyle) {
		setHTMLTableRowStyles(t, oddRowStyle, evenRowStyle);
		setColumnKeyValueStyle(t, keyStyle);
	}
	
	/**
	 * Make a Flextable easy readable setting some styles
	 * @param t FlexTable
	 * @param oddRowStyle style of odd rows
	 * @param evenRowStyle style of even rows
	 * @param skipHeaderRow <code>true</code> avoid the formatter handle the first row
	 */
	public static void setHTMLTableRowStyles(HTMLTable t, String oddRowStyle, String evenRowStyle) {
		RowFormatter rf = t.getRowFormatter();
		for (int i=0; i<t.getRowCount(); i++) {
			if (i % 2 == 0) {
				rf.setStyleName(i, oddRowStyle);
			} else {
				rf.setStyleName(i, evenRowStyle);
			}
		}
	}

	/**
	 * Format FlexTable columns in order to make columns easy-readable  
	 * @param t the FlexTable
	 * @param style Odd column style
	 */
	public static void setColumnKeyValueStyle(FlexTable t, String style) {
		setColumnKeyValueStyle(t, style, false);
	}
	
	/**
	 * Format FlexTable columns in order to make columns easy-readable  
	 * @param t the FlexTable
	 * @param style Odd column style
	 * @param skipFirstRow if <code>true</code> leave the first row as is (for table header)
	 */
	public static void setColumnKeyValueStyle(FlexTable t, String style, boolean skipFirstRow) {
		FlexCellFormatter cf = t.getFlexCellFormatter();
		int i = skipFirstRow ? 1 : 0;
		for (i=0; i<t.getRowCount(); i++) {
			for (int j=0; j<t.getCellCount(i); j++) {
				if (j % 2 == 0) {
					cf.addStyleName(i, j, style);
				}
			}
		}
	}
	
	/**
	 * Format {@link FlexTable} header in order to make header easy-readable, using {@link FlexCellFormatter}
	 * @param t the FlexTable
	 * @param style Odd column style
	 */
	public static void setHeaderStyle(FlexTable t, String style) {
		FlexCellFormatter cf = t.getFlexCellFormatter();
		for (int i=0; i<t.getCellCount(0); i++) {
			cf.addStyleName(0, i, style);
		}
	}
	
	/**
	 * Format {@link HTMLTable} header in order to make header easy-readable, using {@link RowFormatter}
	 * @param t
	 * @param style
	 */
	public static void setHeaderStyle(HTMLTable t, String style) {
		RowFormatter rf = t.getRowFormatter();
		rf.setStyleName(0, style);
	}
	
	/**
	 * Simple tool to used to know if a Widget is visible, and if it is in foreground 
	 * @param object
	 * @return <code>true</code> if the parameter is visible and in foreground
	 */
	public static boolean isInForegroundVisible(UIObject object) {
		return object.isVisible() && object.getOffsetWidth() > 0 && object.getOffsetHeight() > 0;
	}
	
	/**
	 * Set a hint text on a TextBox 
	 * @param textBox
	 * @param hintText
	 */
	public static void setPlaceHolder(TextBox textBox, String hintText) {
		textBox.getElement().setPropertyString(PLACEHOLDER, hintText);
	}
	
	public static void setMaxWidth(Element target, int maxWidth) {
		target.getStyle().setPropertyPx(MAX_WIDTH, maxWidth);
	}
	
	public static void setSize(UIObject target, int w, int h) {
		target.setSize(w + PX, h + PX);
	}
	
	public static void propagateSize(ComplexPanel panel, int w, int h) {
		for (int i=0; i<panel.getWidgetCount(); i++) {
			Widget child = panel.getWidget(i);
			if (child instanceof RequiresResize) {
				((RequiresResize)child).onResize();
			}
		}
	}
	
	public static void propagateSize(SimplePanel panel, int w, int h) {
		Widget child = panel.getWidget();
		if (child instanceof RequiresResize) {
			((RequiresResize)child).onResize();
		}
	}
	
	/**
	 * Returns <code>true</code> if native event is on passed widget, otherwise <code>false</code>.
	 * @param event fired event
	 * @param widget widget to check if event is on it
	 * @return <code>true</code> if native event is on passed widget, otherwise <code>false</code>
	 */
	public static boolean isEventInsideWidget(NativeEvent event, Widget widget){
		int x = event.getClientX();
		int y = event.getClientY();

		int top = widget.getAbsoluteTop();
		int bottom = top + widget.getOffsetHeight();
		int left = widget.getAbsoluteLeft();
		int right = left + widget.getOffsetWidth();

		if (x < left || x > right ){
			return false;
		} else if (y < top || y > bottom ){
			return false;
		}
		return true;
	}
}