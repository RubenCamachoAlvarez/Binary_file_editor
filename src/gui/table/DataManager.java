package gui.table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import datatypes.Hexadecimal;
import datatypes.SingleCharacter;
import gui.communication.DataChangeListener;
import gui.communication.ValueChangeListener;

public class DataManager implements ValueChangeListener{
	
	private DataTable<SingleCharacter> characterValueTable;
	
	private DataTable<Hexadecimal> hexadecimalValueTable;
	
	public DataManager(DataChangeListener dataListener) {
		
		this.characterValueTable = new DataTable<>(new DataModel<SingleCharacter>(SingleCharacter.class, dataListener, this));
		
		this.hexadecimalValueTable = new DataTable<>(new DataModel<Hexadecimal>(Hexadecimal.class, dataListener, this));
		
	}
	
	public void setData(ArrayList<SingleCharacter> characterData, ArrayList<Hexadecimal> hexadecimalData) {
		
		if(characterData != null && hexadecimalData != null) {
			
			this.characterValueTable.setData(characterData);
			
			this.hexadecimalValueTable.setData(hexadecimalData);
			
		}
		
	}
	
	public void removeData() {
		
		if(this.characterValueTable.getRawData() != null && this.hexadecimalValueTable.getRawData() != null) {
			
			this.characterValueTable.removeData();
			
			this.hexadecimalValueTable.removeData();
			
		}
		
	}
	
	@Override
	public boolean updateValues(String newItem, Class<?> tableItemsClass, int indexToUpdate) {
		
		if(this.characterValueTable.getRawData() != null && this.hexadecimalValueTable.getRawData() != null) {
			
			SingleCharacter newSingleCharacter = null;
			
			Hexadecimal newHexadecimal = null;
			
			if(tableItemsClass == SingleCharacter.class) {
				
				newSingleCharacter = SingleCharacter.getInstance(newItem);
				
				if(newSingleCharacter == null)
					
					return false;
				
				newHexadecimal = newSingleCharacter.toHexadecimal();
				
			}else if(tableItemsClass == Hexadecimal.class) {
				
				newHexadecimal = Hexadecimal.getInstance(newItem);
				
				if(newHexadecimal == null)
					
					return false;
				
				newSingleCharacter = newHexadecimal.toSingleCharacter();
				
			}
			
			if(indexToUpdate < this.characterValueTable.getRawData().size() || 
					
					(indexToUpdate >= this.characterValueTable.getRawData().size() && !newItem.strip().equals(""))) {
				
				
				for(int newIndex = this.characterValueTable.getRawData().size(); newIndex <= indexToUpdate; newIndex++) {
					
					this.characterValueTable.getRawData().add(SingleCharacter.getInstance(""));
					
					this.hexadecimalValueTable.getRawData().add(Hexadecimal.getInstance(""));
					
				}
				
				this.characterValueTable.getRawData().set(indexToUpdate, newSingleCharacter);
				
				this.hexadecimalValueTable.getRawData().set(indexToUpdate, newHexadecimal);
				
				this.characterValueTable.repaint();
				
				this.hexadecimalValueTable.repaint();
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public void updateUITables() {
		
		this.characterValueTable.updateUI();
		
		this.hexadecimalValueTable.updateUI();
		
	}
	
	public void insertNewRow() {
		
		DataModel.numberOfRows++;
		
		this.updateUITables();
		
	}
	
	public void setNumberOfRowsAndColumns(int numberOfRows, int numberOfColumns) {
		
		if(numberOfRows > 0 && numberOfColumns > 0) {
			
			DataModel.numberOfRows = numberOfRows;
			
			DataModel.numberOfColumns = numberOfColumns;
			
			this.updateUITables();
			
		}
		
	}
	
	
	public void loadFile(File fileToRead) {
		
		try {
			
			FileInputStream inputStream = new FileInputStream(fileToRead);
			
			ArrayList<SingleCharacter> characters = new ArrayList<SingleCharacter>();
			
			ArrayList<Hexadecimal> hexadecimal = new ArrayList<Hexadecimal>();
			
			String data = new String(inputStream.readAllBytes(), StandardCharsets.ISO_8859_1);
			
			for(String character : data.split("")) {
				
				SingleCharacter singleCharacter = SingleCharacter.getInstance(character);
				
				characters.add(singleCharacter);
				
				hexadecimal.add(singleCharacter.toHexadecimal());
				
			}
			
			inputStream.close();
			
			int numberOfRows = 0;
			
			if((numberOfRows = characters.size() / DataModel.numberOfColumns) >= DataModel.numberOfRows) {
				
				if(characters.size() % DataModel.numberOfColumns > 0)
					
					numberOfRows++;
				
				this.setNumberOfRowsAndColumns(numberOfRows, DataModel.numberOfColumns);
				
			}
			
			this.setData(characters, hexadecimal);
			
		}catch(IOException ex) {
			
			ex.printStackTrace();
			
		}
		
	}
	
	
	public void writeFile(File fileToWrite) {
		
		if(this.characterValueTable.getRawData() != null && this.hexadecimalValueTable.getRawData() != null) {
			
			try {
				
				ArrayList<SingleCharacter> data = this.characterValueTable.getRawData();
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWrite, StandardCharsets.ISO_8859_1));
				
				for (SingleCharacter character : data)
					
					writer.write(character.toString());
				
				writer.close();
				
			}catch(IOException ex) {
				
				ex.printStackTrace();
				
			}
			
		}
		
	}
	
	public DataTable<?>[] getTables() {
		
		return new DataTable<?>[]{this.characterValueTable, this.hexadecimalValueTable};
		
	}
	

}
