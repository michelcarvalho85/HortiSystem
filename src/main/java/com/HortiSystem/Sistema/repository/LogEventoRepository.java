package com.HortiSystem.Sistema.repository;

import com.HortiSystem.Sistema.model.LogEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogEventoRepository extends JpaRepository<LogEvento, Long> {
}
