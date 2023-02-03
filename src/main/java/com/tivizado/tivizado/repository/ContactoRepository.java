package com.tivizado.tivizado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tivizado.tivizado.model.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
    
}
