package tsinghua.softdev.rafael.interfaces;

public interface NameSurferConstants
{
	/** The width of the application window */
	static final int	APPLICATION_WIDTH	= 800;

	/** The height of the application window */
	static final int	APPLICATION_HEIGHT	= 600;

	/** The number of pixels to reserve at the top and bottom */
	static final int	GRAPH_MARGIN_SIZE	= 20;

	/** The padding used in the application window */
	static final int	PADDING				= 5;

	/** The text used in the graph button of the application window */
	static final String	GRAPH_BUTTON		= "Graph";

	/** The text used in the graph button of the application window */
	static final String	DELETE_BUTTON		= "Delete";

	/** The text used in the clear button of the application window */
	static final String	CLEAR_BUTTON		= "Clear";

	/** The action command used in the text field of the application window */
	static final String	ENTER_PRESSED		= "Enter Pressed";

	/** The action command used in the combo box of the application window */
	static final String	COMBO_BOX_CHANGED	= "Combo Box Changed";

	/** The name of the folder containing the data */
	static final String	DATA_FOLDER			= "res/names/";

	/** The name of the file extension of the files used */
	static final String	CSV_EXTENSION		= ".csv";

	/** The name of the icon file */
	static final String	ICON_16px			= "res/icons/icon16.png";

	/** The name of the icon file */
	static final String	ICON_32px			= "res/icons/icon32.png";

	/** The name of the icon file */
	static final String	ICON_48px			= "res/icons/icon48.png";

	/** The name of the icon file */
	static final String	ICON_128px			= "res/icons/icon128.png";

	/** The first year in the database */
	static final int	START_YEAR			= 1900;

	/** The first year in the database */
	static final int	END_YEAR			= 2010;

	/** The number of years between adjacent grid lines */
	static final int	INTERVAL			= 10;

	/** The maximum number of datapoints in the database */
	static final int	MAX_DATA_POINTS		= END_YEAR - START_YEAR + 1;

	/** The maximum rank in the database */
	static final int	MAX_RANK			= 1000;

	/** Value representing the male gender */
	static final String	MALE				= "Male";

	/** Unicode value for the male gender symbol */
	static final String	MALE_SIGN			= "\u2642";

	/** Value representing the female gender */
	static final String	FEMALE				= "Female";

	/** Unicode value for the female gender symbol */
	static final String	FEMALE_SIGN			= "\u2640";
}