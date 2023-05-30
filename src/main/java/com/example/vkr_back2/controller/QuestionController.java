package com.example.vkr_back2.controller;

import com.example.vkr_back2.entity.QuestionEntity;
import com.example.vkr_back2.repository.AnswerRepository;
import com.example.vkr_back2.repository.QuestionRepository;
import com.example.vkr_back2.repository.TestRepository;
import com.example.vkr_back2.resource.AnswerResource;
import com.example.vkr_back2.resource.QuestionResource;
import com.example.vkr_back2.resource.TestResource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@CrossOrigin
@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final TestRepository testRepository;

    public QuestionController(QuestionRepository questionRepository, AnswerRepository answerRepository, TestRepository testRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.testRepository = testRepository;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    QuestionResource[] getAll(@RequestParam(required = false) Integer sourceId,
                              @RequestParam(required = false) Object expand) {
        QuestionEntity[] entities = sourceId == null ?
                questionRepository.select() :
                questionRepository.selectBySourceId(sourceId);
        return Arrays.stream(entities)
                .map(entity -> {
                    QuestionResource resource = new QuestionResource(entity);
                    if (expand != null) {
                        resource.setAnswerResource(new AnswerResource(answerRepository.select(entity.getAnswer_id())));
                        resource.setTestResource(new TestResource(testRepository.select(entity.getTest_id())));
                    }
                    return resource;
                })
                .toArray(QuestionResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    QuestionResource get(@PathVariable Integer id, @RequestParam(required = false) Object expand) {
        QuestionEntity entity = questionRepository.select(id);
        if (entity == null) return null;
        QuestionResource resource = new QuestionResource(entity);
        if (expand != null){
            resource.setAnswerResource(new AnswerResource(answerRepository.select(entity.getAnswer_id())));
            resource.setTestResource(new TestResource(testRepository.select(entity.getTest_id())));}
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    QuestionResource post(@RequestBody QuestionResource resource) {
        QuestionEntity entity = questionRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new QuestionResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    QuestionResource put(@PathVariable Integer id,
                       @RequestBody QuestionResource resource) {
        QuestionEntity entity = questionRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new QuestionResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    QuestionResource delete(@PathVariable Integer id) {
        QuestionEntity entity = questionRepository.delete(id);
        if (entity == null) return null;
        QuestionResource resource = new QuestionResource(entity);
        return resource;
    }
}
