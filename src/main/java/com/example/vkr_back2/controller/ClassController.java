package com.example.vkr_back2.controller;

import com.example.vkr_back2.entity.ClassEntity;
import com.example.vkr_back2.repository.ClassRepository;
import com.example.vkr_back2.repository.StudentRepository;
import com.example.vkr_back2.resource.ClassResource;
import com.example.vkr_back2.resource.StudentResource;
import com.example.vkr_back2.entity.StudentEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@CrossOrigin
@RestController
@RequestMapping("/classes")
public class ClassController {
    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;


    public ClassController(ClassRepository classRepository, StudentRepository studentRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    ClassResource[] getAll(@RequestParam(required = false) Object expand) {
        return Arrays.stream(classRepository.select()).map( classEntity ->
        {
            ClassResource resource = new ClassResource(classEntity);
            if (expand != null)
                resource.setOrder(
                        Arrays.stream(studentRepository.selectBySourceId(classEntity.getId()))
                                .map(e -> new StudentResource(e))
                                .toArray(StudentResource[]::new)
                );
            return resource;
        })
                .toArray(ClassResource[]::new);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ClassResource get(@PathVariable Integer id,
                      @RequestParam(required = false) Object expand) {
        ClassEntity entity = classRepository.select(id);
        if (entity == null) return null;
        ClassResource resource = new ClassResource(entity);
        if (expand != null)
            resource.setOrder(
                    Arrays.stream(studentRepository.selectBySourceId(entity.getId()))
                            .map(e -> new StudentResource(e))
                            .toArray(StudentResource[]::new)
            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    ClassResource post(@RequestBody ClassResource resource) {
        ClassEntity entity = classRepository.insert(resource.toEntity());
        if (entity == null) return null;
        return new ClassResource(entity);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ClassResource put(@PathVariable Integer id,
                       @RequestBody ClassResource resource) {
        ClassEntity entity = classRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        return new ClassResource(entity);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ClassResource delete(@PathVariable Integer id) {
        ClassEntity entity = classRepository.delete(id);
        if (entity == null) return null;
        return new ClassResource(entity);
    }
}
