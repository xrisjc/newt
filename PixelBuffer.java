/*  
 *  $Id: PixelBuffer.java,v 1.1 2003/09/25 01:25:02 christophercowan Exp $
 *
 *  This class holds a buffer of pixels. It creates a 
 *  MemoryImageSource object so that an Image object
 *  can be made using the pixel values in the buffer.
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
 *  Version: $Revision: 1.1 $
 *
 *  Revision History:
 *  $Log: PixelBuffer.java,v $
 *  Revision 1.1  2003/09/25 01:25:02  christophercowan
 *  Initial revision
 *
 */
import java.awt.*;
import java.awt.image.*;

public class PixelBuffer {

    // width and height of the pixel buffer
    private int width, height;

    // the actual pixel buffer
    private int[] pixel;

    // MemoryImageSource object used to create and Image object
    private MemoryImageSource mis;

    // constructor takes the width and height of the pixel buffer.
    public PixelBuffer(int width, int height) {
        // initialize the pixel buffer
        this.width = width;
        this.height = height;
        pixel = new int[width * height];

        // initialize MemoryImageSource object
        mis = new MemoryImageSource(width, height, pixel, 0, width);
        mis.newPixels(pixel, ColorModel.getRGBdefault(), 0, width);
        mis.setAnimated(true);        
    }

    // accessors
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public MemoryImageSource getMemoryImageSource() { return mis; }

    // set a pixel to a color
    public void setPixel(int x, int y, int color) {
        pixel[y * width + x] = color;
    }

    // updates MemoryImageSource object if some pixels have changed
    public void update() {
        mis.newPixels(0, 0, width, height);
    }

    // pack color value into and int with the format 0xFFRRGGBB
    public static int rgb(int red, int green, int blue) {
        return (255 << 24) | (red << 16) | (green << 8) | blue;
    }
}
