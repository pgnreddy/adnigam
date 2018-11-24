/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.utils;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author santosh
 */
public class ImageUtils {

    public static Map<String, String> processImage(String contentType, String fileName, long fileSize, InputStream inputStream, Map<String, Dimension> dimensions) {
        Map<String, String> imagesMap = new HashMap<String, String>();
        try {
            String assetsFolder = SettingsUtil.getSettings("IMAGES_PHYSICAL_PATH"); // ConfigurationsUtil.getProperty("ASSETS_DIRECTORY");
            String fileseperator = System.getProperty("file.separator");
            if (!assetsFolder.endsWith(fileseperator)) {
                assetsFolder = assetsFolder + fileseperator;
            }
            String randomStr = getRandomString();
            String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            fileName = randomStr + dateStr + ".jpg";
            String fullpath = assetsFolder + fileName;
            IOUtils.copy(inputStream, new FileOutputStream(fullpath));
            imagesMap.put("o", fileName);

            if (dimensions != null) {
                for (String key : dimensions.keySet()) {
                    Dimension dim = dimensions.get(key);
                    String outputFileName = key + "_" + fileName;
                    String outputFilepath = assetsFolder + outputFileName;
                    transcodeImage(fullpath, outputFilepath, dim.getWidth(), dim.getHeight());
                    imagesMap.put(key, outputFileName);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagesMap;
    }

    public static void removeImage(String categories_image, Map<String, Dimension> dimensions) {
		if(StringUtils.isBlank(categories_image)){
			return;
		}
		
        List<String> list = new ArrayList<String>();
        list.add(categories_image);
        removeImages(list, dimensions);
    }

    public static void removeImages(List<String> categoriesImagesList, Map<String, Dimension> dimensions) {
        
        //presently we are not deleting old images
        if(true){
            return;
        }
        
        try {
            String assetsFolder = SettingsUtil.getSettings("IMAGES_PHYSICAL_PATH"); //ConfigurationsUtil.getProperty("ASSETS_DIRECTORY");
            String fileseperator = System.getProperty("file.separator");
            if (!assetsFolder.endsWith(fileseperator)) {
                assetsFolder = assetsFolder + fileseperator;
            }

            for (String image : categoriesImagesList) {
			
			   if(StringUtils.isNotBlank(image)){
					if(image.startsWith("m_")){
						image = image.replace("m_","");
					}else if(image.startsWith("l_")){
						image = image.replace("l_","");
					}
					
					for (String key : dimensions.keySet()) {
						Dimension dim = dimensions.get(key);
						String outputFileName = key + "_" + image;
						String outputFilepath = assetsFolder + outputFileName;
                                                File f = new File(outputFilepath);
                                                if(f!=null && f.exists()&&f.isFile()){
                                                    FileUtils.deleteQuietly(f);
                                                }
						
					}
                                        
                                        File f = new File(assetsFolder+image);
                                                if(f!=null && f.exists()&& f.isFile()){
                                                    FileUtils.deleteQuietly(f);
                                                }
					//FileUtils.deleteQuietly(new File(assetsFolder+image));
				}
			}
			
        } catch (Exception e) {
        }


    }

    public static void transcodeImage(String input, String output, double maxWidth, double maxHeight) throws Exception {
        File inputFile = new File(input);
        BufferedImage fullSizeImage = ImageIO.read(inputFile);
        int fullWidth = fullSizeImage.getWidth(null);
        int fullHeight = fullSizeImage.getHeight(null);
        File outputFile = new File(output);

        double requiredRatio = (double) maxWidth / (double) maxHeight;
        double imageRatio = (double) fullWidth / (double) fullHeight;

        boolean widthDominant = imageRatio >= requiredRatio;

        int targetWidth;
        int targetHeight;
        double scaling;

        boolean scaleToShortSide = true;
        if (scaleToShortSide) {
            scaling = !widthDominant ? (double) maxWidth / (double) fullWidth
                    : (double) maxHeight / (double) fullHeight;

            targetWidth = !widthDominant ? (int) maxWidth : (int) Math.floor(fullWidth * scaling);
            targetHeight = widthDominant ? (int) maxHeight : (int) Math.floor(fullHeight * scaling);
        } else {
            scaling = widthDominant ? (double) maxWidth / (double) fullWidth
                    : (double) maxHeight / (double) fullHeight;

            targetWidth = widthDominant ? (int) maxWidth : (int) Math.floor(fullWidth * scaling);
            targetHeight = !widthDominant ? (int) maxHeight : (int) Math.floor(fullHeight * scaling);
        }

        AffineTransform transform = new AffineTransform();
        transform.setToScale(scaling, scaling);
       // transform.rotate(0);
        BufferedImage scaled = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D scaledGraphics = scaled.createGraphics();
        //scaledGraphics.translate(targetWidth, targetHeight);
        scaledGraphics.drawRenderedImage(fullSizeImage, transform);


        String format = "jpg";
        int lastDot = outputFile.getName().lastIndexOf('.');
        if (lastDot >= 0) {
        format = outputFile.getName().substring(lastDot + 1).toLowerCase();
        }

        ImageIO.write(scaled, format, outputFile);
    }
    
    private static String getRandomString(){
        String randomStr = RandomStringUtils.random(4,"abcdefghijklmnopqrstuvwxyz");
        return randomStr;
    }
    
    
    public static String saveImage(String contentType, String fileName, long fileSize, InputStream inputStream, String subFolder) {
        String fullpath = null;
        try {
            String assetsFolder = SettingsUtil.getSettings("IMAGES_PHYSICAL_PATH"); //ConfigurationsUtil.getProperty("ASSETS_DIRECTORY");
            String fileseperator = System.getProperty("file.separator");
            if (!assetsFolder.endsWith(fileseperator)) {
                assetsFolder = assetsFolder + fileseperator;
            }            
            
            fullpath = assetsFolder +subFolder+fileseperator+fileName;
            IOUtils.copy(inputStream, new FileOutputStream(fullpath));
           

        } catch (Exception e) {
            e.printStackTrace();
        }    
        
         return fullpath;
    }
}
