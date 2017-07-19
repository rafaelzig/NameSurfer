package tsinghua.softdev.rafael;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import tsinghua.softdev.rafael.interfaces.INameSurferDatabase;
import tsinghua.softdev.rafael.interfaces.INameSurferEntry;

/**
 * Class responsible for storing the data of the application.
 * 
 * @author Rafael da Silva Costa - 2015280364
 * @version EXTENDED
 */
class NameSurferDatabase implements INameSurferDatabase
{
	private final Map<String, INameSurferEntry>	fEntries	= new HashMap<>();
	private final Map<String, INameSurferEntry>	mEntries	= new HashMap<>();
	private final Pattern						comma		= Pattern.compile(",");
	private String								dir			= DATA_FOLDER;

	/**
	 * Creates a NameSurferDatabase to store NameSurferEntry objects.
	 */
	NameSurferDatabase()
	{
		super();
		
		for (int year = START_YEAR; year <= END_YEAR; year++)
		{
			InputStream is = getClass().getResourceAsStream(dir + year + CSV_EXTENSION);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			int temp = year;
			reader.lines().forEach(line -> updateDatabase(temp, line));
		}
	}

	/**
	 * Updates the database with the specified year and line of data.
	 * 
	 * @param year
	 *            : The year which the line of data belongs to.
	 * @param line
	 *            : The information to be stored in the database.
	 */
	private void updateDatabase(int year, String line)
	{
		String[] cells = comma.split(line);
		int rank = Integer.valueOf(cells[0]);

		addEntry(year, rank, cells[1], MALE);
		addEntry(year, rank, cells[2], FEMALE);
	}

	/**
	 * Adds a NameSurfer object to the database or updates an existing one.
	 * 
	 * @param year
	 *            : The year of the entry.
	 * @param rank
	 *            : The rank of the entry.
	 * @param name
	 *            : The name of the entry.
	 * @param gender
	 *            : The gender of the entry.
	 */
	private void addEntry(int year, int rank, String name, String gender)
	{
		INameSurferEntry entry;

		Map<String, INameSurferEntry> curr = gender.equals(MALE) ? mEntries : fEntries;

		entry = curr.get(name);

		if (entry == null)
		{
			entry = new NameSurferEntry(name, gender);
		}

		entry.setRank(year, rank);
		curr.put(name, entry);
	}

	@Override
	public void setDir(String dir)
	{
		this.dir = dir;
	}

	@Override
	public String getDir()
	{
		return dir;
	}

	@Override
	public INameSurferEntry findEntry(String name, String gender)
	{
		return gender.equals(MALE) ? mEntries.get(name) : fEntries.get(name);
	}
}