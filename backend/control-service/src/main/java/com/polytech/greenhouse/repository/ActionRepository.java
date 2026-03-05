package com.polytech.greenhouse.repository;

import com.polytech.greenhouse.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
}