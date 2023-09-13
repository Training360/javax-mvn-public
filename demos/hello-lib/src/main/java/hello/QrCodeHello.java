package hello;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.Path;

public class QrCodeHello {

    public void writeHelloImage(String name) {
        var message = "Welcome %s!".formatted(name);

        try {
            var barcodeWriter = new QRCodeWriter();
            var bitMatrix =
                    barcodeWriter.encode("Hello World!", BarcodeFormat.QR_CODE, 200, 200);

            MatrixToImageWriter.writeToPath(bitMatrix, "png", Path.of("./hello.png"));
        } catch (WriterException | IOException we) {
            throw new IllegalStateException("Can not write barcode", we);
        }
    }
}
