package gui.table;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class DataTable<T> extends JTable {
	
	private DataModel<T> dataModel;
	
	public DataTable(DataModel<T> dataModel) {
		
		this.dataModel = dataModel;
		
		this.initDefaultConfig();
		
	}
	
	private void initDefaultConfig() {
		
		this.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("DOWN"), "Down arrow key");
		
		this.getActionMap().put("Down arrow key", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(getSelectedRow() == getRowCount()- 1) { 
					
					if(getRawData() == null)
						
						return;
					
					else
						
						((DataManager)dataModel.getValueListener()).insertNewRow();
					
				}
				
				addRowSelectionInterval(getSelectedRow() + 1, getSelectedRow() + 1);
				
			}
			
		});
		
		this.setCellSelectionEnabled(true);
		
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.setTableHeader(null);
		
		this.setModel(this.dataModel);
		
		DefaultTableCellRenderer defaultCellRender = new DefaultTableCellRenderer();
		
		defaultCellRender.setHorizontalAlignment(JLabel.CENTER);
		
		TableColumnModel columnModel = this.getColumnModel();
		
		for(int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++)
			
			columnModel.getColumn(columnIndex).setCellRenderer(defaultCellRender);
		
	}
	
	public void setData(ArrayList<T> data) {
		
		this.dataModel.setData(data);
		
		this.repaint();
		
	}
	
	public void removeData() {
		
		this.dataModel.removeData();
		
		this.repaint();
		
	}
	
	public ArrayList<T> getRawData() {
		
		return this.dataModel.getRawData();
		
	}

}
