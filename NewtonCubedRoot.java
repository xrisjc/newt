/*  
 *  $Id: NewtonCubedRoot.java,v 1.3 2003/12/28 03:46:13 chris Exp $
 *
 *  Implementation of the interface Fractal the calculates
 *  the Newton Basin for the function f(z)=z^3-1.
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
 *  $Log: NewtonCubedRoot.java,v $
 *  Revision 1.3  2003/12/28 03:46:13  chris
 *  Changed the default iteration color to black and added
 *  the DEFAULT_TOLERANCE constant.
 *
 *  Revision 1.2  2003/10/15 00:52:42  christophercowan
 *  Made tolerance modifiable and added some comments.
 *
 *  Revision 1.1  2003/09/26 21:07:28  christophercowan
 *  Initial revision
 *
 */
public class NewtonCubedRoot implements Fractal {
    
    // Default tolerance used for the default constructor.
    private static final double DEFAULT_TOLERANCE = 1e-3;

    // Tolerance used to determine convergence.
    private double tolerance;

    // Currernt value in the sequence.
    private double re, im;

    // Keep the old values to test for convergence.
    private double reOld, imOld;
   
    // constructor
    public NewtonCubedRoot(double tolerance) {
        re = im = reOld = imOld = 0;
	this.tolerance = tolerance;
    }

    // constructor with default tolerance
    public NewtonCubedRoot() {
	this(DEFAULT_TOLERANCE);
    }

    // modifier for tolerance
    public void setTolerance(double tolerance) {
	this.tolerance = tolerance;
    }

    public int initializeIteration(double x, double y) {
        re = x;
        im = y;
        return PixelBuffer.rgb(0, 0, 0);
    }

    public void iterationCalculation() {
        double reSq = re * re;
        double imSq = im * im;
        double modulusSq = reSq + imSq;
        double modulusSqSq = modulusSq * modulusSq;
        
	// The computation is an optimized form of:
	//    
	//                f(zn)
	//  z    = zn  - -------
	//   n+1          f'(zn)
	//                    
	
        double reNew = 2 * re / 3 + (reSq - imSq) / (3 * modulusSqSq);
        double imNew = 2 * (im - (re * im) / modulusSqSq) / 3;

	// Save the old values.
        reOld = re;
        imOld = im;

	// update
        re = reNew;
        im = imNew;
    }

    public boolean iterationStop() {
        double x = reOld - re;
        double y = imOld - im;

        return x * x + y * y < tolerance;
    }

    public int getColor(int itr) {
        double angle = 0.2 * itr;

        int red = 100;

        int green =(int) (64 * (Math.cos(angle) + 1)) + 50;

        int blue = (int) (64 * (Math.sin(angle) + 1)) + 50;

        return PixelBuffer.rgb(red, green, blue);
    }
    
}
