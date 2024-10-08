package com.cob.billing.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;

public class FileCompressor {
    public static byte[] compress(byte[] input){
        Deflater compressor = new Deflater(Deflater.BEST_COMPRESSION, false);
        compressor.setInput(input);
        compressor.finish();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] readBuffer = new byte[1024];
        int readCount = 0;
        while (!compressor.finished()) {
            readCount = compressor.deflate(readBuffer);
            if (readCount > 0) {
                bao.write(readBuffer, 0, readCount);
            }
        }
        compressor.end();
        return bao.toByteArray();
    }
}
