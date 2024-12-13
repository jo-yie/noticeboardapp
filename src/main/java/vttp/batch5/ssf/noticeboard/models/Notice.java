package vttp.batch5.ssf.noticeboard.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// task 1 model class
public class Notice {

    @NotEmpty(message = "Title is mandatory")
    @Size(min = 3, max = 128, message = "Title must be between 3 and 128 characters long")
    private String title;
    
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email must be in valid format")
    private String poster; 

    @NotNull(message = "Date is mandatory")
    @Future(message = "Date must be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postDate; 

    // validation in validCategories()
    private List<String> categories; 

    @NotEmpty(message = "Text is mandatory")
    private String text;

    
    // constructors
    public Notice() {
    }

    public Notice(String title, String poster, Date postDate, List<String> categories, String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }

    // getter setters 
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // check at least 1 category is selected 
    public Boolean validCategories() { 

        if (categories == null || categories.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }
    
}
