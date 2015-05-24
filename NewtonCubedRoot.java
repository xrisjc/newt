/*  
 *  Implementation of the interface Fractal the calculates
 *  the Newton Basin for the function f(z)=z^3-1.
 *
 *  Copyright (C) 2003-2015 Christopher Cowan
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
}
