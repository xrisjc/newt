/*  
 *  $Id: Plane.java,v 1.2 2003/09/25 01:06:34 christophercowan Exp $
 *
 *  This class extends the java.awt.Canvas class. It lets the user
 *  specify coordinates in Cartesian coordinates rather than in
 *  pixel coordinates.
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
 *  Version: $Revision: 1.2 $$
 *
 *  Revision History:
 *  $Log: Plane.java,v $
 *  Revision 1.2  2003/09/25 01:06:34  christophercowan
 *  Fixed some problems with RCS Keywords in code.
 *
 *  Revision 1.1  2003/09/25 01:03:00  christophercowan
 *  Initial revision
 *
 */
import java.awt.*;

public class Plane extends Canvas {

    // the coordinates of the viewing rectangle
    private double minX, maxX, minY, maxY;

    // the width and height of a pixel in the cartesian plane
    private double pixelWidth, pixelHeight;

    // constructor takes coordinates of viewing rectangle and the
    // width and height of the canvas in pixels
    public Plane(double minX, double maxX, double minY, double maxY, int width, int height) {
        super.setSize(width, height);
	setView(minX, maxX, minY, maxY);
    }
 
    // accessors
    public double getMinX() { return minX; }
    public double getMaxX() { return maxX; }
    public double getMinY() { return minY; }    
    public double getMaxY() { return maxY; }
    public double getPixelWidth() { return pixelWidth; }
    public double getPixelHeight() { return pixelHeight; }

    
    // override the setSize methods so we can update the pixels
    // size varibles

    public void setSize(int width, int height) {
        super.setSize(width, height);
        calculatePixelSize();
    }

    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }

    // set the coordinates of the viewing window
    public void setView(double minX, double maxX, double minY, double maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;

        calculatePixelSize();
    }

    
    // convert a horizontal pixel coordiate to it's equivalent on the plane
    public double convertX(int x) {
        return minX + x * pixelWidth;
    }

    
    // convert a vertical pixel coordinate to its equivalent on the plane
    public double convertY(int y) {
        // The getHeight() - y is necessary because the canvas
        // starts from the top while the Cartesian plane starts
        // from the bottom.
        return minY + (getHeight() - y) * pixelHeight;
    }

    // Calculates the horizontal and vertical size of a pixel.
    private void calculatePixelSize() {
        pixelWidth = (maxX - minX) / getWidth();
        pixelHeight = (maxY - minY) / getHeight();
    }
}
