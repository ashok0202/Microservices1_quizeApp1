package com.quize_app1.controller;


import com.quize_app1.dto.AnswersResponseDTO;
import com.quize_app1.dto.QuestionDto;
import com.quize_app1.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;


    @PostMapping("/create")
    public ResponseEntity<String> conductQuiz(@RequestParam String category ,@RequestParam int numQ,@RequestParam String title){
        //return new ResponseEntity<>("I am Here", HttpStatus.OK);
        return quizService.createQuize(category,numQ,title);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionDto>> getQuizQuestions(@PathVariable int id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<AnswersResponseDTO> responses){
        return quizService.calculateResult(id, responses);
    }


}
