package SimpleDataStructure;
/*
 * Hussein Alsowadi
 * Created: 2/14/21
 * Last Updated:2/16/21
 * This class is a record of an index. This class returns and sets a Key, and location.  
 * 
 */
public class IndexRecord{
	String key;
	int where;
//Default constructor	
public IndexRecord() {
	
	
	
}	
//Parameterized Constructor
public IndexRecord (String key, int where) {
	this.key=key;
	this.where=where;
}
//Getters and Setters
public String getKey() {
	return key;
}
public void setKey(String key) {
	this.key=key;
}
public int getWhere() {
	return where;
}
public void setWhere(int where) {
	this.where=where;
}
//compareTo Method
public int compareTo(IndexRecord otherRecord) {
	

return this.key.compareTo(otherRecord.key);
}
}