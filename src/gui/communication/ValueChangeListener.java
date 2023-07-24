package gui.communication;

public interface ValueChangeListener {
	
	public boolean updateValues(String newItem, Class<?> tableItemsClass, int indexToUpdate);

}