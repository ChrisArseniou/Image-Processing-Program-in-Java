
package ce326.hw2;

public class RGBPixel {
    private int color = 0x0000;
    int redMask = 0b00000000111111110000000000000000;
    int greenMask = 0b00000000000000001111111100000000;
    int blueMask = 0b00000000000000000000000011111111;
     
    public RGBPixel(short red, short green, short blue){
       
        color = 0;

        color = color + red;
        color = color << 8;
        
        color = color + green;
        color = color << 8;
        
        color = color + blue;
    }
    
    public RGBPixel(RGBPixel pixel){
        color = pixel.color;
    }
    
    public RGBPixel(YUVPixel pixel){
        short C,D,E;
        short pixelRed,pixelGreen,pixelBlue;
        
        C = (short)(pixel.getY() - 16);
        D = (short)(pixel.getU() - 128);
        E = (short)(pixel.getV() - 128);
        
        pixelRed = clip((298*C + 409*E + 128)>>8); 
        pixelGreen = clip((298*C - 100*D - 208*E + 128)>>8);
        pixelBlue = clip((298*C + 516*D + 128)>>8);  
        
        color = 0;
        
        color = color + pixelRed;
        color = color << 8;
        
        color = color + pixelGreen;
        color = color << 8;
        
        color = color + pixelBlue;
    }
    
    public short getRed(){
       short red = (short)((color&redMask)>>16);
       return red;
    }
    
    
    public short getGreen(){
       short green = (short)((color&greenMask)>>8); 
       return green;
    }
    
    public short getBlue(){
       short blue = (short)((color&blueMask));
       return blue;
    }
    
    public void setRed(short red){
        short pixelGreen,pixelBlue;
        
        pixelGreen = (short)((color&greenMask)>>8);
        pixelBlue = (short)((color&blueMask));
               
        color = 0;

        color = color + red;
        color = color << 8;
        
        color = color + ((color&greenMask)>>8);
        color = color << 8;
        
        color = color + ((color&blueMask));
    }
    
    public void setGreen(short green){
        short pixelRed,pixelBlue;
        
        pixelRed = (short)((color&redMask)>>16);
        pixelBlue = (short)((color&blueMask));
        
       color = 0;
          
        color = color + pixelRed;
        color = color << 8;
        
        color = color + green;
        color = color << 8;
        
        color = color + pixelBlue;    
    }
    
    public void setBlue(short blue){
        short pixelRed, pixelGreen;
        
        pixelRed = (short)((color&redMask)>>16);
        pixelGreen = (short)((color&greenMask)>>8);
  
        color = 0;
         
        color = color + pixelRed;
        color = color << 8;
        
        color = color + pixelGreen;
        color = color << 8;
        
        color = color + blue;
    
    }
    
    public int getRGB(){
        return color;
    }
    
    public void setRGB(int value){
        color = value;       
    }
    
    final void setRGB(short red, short green, short blue){
        
        color = 0;

        color = color + red;
        color = color << 8;
        
        color = color + green;
        color = color << 8;
        
        color = color + blue; 
    }
    
    @Override
    public String toString(){
        String pixelString = ((color&redMask)>>16) + " "  + ((color&greenMask)>>8) + " " + ((color&blueMask)); 
        return pixelString;   
    } 

    private short clip(int i) {
        
        if(i>255){
            return 255;
        }
        else if(i<0){
            return 0;
        }
        else {
            return (short)(i);
        }
        
    }
}
