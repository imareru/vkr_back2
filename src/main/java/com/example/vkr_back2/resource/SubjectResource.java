package com.example.vkr_back2.resource;

import com.example.vkr_back2.entity.SubjectEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

public class SubjectResource extends BaseResource{

    private Integer subject_id;
    private String sub_name;
    private Integer teacher_id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TeacherResource teacherResource;

    public SubjectResource(){}

    public SubjectResource(SubjectEntity subjectEntity) {
        this.subject_id = subjectEntity.getId();
        this.sub_name = subjectEntity.getSub_name();
        this.teacher_id = subjectEntity.getTeacher_id();
    }

    public Integer getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Integer subject_id) {
        this.subject_id = subject_id;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public TeacherResource getTeacherResource() {
        return teacherResource;
    }

    public void setTeacherResource(TeacherResource teacherResource) {
        this.teacherResource = teacherResource;
    }

    public SubjectEntity toEntity(){
        return new SubjectEntity(
                this.subject_id,
                this.sub_name,
                this.teacher_id
        );
    }
}
