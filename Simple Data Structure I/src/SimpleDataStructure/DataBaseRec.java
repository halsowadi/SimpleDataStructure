package SimpleDataStructure;
/*
 * Hussein Alsowadi
 * Created: 2/14/21
 * Last Updated:2/16/21
 * This class is a record containing first name, last name, and ID. 
 * 
 */

public class DataBaseRec {
	private String Fname;
	private String Lname;
	private String ID;
	private int nelems;



public  DataBaseRec() {
	Fname = "";
    Lname ="";
    ID = "";
    nelems=0;
    }
public  DataBaseRec(int nelemsSize) {
	Fname = "";
    Lname ="";
    ID = "";
    nelems=nelemsSize;
    }
public DataBaseRec(String Fname, String Lname, String ID) {
	this.Fname = Fname;
	this.Lname = Lname;
	this.ID = ID;
}


//Getter methods
public String getID() {
return ID;
}

public String getFirst() {
return Fname;
}

public String getLast() {
return Lname;
}
public int getNelems() {
	return nelems;
}
public String toString() {
	return Fname + " "+ Lname + " " + ID;
}


}