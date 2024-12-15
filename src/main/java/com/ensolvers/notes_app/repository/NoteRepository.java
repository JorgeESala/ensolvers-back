package com.ensolvers.notes_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensolvers.notes_app.model.Note;
import java.util.List;


public interface NoteRepository extends JpaRepository<Note, Integer> {

    Optional<Note> findByName(String name);
    List<Note> findByArchived(Boolean archived);
    
}
