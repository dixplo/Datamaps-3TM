package fr.tm.datasmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.tm.datasmap.entity.Cuser;

public interface ICuserRepo extends JpaRepository<Cuser, Long> {

}
