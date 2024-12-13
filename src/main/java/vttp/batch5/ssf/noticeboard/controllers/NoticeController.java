package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

        // check num categories selected
        if (!bindingResult.hasErrors() && !notice.validCategories()) {
            bindingResult.rejectValue("categories", "error.notice", "Must select at least one category");

            return "notice";

        }

        // // no binding errors -->
        // // post form results to server
        
        // // SUCCESS: if saved to redis 
        // if (noticeService.postToNoticeServer(notice)) {

        //     // return view2.html
        //     return "view2";

        // } else {

        //     // UNSUCCESSFUL VIEW:
        //     return "view3";

        // }

        noticeService.postToNoticeServer(notice);

        return "test";


    }

}
