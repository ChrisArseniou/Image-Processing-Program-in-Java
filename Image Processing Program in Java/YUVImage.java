
package ce326.hw2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class YUVImage {
    int width,height;
    YUVPixel [][] image= null;
    String type;
    
    public YUVImage(int width, int height){
        
        int i,j;
        
        this.width = width;
        this.height = height;
        
        image = new YUVPixel[height][width];
        
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
               image[i][j] = new YUVPixel((short)16,(short)128,(short)128); 
            }
        }  
    }
    
    public YUVImage(YUVImage copyImg){
   
        this.width = copyImg.width;
        this.height = copyImg.height;
        
        image = new YUVPixel[height][width];
        
	image = copyImg.image;
    }
    
    public YUVImage(RGBImage RGBImg){
        
        int i,j;

        this.width = RGBImg.width;
        this.height = RGBImg.height;
        
        image = new YUVPixel[height][width];
         
        for(i=0;i<this.height;i++){
           for(j=0;j<this.width;j++){
              image[i][j] = new YUVPixel(RGBImg.array[i][j]);
           }
        }
    }
    
    public YUVImage(java.io.File file) throws UnsupportedFileFormatException, FileNotFoundException{
        
        int i,j;
        short Y,U,V;
        
         try{
            if(!file.exists()){
                throw new FileNotFoundException();
            } 
             
            Scanner sc = new Scanner(file);
            type = sc.next();
  
            if(!type.equals("YUV3")){
                throw new UnsupportedFileFormatException("Format is not right!");    
            }
            
            if(sc.hasNextInt()){
                width = sc.nextInt();
            }
            else {
                throw new UnsupportedFileFormatException("Format is not right!");
            }
            
            if(width <= 0 ){
               throw new UnsupportedFileFormatException("Format is not right!"); 
           }
           
            if(sc.hasNextInt()){
                height = sc.nextInt();  
            }
            else {
                throw new UnsupportedFileFormatException("Format is not right!");
            }
            
            if(height <= 0 ){
               throw new UnsupportedFileFormatException("Format is not right!"); 
           }
            image = new YUVPixel[height][width];

            for(i=0;i<height;i++){
                for(j=0;j<width;j++){
                        
                    if(sc.hasNextInt()){
                        Y = (short)sc.nextInt();
                    }
                    else {
                        throw new UnsupportedFileFormatException("Format is not right!");
                    }
                      
                    if(sc.hasNextInt()){
                         U = (short)sc.nextInt();
                    }
                    else {
                        throw new UnsupportedFileFormatException("Format is not right!");
                    }
                        
                    if(sc.hasNextInt()){
                        V = (short)sc.nextInt();
                    }
                    else {
                        throw new UnsupportedFileFormatException("Format is not right!");
                    }
                        
                    image[i][j] = new YUVPixel(Y,U,V);
                        
                }
            }   
        }catch(FileNotFoundException | NoSuchElementException x) {
             throw new FileNotFoundException();
        }    
    }
    
    @Override
    public String toString(){
        int i,j;
        String YUVString = "",intermediateString="";
        type = "YUV3";
        
        YUVString = YUVString + (type + '\n');
        YUVString = YUVString + (width + " " + height + '\n');
        
        for(i=0;i<(height);i++){
            for(j=0;j<(width);j++){
                intermediateString = intermediateString + (image[i][j].toString()) + '\n';
            }
            YUVString = YUVString + intermediateString;
            intermediateString = "";
        }
        return YUVString; 
    }
    
    public void toFile(java.io.File file){
        String YUVToFile;
        int YUVLength;
        byte []byteArray;
        
        YUVToFile = toString();
        YUVLength = YUVToFile.length();
        byteArray = YUVToFile.getBytes();
        
        try(FileOutputStream out = new FileOutputStream(file)){
            out.write(byteArray,0,YUVLength);
        }catch (IOException x) {
                 System.out.println("cannot write to file"); 
        } 
    }
    
    public void equalize(){
        int i,j;
        short luminocity,equalizedLuminocity;
     
       YUVImage newImg = new YUVImage(width,height);
       
       newImg.image = this.image;
       
       Histogram histogramOfNewImg = new Histogram(newImg);
       
       histogramOfNewImg.equalize();
       
       for(i=0;i<height;i++){
           for(j=0;j<width;j++){
               luminocity = newImg.image[i][j].getY();
               equalizedLuminocity = histogramOfNewImg.getEqualizedLuminocity(luminocity);
               newImg.image[i][j].setY(equalizedLuminocity);
           }
       }
       this.image = newImg.image;
       
       /*histogram to file example
       File file = new File("YUVImageToFile.txt");
       try {
           histogramOfNewImg.toFile(file);
       }catch (IOException x) {
                throw new FileNotFoundException(); 
       } 
       */
    }  
}
