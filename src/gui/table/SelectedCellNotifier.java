package gui.table;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.DefaultListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

public class SelectedCellNotifier implements ListSelectionListener, TableColumnModelListener {
	
	private ArrayList<DataTable<?>> tables = new ArrayList<DataTable<?>>();
	
	private Point lastSelectedCellCoordinates = new Point(-1,-1);
	
	public SelectedCellNotifier(DataTable<?> ...dataTables) {
		
		for(DataTable<?> table : dataTables)
			
			tables.add(table);
		
	}
	
	private DataTable<?> getSourceTable(DefaultListSelectionModel eventSource) {
		
		for(DataTable<?> table : this.tables)
			
			if (table.getSelectionModel() == eventSource || table.getColumnModel().getSelectionModel() == eventSource)
				
				return table;
		
		return null;
		
	}
	
	private Point getNewSelectedCellCoordinates(DataTable<?> sourceTable) {
		
		if(sourceTable != null)
			
			return new Point(sourceTable.getSelectedRow(), sourceTable.getSelectedColumn());
		
		return null;
		
	}
	
	@Override
	public void columnSelectionChanged(ListSelectionEvent e) {
		
		DataTable<?> sourceTable = this.getSourceTable((DefaultListSelectionModel)e.getSource());
		
		this.updateSelectedCell(sourceTable, this.getNewSelectedCellCoordinates(sourceTable));
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		DataTable<?> sourceTable = this.getSourceTable((DefaultListSelectionModel)e.getSource());
		
		this.updateSelectedCell(sourceTable, this.getNewSelectedCellCoordinates(sourceTable));
		
	}
	
	public boolean updateSelectedCell(DataTable<?> sourceTable, Point newSelectedCellCoordinates) {
		
		if(sourceTable != null && newSelectedCellCoordinates != null && !newSelectedCellCoordinates.equals(this.lastSelectedCellCoordinates) &&
				newSelectedCellCoordinates.x > -1 && newSelectedCellCoordinates.y > -1) {
			
			for(DataTable<?> table : this.tables)
				
				if(table != sourceTable && (table.getSelectedColumn() != newSelectedCellCoordinates.y || table.getSelectedRow() != newSelectedCellCoordinates.x)) {
					
					table.setRowSelectionInterval(newSelectedCellCoordinates.x, newSelectedCellCoordinates.x);
					
					table.setColumnSelectionInterval(newSelectedCellCoordinates.y, newSelectedCellCoordinates.y);
					
				}
			
			this.lastSelectedCellCoordinates = newSelectedCellCoordinates;
			
			return true;
			
		}
		
		return false;
		
	}

	@Override
	public void columnAdded(TableColumnModelEvent e) {}

	@Override
	public void columnRemoved(TableColumnModelEvent e) {}

	@Override
	public void columnMoved(TableColumnModelEvent e) {}

	@Override
	public void columnMarginChanged(ChangeEvent e) {}

}
