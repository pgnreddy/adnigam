/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author santosoh.r
 */
public class TranscoderUtils {

    //get log4j handler
    private static org.slf4j.Logger log = LoggerFactory.getLogger(TranscoderUtils.class);

    public static boolean transcodeAudio(String sourcePath, String destinationPath) {
        try {
            // ffmpeg -i Ahmad_Amr.wav -ar 8000 -ac 1 -acodec pcm_u8 output.wav
            String ffmpCmd = null;
            if (destinationPath.contains("wav")) {
                //ffmpCmd = "ffmpeg -i " +sourcePath+ " -vn -acodec pcm_s16le -ar 8000 -y -ac 1 -f wav  "+destinationPath;
                ffmpCmd = "ffmpeg -i " + sourcePath + " -vn -acodec pcm_u8 -ar 8000 -y -ac 1 -f wav  " + destinationPath;
            }
            log.info("ffmpeg cmd>>>>" + ffmpCmd);
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(ffmpCmd);
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");
            errorGobbler.start();
            outputGobbler.start();
            int exitCode = p.waitFor();
            if (exitCode != 0) {
                throw new Exception("Executable stopped with error code " + exitCode);
            }
            return true;
        } catch (Exception ex) {
            log.error("conversion exception ", ex);
        }
        return false;
    }

    public static boolean transcodeImage(String sourcePath, String destinationPath, double maxWidth, double maxHeight) {
        try {
            // ffmpeg -i Ahmad_Amr.wav -ar 8000 -ac 1 -acodec pcm_u8 output.wav
            String ffmpCmd = null;

            //ffmpCmd = "ffmpeg -i " +sourcePath+ " -vn -acodec pcm_s16le -ar 8000 -y -ac 1 -f wav  "+destinationPath;
            ffmpCmd = "ffmpeg -i " + sourcePath + " -vf scale=" + maxWidth + ":" + maxHeight + " " + destinationPath;

            log.info("ffmpeg cmd>>>>" + ffmpCmd);
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(ffmpCmd);
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");
            errorGobbler.start();
            outputGobbler.start();
            int exitCode = p.waitFor();
            if (exitCode != 0) {
                throw new Exception("Executable stopped with error code " + exitCode);
            }
            return true;
        } catch (Exception ex) {
            log.error("conversion exception ", ex);
        }
        return false;
    }

    public static boolean transcodeImageNew(String sourcePath, String destinationPath, double maxWidth, double maxHeight) {
        try {

            String[] strArr = new String[]{"ffmpeg", "-i", sourcePath, "-vf", "scale=" + maxWidth + ":" + maxHeight, destinationPath};
            log.info("ffmpeg cmd>>>>" + strArr);
            Runtime rt = Runtime.getRuntime();

            try {
                ProcessBuilder pb = new ProcessBuilder(strArr);
                Process p = pb.start();
                new Thread(new StreamGobbler(p.getErrorStream(), "OUTPUT")).start();
                int exitCode = p.waitFor();
                log.debug("exit code :: " + exitCode);
            } catch (IOException e) {
                log.error("Ex: " + ExceptionUtils.getFullStackTrace(e));
            } catch (InterruptedException e) {
                log.error("Ex: " + ExceptionUtils.getFullStackTrace(e));
            }
            return true;
        } catch (Exception ex) {
            log.error("conversion exception ", ex);
        }
        return false;
    }

    static class StreamGobbler extends Thread {

        InputStream is;
        String type;

        StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        @Override
        public void run() {
            InputStreamReader isr = null;
            BufferedReader br = null;
            try {
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);

                String line;
                while ((line = br.readLine()) != null) {
                    log.debug(type + ">" + line);
                }
            } catch (IOException ioe) {
                log.error("io exception", ioe);
            } finally {
                try {

                    if (br != null) {

                        br.close();
                    }
                } catch (Exception e) {
                }
                try {

                    if (isr != null) {

                        isr.close();
                    }
                } catch (Exception e) {
                }
                try {

                    if (is != null) {

                        is.close();
                    }
                } catch (Exception e) {
                }
            }
        }
    }
}
