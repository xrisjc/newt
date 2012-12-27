/*  
 *  $Id: Fractal.java,v 1.2 2003/12/26 03:18:36 chris Exp $
 *
 *  This interface is used by FractalGenerator to make the generation
 *  of fractals (on the complex plane) more general. New fractals can
 *  be drawn by implementing this interface.
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
 *  $Log: Fractal.java,v $
 *  Revision 1.2  2003/12/26 03:18:36  chris
 *  Removed "abstract" from the method declarations.
 *
 *  Revision 1.1  2003/09/26 20:56:09  christophercowan
 *  Initial revision
 *
 */

public interface Fractal {

    // This is called before an iteration is about to begin.
    // The coordinates where on the complex plane the iteration
    // is taking place are passed to it. The default color is
    // then passed back to the caller.
    public int initializeIteration(double re, double im);

    // This is called to preform an iteration caclulation.
    public void iterationCalculation();

    // Returns true if the iteration is to stop.
    public boolean iterationStop();

    // If the iteration stops before reaching the maximum iteration
    // value, then this is called to get the color for the point.
    public int getColor(int itr);
}
