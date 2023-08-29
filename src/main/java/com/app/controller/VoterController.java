package com.app.controller;

import java.io.IOException;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.entity.VoterInfo;
import com.app.service.VoterService;



@Controller
public class VoterController {
	

	@Autowired
     private VoterService voterService;
	
	public VoterController(VoterService voterService) {
		super();
		this.voterService = voterService;
	}



	@GetMapping("/generate-voter")
    public String generateVoterForm(Model model) {
        model.addAttribute("voterInfo" , new VoterInfo());
        return "generate-voter";
    }
	
	

	@PostMapping("/generate-voter")
	public ResponseEntity<Resource> generateVoter(@ModelAttribute VoterInfo voterInfo,
			@RequestParam("photo") MultipartFile photo){
		
		try {
			
			String filename =voterInfo.getName()+ "-" +voterInfo.getVoterNumber()+ ".pdf";
			  Resource pdfResource = voterService.generateVoterPDF(voterInfo, photo, filename);
			  
			  HttpHeaders headers = new HttpHeaders();
		        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
			
			
		        return ResponseEntity.ok()
		                .headers(headers)
		                .contentType(MediaType.APPLICATION_PDF)
		                .body(pdfResource);
			
			
		}catch (IOException e) {
	        // Handle the exception
	        e.printStackTrace();
	        // You might want to return an error page or response here
	        return ResponseEntity.badRequest().build();
	    }
	}
	
    @GetMapping("/generate-success")
    public String generateSuccess() {
        return "generate-success";
    }
	
	
	
	

}
