/*  
 *  $Id: FractalGenerator.java,v 1.4 2003/12/25 04:17:33 chris Exp $
 *
 *  FractalGenerator iterates over the complex plane and generates
 *  a fractal image. The image genertion is performed in a seperate
 *  thread.
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
 *  Version: $Revision: 1.4 $
 *
 *  Revision History:
 *  $Log: FractalGenerator.java,v $
 *  Revision 1.4  2003/12/25 04:17:33  chris
 *  Added some accessor functions.
 *
 *  Revision 1.3  2003/09/30 02:16:43  christophercowan
 *  Added some comments, a little cleaning up of the code.
 *
 *  Revision 1.2  2003/09/26 21:15:42  christophercowan
 *  Fixed some problem with an import statement that broke
 *  the compilation.
 *
 *  Revision 1.1  2003/09/26 21:10:50  christophercowan
 *  Initial revision
 *
 */

import java.awt.*;
import java.awt.event.*;

public class FractalGenerator extends Plane implements Runnable {

    // pixel buffer to create image in and image object
    // to display the image created in the pixel buffer
    private PixelBuffer pixelBuffer;
    private Image image = null;
    
    // reference to some object that implements the fractal interface
    private Fractal fractal;

    // maximum number of iterations to perform at a point
    private int maxItr;


    // constructor
    public FractalGenerator(double minX, double maxX, double minY, double maxY,
			    int width, int height, int maxItr)
    {
        super(minX, maxX, minY, maxY, width, height);
	
        pixelBuffer = new PixelBuffer(width, height);
        image = createImage(pixelBuffer.getMemoryImageSource());
	
	fractal = null;
	
        this.maxItr = maxItr;
        
	// add event handlers
	addMouseListener(new Handler());
    }

    // accessors
    public Fractal getFractal() { return fractal; }
    public int getMaxItr() { return maxItr; }

    // modifiers
    public void setFractal(Fractal fractal) { this.fractal = fractal; }
    public void setMaxItr(int maxItr) { this.maxItr = maxItr; }

    // this method actually generates the fractal
    // this is called when a thread is created using this class
    public void run() {
        if (fractal != null) {

	    // we need to perform an iteration for every pixel
	    // in the pixel buffer
            for (int y = 0; y < getHeight(); y++) {
                
                for (int x = 0; x < getWidth(); x++) {
                    
		    // get the fractal ready by passing it
		    // the point we're iterating on.
                    int color = fractal.initializeIteration(convertX(x),
                                                            convertY(y));
    
		    // start the iterations
                    for (int itr = 0; itr < maxItr; itr++) {
                        fractal.iterationCalculation();
                        
                        if (fractal.iterationStop()) {
                            color = fractal.getColor(itr);
                            break;
                        }
                    }
                    
                    pixelBuffer.setPixel(x, y, color);
                }
    
            }
    
            pixelBuffer.update();
        }
    }
    
    // this is called to redraw the canvas
    // it redraws the image of the fractal
    public void paint(java.awt.Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }

    // this method starts a thread the generates
    // the fractal image
    public void generateImage() {
        Thread t = new Thread(this);
        t.start();
    }

    // this class allows for the mouse to be used
    // in zooming in
    private class Handler extends MouseAdapter {
        public void mouseClicked(MouseEvent event) {

	    // get where on the plane the mouse was clicked
            double X = convertX(event.getX());
            double Y = convertY(event.getY());

	    // the radius of the new viewing box (to be determined)
            double xradius, yradius;

	    // if button 1 was pushed, zoom in
            boolean zoomIn = (event.getModifiers() & InputEvent.BUTTON1_MASK) != 0;
            if (zoomIn) {
                xradius = (getMaxX() - getMinX()) / 4.0;
                yradius = (getMaxY() - getMinY()) / 4.0;
           }
            else { // zoom out
                xradius = (getMaxX() - getMinX()) * 2.0;
                yradius = (getMaxY() - getMinY()) * 2.0;
            }

            setView(X - xradius, X + xradius, Y - yradius, Y + yradius);

            generateImage();
        }
    }
}
