/*  
 *  Implementation of the interface Fractal the calculates
 *  a Julia sets based on some I saw in the Notices of the
 *  American Mathematical Society. The function is
 *
 *  F_c(z) = z^2 - c/z^2 = (1 - c) * z^2
 *
 *  where c is a real number.
 *
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

public class AMSJulia implements Fractal {
    
    private double bound;
    private double c;
    private double re, im;
    private double reSq, imSq, reSqMinusImSq;

    public AMSJulia(double c, double bound) {
        this.c = c;
	this.bound = bound;

        re = im = reSq = imSq = reSqMinusImSq = 0;
    }

    public AMSJulia(double c) {
	this(c, 1000);
    }

    public void setBound(double bound) { this.bound = bound; }
    
    public void setConstant(double c) {
        this.c = c;
    }

    public int initializeIteration(double re, double im) {
        this.re = re;
        this.im = im;
        reSq = re * re;
        imSq = im * im;
	reSqMinusImSq = reSq - imSq;

        return PixelBuffer.rgb(0, 0, 0);
    }

    public void iterationCalculation() {
	double denominator = reSqMinusImSq * reSqMinusImSq + 4 * reSq * imSq;
	double cDividedByDenominator = c / denominator;

        double reTemp = reSqMinusImSq * (1 + cDividedByDenominator);
	double imTemp = 2 * re * im * (1 - cDividedByDenominator);

	re = reTemp;
	im = imTemp;
        reSq = re * re;
        imSq = im * im;
	reSqMinusImSq = reSq - imSq; 
    }

    public boolean iterationStop() {
        return reSq + imSq > bound;
    }
}
