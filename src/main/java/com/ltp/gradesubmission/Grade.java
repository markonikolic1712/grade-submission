package com.ltp.gradesubmission;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

public class Grade {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Name cannot be blank")
    private String subject;

    @Score(message = "Score must be a letter grade") // ovo je poruka koju ce BindingResult preneti do thymeleaf-a
    private String score;
    private String id;

    public Grade() {
        this.id = UUID.randomUUID().toString();
    }


    public Grade(String name, String subject, String score) {
        this.name = name;
        this.subject = subject;
        this.score = score;
        this.id = UUID.randomUUID().toString();
    }


    @Override
    public String toString() {
        return "Grade [name=" + name + ", subject=" + subject + ", score=" + score + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
