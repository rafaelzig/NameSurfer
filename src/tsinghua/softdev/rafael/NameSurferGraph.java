package tsinghua.softdev.rafael;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javafx.util.Pair;

import javax.swing.JComponent;

import tsinghua.softdev.rafael.interfaces.INameSurferEntry;
import tsinghua.softdev.rafael.interfaces.NameSurferConstants;

/**
 * Class extends the abstract class javax.swing.JComponent, and is responsible
 * for displaying the graph of the various names by arranging the lines and
 * labels on the screen.
 * 
 * @author Rafael da Silva Costa - 2015280364
 * @version EXTENDED
 */
class NameSurferGraph extends JComponent implements NameSurferConstants
{
	/** Default generated serial version ID */
	private static final long					serialVersionUID	= 1L;

	private final Color[]						mColours			= new Color[] {
			new Color(0x088DA5), new Color(0x00B5CA), new Color(0x3360FF),
			new Color(0x5379FF), new Color(0x9EC9FF)				};

	private final Color[]						fColours			= new Color[] {
			new Color(0xEC0D88), new Color(0xB710CC), new Color(0xF92B54),
			new Color(0xFDA7A7), new Color(0x83196E)				};

	private final Map<INameSurferEntry, Color>	entries				= new HashMap<>();
	private final Font							FONT				= new Font(
																			Font.DIALOG,
																			Font.BOLD, 10);
	private int									mCursor				= 0;
	private int									fCursor				= 0;

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	void clear()
	{
		entries.clear();
		repaint();
	}

	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * 
	 * @param entry
	 *            : The entry to be added to the graph.
	 */
	void addEntry(INameSurferEntry entry)
	{
		Color[] colours;
		int cursor;

		if (entry.getGender().equals(MALE))
		{
			colours = mColours;
			cursor = mCursor++;
		}
		else
		{
			colours = fColours;
			cursor = fCursor++;
		}

		entries.put(entry, colours[cursor % colours.length]);
		plotLine(getGraphics(), entry);
	}

	/**
	 * Removes the specified entry from the graph.
	 * 
	 * @param entry
	 *            : The entry to be removed from the graph.
	 */
	void removeEntry(INameSurferEntry entry)
	{
		entries.remove(entry);
		repaint();
	}

	/**
	 * Returns true if the graph is empty, false otherwise.
	 * 
	 * @return True if the graph is empty, false otherwise.
	 */
	boolean isEmpty()
	{
		return entries.isEmpty();
	}

	/**
	 * Returns true if the specified entry is contained in the graph, false
	 * otherwise.
	 * 
	 * @param entry
	 *            : The entry object to be checked.
	 * @return True if the specified entry is contained in the graph, false
	 *         otherwise.
	 */
	boolean contains(INameSurferEntry entry)
	{
		return entries.containsKey(entry);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		drawGrids(g);
		entries.keySet().forEach(entry -> plotLine(g, entry));
	}

	/**
	 * Plots a line on the specified graphics object representing the specified
	 * entry.
	 * 
	 * @param g
	 *            : The Graphics object to be used to draw.
	 * @param entry
	 *            : The INameSurferEntry object to be drawn.
	 */
	private void plotLine(Graphics g, INameSurferEntry entry)
	{
		g.setColor(entries.get(entry));
		g.setFont(FONT);

		float xUnit = (float) getWidth() / (MAX_DATA_POINTS - 1);
		float yUnit = (float) (getHeight() - 2 * GRAPH_MARGIN_SIZE) / (MAX_RANK + 1);

		for (int decade = START_YEAR; decade < END_YEAR; decade += INTERVAL)
		{
			drawLabel(g, xUnit, yUnit, entry, decade,
					drawLine(g, xUnit, yUnit, entry, decade));
		}

		drawLastLabel(g, entry, xUnit, yUnit);
	}

