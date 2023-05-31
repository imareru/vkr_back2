package com.example.vkr_back2.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class RoleEntity extends BaseEntity {

    private String role_name;

    public RoleEntity(Integer id, String role_name){
        super(id);
        this.role_name = role_name;
    }
}