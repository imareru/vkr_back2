package com.example.vkr_back2.controller;

import com.example.vkr_back2.entity.StudentEntity;
import com.example.vkr_back2.repository.ClassRepository;
import com.example.vkr_back2.repository.StudentRepository;
import com.example.vkr_back2.resource.ClassResource;
import com.example.vkr_back2.resource.StudentResource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@CrossOrigin
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;


    public StudentController(StudentRepository studentRepository, ClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    StudentResource[] getAll(@RequestParam(required = false) Integer sourceId,
                             @RequestParam(required = false) Object expand) {
        StudentEntity[] entities = sourceId == null ?
                studentRepository.select() :
                studentRepository.selectBySourceId(sourceId);
        return Arrays.stream(entities)
                .map(entity -> {
                    StudentResource resource = new StudentResource(entity);
                    if (expand != null)
                        resource.setClassResource(new ClassResource(classRepository.select(entity.getClass_id())));
                    return resource;
                })
                .toArray(StudentResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    StudentResource get(@PathVariable Integer id,
                        @RequestParam(required = false) Object expand) {
        StudentEntity entity = studentRepository.select(id);
        if (entity == null) return null;
        StudentResource resource = new StudentResource(entity);
        if (expand != null)
            resource.setClassResource(
                    new ClassResource(classRepository.select(entity.getClass_id()))
            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    StudentResource post(@RequestBody StudentResource resource) {
        StudentEntity entity = studentRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new StudentResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    StudentResource put(@PathVariable Integer id,
                        @RequestBody StudentResource resource) {
        StudentEntity entity = studentRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new StudentResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    StudentResource delete(@PathVariable Integer id) {
        StudentEntity entity = studentRepository.delete(id);
        if (entity == null) return null;
        StudentResource resource = new StudentResource(entity);
        return resource;
    }
}
