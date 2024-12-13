package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers

@Controller
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    // task 1 
    // GET / 
    // view 1: landing page with community notice board form
    @GetMapping("/")
    public String getForm(Model model) {

        Notice notice = new Notice();

        model.addAttribute("notice", notice);

        // return notice.html
        return "notice";
    }

    // task 2
    // POST /notice 
    // check validations
    // use "test.html" for testing 
    @PostMapping("/notice")
    public String postForm(
        @Valid @ModelAttribute Notice notice, BindingResult bindingResult, Model model) {

        // if validation unsuccessful, return notice.html
        if (bindingResult.hasErrors()) {
            return "notice";

        }

        // if num of categories selected is zero, return "notice.html"
        if (!bindingResult.hasErrors() && !notice.validCategories()) {
            bindingResult.rejectValue("categories", "error.notice", "Must select at least one category");

            return "notice";

        }

        // get postingId OR error message from service
        String response = noticeService.postToNoticeServer(notice);
        model.addAttribute("response", response);

        // unsuccessful
        if (response.startsWith("Error: ")) {

            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

            model.addAttribute("baseUrl", baseUrl);

            return "unsuccessful";

        } 
        
        // successful
        else {

            // successful
            return "successful";

        }


    }
    

    // task 5 
    // POST /
    // return original view after successful form post
    @PostMapping("/")
    public String showForm(Model model) {

        Notice notice = new Notice();

        model.addAttribute("notice", notice);

        // return notice.html
        return "notice";
    }

    // task 6 
    // GET /status 
    // returns health check of application
    @GetMapping("/status")
    public ResponseEntity<String> healthCheck() {

        // getting random key from redis works
        if(noticeService.getRandomKeyFromRepo()) {

            return ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("{}");

        } 

        // getting random key from redis doesn't work 
        else {

            return ResponseEntity.status(503)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("{}");

        }

    }

}
