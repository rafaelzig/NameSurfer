/**
 * 
 */
package tsinghua.softdev.rafael;

import tsinghua.softdev.rafael.interfaces.INameSurferEntry;

/**
 * Class responsible for storing all the information for a particular name and
 * gender.
 * 
 * @author Rafael da Silva Costa - 2015280364
 * @version EXTENDED
 */
class NameSurferEntry implements INameSurferEntry
{
	private final int[]	rankings	= new int[MAX_DATA_POINTS];
	private String		gender;
	private String		name;

	/**
	 * Constructs an entry with the specified name, gender and empty rankings.
	 * 
	 * @param name
	 *            : The name of this entry.
	 * @param gender
	 *            : The gender of this entry.
	 */
	NameSurferEntry(String name, String gender)
	{
		super();
		this.name = name;
		this.gender = gender;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String getGender()
	{
		return gender;
	}

	@Override
	public void setGender(String gender)
	{
		this.gender = gender;
	}

	@Override
	public int getRank(int year)
	{
		return rankings[year - START_YEAR];
	}

	@Override
	public void setRank(int year, int rank)
	{
		rankings[year - START_YEAR] = rank;
	}

	@Override
	public String toString()
	{
		return (gender.equals(MALE) ? MALE_SIGN : FEMALE_SIGN) + name;
	}
}