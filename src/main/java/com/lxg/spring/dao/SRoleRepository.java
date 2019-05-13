package com.lxg.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lxg.spring.entity.SRole;

@Repository
public interface SRoleRepository extends JpaRepository<SRole,Integer> {

}