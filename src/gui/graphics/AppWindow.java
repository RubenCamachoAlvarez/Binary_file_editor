package gui.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import gui.communication.AppDesignListener;

@SuppressWarnings("serial")
public class AppWindow extends JFrame implements AppDesignListener{
	
	private final String defaultWindowTitle = "Binary file editor";
	
	private Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
	
	private Dimension windowSize;
	
	private AppDesign design;
	
	public AppWindow() {
		
		this.initWindowConfig();
		
	}
	
	private void initWindowConfig() {
		
		Dimension screenSize = this.defaultToolkit.getScreenSize();
		
		Insets screenInsets = this.defaultToolkit.getScreenInsets(this.getGraphicsConfiguration());
		
		this.windowSize = new Dimension(screenSize.width - screenInsets.left - screenInsets.right, screenSize.height - screenInsets.top - screenInsets.bottom);
		
		this.setMinimumSize(new Dimension(500, 500));
		
		this.setSize(this.windowSize);
		
		this.setLocationRelativeTo(null);
		
		this.setTitle(this.defaultWindowTitle);
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				
				if(design.isFileSaved())
				
					dispose();
				
				else {
					String[] messages = {"Save file", "Discard changes"};
					
					int selectedOption = JOptionPane.showInternalOptionDialog(null, "There are changes in file. Do you want save it?", "Select an option", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, messages, messages[0]);
					
					JOptionPane.getRootFrame().dispose();
					
					if( selectedOption == JOptionPane.OK_OPTION) {
					
						design.saveFile();
						
						dispose();
						
					}else {
						
						dispose();
						
					}
					
				}
				
			}
		
		});
		
		this.design = new AppDesign(this, this.windowSize);
		
		this.add(this.design, BorderLayout.CENTER);
		
		this.setVisible(true);
		
	}

	@Override
	public void fileOpened(String pathToFile) {
		
		this.setTitle(pathToFile);
		
	}

	@Override
	public void fileClosed() {
		
		this.setTitle(this.defaultWindowTitle);
		
	}

	@Override
	public void fileSaved() {
		
		if(this.getTitle().startsWith("*"))
			
			this.setTitle(this.getTitle().substring(1));
		
	}

	@Override
	public void fileChanged() {
		
		if(!this.getTitle().startsWith("*"))
			
			this.setTitle("*" + this.getTitle());
		
	}

	@Override
	public void exitRequested() {
		
		this.dispose();
		
	}

}
