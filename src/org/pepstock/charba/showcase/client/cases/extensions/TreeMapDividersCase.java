package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Arrays;
import java.util.List;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.DatasetContext;
import org.pepstock.charba.client.colors.GwtMaterialColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.commons.ArrayListHelper;
import org.pepstock.charba.client.commons.ArrayObject;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.commons.NativeObject;
import org.pepstock.charba.client.commons.NativeObjectContainer;
import org.pepstock.charba.client.commons.NativeObjectContainerFactory;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.ContextType;
import org.pepstock.charba.client.gwt.widgets.TreeMapChartWidget;
import org.pepstock.charba.client.impl.plugins.enums.GwtMaterialScheme;
import org.pepstock.charba.client.treemap.TreeMapDataPoint;
import org.pepstock.charba.client.treemap.TreeMapDataset;
import org.pepstock.charba.client.treemap.callbacks.FormatterCallback;
import org.pepstock.charba.client.treemap.enums.Align;
import org.pepstock.charba.client.utils.JSON;
import org.pepstock.charba.client.utils.Utilities;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TreeMapDividersCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TreeMapDividersCase> {
	}

	private static final String DATA = "[{\"category\": \"main\", \"value\": 1},{\"category\": \"main\", \"value\": 2},{\"category\": \"other\", \"value\": 4}]";
	
	private static final TreeMapObjectFactory FACTORY = new TreeMapObjectFactory();

	@UiField
	TreeMapChartWidget chart;

	private final List<TreeMapObject> tree;

	public TreeMapDividersCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		ArrayObject array = JSON.parseForArray(DATA);
		tree = ArrayListHelper.unmodifiableList(array, FACTORY);

		chart.getOptions().setMaintainAspectRatio(false);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Dividers on a tree map chart");

		TreeMapDataset dataset1 = chart.newDataset();
		
		dataset1.setTreeObjetcs(tree);
		
		dataset1.setKey(Property.VALUE);
		dataset1.setGroups(Property.CATEGORY);
		dataset1.setSpacing(1);
		dataset1.setBorderWidth(2);
		dataset1.setBorderColor(GwtMaterialColor.BLUE_DARKEN_2);
		dataset1.setBackgroundColor(new ColorCallback<DatasetContext>() {
			
			private List<IsColor> colors = GwtMaterialScheme.BLUE.getColors();

			@Override
			public Object invoke(DatasetContext context) {
				if (context.getDataIndex() < 0) {
					return null;
				}
				int index = (colors.size() / 2) - (context.getDataIndex() * 2) - 1;
				return colors.get(index);
			}
			
		});
		dataset1.getDividers().setLineWidth(2);
		dataset1.getDividers().setLineDash(2,3);
		dataset1.getDividers().setDisplay(true);
		dataset1.getLabels().setDisplay(true);
		dataset1.getLabels().setAlign(Align.LEFT);
		dataset1.getLabels().setFormatter(new FormatterCallback() {
			
			@Override
			public List<String> invoke(DatasetContext context) {
				if (ContextType.DATA.equals(context.getType())) {
					TreeMapDataPoint point = dataset1.getDataPoints().get(context.getDataIndex());
					return Arrays.asList("Value:", Utilities.applyPrecision(point.getValue(), 2));
				}
				return null;
			}
		});
		
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			TreeMapDataset tds = (TreeMapDataset)dataset;
			tds.getTreeObjects(FACTORY).forEach((obj) -> obj.setValue(getRandomDigit(1, 10)));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

	private enum Property implements Key
	{
		CATEGORY("category"),
		VALUE("value");

		private final String value;

		private Property(String value) {
			this.value = value;
		}

		@Override
		public String value() {
			return value;
		}

	}
	
	private static class TreeMapObject extends NativeObjectContainer {

		private TreeMapObject(NativeObject nativeObject) {
			super(nativeObject);
		}
		
		private void setValue(double s) {
			setValue(Property.VALUE, s);
		}
		
	}
	
	private static class TreeMapObjectFactory implements NativeObjectContainerFactory<TreeMapObject>{

		@Override
		public TreeMapObject create(NativeObject nativeObject) {
			return new TreeMapObject(nativeObject);
		}
		
	}
	
}
