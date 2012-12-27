/*  
 *  $Id: Newt.java,v 1.3 2003/12/28 03:48:25 chris Exp $
 *
 *  Implements the UI for the program Newt.
 *
 *  Copyright (C) 2003 Christopher Cowan
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  Version: $Revision: 1.3 $
 *
 *  Revision History:
 *  $Log: Newt.java,v $
 *  Revision 1.3  2003/12/28 03:48:25  chris
 *  Cleaned up the Fractal Generator Settings Dialog box and
 *  added a lot of static final constants for things like labels
 *  for GUI widgets and default numerical values.
 *
 *  Revision 1.2  2003/12/25 04:18:29  chris
 *  Added the Fractal Generator Settings Dialog Box.
 *
 *  Revision 1.1  2003/09/26 20:59:03  christophercowan
 *  Initial revision
 *
 */

import java.awt.*;
import java.awt.event.*;

public class Newt extends Frame {

    // Default settings for the Fractal Generator.
    private static final double DEFAULT_MINX = -3.0;
    private static final double DEFAULT_MAXX =  3.0;
    private static final double DEFAULT_MINY = -3.0;
    private static final double DEFAULT_MAXY =  3.0;

    private static final int DEFAULT_WIDTH          = 500;
    private static final int DEFAULT_HEIGHT         = 500;
    private static final int DEFAULT_MAX_ITERATIONS = 500;

    private FractalGenerator fractalGenerator;

    public Newt() {
        addWindowListener(new WindowEventHandler());
        setTitle("Newt");

        setLayout(new BorderLayout());

	// create a panel with the fractal image contained in it
        Panel p = new Panel(new FlowLayout());
        fractalGenerator = new FractalGenerator(DEFAULT_MINX, DEFAULT_MAXX, 
						DEFAULT_MINY, DEFAULT_MAXY, 
						DEFAULT_WIDTH, DEFAULT_HEIGHT, 
						DEFAULT_MAX_ITERATIONS);
        fractalGenerator.setFractal(new MandelbrotSet());
        p.add(fractalGenerator);
        add("Center", p);


	setMenuBar(new NewtMenuBar());

        pack();
        setVisible(true);

        fractalGenerator.generateImage();
    }

    private class NewtMenuBar extends MenuBar implements ActionListener {

	// Strings for the menu labels.
	private static final String FILE_MENU_TEXT           = "File";
	private static final String FRACTAL_MENU_TEXT        = "Fractal";
	private static final String SETTINGS_MENU_TEXT       = "Settings";
	private static final String QUIT_MENUITEM_TEXT       = "Quit";
	private static final String MANDELBROT_MENUITEM_TEXT = "Mandelbrot Set";
	private static final String JULIA_MENUITEM_TEXT      = "Julia Set";
	private static final String NEWTON_MENUITEM_TEXT     = "Newton Basin";
	private static final String AMSJULIA_MENUITEM_TEXT   = "AMS Julia";
	private static final String FRACTALGENERATORSETTINGS_MENUITEM_TEXT = "Fractal Generator Settings...";
	private Menu fileMenu, fractalMenu, settingsMenu;

	public NewtMenuBar() {
	    fileMenu = new Menu(FILE_MENU_TEXT);
	    fileMenu.add(new MenuItem(QUIT_MENUITEM_TEXT));
	    add(fileMenu);

	    fractalMenu = new Menu(FRACTAL_MENU_TEXT);
	    fractalMenu.add(new MenuItem(MANDELBROT_MENUITEM_TEXT));
	    fractalMenu.add(new MenuItem(JULIA_MENUITEM_TEXT));
	    fractalMenu.add(new MenuItem(NEWTON_MENUITEM_TEXT));
	    fractalMenu.add(new MenuItem(AMSJULIA_MENUITEM_TEXT));
	    add(fractalMenu);

	    settingsMenu = new Menu(SETTINGS_MENU_TEXT);
	    settingsMenu.add(new MenuItem(FRACTALGENERATORSETTINGS_MENUITEM_TEXT));
	    add(settingsMenu);

	    fileMenu.addActionListener(this);
	    fractalMenu.addActionListener(this);
	    settingsMenu.addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {
	    String cmd = event.getActionCommand();

	    if (cmd.equals(QUIT_MENUITEM_TEXT)) {
		dispose();
		System.exit(0);
	    }
	    else if (cmd.equals(MANDELBROT_MENUITEM_TEXT)) {
		fractalGenerator.setFractal(new MandelbrotSet());
	    }
	    else if (cmd.equals(JULIA_MENUITEM_TEXT)) {
		fractalGenerator.setFractal(new JuliaSet(.35, .6));
	    }
	    else if (cmd.equals(NEWTON_MENUITEM_TEXT)) {
		fractalGenerator.setFractal(new NewtonCubedRoot());
	    }
	    else if (cmd.equals(AMSJULIA_MENUITEM_TEXT)) {
		fractalGenerator.setFractal(new AMSJulia(-0.0625));
	    }
	    else {
		new FractalGeneratorSettingsDialog();
		return;  // don't want it to redraw the fractal
	    }

	    // Set the viewing window back to the default and redraw the fractal.
	    fractalGenerator.setView(DEFAULT_MINX, DEFAULT_MAXX, DEFAULT_MINY, DEFAULT_MAXY);
	    fractalGenerator.generateImage();
	}
    }

