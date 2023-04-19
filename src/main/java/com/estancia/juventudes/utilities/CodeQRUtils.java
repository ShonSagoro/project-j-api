package com.estancia.juventudes.utilities;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.image.BufferedImage;

public class CodeQRUtils {

    public static BufferedImage generateQRCode(String info)throws WriterException{
        QRCodeWriter qrCodeWriter= new QRCodeWriter();
        BitMatrix bitMatrix=qrCodeWriter.encode(info, BarcodeFormat.QR_CODE,600,600);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
