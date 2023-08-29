package com.app.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.entity.VoterInfo;
import com.itextpdf.io.source.ByteArrayOutputStream;


@Service
public class VoterService {

	private final ResourceLoader resourceLoader;
	
    @Value("${pdf.output.directory}")
	private String pdfOutputDirectory;
	

    
    public VoterService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    
    
    public Resource  generateVoterPDF(VoterInfo voterInfo ,MultipartFile photo,String filename) throws IOException {
    
    	 Resource templateImageResource = resourceLoader.getResource("classpath:VOTER-ODIA.JPG");
    
    	 
    	  InputStream templateImageInputStream = templateImageResource.getInputStream();
    
    
    	  int dpi = 72;

    	// A4 dimensions in inches
    	float a4WidthInInches = 8.27f;
    	float a4HeightInInches = 11.69f;

    	float widthInPoints = a4WidthInInches * dpi;
    	float heightInPoints = a4HeightInInches * dpi;
    
	        
            try (PDDocument document = new PDDocument()) {
            	 PDRectangle customPageSize = new PDRectangle(widthInPoints, heightInPoints);
            	    PDPage page = new PDPage(customPageSize);
            	    document.addPage(page);

            	    PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, IOUtils.toByteArray(templateImageInputStream), "template");   
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true, true)) {
                    contentStream.drawImage(pdImage, 0, 0, widthInPoints, heightInPoints);
    

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA,7);
                    contentStream.newLineAtOffset(160, 735);
                    contentStream.showText(voterInfo.getVoterNumber());
                    contentStream.endText();
                    
                    
                    InputStream odiaFontStream = getClass().getResourceAsStream("/2- Jagannatha.TTF");
                    PDFont odiaFont = PDType0Font.load(document, odiaFontStream);
                    
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 8);
                    contentStream.newLineAtOffset(97, 608);
                    contentStream.showText("" + voterInfo.getName());
                    contentStream.endText();
                    
                    String odiaText = "" + voterInfo.getNameOdia(); // Replace with Odia text
                    contentStream.beginText();
                    contentStream.setFont(odiaFont, 8);
                    contentStream.newLineAtOffset(90, 620); // Adjust the coordinates
                    contentStream.showText(odiaText);
                    contentStream.endText();
                    
                    
   
                    
                    InputStream odiaFontStream1 = getClass().getResourceAsStream("/2- Jagannatha.TTF");
                    PDFont odiaFont1 = PDType0Font.load(document, odiaFontStream1);
                    
                    String odiaText1 = "" + voterInfo.getNameOdiaFather(); // Replace with Odia text
                    contentStream.beginText();
                    contentStream.setFont(odiaFont, 8);
                    contentStream.newLineAtOffset (115, 590); // Adjust the coordinates
                    contentStream.showText(odiaText1);
                    contentStream.endText();
                    
                    
                    
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 8);
                    contentStream.newLineAtOffset(118, 578); 
                    contentStream.showText("" + voterInfo.getNameFather());
                    contentStream.endText();
                    

                    
                    
                    
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA,5);
                    contentStream.newLineAtOffset(277, 770);
                    contentStream.showText(voterInfo.getVoterNumber());
                    contentStream.endText();
                    
                    
                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                    String dob =  voterInfo.getDateOfBirth().toString();
                    
                    LocalDate date = LocalDate.parse(dob, inputFormatter);
                    String formattedDob = "" + date.format(outputFormatter);
                    
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 5);
                    contentStream.newLineAtOffset(314, 738);
                    contentStream.showText(formattedDob);
                    contentStream.endText();
                    
                    
                    
                    
                    InputStream odiaFontStream2 = getClass().getResourceAsStream("/2- Jagannatha.TTF");
                    PDFont odiaFont2 = PDType0Font.load(document, odiaFontStream2);
                    
                    
                    String genderText;
                    if ("Male".equals(voterInfo.getGender())) {
                        genderText = " ପୁରୁଷ /Male";
                    } else  {
                        genderText = "ମହିଳା /Female";
                    } 
                    
   
