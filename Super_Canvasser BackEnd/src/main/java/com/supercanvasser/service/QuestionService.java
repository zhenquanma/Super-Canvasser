package com.supercanvasser.service;

import com.supercanvasser.bean.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    public List<Question> retrieveQuestions(String questionStr){
        List<Question> questionlist = new ArrayList<>();
        questionStr = questionStr.trim();
        String[] questionStrArr = questionStr.split("\n");
        for(int i = 0; i < questionStrArr.length; i++){
            Question q = new Question();
            q.setContent(questionStrArr[i]);
            questionlist.add(q);
        }
        return questionlist;
    }


}
