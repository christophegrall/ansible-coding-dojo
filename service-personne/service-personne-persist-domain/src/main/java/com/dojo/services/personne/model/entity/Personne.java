package com.dojo.services.personne.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;


import java.util.Objects;



@Entity
@Table(name = "personne")
@ApiModel("Personne")
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "integer")
    @ApiModelProperty("Technical ID")
    private Long id;
    
    @ApiModelProperty(value = "nom")
    @Column(name = "nom")
    private String nom;
    
    @ApiModelProperty(value = "prenom")
    @Column(name = "prenom")
    private String prenom;
    
    

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      EQUALS & HASHCODE                                                     //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Personne)) {
            return false;
        }
        Personne personne = (Personne) o;
        if(personne.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personne.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  TO STRING                                                                 //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + "'" +
                ", prenom='" + prenom + "'" +
                '}';
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  GETTER & SETTER                                                           //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    

}
