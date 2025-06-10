package com.edutech.cupones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutech.cupones.models.Cupon;

public interface CuponRepository extends JpaRepository<Cupon, Integer>{

}
