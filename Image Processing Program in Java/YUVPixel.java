
package ce326.hw2;

public class YUVPixel {
    private short pixelY=0,pixelU=0,pixelV=0;
    
    public YUVPixel(short Y, short U, short V){
        
        pixelY = Y;
        
        pixelU = U;
        
        pixelV = V;
        
    }
    
    public YUVPixel(YUVPixel pixel){
        
        pixelY = pixel.getY();
        pixelU = pixel.getU();
        pixelV = pixel.getV();
    }
    
    public YUVPixel(RGBPixel pixel){
        
        pixelY = (short)(((66*pixel.getRed() + 129*pixel.getGreen() + 25*pixel.getBlue() + 128)>>8) + 16);
        pixelU = (short)(((-38*pixel.getRed() - 74*pixel.getGreen() + 112*pixel.getBlue() + 128)>>8) + 128);
        pixelV = (short)(((112*pixel.getRed() - 94*pixel.getGreen() - 18*pixel.getBlue() + 128)>>8) + 128); 
       
    }
    
    public short getY(){
       return pixelY;
    }
    
    public short getU(){
       return pixelU;
    }
    
    public short getV(){
       return pixelV;
    }
    
    public void setY(short Y){  
        pixelY = Y;
    }
    
    public void setU(short U){   
        pixelU = U;
    }
    
     public void setV(short V){   
        pixelV = V;
    }
     
    @Override
    public String toString(){ 
        String YUVString = (pixelY) + " "  + (pixelU) + " " + (pixelV); 
        return YUVString;   
    } 
}
