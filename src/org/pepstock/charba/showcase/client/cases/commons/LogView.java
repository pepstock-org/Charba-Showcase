package org.pepstock.charba.showcase.client.cases.commons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LogView extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LogView> {
	}

	private static final int MAX = 8;

	private int counter = 1;

	@UiField
	VerticalPanel log;

	PreElement element = Document.get().createPreElement();

	public LogView() {
		initWidget(uiBinder.createAndBindUi(this));
		log.getElement().appendChild(element);
	}

	public void addLogEvent(String innerHtml) {
		DivElement newDiv = Document.get().createDivElement();
		newDiv.setInnerHTML(counter + ". " + innerHtml);
		element.insertBefore(newDiv, element.getFirstChild());
		if (element.getChildCount() > MAX) {
			element.removeChild(element.getLastChild());
		}
		counter++;
	}

	@UiHandler("titleLog")
	protected void handleTitleLog(ClickEvent event) {
		element.removeAllChildren();
	}
}
