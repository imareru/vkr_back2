package com.example.vkr_back2.controller;

import com.example.vkr_back2.entity.SubjectEntity;
import com.example.vkr_back2.repository.SubjectRepository;
import com.example.vkr_back2.repository.TeacherRepository;
import com.example.vkr_back2.resource.SubjectResource;
import com.example.vkr_back2.resource.TeacherResource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@CrossOrigin
@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;


    public SubjectController(SubjectRepository subjectRepository, TeacherRepository teacherRepository) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    SubjectResource[] getAll(@RequestParam(required = false) Integer sourceId,
                             @RequestParam(required = false) Object expand) {
        SubjectEntity[] entities = sourceId == null ?
                subjectRepository.select() :
                subjectRepository.selectBySourceId(sourceId);
        return Arrays.stream(entities)
                .map(entity -> {
                    SubjectResource resource = new SubjectResource(entity);
                    if (expand != null)
                        resource.setTeacherResource(new TeacherResource(teacherRepository.select(entity.getTeacher_id())));
                    return resource;
                })
                .toArray(SubjectResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    SubjectResource get(@PathVariable Integer id,
                     @RequestParam(required = false) Object expand) {
        SubjectEntity entity = subjectRepository.select(id);
        if (entity == null) return null;
        SubjectResource resource = new SubjectResource(entity);
        if (expand != null)
            resource.setTeacherResource(
                    new TeacherResource(teacherRepository.select(entity.getTeacher_id()))
            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    SubjectResource post(@RequestBody SubjectResource resource) {
        SubjectEntity entity = subjectRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new SubjectResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    SubjectResource put(@PathVariable Integer id,
                     @RequestBody SubjectResource resource) {
        SubjectEntity entity = subjectRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new SubjectResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    SubjectResource delete(@PathVariable Integer id) {
        SubjectEntity entity = subjectRepository.delete(id);
        if (entity == null) return null;
        SubjectResource resource = new SubjectResource(entity);
        return resource;
    }
}
