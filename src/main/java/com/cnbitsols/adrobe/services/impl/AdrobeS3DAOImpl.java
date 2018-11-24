/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.services.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.cnbitsols.adrobe.services.AdrobeS3DAO;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author rkumar
 */
public class AdrobeS3DAOImpl implements AdrobeS3DAO {
    
    private static final AWSCredentials CREDENTIALS = new AWSCredentials() {
        @Override
        public String getAWSAccessKeyId() {
            return "AKIAIYBPHWHWOYI3LMYA";
        }

        @Override
        public String getAWSSecretKey() {
            return "2j+f1QuW/bvlt9MZ8iY0HHgfx1d+CUKKrAP2Mvux";
        }
        
    };
    
    private final AmazonS3Client CLIENT;

    public AdrobeS3DAOImpl() {
        CLIENT = new AmazonS3Client(CREDENTIALS);
    }
    
    @Override
    public void upload(String bucketName, String fileName, InputStream inStream) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inStream, metadata);
        CLIENT.putObject(putObjectRequest);
    }

    @Override
    public InputStream download(String bucketName, String fileName) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
        S3Object obj = CLIENT.getObject(getObjectRequest);
        return obj.getObjectContent();
    }
    
}
