package com.simple.accounts.inventory.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@Service
public class BarcodeService {
    public BufferedImage generateBarcode(String text, int width, int height) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, width, height, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public BufferedImage generateQRCode(String text, int size) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, size, size, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
