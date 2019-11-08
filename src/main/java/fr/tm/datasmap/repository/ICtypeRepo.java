package fr.tm.datasmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.tm.datasmap.entity.Ctype;

public interface ICtypeRepo extends JpaRepository<Ctype, Long> {

    public Ctype findOneByTitle(String title);
}