	/**
	 * Draws the lines representing the specified entry object during the decade
	 * specified.
	 * 
	 * @param g
	 *            : The Graphics object to be used to draw.
	 * @param entry
	 *            : The INameSurferEntry object to be drawn.
	 * @param xUnit
	 *            : Integer representing a single unit of length of the graph in
	 *            the x coordinate.
	 * @param yUnit
	 *            : Integer representing a single unit of length of the graph in
	 *            the y coordinate.
	 * @param decade
	 *            : The decade of which the data belongs to.
	 * @return A Pair object corresponding to the best and worst rankings of the
	 *         decade.
	 */
	private Pair<Integer, Integer> drawLine(Graphics g, float xUnit, float yUnit,
			INameSurferEntry entry, int decade)
	{
		TreeSet<Integer> rankings = new TreeSet<>();
		int startRank = entry.getRank(decade);
		startRank = startRank > 0 ? startRank : MAX_RANK + 1;
		rankings.add(startRank);

		for (int year = 0; year < INTERVAL; year++)
		{
			int currRank = entry.getRank(decade + year);
			currRank = currRank > 0 ? currRank : MAX_RANK + 1;
			int nextRank = entry.getRank(decade + year + 1);
			nextRank = nextRank > 0 ? nextRank : MAX_RANK + 1;

			float x1 = (decade + year - START_YEAR) * xUnit;
			float y1 = currRank * yUnit + GRAPH_MARGIN_SIZE;
			float x2 = (decade + year + 1 - START_YEAR) * xUnit;
			float y2 = nextRank * yUnit + GRAPH_MARGIN_SIZE;

			((Graphics2D) g).draw(new Line2D.Float(x1, y1, x2, y2));

			rankings.add(nextRank);
		}

		return new Pair<Integer, Integer>(rankings.first(), rankings.last());
	}

	/**
	 * Draws a single label on the graph.
	 * 
	 * @param g
	 *            : The Graphics object to be used to draw.
	 * @param xUnit
	 *            : Integer representing a single unit of length of the graph in
	 *            the x coordinate.
	 * @param yUnit
	 *            : Integer representing a single unit of length of the graph in
	 *            the y coordinate.
	 * @param entry
	 *            : The INameSurferEntry object to be drawn.
	 * @param year
	 *            : The year of which the data belongs to.
	 * @param bestAndWorst
	 *            : The best and worst rankings of the decade.
	 */
	private void drawLabel(Graphics g, float xUnit, float yUnit, INameSurferEntry entry,
			int year, Pair<Integer, Integer> bestAndWorst)
	{
		int startRank = entry.getRank(year);
		startRank = startRank > 0 ? startRank : MAX_RANK + 1;

		float x = (year - START_YEAR) * xUnit;
		float y = startRank * yUnit + GRAPH_MARGIN_SIZE;

		// If startRank is worse than some other rank in decade
		if (startRank > bestAndWorst.getKey())
		{
			y = bestAndWorst.getKey() * yUnit + GRAPH_MARGIN_SIZE;
		}

		((Graphics2D) g).drawString(entry.toString() + " "
				+ (startRank > MAX_RANK ? "*" : startRank), x, y);
	}

	/**
	 * Draws the last label on the graph, which will be printed vertically to
	 * improve readability.
	 * 
	 * @param g
	 *            : The Graphics object to be used to draw.
	 * @param entry
	 *            : The INameSurferEntry object to be drawn.
	 * @param xUnit
	 *            : Integer representing a single unit of length of the graph in
	 *            the x coordinate.
	 * @param yUnit
	 *            : Integer representing a single unit of length of the graph in
	 *            the y coordinate.
	 */
	private void drawLastLabel(Graphics g, INameSurferEntry entry, float xUnit,
			float yUnit)
	{
		AffineTransform at = new AffineTransform();
		at.setToRotation(Math.toRadians(90), 0, 0);
		g.setFont(FONT.deriveFont(at));

		int rank = entry.getRank(END_YEAR);
		rank = rank > 0 ? rank : MAX_RANK + 1;

		float x = (END_YEAR - START_YEAR - 1) * xUnit;
		float y = rank * yUnit + GRAPH_MARGIN_SIZE;

		String str = entry.toString() + " " + (rank > MAX_RANK ? "*" : rank);
		int strWidth = g.getFontMetrics(FONT).stringWidth(str);

		if (y + strWidth > getHeight() - 2 * GRAPH_MARGIN_SIZE)
		{
			y = getHeight() - GRAPH_MARGIN_SIZE - strWidth;
		}

		((Graphics2D) g).drawString(str, x, y);
	}

	/**
	 * Draws the grids of the graph using the specified Graphics object.
	 * 
	 * @param g
	 *            : The Graphics object to be used to draw.
	 */
	private void drawGrids(Graphics g)
	{
		// Draws the horizontal lines
		g.drawLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		g.drawLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight()
				- GRAPH_MARGIN_SIZE);

		// Draws the first year
		g.drawString(Integer.toString(START_YEAR), 0, getHeight() - PADDING);

		// Draws the vertical lines and years
		float gap = (float) getWidth() / (INTERVAL + 1);
		for (int i = 1; i <= INTERVAL; i++)
		{
			float x = i * gap;

			((Graphics2D) g).draw(new Line2D.Float(x, 0, x, getHeight()));
			((Graphics2D) g).drawString(Integer.toString(START_YEAR + (INTERVAL * i)), x,
					getHeight() - PADDING);
		}
	}
}