    // This class implements the "Fractal Generator Settings" dialog box. This allows
    // some basic control over settings such as the viewing window for the fractal
    // and the maximal number of iterations.
    private class FractalGeneratorSettingsDialog extends Dialog implements ActionListener {

	// Some strings for labels of GUI widgets.
	private static final String DIALOG_TITLE      = "Fractal Generator Settings";
	private static final String SET_BUTTON_TEXT   = "Set";
	private static final String CLOSE_BUTTON_TEXT = "Close";
	private static final String MAXITR_LABEL_TEXT = "Max Iterations: ";
	private static final String MINX_LABEL_TEXT   = "Minimum X: ";
	private static final String MAXX_LABEL_TEXT   = "Maximum X: ";
	private static final String MINY_LABEL_TEXT   = "Minimum Y: ";
	private static final String MAXY_LABEL_TEXT   = "Maximum Y: ";

	// Some numerical values for the dialog box.
	private static final int DIALOG_WIDTH = 250;
	private static final int DIALOG_HEIGHT = 175;
	private static final int LABEL_ALIGNMENT = Label.RIGHT;

	// references for textfields in the dialog box
	private TextField tfMaxItr, tfMaxX, tfMinX, tfMaxY, tfMinY;

	public FractalGeneratorSettingsDialog() {
	    super(Newt.this);
	    setTitle(DIALOG_TITLE);
	    setModal(false);
	    setSize(DIALOG_WIDTH, DIALOG_HEIGHT); 
	    setLayout(new BorderLayout());
	    addWindowListener(new DialogEventHandler());

	    // create a panel with <Set> and <Close> buttons and add
	    // it to the "South" of the dialog box.
	    Panel p = new Panel(new FlowLayout());
	    Button b = new Button(SET_BUTTON_TEXT);
	    b.addActionListener(this);
	    p.add(b);

	    b = new Button(CLOSE_BUTTON_TEXT);
	    b.addActionListener(this);
	    p.add(b);
	    add("South", p);
	  
	    // create a panel for the text fields with a grid layout
	    p = new Panel(new GridLayout(5, 2));

	    p.add(new Label(MAXITR_LABEL_TEXT, LABEL_ALIGNMENT));
	    tfMaxItr = new TextField(fractalGenerator.getMaxItr()+"");
	    tfMaxItr.addActionListener(this);
	    p.add(tfMaxItr);
	    
	    p.add(new Label(MINX_LABEL_TEXT, LABEL_ALIGNMENT));
	    tfMinX = new TextField(fractalGenerator.getMinX()+"");
	    tfMinX.addActionListener(this);
	    p.add(tfMinX);

	    p.add(new Label(MAXX_LABEL_TEXT, LABEL_ALIGNMENT));
	    tfMaxX = new TextField(fractalGenerator.getMaxX()+"");
	    tfMaxX.addActionListener(this);
	    p.add(tfMaxX);

	    p.add(new Label(MINY_LABEL_TEXT, LABEL_ALIGNMENT));
	    tfMinY = new TextField(fractalGenerator.getMinY()+"");
	    tfMinY.addActionListener(this);
	    p.add(tfMinY);

	    p.add(new Label(MAXY_LABEL_TEXT, LABEL_ALIGNMENT));
	    tfMaxY = new TextField(fractalGenerator.getMaxY()+"");
	    tfMaxY.addActionListener(this);
	    p.add(tfMaxY);

	    add("Center", p);

	    // Everything's ready, show the dialog box.
	    setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
	    String cmd = event.getActionCommand();
	    if (cmd.equals("Close")) {
		dispose();
	    }
	    else {
		try {
		    String strMaxItr = tfMaxItr.getText();
		    fractalGenerator.setMaxItr(Integer.parseInt(strMaxItr));
		    double minX = (new Double(tfMinX.getText())).doubleValue();
		    double maxX = (new Double(tfMaxX.getText())).doubleValue();
		    double minY = (new Double(tfMinY.getText())).doubleValue();
		    double maxY = (new Double(tfMaxY.getText())).doubleValue();
		    fractalGenerator.setView(minX, maxX, minY, maxY);
		    fractalGenerator.generateImage();
		}
		catch (NumberFormatException exception) {
		    // Need to add some code to create a dialog box so I 
		    // can scold the user about his/her data entry.
		}
	    }
	}    

	// Class to handle window events for the dialog box.
	private class DialogEventHandler extends WindowAdapter {

	    // When the window is asked to close make the dialog
	    // box "go away" with dispose()
	    public void windowClosing(WindowEvent event) {
		dispose();
	    }
	}
    }
    
    // Class to handle window events for the main window.
    private class WindowEventHandler extends WindowAdapter {

	// When the window is asked to close, make the
	// main window "go away" with dispose and
	// then end the program
        public void windowClosing(WindowEvent event) {
            dispose();
            System.exit(0);            
        }
    }

    // Program entry.
    public static void main (String[] args) {
	// Start a new Newt window.
        new Newt();
    }
}
