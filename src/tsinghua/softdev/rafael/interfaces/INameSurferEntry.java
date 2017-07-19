package tsinghua.softdev.rafael.interfaces;
public interface INameSurferEntry extends NameSurferConstants {

	/**
	 * Returns the name associated with this entry.
	 */
	public String getName();
	
	/**
	 * Sets the name associated with this entry.
	 */
	public void setName(String name);
	
	/**
	 * Returns the gender associated with this entry.
	 */
	public String getGender();
	
	/**
	 * Sets the gender associated with this entry.
	 */
	public void setGender(String gender);

	/**
	 * Returns the rank associated with an entry for a particular year.
	 * If a name is not among the first 1000 names of the particular 
	 * year, the method should return 0.
	 */
	public int getRank(int year);
	
	/**
	 * Sets the rank associated with an entry for a particular year and 
	 * gender. 0 value of the parameter rank indicates that the name is 
	 * not among the first 1000 names of the particular year.
	 */
	public void setRank(int year, int rank);

	/**
	 * Returns a string that makes it easy to see the value of a
	 * INameSurferEntry.
	 */
	public String toString();
}
