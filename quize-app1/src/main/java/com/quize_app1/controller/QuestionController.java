package com.quize_app1.controller;

import com.quize_app1.model.Question;
import com.quize_app1.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>>getQuestionsByCategorey(@PathVariable String category){
        return questionService.getQuestionsByCategorey(category);
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveQuestions(@RequestBody Question question){
        return questionService.saveQuestion(question);
    }



}
