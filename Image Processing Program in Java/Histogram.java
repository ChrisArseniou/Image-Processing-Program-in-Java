
package ce326.hw2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Histogram {
    int [] histogram;
    short [] histogramBalanced;
    long numberOfPixels;
       
    public Histogram(YUVImage img){
        
        int i,j,numberOfPixelsWithSameY=0;
        short pixel;
        
        numberOfPixels = (img.width * img.height);

        histogram = new int[236];
        for(pixel=0;pixel<236;pixel++){
            numberOfPixelsWithSameY = 0;
            for(i=0;i<img.height;i++){
                for(j=0;j<img.width;j++){
                    if(img.image[i][j].getY()== pixel  ){
                        numberOfPixelsWithSameY++;
                    }
                }
            }
            histogram[pixel] = numberOfPixelsWithSameY;
        }   
    }
        
    @Override
    public String toString(){
       
	int i,j,value,thousands,hundreds,tens,units;
        //redirecting the output   
        ByteArrayOutputStream histogramToString = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(histogramToString);
        PrintStream old = System.out;
        System.setOut(ps);
        
    
        for(i=0;i<236;i++){
                        
            System.out.format('\n' + "%3d" + '.',i);
            System.out.format('(' + "%4d" + ')' + "	", histogram[i]);
            
            value = histogram[i];
            
            units = value % 10;
            tens = ((value / 10) % 10);
            hundreds = ((value / 100) % 10);
            thousands = (value / 1000);
               
            for(j=0;j<thousands;j++){
                System.out.print('#');
            }
           
            for(j=0;j<hundreds;j++){
                System.out.print('$');
            }
           
            for(j=0;j<tens;j++){
                System.out.print('@');
            }
            
            for(j=0;j<units;j++){
                System.out.print('*');
            }
        }
        
        System.out.print('\n');

        System.setOut(old);
        return histogramToString.toString();
    }
    
    public void toFile(File file) throws FileNotFoundException, IOException{
        
        String histogramString = "";

       histogramString = toString();
       PrintStream old = System.out;
       FileOutputStream histogramToFile = new FileOutputStream(file);
       PrintStream ps = new PrintStream(histogramToFile);
       System.setOut(ps);
       System.out.println(histogramString);
       System.out.flush();
       System.setOut(old);
    }
    
    public void equalize(){
        double [] probability;
        double [] cumulativeProbability;
               
        double prob=0;
        int i,j,maxY;
        
        probability = new double[236];
        for(i=0;i<236;i++){
            probability[i] = ((double)histogram[i] / (double)numberOfPixels);
        }
	 
        cumulativeProbability = new double[236];
        
        for(i=0;i<236;i++){
            j = i;
            prob = probability[i];
            while(j>0){
                j--;
                prob = prob + probability[j];
            }
            cumulativeProbability[i] = prob;
        }
	       
        maxY = 235;
        
	histogramBalanced = new short[236];
	
        for(i=0;i<236;i++){
             histogramBalanced[i] = (short)(cumulativeProbability[i] * maxY);
        }   
    }
    
    public short getEqualizedLuminocity(int luminocity){
        return (histogramBalanced[luminocity]);
    }
    
}
