package gui.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import gui.communication.AppDesignListener;
import gui.communication.DataChangeListener;
import gui.table.DataManager;
import gui.table.DataTable;
import gui.table.SelectedCellNotifier;

@SuppressWarnings("serial")
public class AppDesign extends JPanel implements ActionListener, DataChangeListener{
	
	private boolean fileSaved = true;
	
	private AppDesignListener windowListener;
	
	private Dimension containerSize;
	
	private File currentFile;

	private DataManager tablesDataManager;
	
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu[] menuesArray = {
		
		new JMenu("File"),
		
		new JMenu("Help")
		
	};
	
	private JMenuItem[][] menuItemsArray = {
		
		{ new JMenuItem("Open File"), new JMenuItem("Save file"), new JMenuItem("Close file"), new JMenuItem("Exit") },
		
		{ new JMenuItem("About") }
		
	};
	
	public AppDesign(AppDesignListener windowListener, Dimension containerSize) {
		
		this.windowListener = windowListener;
		
		this.containerSize = containerSize;
		
		this.setPreferredSize(this.containerSize);
		
		this.tablesDataManager = new DataManager(this);
		
		this.initDefaultSettings();
		
	}
	
	private void initDefaultSettings() {
		
		this.setLayout(new BorderLayout());
		
		this.initMenuBar();
		
		this.initTables();
		
	}
	
	private void initMenuBar() {
		
		for(int index = 0; index < this.menuesArray.length; index++) {
			
			JMenu menu = this.menuesArray[index];
			
			JMenuItem items[] = this.menuItemsArray[index];
			
			for(JMenuItem item : items) {
				
				item.addActionListener(this);
				
				menu.add(item);
				
			}
			
			this.menuBar.add(menu);
			
		}
		
		this.menuItemsArray[0][0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		this.menuItemsArray[0][1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		
		this.add(this.menuBar, BorderLayout.NORTH);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object sourceObject = null;
		
		if ((sourceObject = e.getSource()) instanceof JMenuItem) {
			
			JMenuItem menuItem = (JMenuItem)sourceObject;
			
			if(menuItem == this.menuItemsArray[0][0]) {
				
				JFileChooser fileChooser = new JFileChooser();
				
				if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					
					this.currentFile = fileChooser.getSelectedFile();
					
					this.tablesDataManager.loadFile(this.currentFile);
					
					this.windowListener.fileOpened(this.currentFile.getAbsolutePath());
					
				}
				
			}else if(menuItem == this.menuItemsArray[0][1]) {
				
				this.saveFile();
				
			}else if(menuItem == this.menuItemsArray[0][2]) {
				
				if(this.isFileSaved() == false)
					
					this.saveFileRequest();
				
				this.tablesDataManager.removeData();
				
				this.windowListener.fileClosed();
				
			}else if(menuItem == this.menuItemsArray[0][3]) {
				
				if(this.isFileSaved() == false)
					
					this.saveFileRequest();
				
				this.windowListener.exitRequested();
				
			}else if(menuItem == this.menuItemsArray[1][0]) {
				
				//This is not implemented yet.
				
			}
			
		}
		
		
	}
	
	private void initTables() {
		
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, (int)(this.containerSize.width * 0.15), (int)(this.containerSize.height * 0.05));
		
		JPanel tablesPanel = new JPanel(flowLayout);
		
		SelectedCellNotifier notifier = new SelectedCellNotifier(this.tablesDataManager.getTables());
		
		for(DataTable<?> table : this.tablesDataManager.getTables()) {
			
			table.getSelectionModel().addListSelectionListener(notifier);
			
			table.getColumnModel().getSelectionModel().addListSelectionListener(notifier);
			
			JScrollPane tableScrollPane = new JScrollPane(table);
			
			tableScrollPane.setPreferredSize(new Dimension((int)(this.containerSize.width * 0.35), (int)(this.containerSize.height * 0.90)));
			
			tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
			
			tablesPanel.add(tableScrollPane);
			
		}
		
		tablesPanel.setPreferredSize(this.containerSize);
		
		JScrollPane scrollPane = new JScrollPane(tablesPanel);
		
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		
		scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
		
		this.add(scrollPane, BorderLayout.CENTER);
		
	}
	
	public boolean isFileSaved() {
		
		return this.fileSaved;
		
	}
	
	public void saveFile() {
		
		if(this.fileSaved == false && this.currentFile != null) {
			
			this.tablesDataManager.writeFile(this.currentFile);
			
			this.fileSaved = true;
			
			this.windowListener.fileSaved();
			
		}
		
	}
	
	private void saveFileRequest() {
			
		String[] messages = {"Save file", "Discard changes"};
			
		int selectedOption = JOptionPane.showInternalOptionDialog(null, "There are changes in file. Do you want save it?", "Select an option", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, messages, messages[0]);
		
		JOptionPane.getRootFrame().dispose();
		
		if(selectedOption == JOptionPane.OK_OPTION)
			
			this.saveFile();
			
	}
	
	@Override
	public void dataChanged() {
		
		this.fileSaved = false;
		
		this.windowListener.fileChanged();
		
	}

}
