package SimpleDataStructure;
/*
 * Hussein Alsowadi
 * Created: 2/14/21
 * Last Updated:2/19/21
 * This class creates an orderredArray of an index. Theres methods to find, insert and delete records into the index. 
 */

public class Index {
	private IndexRecord[] iArray;
	private int nelems;
	private boolean primaryKey;
	private int iterator;


	public Index() {
		iArray = new IndexRecord[200];
		nelems = 0;
	}
	public Index(int size) {
		iArray= new IndexRecord[size];
		nelems =0;
	}

	//Method to print
	public void print() {
		int i;
		for (i =0; i<nelems; i++)
			System.out.println(iArray[i].toString());
	}
	
	//Method to insert a key into a specific location. 
	public void insert(String key, int where) {

		//Create a new IndexRedcord and place it in the index 
		iArray[nelems] = new IndexRecord(key, where);
		nelems++;

		//Iterate over list of elements check and sort accordingly
		for (int i = nelems - 1; i > 0 && key.compareTo(iArray[i - 1].getKey()) < 0; i--) {
			IndexRecord tempRecord = iArray[i];
			iArray[i] = iArray[i - 1];
			iArray[i - 1] = tempRecord;
		}
	}
	
	//Find by ID
	public int find(String key) {
		int mid = -1;
		int low = 0;
		int high = this.nelems - 1;
		int compare = -1;
		if (this.primaryKey) {
			while (low <= high) {
				mid = (low + high) / 2;
				compare = this.iArray[mid].key.compareTo(key);
				if (compare == 0) {
					break;
				} else if (compare > 0) {
					high = mid - 1;
				} else {
					low = mid + 1;
				}
			}
		}
		else {
			for(int i = 0; i < this.nelems; i++) {
				if(this.iArray[i].key.compareTo(key) == 0) {
					mid = i;
				}
			}
		}

		if (low > high) {
			mid = -1;
		}
		return mid;
	}
	public  int getWhereByKey(String key) {
		int index = find(key);
		if (index != -1) {
			index = this.iArray[index].where;
		}

		return index;
	}
	
	//returns location or -1 if not in array
	public int searchByWhere(int where) {
		for(int i = 0; i < this.nelems; i++) {
			if (this.iArray[i].where == where) {
				return i;
			}
		}
		return -1; 
	}
	
	//Deletes record at specified index
	public void deleteIndex(int databaseIndexToDelete) {
		int iterator;

		// Linear search to find the proper record to delete
		for (iterator = 0; iterator < nelems; iterator++) {
			if (this.getIndexRecord(iterator).getWhere() == databaseIndexToDelete)
				break; //exits out of loop when found
		}

		// Shift all the records after the record to delete up one space,
		// overwriting the deleted record.
		while (iterator < nelems) {
			this.iArray[iterator] = this.getIndexRecord(iterator + 1);
			iterator++;
		}

		nelems--;
	}

//Iterators
	
    //Set iterator to front 
	public void iteratorInitFront() {
		iterator = 0;
	}

	//set iterator to back
	public void iteratorInitBack() {
		iterator = iArray.length - 1;
	}
	
	public void addRecord(IndexRecord recordToAdd) {
		// Add record to end of  the index
		iArray[nelems] = recordToAdd;

		IndexRecord temp; // For swapping
		boolean recordInCorrectLocation; 

		
		// Insert record in appropriate location
		for (int currentIndex = nelems - 1; currentIndex >= 0; currentIndex--) {
			recordInCorrectLocation = (recordToAdd.getKey().compareTo(iArray[currentIndex].getKey()) > 0);

			if (recordInCorrectLocation)
				break;
			else {
				// Swap recordToAdd up a spot in the Index
				temp = iArray[currentIndex];
				iArray[currentIndex] = iArray[currentIndex + 1];
				iArray[currentIndex + 1] = temp;
			}
		}

		nelems++;
	}
	//returns number of records 
	public int getNumberOfRecords() {
		return nelems;
	}
	
	//Returns IndexRecord
	public IndexRecord getIndexRecord(int indexOfRecordToReturn) {
		return iArray[indexOfRecordToReturn];
	}


}
