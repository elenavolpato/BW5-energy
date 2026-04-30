package BW5.epicEnergy.runner;

import BW5.epicEnergy.service.DataImportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;

@Component
public class CsvImportRunner implements CommandLineRunner {

    private final DataImportService importService;

    public CsvImportRunner(DataImportService importService) {
        this.importService = importService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            // Look for files directly starting from the 'resources' folder
            InputStream provStream = new ClassPathResource("csv/province-italiane.csv").getInputStream();
            InputStream comStream = new ClassPathResource("csv/comuni-italiani.csv").getInputStream();


            importService.importProvince(provStream);
            importService.importComuni(comStream);

            System.out.println("Import comuni e province finished!");
        } catch (Exception e) {
            System.err.println("Import failed: " + e.getMessage());
        }
    }

}
