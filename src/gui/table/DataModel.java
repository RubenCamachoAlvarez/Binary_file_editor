package gui.table;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import gui.communication.DataChangeListener;
import gui.communication.ValueChangeListener;

@SuppressWarnings("serial")
public class DataModel<T> extends AbstractTableModel{
	
	private ArrayList<T> data;
	
	private ValueChangeListener valuesListener;
	
	private Class<T> itemsClassType;
	
	private DataChangeListener windowListener;
	
	protected static int numberOfRows = 50;
	
	protected static int numberOfColumns = 15;
	
	public DataModel(Class<T> itemsClassType, DataChangeListener windowListener, ValueChangeListener valuesListener) {
	
		this.itemsClassType = itemsClassType;
		
		this.windowListener = windowListener;
		
		this.valuesListener = valuesListener;
		
	}
	
	public void setData(ArrayList<T> data) {
		
		if(data != null) {
			
			this.data = data;
			
		}
		
	}
	
	public void removeData() {
		
		this.data = null;
		
	}

	@Override
	public int getRowCount() {
		
		return numberOfRows;
		
	}

	@Override
	public int getColumnCount() {
		
		return numberOfColumns;
		
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		
		int index = numberOfColumns * rowIndex + columnIndex;
		
		return (this.data != null && index < this.data.size()) ? this.data.get(index).toString() : "";
		
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return this.data != null;
		
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		
		if(this.data != null) {
			
			String input = (String)value;
			
			String newValue = "";
			
			for(char character : input.toCharArray()) {
				
				if(character != '\n' && character != '\t' && character != '\0') {
					
					if (character == '\\')
						
						newValue += "\\";
					
					else
						
						newValue += Character.toString(character);
					
					
				}
				
			}
			
			
			if(this.valuesListener.updateValues(newValue, this.itemsClassType, numberOfColumns * rowIndex + columnIndex)) {
				
				this.windowListener.dataChanged();
				
			}
			
		}
		
	}
	
	public ArrayList<T> getRawData() {
		
		return this.data;
		
	}
	
	public Class<T> getItemClassType() {
		
		return this.itemsClassType;
		
	}
	
	public ValueChangeListener getValueListener() {
		
		return this.valuesListener;
		
	}
	
}
