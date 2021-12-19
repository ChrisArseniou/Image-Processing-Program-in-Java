
package ce326.hw2;

public class RGBImage implements ce326.hw2.Image {
    int colordepth,width,height;
    public static final int MAX_COLORDEPTH = 255;
    RGBPixel [][] array;
    
    public RGBImage(){
    
    }
    
    public RGBImage(int width, int height, int colordepth){
        
        this.width = width;
        this.height = height;
        this.colordepth = colordepth;
        
        array = new RGBPixel[height][width];   
    }
    
    public RGBImage(RGBImage copyImg){
        this.width = copyImg.width;
        this.height = copyImg.height;
        this.colordepth = copyImg.colordepth;
        
        array = new RGBPixel[height][width];
        
        array = copyImg.array;  
    }
    
    public RGBImage(YUVImage YUVImg){
        colordepth = 255;
        int i,j;
        
        this.width = YUVImg.width;
        this.height = YUVImg.height;
        
        array = new RGBPixel[height][width];
         
        for(i=0;i<this.height;i++){
            for(j=0;j<this.width;j++){
               array[i][j] = new RGBPixel(YUVImg.image[i][j]);
            }
        }
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int ColorDepth(){
        return colordepth;
    }
    
    public RGBPixel getPixel(int row, int col){
        return array[row][col];
    
    }
    
    public void setPixel(int row, int col, RGBPixel pixel){
        array[row][col] = pixel;
    }

    @Override
    public void grayscale() {
        int i,j;
        short gray;

        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
                gray = (short)(array[i][j].getRed() * 0.3 + array[i][j].getGreen()*0.59 + array[i][j].getBlue()*0.11);
                array[i][j].setRGB(gray, gray,gray);
            }
        }
    }

    @Override
    public void doublesize() {
        int i,j;
        RGBPixel [][] newArray = new RGBPixel[2*height][2*width];
        
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
                newArray[2*i][2*j] = array[i][j];
                newArray[2*i + 1][2*j] = array[i][j];
                newArray[2*i][2*j + 1] = array[i][j];
                newArray[2*i + 1][2*j + 1] = array[i][j]; 
            }
        }
        width = 2*width;
        height = 2*height; 
        array = newArray; 
    }
    
    @Override
    public void halfsize() {
        
        int i,j;
        RGBPixel [][] newArray;
        short red,green,blue;
        
        if(width/2 <=  0 || height/2 <= 0){
            System.out.println("You cannot decrease the size of this picture");
            return;
        }
        
        width = width/2;
        height = height/2;
        
        newArray = array;
        
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
                red = (short)((array[2*i][2*j].getRed() + array[2*i+1][2*j].getRed() +  array[2*i][2*j+1].getRed() + array[2*i+1][2*j+1].getRed())/4);
                
                green = (short)((array[2*i][2*j].getGreen() + array[2*i+1][2*j].getGreen() +  array[2*i][2*j+1].getGreen() + array[2*i+1][2*j+1].getGreen())/4);
                  
                blue = (short)((array[2*i][2*j].getBlue() + array[2*i+1][2*j].getBlue() +  array[2*i][2*j+1].getBlue() + array[2*i+1][2*j+1].getBlue())/4);

                array[i][j].setRGB(red, green,blue);
            }
        }
        array = newArray;  
    }

    @Override
    public void rotateClockwise() {
        int i,j,newHeight;
        RGBPixel [][] newArray = new RGBPixel[width][height];

        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
                newArray[j][height-1-i] = array[i][j];
            }
        }
        newHeight = width;
        width = height;
        height = newHeight;
        array = newArray;  
    }   
}
