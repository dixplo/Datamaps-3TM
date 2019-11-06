package fr.tm.datasmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.tm.datasmap.entity.Cevent;

public interface ICeventRepo extends JpaRepository<Cevent, Long> {

}
