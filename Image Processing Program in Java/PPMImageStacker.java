
package ce326.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PPMImageStacker {
     List<PPMImage> stackList = new ArrayList<>();
     PPMImage ppm = null;
     int width,height;
     
    public PPMImageStacker(java.io.File dir) throws UnsupportedFileFormatException,FileNotFoundException{
        PPMImage ppmFormat = null;
        
        if(!dir.exists()){
            throw new FileNotFoundException("[ERROR] Directory " + dir.getName() + " does not exist!");
        }
        
        if(!dir.isDirectory()){
            throw new FileNotFoundException("[ERROR] " + dir.getName() + " is not a directory!");
        }

        File files[] = dir.listFiles();
        for(File f : files){
	   //o kwdikas sta sxolia mporei na xrisimopoieithei gia na prosperasoume ena arxeio ston kwdika pou den einai tou typou pou theloume (eixe xrisimopoieithei arxika gia
	   //na prosperasoume to arxeio .directory pou uphrxe mesa sto fakelo sto autolab)
           //  try{
            ppmFormat = new PPMImage(f);
           //   }catch (UnsupportedFileFormatException | FileNotFoundException | MissingFormatArgumentException x){
           //         continue;
           //    }
            stackList.add(ppmFormat);
            width = ppmFormat.width;
            height = ppmFormat.height;
        }  
        ppm = new PPMImage(new RGBImage(width,height,255));   
    }
    
    public void stack(){
        int files,i,j,red=0,green=0,blue=0;
        PPMImage current;
        
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
                for(files=0;files<stackList.size();files++){
                    current = stackList.get(files);
                    red = red + current.array[i][j].getRed();
                    green = green + current.array[i][j].getGreen();
                    blue = blue + current.array[i][j].getBlue();
                }
                red = (red / (stackList.size()));
                green = (green / (stackList.size()));
                blue = (blue / (stackList.size()));
                ppm.array[i][j] = new RGBPixel((short)red, (short)green, (short)blue);
                red = green = blue = 0;       
            }
        }
    }
    
    public PPMImage getStackedImage(){
        ppm.type = "P3";
        return ppm;
    }   
}
