package com.dojo.services.personne.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dojo.services.personne.model.entity.Personne;

public interface PersonneRepository extends JpaRepository<Personne, Long> {

}
