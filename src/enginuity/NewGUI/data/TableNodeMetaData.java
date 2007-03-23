package enginuity.NewGUI.data;

import enginuity.NewGUI.interfaces.TuningEntity;

public class TableNodeMetaData {

	public static final int DATA1D = 0;
	public static final int DATA2D = 1;
	public static final int DATA3D = 3;
	public static final int CATEGORY = 4;
	public static final int RESERVED_ROOT = 5;
	
	
	
	private double maxValue;
	private double minValue;
	private Object[] ignoredValues;
	private boolean isInvertedColoring;
	private String tableName;
	private String tableIdentifier;
	private int dimensions;
	private TuningEntity parentTuningEntity;
	
	
	public TableNodeMetaData(int dimensions, double minValue, double maxValue, Object[] ignoredValues, boolean isInvertedColoring, String tableName, String tableIdentifier, TuningEntity parentTuningEntity) {
		this.dimensions = dimensions;
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.ignoredValues = ignoredValues;
		this.isInvertedColoring = isInvertedColoring;
		this.tableName = tableName;
		this.tableIdentifier = tableIdentifier;
		this.parentTuningEntity = parentTuningEntity;
		
		System.out.println("Min:"+this.minValue+ " Max:"+this.maxValue + " Name:"+this.tableName+ " Inv:"+this.isInvertedColoring);
	}

	public Object[] getIgnoredValues() {
		return ignoredValues;
	}

	public boolean isInvertedColoring() {
		return isInvertedColoring;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public String getTableName() {
		return tableName;
	}

	public int getNodeType() {
		return dimensions;
	}

	public String getTableIdentifier() {
		return tableIdentifier;
	}
}