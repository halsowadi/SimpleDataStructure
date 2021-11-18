package SimpleDataStructure;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
/*
 * Hussein Alsowadi
 * Created: 2/14/21
 * Last Updated:2/21/21
 * This class creates a simple DataBase with the indexes of ID, First Name, and Last name. This class also contains methods to add, delete, find, and sort the database. 
 */

public class DataBase {

	private DataBaseRec[] data;
	private Index iID, iFname, iLname;
	private int dbnext;
	private Scanner scan;

	//no-args constructor 
	public DataBase() 
	{
		dbnext=0;
		data = new DataBaseRec[200];
		iID = new Index();
		iFname = new Index();
		iLname = new Index();
		Scanner keyboard=new Scanner(System.in);
		//importing file 
		try {
			this.fillDataBase();
		}catch (IOException  e) {

		}


	} 

	// Parameterized constructor
	public DataBase(int size) {
		dbnext = 0;
		data = new DataBaseRec[size];
		iID = new Index(size);
		iFname = new Index(size);
		iLname = new Index(size);
		Scanner keyboard=new Scanner(System.in);
		//importing file 
		try {
			this.fillDataBase();
		}catch (IOException  e) {

		}



	}

//Reads data from file and fills DataBase
	public void fillDataBase()
			throws IOException{
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream("Students.txt")));
		String currentLine;

		// Iterates through file line by line
		while ((currentLine = reader.readLine()) != null) {
			// Trim extra whitespace off currentLine
			currentLine = currentLine.trim();

			// Splits currentLine into multiple Strings at whitespaces
			String[] parts = currentLine.split("\\s+");

			// Adds record to dataBase
			this.insert(parts[1].trim(), parts[0].trim(),parts[2].trim());
		}

	}

	//Checks to see if there is anything matching key in array returns true if there is a match
	public boolean searchForMatching(String tempID) {
		// Makes ID into 5 digit int
		tempID = tempID.trim();
		tempID = String.format("%05d", Integer.parseInt(tempID));

		//binary search
		int lo = 0;
		int hi = iID.getNumberOfRecords() - 1;
		int mid;
		boolean found;

		while (lo <= hi) {
			mid = (hi + lo) / 2;
			found = (iID.getIndexRecord(mid).getKey().compareTo(tempID) == 0);

			if (found)
				return true;
			else if (iID.getIndexRecord(mid).getKey().compareTo(tempID) > 0) {
				hi = mid - 1;
			} else {
				lo = mid + 1;
			}
		}
		return false; // Not found
	}

	//Inserts parameters into each appropriate index
	public void insert(String firstName, String lastName, String tempID) {

		// Trim whitespace and converts String ID to 5 dight Int (better be safe then sorry)
		firstName = firstName.trim();
		lastName = lastName.trim();
		tempID = tempID.trim();					
		tempID = String.format("%05d", Integer.parseInt(tempID));

		
		

		//Creating a new record
		DataBaseRec record = new DataBaseRec(firstName,lastName, tempID);
		int indexToInsertAt;

        //location to insert @
		indexToInsertAt = dbnext;
        //Only inserts when ID is unique 
		while (!searchForMatching(tempID)) {

		//location @record to insert @
		data[indexToInsertAt] = record;
		// Add record to indexes
		iFname.addRecord(new IndexRecord(firstName, indexToInsertAt));
		iLname.addRecord(new IndexRecord(lastName, indexToInsertAt));
		iID.addRecord(new IndexRecord(tempID, indexToInsertAt));

		dbnext++;
		}
		
	}

	//Delete a record by key 
	public  void delete(String key) {
        //gets location to delete 
		int whereToDelete = iID.getWhereByKey(key); 
       //Delete ID, first name, and last name from their indexes 
		iID.deleteIndex(iID.getWhereByKey(key));
		iFname.deleteIndex(iFname.searchByWhere(whereToDelete));  
		iLname.deleteIndex(iLname.searchByWhere(whereToDelete));

	}
	
	//Ask user to specify ID, return data of that record if found
		public void findIt() {
			Scanner kb = new Scanner (System.in);
			String ID = "";

			System.out.println("\nFINDING......");
			System.out.println("Please enter the ID of the record you would like to be found: ");
			ID = kb.next();
			int iaIndexOfRecord = this.iID.find(ID);

			if (iaIndexOfRecord == -1) {
				System.out.println( "Record not found, please try again." );
				return;
			}
			//if record found
			int x =  iID.getWhereByKey(ID);

			System.out.println(data[x]);
			System.out.println( "Record found");
		}

	//Ask user to specify ID they would like to add, if ID is not already in use, a new student is added. 
	public void addIt() {
		Scanner keyboard = new Scanner(System.in);
		String ID = "";
		String first = "";
		String last = "";
		System.out.println("\nADDING");
		System.out.println("Please enter the ID of the record you would like to add: ");
		ID = keyboard.nextLine();
		//if ID is in use
		if (this.iID.getWhereByKey(ID) != -1) {
			System.out.println( "ID already in use, please try again.");
			return;
		}
		//if ID is not in use
		System.out.println("Please enter the first name of the record: ");
		first = keyboard.nextLine();

		System.out.println("Please enter the last name of the record: ");
		last = keyboard.nextLine();

		this.insert(first, last,ID);
		System.out.println( "Record successfully added"  );

	}
	//Ask user to specify a ID they would like to delete, and delete that record if it is in in the database. 
	public void deleteIt() {
		Scanner keyboard = new Scanner(System.in);
		String ID = "";
		System.out.println("\nDELETING" );
		System.out.println("Please enter the ID of the record to be deleted: ");
		ID = keyboard.nextLine();
		//if record is not in DB
		if (this.iID.getWhereByKey(ID) == -1) {
			System.out.println( "Record not found, please try again.");
			return;
		}
		//if record is in DB 
		delete(ID);
		System.out.println("Record successfully deleted");

	}

	//Method to return index ascending
	public void Ascending(Index x) {
		for (int i = 0; i < x.getNumberOfRecords(); i++) {
			System.out.println(data[x.getIndexRecord(i).getWhere()].toString());
		}
	}
	
	//Method to return index descending
	public void Descending(Index x) {
		for (int i = x.getNumberOfRecords() - 1; i >= 0; i--) {
			System.out.println(data[x.getIndexRecord(i).getWhere()].toString());
		}
	}

	// Methods to return indexes
	public void ListByIDAscending() {

		iID.iteratorInitFront();
		Ascending(iID);
		System.out.println();


	}

	public void ListByFirstAscending() {

		iFname.iteratorInitFront();
		Ascending(iFname);
		System.out.println();

	}

	public void ListByLastAscending() {

		iLname.iteratorInitFront();
		Ascending(iLname);
		System.out.println();

	}

	public void ListByIDDescending() {

		//Get the iterator
		iID.iteratorInitBack();
		Descending(iID);
		System.out.println();

	}

	public void ListByFirstDescending() {

		iFname.iteratorInitBack();
		Descending(iFname);
		System.out.println();

	}


	public void ListByLastDescending() {

		iLname.iteratorInitBack();
		Descending(iLname);
		System.out.println();
	}

}
