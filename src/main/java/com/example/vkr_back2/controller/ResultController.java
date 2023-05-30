package com.example.vkr_back2.controller;

import com.example.vkr_back2.entity.ResultEntity;
import com.example.vkr_back2.repository.ResultRepository;
import com.example.vkr_back2.repository.StudentRepository;
import com.example.vkr_back2.repository.TestRepository;
import com.example.vkr_back2.resource.ResultResource;
import com.example.vkr_back2.resource.StudentResource;
import com.example.vkr_back2.resource.TestResource;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@CrossOrigin
@RestController
@RequestMapping("/results")
public class ResultController {
    private final ResultRepository resultRepository;
    private final StudentRepository studentRepository;
    private final TestRepository testRepository;

    public ResultController(ResultRepository resultRepository, StudentRepository studentRepository, TestRepository testRepository) {
        this.resultRepository = resultRepository;
        this.studentRepository = studentRepository;
        this.testRepository = testRepository;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    ResultResource[] getAll(@RequestParam(required = false) Integer sourceId,
                            @RequestParam(required = false) Object expand) {
        ResultEntity[] entities = sourceId == null ?
                resultRepository.select() :
                resultRepository.selectBySourceId(sourceId);
        return Arrays.stream(entities)
                .map(entity -> {
                    ResultResource resource = new ResultResource(entity);
                    if (expand != null) {
                        resource.setStudentResource(new StudentResource(studentRepository.select(entity.getStudent_id())));
                        resource.setTestResource(new TestResource(testRepository.select(entity.getTest_id())));
                    }
                    return resource;
                })
                .toArray(ResultResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResultResource get(@PathVariable Integer id, @RequestParam(required = false) Object expand) {
        ResultEntity entity = resultRepository.select(id);
        if (entity == null) return null;
        ResultResource resource = new ResultResource(entity);
        if (expand != null){
            resource.setStudentResource(new StudentResource(studentRepository.select(entity.getStudent_id())));
            resource.setTestResource(new TestResource(testRepository.select(entity.getTest_id())));
        }
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    ResultResource post(@RequestBody ResultResource resource) {
        ResultEntity entity = resultRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new ResultResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ResultResource put(@PathVariable Integer id,
                      @RequestBody ResultResource resource) {
        ResultEntity entity = resultRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new ResultResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResultResource delete(@PathVariable Integer id) {
        ResultEntity entity = resultRepository.delete(id);
        if (entity == null) return null;
        ResultResource resource = new ResultResource(entity);
        return resource;
    }
}
