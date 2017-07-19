package tsinghua.softdev.rafael;

import tsinghua.softdev.rafael.interfaces.INameSurferDatabase;
import tsinghua.softdev.rafael.interfaces.INameSurferEntry;
import tsinghua.softdev.rafael.interfaces.NameSurferConstants;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class responsible for displaying a graph containing the rankings of birth
 * names over a period of time.
 * 
 * @author Rafael da Silva Costa - 2015280364
 * @version EXTENDED
 */
class NameSurfer extends JFrame implements NameSurferConstants, ActionListener,
		CaretListener
{
	/** Default generated serial version ID */
	private static final long			serialVersionUID	= 1L;

	private final INameSurferDatabase	db					= new NameSurferDatabase();
	private final Pattern				space				= Pattern.compile(" ");
	private JButton						btnGraph, btnDelete, btnClear;
	private JTextField					txtName;
	private JComboBox<String>			cbbGender;
	private NameSurferGraph				graph;

	public static void main(String args[])
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}

		javax.swing.SwingUtilities.invokeLater(NameSurfer::new);
	}

	/**
	 * Constructor - Initialises the frame and its widgets, ultimately
	 * displaying itself to the user.
	 */
	private NameSurfer()
	{
		super("Name Surfer - EXTENDED EDITION");
		setLayout(new BorderLayout(PADDING, PADDING));
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImages(getIcons());
		setWidgets();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Loads the icons from resources.
	 * 
	 * @return List of icons to be used in the application.
	 */
	private List<Image> getIcons()
	{
		List<Image> icons = new LinkedList<Image>();
		icons.add(new ImageIcon(getClass().getResource(ICON_16px)).getImage());
		icons.add(new ImageIcon(getClass().getResource(ICON_32px)).getImage());
		icons.add(new ImageIcon(getClass().getResource(ICON_48px)).getImage());
		icons.add(new ImageIcon(getClass().getResource(ICON_128px)).getImage());
		
		return icons;
	}

	/**
	 * Sets the widgets to be used in the frame.
	 */
	private void setWidgets()
	{
		graph = new NameSurferGraph();
		add(graph, BorderLayout.CENTER);

		Container pnlSouth = new JPanel(); // FLOW LAYOUT

		pnlSouth.add(new JLabel("Name"));

		txtName = new JTextField(APPLICATION_WIDTH / 30);
		txtName.setActionCommand(ENTER_PRESSED);
		txtName.addActionListener(this);
		txtName.addCaretListener(this);
		pnlSouth.add(txtName);

		pnlSouth.add(new JLabel("Gender"));

		cbbGender = new JComboBox<>(new String[] { MALE, FEMALE });
		cbbGender.addActionListener(this);
		cbbGender.setActionCommand(COMBO_BOX_CHANGED);
		pnlSouth.add(cbbGender);

		btnGraph = new JButton(GRAPH_BUTTON);
		btnGraph.addActionListener(this);
		btnGraph.setEnabled(false);
		pnlSouth.add(btnGraph);

		btnDelete = new JButton(DELETE_BUTTON);
		btnDelete.addActionListener(this);
		btnDelete.setEnabled(false);
		pnlSouth.add(btnDelete);

		btnClear = new JButton(CLEAR_BUTTON);
		btnClear.addActionListener(this);
		btnClear.setEnabled(false);
		pnlSouth.add(btnClear);

		add(pnlSouth, BorderLayout.SOUTH);
	}

	/**
	 * Updates the graph with the information provided by the user.
	 * @param isInsertion : True if the action is an insertion, false if deletion.
	 */
	private void updateGraph(boolean isInsertion)
	{
		String name = getFormattedName(txtName.getText());
		String gender = cbbGender.getItemAt(cbbGender.getSelectedIndex());
		INameSurferEntry entry = db.findEntry(name, gender);

		if (entry != null)
		{
			if (isInsertion)
			{
				if (!graph.contains(entry))
				{
					graph.addEntry(entry);
				}
				else
				{
					JOptionPane.showMessageDialog(this, entry.toString()
							+ " is already plotted on the graph.");
				}
			}
			else if (graph.contains(entry))
			{
				graph.removeEntry(entry);
			}
			else
			{
				JOptionPane.showMessageDialog(this, entry.toString()
						+ " is not plotted on the graph.");
			}
		}
		else
		{
			JOptionPane.showMessageDialog(this, "No results found.");
		}

		btnClear.setEnabled(!graph.isEmpty());
		txtName.setText("");
		txtName.requestFocusInWindow();
	}

	/**
	 * Acquires the formatted name of the specified input.
	 * 
	 * @param input
	 *            : Input to be formatted.
	 * @return The formatted name.
	 */
	private String getFormattedName(String input)
	{
		StringBuilder builder = new StringBuilder();
		String[] names = space.split(input.trim().toLowerCase());

		for (int i = 0; i < names.length; i++)
		{
			builder.append(Character.toUpperCase(names[i].charAt(0)));
			builder.append(names[i].substring(1));

			if (i < names.length - 1)
			{
				builder.append(' ');
			}
		}

		return builder.toString();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch (e.getActionCommand())
		{
			case COMBO_BOX_CHANGED:
				txtName.requestFocusInWindow();
				break;
			case ENTER_PRESSED:
				if (txtName.getText().isEmpty())
					break;
				// else falls through
			case GRAPH_BUTTON:
				updateGraph(true);
				break;
			case DELETE_BUTTON:
				updateGraph(false);
				break;
			case CLEAR_BUTTON:
				graph.clear();
				txtName.requestFocusInWindow();
				btnClear.setEnabled(false);
				break;
		}
	}

	@Override
	public void caretUpdate(CaretEvent e)
	{
		boolean isNameEmpty = txtName.getText().isEmpty();
		btnGraph.setEnabled(!isNameEmpty);
		btnDelete.setEnabled(!isNameEmpty && !graph.isEmpty());
	}
}