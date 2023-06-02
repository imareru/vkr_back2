package com.example.vkr_back2.resource;

import com.example.vkr_back2.entity.ClassEntity;
import com.example.vkr_back2.entity.RoleEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleResource extends BaseResource{

    private Integer role_id;
    private String role_name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TeacherResource[] order;
    private StudentResource[] student_order;

    public RoleResource(){}

    public RoleResource(RoleEntity roleEntity){
        this.role_id = roleEntity.getId();
        this.role_name = roleEntity.getRole_name();
    }

    public StudentResource[] getOrder() {
        return student_order;
    }

    public void setOrder(StudentResource[] order) {
        this.student_order = student_order;
    }

    public void setOrder(TeacherResource[] order) {
        this.order = order;
    }

    public RoleEntity toEntity(){
        return new RoleEntity(
                this.role_id,
                this.role_name
        );
    }
}
