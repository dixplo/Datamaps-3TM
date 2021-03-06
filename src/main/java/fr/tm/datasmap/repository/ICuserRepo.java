package fr.tm.datasmap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.tm.datasmap.entity.Cuser;

public interface ICuserRepo extends JpaRepository<Cuser, Long> {

    public Optional<Cuser> findOneByEmailAndPwd(String email, String pwd);
}
