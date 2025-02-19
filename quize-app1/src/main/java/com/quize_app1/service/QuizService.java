package com.quize_app1.service;

import com.quize_app1.dao.QuestionDao;
import com.quize_app1.dao.QuizDao;
import com.quize_app1.dto.AnswersResponseDTO;
import com.quize_app1.dto.QuestionDto;
import com.quize_app1.model.Question;
import com.quize_app1.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;


    public ResponseEntity<String> createQuize(String category, int numQ, String title) {

        List<Question> questions=questionDao.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz=new Quiz();

        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>( "Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionDto>> getQuizQuestions(int id){
        try{
            Optional<Quiz> quiz=quizDao.findById(id);
            if(quiz.isPresent()){
                List<Question> questionDB=quiz.get().getQuestions();
                List<QuestionDto> allQuestionDto=new ArrayList<>();
                for(Question q: questionDB){
                    QuestionDto dto=new QuestionDto(q.getId(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4(),q.getQuestionTitle());
                    allQuestionDto.add(dto);
                }
                return  new ResponseEntity<>(allQuestionDto,HttpStatus.OK);
            }
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NO_CONTENT);

        }
        catch (Exception e){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    public ResponseEntity<Integer> calculateResult(Integer id, List<AnswersResponseDTO> responses) {
        Quiz quiz = quizDao.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));


        List<Question> questions = quiz.getQuestions()
                .stream()
                .sorted(Comparator.comparing(Question::getId)) // Sort by ID (ascending)
                .toList();

        List<AnswersResponseDTO> responseDTOList = responses
                .stream()
                .sorted(Comparator.comparing(AnswersResponseDTO::getId))
                .toList();


        int right = (int) IntStream.range(0, Math.min(responseDTOList.size(), questions.size()))
                .filter(i -> responseDTOList.get(i).getResponse().equals(questions.get(i).getRightAnswer()))
                .count();

        return ResponseEntity.ok(right);
    }




}