//                    String odiaText2 = "ପୁରୁଷ / Male"; 
//                    
//                    String odiatext2= "ମହିଳା /female";

               
                 contentStream.beginText();
                 contentStream.setFont(odiaFont2,6);
                 contentStream.newLineAtOffset(311, 755);
                 contentStream.showText(genderText);
                 contentStream.endText();
                    
                    
                    
                 DateTimeFormatter inputFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                 DateTimeFormatter outputFormatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                 String dob1 =  voterInfo.getVoterDate().toString();
                 
                 LocalDate date1 = LocalDate.parse(dob1, inputFormatter1);
                 String formattedDob1 = "" + date1.format(outputFormatter1);
                 
                 contentStream.beginText();
                 contentStream.setFont(PDType1Font.HELVETICA, 6);
                 contentStream.newLineAtOffset(265, 677);
                 contentStream.showText(formattedDob1);
                 contentStream.endText();
                 
                 
                 
                 contentStream.beginText();
                 contentStream.setFont(PDType1Font.HELVETICA, 6);
                 contentStream.newLineAtOffset(248, 625); 
                 contentStream.showText("" + voterInfo.getConstituencyNoName());
                 contentStream.endText();
                 
                 
                 
                 InputStream odiaFontStream3 = getClass().getResourceAsStream("/2- Jagannatha.TTF");
                 PDFont odiaFont3 = PDType0Font.load(document, odiaFontStream3);
                 
                 String odiaText3 = "" + voterInfo.getConstituencyNoNameOdia(); // Replace with Odia text
                 contentStream.beginText();
                 contentStream.setFont(odiaFont3, 6);
                 contentStream.newLineAtOffset (245, 643); // Adjust the coordinates
                 contentStream.showText(odiaText3);
                 contentStream.endText();
                 
                 
                 
                 String[] addressParts = voterInfo.getAddress().split(",");
                 float yCoordinate = 707;
                 for (String part : addressParts) {
                     contentStream.beginText();
                     contentStream.setFont(PDType1Font.HELVETICA, 6);
                     contentStream.newLineAtOffset(270, yCoordinate);
                     contentStream.showText(part.trim());
                     contentStream.endText();
                     yCoordinate -= 7;
                 }
                 
                 
                 
                 
                    
                 InputStream odiaFontStream4 = getClass().getResourceAsStream("/2- Jagannatha.TTF");
                 PDFont odiaFont4 = PDType0Font.load(document, odiaFontStream4);
                    
                    
                    String[] addressParts1 = voterInfo.getAddressOdia().split(",");
                    float yCoordinate1 = 727;
                    for (String part : addressParts1) {
                        contentStream.beginText();
                        contentStream.setFont(odiaFont4, 6);
                        contentStream.newLineAtOffset(270, yCoordinate1);
                        contentStream.showText(part.trim());
                        contentStream.endText();
                        yCoordinate1 -= 7;
                    }
                    
   
                    
                    int photoX = 100;
                    int photoY = 640;
                    int photoWidth = 65;
                    int photoHeight = 85;

                    // Load and insert the uploaded photo
                    PDImageXObject photoImage = PDImageXObject.createFromByteArray(document, photo.getBytes(), "photo");
                    
                    PDExtendedGraphicsState graphicsState = new PDExtendedGraphicsState();
                    graphicsState.setNonStrokingAlphaConstant(1f);
                    
                    contentStream.saveGraphicsState();
                    
                    contentStream.setGraphicsStateParameters(graphicsState);
                    
                    contentStream.drawImage(photoImage, photoX, photoY, photoWidth, photoHeight);
                    
                    contentStream.restoreGraphicsState();
                }
                
                
                // Generate and return the PDF as a Resource
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                document.save(byteArrayOutputStream);
                document.close();
                
                byte[] pdfBytes = byteArrayOutputStream.toByteArray();
                    
    
                    File outputFile = new File(pdfOutputDirectory, filename);
                    FileUtils.writeByteArrayToFile(outputFile, pdfBytes);

                    Resource pdfResource = new FileSystemResource(outputFile);

                    return pdfResource;
             
                    
                } catch (IOException e) {
                    // Handle the exception
                    e.printStackTrace();
                    throw e;
                }
            }
    
    
    
	
}
