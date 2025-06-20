package com.examples.with.different.packagename;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLProcessor {

    private final File xmlFile;

    public XMLProcessor(File xmlFile) {
        this.xmlFile = xmlFile;
    }

//    public void processXML(DocumentBuilder documentBuilder){

//        int x = 0;
//
//        if (x >= 0){
//            System.out.println("x is greater than or equal to 0");
//        }
//        int y = x + 5;
//        System.out.println(y);

//        if (isValidExtension()){
//            int x = 0;
//            int y = x + 5;
//            System.out.println(y);
//            try{
//                Document document = documentBuilder.parse(this.xmlFile);
//                document.getDocumentElement().normalize();
//            } catch (Exception e) {
//                System.err.println("Error processing XML: " + e.getMessage());
//            }
        //}
//    }

    public File getXmlFile() {
        return xmlFile;
    }

    public void processXML(DocumentBuilder builder) {
        if (isValidExtension()) {
            try {
                Document document = builder.parse(xmlFile);
                document.getDocumentElement().normalize();

                System.out.println("Processing XML: " + xmlFile.getName());

                //renameFile();

            } catch (Exception e) {
                System.err.println("Error processing XML: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid file extension. Please provide an XML file.");
        }
    }

    private boolean isValidExtension() {
        return this.xmlFile.getAbsolutePath().endsWith(".xml");
    }

    private void renameFile() {
        File renamedFile = new File(xmlFile.getAbsolutePath().replace(".xml", ".processed.xml"));
        if (xmlFile.renameTo(renamedFile)) {
            System.out.println("File renamed to: " + renamedFile.getName());
        } else {
            System.err.println("Failed to rename file.");
        }
    }
}
