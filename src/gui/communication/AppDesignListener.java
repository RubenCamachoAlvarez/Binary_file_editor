package gui.communication;

public interface AppDesignListener {
	
	public void fileOpened(String pathToFile);
	
	public void fileClosed();
	
	public void fileSaved();
	
	public void fileChanged();
	
	public void exitRequested();

}
