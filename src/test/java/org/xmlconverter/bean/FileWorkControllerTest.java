package org.xmlconverter.bean;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.xmlconverter.converter.bean.DataWritingException;
import org.xmlconverter.converter.bean.FileWorkController;
import org.xmlconverter.converter.bean.FileWorkModel;
import org.xmlconverter.converter.bean.UnreadableFileException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class FileWorkControllerTest {

    @Mock
    private FileWorkModel fileWorkModel;

    @InjectMocks
    private FileWorkController fileWorkController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckAndSetXmlFile() throws UnreadableFileException {
        val xmlFilePath = mock(Path.class);
        val selectedXmlFile = mock(File.class);
        when(xmlFilePath.toFile()).thenReturn(selectedXmlFile);
        when(selectedXmlFile.canRead()).thenReturn(true);
        fileWorkController.checkAndSetXmlFile(xmlFilePath);
        verify(fileWorkModel).setXmlFile(selectedXmlFile);
    }

    @Test
    void testCreateAndSetCsvFile() throws Exception {
        val testCsvFilePath = Paths.get("test-directory");
        val expectedCsvFile = new File("test-directory/Airport.csv");
        fileWorkController.createAndSetCsvFile(testCsvFilePath);
        verify(fileWorkModel).createCsvFile(expectedCsvFile);
        verify(fileWorkModel).setCsvFile(expectedCsvFile);
        verify(fileWorkModel).setCsvFile(eq(expectedCsvFile));
    }

    @Test
    void testWriteCsvDataToFile() throws DataWritingException, IOException {
        List<String[]> csvRecords = Collections.singletonList(new String[]{"value1", "value2"});
        when(fileWorkModel.writeCsvData(csvRecords)).thenReturn(true);
        fileWorkController.writeCsvDataToFile(csvRecords);
    }

}
