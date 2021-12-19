package ce326.hw2;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PPMImage extends RGBImage {
        String type;

    public PPMImage(java.io.File file) throws UnsupportedFileFormatException,FileNotFoundException {
        super();
        
        int i,j;
        short red, blue, green;
        
        try{
            if(!file.exists()){
                throw new FileNotFoundException();
            }
            Scanner sc = new Scanner(file);
            type = sc.next();
           
            
            if(!type.equals("P3")){
                throw new UnsupportedFileFormatException("Format is not right!");    
            }
            
           width = sc.nextInt();
           if(width <= 0 ){
               throw new UnsupportedFileFormatException("Format is not right!"); 
           }
    
           height = sc.nextInt();
            
           if(height <= 0 ){
                throw new UnsupportedFileFormatException("Format is not right!"); 
           }
             
           colordepth = sc.nextInt();

            array = new RGBPixel [height] [width];
           
            for(i=0;i<height;i++){
                for(j=0;j<width;j++){
                       
                    if(sc.hasNextInt()){ 
                        red = (short)sc.nextInt();
                    }
                    else {
                        throw new UnsupportedFileFormatException("Format is not right!");
                    }

                    if(sc.hasNextInt()){
                        green = (short)sc.nextInt();
                    }
                    else {
                         throw new UnsupportedFileFormatException("Format is not right!");
                    }

                    if(sc.hasNextInt()){
                        blue = (short)sc.nextInt();
                    }
                    else {
                        throw new UnsupportedFileFormatException("Format is not right!");
                    }

                    array[i][j] = new RGBPixel(red,green,blue);
                        
                }
            }        
        }catch(FileNotFoundException | NoSuchElementException x) {
            throw new FileNotFoundException(); 
        }
    }

    public PPMImage(RGBImage img){
        super(img);
    }

    public PPMImage(YUVImage img){
        super(img);
        type = "P3";
        colordepth = 255;
    }

        @Override
    public String toString(){
        int i,j;
        String ppmString = "", intermediateString="";
        type = "P3";
      
        ppmString = ppmString + type + '\n';
        ppmString = ppmString + width + " " + height + '\n';
        ppmString = ppmString + colordepth + '\n';

        for(i=0;i<(height);i++){
            for(j=0;j<(width);j++){
                intermediateString = intermediateString + (array[i][j].toString()) + '\n';
            }
            ppmString = ppmString + intermediateString;
            intermediateString = "";
          
        }

        return ppmString;
    }

    public void toFile(java.io.File file){
        String ppmToFile;
        int ppmLength;
        byte []byteArray;
        
        ppmToFile = toString();
        ppmLength = ppmToFile.length();

        byteArray = ppmToFile.getBytes();
               
        try(FileOutputStream out = new FileOutputStream(file)){
            out.write(byteArray,0,ppmLength);
        }catch (IOException x) {
               System.out.println("cannot write to file");
        }                
    }

}
