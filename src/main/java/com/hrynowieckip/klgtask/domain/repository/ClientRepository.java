package com.hrynowieckip.klgtask.domain.repository;

import com.hrynowieckip.klgtask.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
