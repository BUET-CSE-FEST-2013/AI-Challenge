 
import java.awt.*;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
 

import java.awt.geom.*;






public class ImageToRawData {

	final float redModifier = (float)0.298;
	final float greenModifier = (float)0.587;
	final float blueModifier = (float)0.114;
 	public  BufferedImage  image,imageGray,imageBW,resizedImage;
 	public int height,width;
 	public int imageArray[][];

    public ImageToRawData(String imageName) {
    	
    	try {
	  	File input = new File(imageName);
	      	image = ImageIO.read(input);
	      	height=image.getHeight();
	      	width=image.getWidth();
		imageArray=new int [width][height]; 	
                
                imageBW=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY);

                //resizedImage=resizeImage(image);
                imageBW=imageBW=(new RGBToBlackAndWhite(image)).getBlackAndWhiteImage();
                createArrayFromBWImage(imageBW);
	      	
                
                
	      	
	      	
	    } catch (IOException ie) {
	      System.out.println("Error:"+ie.getMessage());
	    }
    }
    
    public static void main(String args[]){
    	new ImageToRawData("4.jpg");
    }
    
    
   
    
    
    
    		
		
		
	private void createArrayFromBWImage( BufferedImage in){
	
		 
	  	
	  	for(int y=0;y<in.getHeight();y++){
	  		for(int x=0;x<in.getWidth();x++){
	  			if((in.getRGB(x,y)&255)>0){//white pixel
	  				imageArray[x][y]=0;
	  			}
	  				
	  			else{
	  				 imageArray[x][y]=1;
	  			}
	  			
	  		}	
			 
  		}
	}
    	
	
	public int[][] getBinaryArry(){
		return imageArray;
		
	}
        public BufferedImage getInputBWImage(){
            return imageBW;
        }
        public void showBinaryImage(){
            for(int y=0;y<imageArray.length;y++){
	  		for(int x=0;x<imageArray[0].length;x++){
	  			if(imageArray[x][y]==0){
	  				
	  				System.out.print(". ");
	  				
	  			}
	  				
	  			else{
	  				System.out.print("* ");
	  				
	  				
	  			}
	  			
	  		}	
			System.out.println();
	  
  		}
        }
	
	
	
	
	
   
    
}