package com.cs.serverlogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cs.serverlogger.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

}
