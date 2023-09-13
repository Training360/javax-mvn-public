package employees;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
//import java.util.ArrayList;
import java.util.Properties;

public class EmployeesMain {

    private static final Logger log = LoggerFactory.getLogger(EmployeesMain.class);

    public static void main(String[] args) {
        System.out.println("Employees22");

//        new ArrayList().add(10);

        var properties = new Properties();
        try (var reader = new InputStreamReader(EmployeesMain.class.getResourceAsStream("/application.properties"), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not load application.properties", ioe);
        }
        System.out.println(properties.get("welcome-message"));

        System.out.println(properties.get("project-version"));
        System.out.println(properties.get("project-info"));

        log.info("Hello SLF4J");

        try {
            var barcodeWriter = new QRCodeWriter();
            var bitMatrix =
                    barcodeWriter.encode("Hello World!", BarcodeFormat.QR_CODE, 200, 200);

            MatrixToImageWriter.writeToPath(bitMatrix, "png", Path.of("./hello.png"));
        } catch (WriterException | IOException we) {
            throw new IllegalStateException("Can not write barcode", we);
        }

        var employee = new Employee("John Doe", 100);
        log.info(employee.getName());

        var mapper = new EmployeeMapperImpl();
        var presentationModel = mapper.toPresentationModel(employee);
        log.info(presentationModel.getName());
    }
}
