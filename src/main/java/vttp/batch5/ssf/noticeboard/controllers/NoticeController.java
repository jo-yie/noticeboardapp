package vttp.batch5.ssf.noticeboard.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;

// Use this class to write your request handlers

@Controller
public class NoticeController {

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

        // if no binding result errors 
        // return test.html
        return "test";

    }

}
