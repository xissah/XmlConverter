package org.xmlconverter.converter;

import lombok.val;
import org.xmlconverter.converter.conversion.ConversionController;
import org.xmlconverter.converter.conversion.ConversionModel;
import org.xmlconverter.converter.filework.FileWorkController;
import org.xmlconverter.converter.filework.FileWorkModel;
import org.xmlconverter.converter.filework.FileWorkView;
import org.xmlconverter.converter.consoleUtils.ConsoleUtils;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Converter {
    private final Logger logger = Logger.getLogger(Converter.class.getName());

    public void convertXmlToCsv() {
        try {
            val fileWorkModel = new FileWorkModel();
            val fileWorkController = new FileWorkController(fileWorkModel);
            val fileWorkView = new FileWorkView(fileWorkController);

            fileWorkView.openXmlFile();

            val consoleUtils = new ConsoleUtils();
            val conversionModel = new ConversionModel();
            val conversionController = new ConversionController(conversionModel, consoleUtils);

            conversionController.starConversion(fileWorkController.getXmlFile());

            consoleUtils.showCsv(conversionController.getCsvRecords());

            fileWorkView.saveCsvFile(conversionController.getCsvRecords());

        } catch (RuntimeException e) {
            logger.log(Level.INFO, "Произошла ошибка во время работы программы.", e);
            System.exit(1);
        }
    }
}