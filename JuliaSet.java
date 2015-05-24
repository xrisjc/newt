/*  
 *  Implementation of the interface Fractal the calculates
 *  a Julia set.
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

public class JuliaSet implements Fractal {
    
    private int bound;
    private double cre, cim;
    private double re, im;
    private double reSq, imSq;

    public JuliaSet(double cre, double cim, int bound) {
        this.cre = cre;
        this.cim = cim;
	this.bound = bound;

        re = im = reSq = imSq = 0;
    }

    public JuliaSet(double cre, double cim) {
	this(cre, cim, 200);
    }

    public void setBound(int bound) { this.bound = bound; }
    
    public void setConstant(double cre, double cim) {
        this.cre = cre;
        this.cim = cim;
    }

    public int initializeIteration(double re, double im) {
        this.re = re;
        this.im = im;
        reSq = re * re;
        imSq = im * im;

        return PixelBuffer.rgb(0, 0, 0);
    }

    public void iterationCalculation() {
        im = 2 * re * im - cim;
        re = reSq - imSq - cre;
        reSq = re * re;
        imSq = im * im;
    }

    public boolean iterationStop() {
        return reSq + imSq > bound;
    }
}
