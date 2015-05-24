/*  
 *  $Id: MandelbrotSet.java,v 1.2 2003/10/15 01:07:02 christophercowan Exp $
 *
 *  Implementation of the interface Fractal the calculates
 *  the Mandelbrot Set.
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
 *  Version: $Revision: 1.2 $
 *
 *  Revision History:
 *  $Log: MandelbrotSet.java,v $
 *  Revision 1.2  2003/10/15 01:07:02  christophercowan
 *  Made bound modifiable.
 *
 *  Revision 1.1  2003/09/26 21:03:39  christophercowan
 *  Initial revision
 *
 */
import java.awt.*;
import java.awt.event.*;

class MandelbrotSet implements Fractal {
    
    private int bound;

    private double cr;
    private double ci;
    private double r, i, rSq, iSq;

    // constructor
    public MandelbrotSet(int bound) {
        cr = ci = r = i = rSq = iSq = 0;
	this.bound = bound;
    }

    // constructor with default bound = 200
    public MandelbrotSet() { this(200); }

    // modifier for bound
    public void setBound(int bound) { this.bound = bound; }

    public int initializeIteration(double x, double y) {
        cr = x;
        ci = y;
        r = i = rSq = iSq = 0;
        return PixelBuffer.rgb(0, 0, 0);
    }

    public void iterationCalculation() {
        i = 2 * r * i - ci;
        r = rSq - iSq - cr;
        rSq = r * r;
        iSq = i * i;
    }        

    public boolean iterationStop() {
        return rSq + iSq > bound;
    }
}
