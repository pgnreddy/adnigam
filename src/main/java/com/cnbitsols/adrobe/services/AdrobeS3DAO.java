/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.services;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author rkumar
 */
public interface AdrobeS3DAO {
    
    /**
     * Method to upload content from an input stream to an S3 Bucket
     * @param bucketName
     * @param fileName
     * @param inStream
     * @throws IOException 
     */
    public void upload(String bucketName, String fileName, InputStream inStream) throws IOException;
    
    /**
     * Method to download a file from an S3 bucket
     * @param bucketName
     * @param fileName
     * @return InputStream object to the downloaded file
     */
    public InputStream download(String bucketName, String fileName);
}
