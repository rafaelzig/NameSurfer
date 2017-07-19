package tsinghua.softdev.rafael.interfaces;
public interface INameSurferDatabase extends NameSurferConstants {

	/**
	 * Set path of the directory where data files locate
	 */
	public void setDir(String dir);

	/**
	 * Return path of the directory where data files locate
	 */

	public String getDir();

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one exists. If
	 * the name does not appear in the database, this method returns null.
	 */
	public INameSurferEntry findEntry(String name, String gender);
}