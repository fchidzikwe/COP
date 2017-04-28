package com.fortune.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fortune.service.StorageService;

@Controller
public class UploadController {
	
	 Logger log = LoggerFactory.getLogger(this.getClass().getName());
 
    @Autowired
    StorageService storageService;
 
    List<String> files = new ArrayList<String>();
 
    
    @RequestMapping(value = "/fileupload", method = RequestMethod.GET)
     public ModelAndView getUploadPage() {
    	ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("uploadCSV");
		return modelAndView;
        
     }
   
   // @RequestMapping(value = "/upload", method = RequestMethod.POST)
   @PostMapping("/upload1")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            storageService.store(file);
            model.addAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
            files.add(file.getOriginalFilename());
        } catch (Exception e) {
            model.addAttribute("message", "FAIL to upload " + file.getOriginalFilename() + "!");
        }
        return "uploadCSV";
    }
 

 
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
