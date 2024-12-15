package com.ensolvers.notes_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensolvers.notes_app.model.Note;
import com.ensolvers.notes_app.repository.NoteRepository;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    public List<Note> getNotes(){
        return noteRepository.findAll();
    }
    public Note findNoteById(Integer id){
        return noteRepository.findById(id).orElse(null);
    }

    public Note findNoteByName(String name){
        return noteRepository.findByName(name).orElse(null);
    }

    public Note createNote(Note note) {
        note.setArchived(false);
        note.setId(null);
        
        return noteRepository.save(note);
    }

    public boolean deleteNoteById(Integer id) {

        if(noteRepository.existsById(id)){
            noteRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public List<Note> findNotesByArchived(boolean isArchived){
        return noteRepository.findByArchived(isArchived);
    }

    public Note updateNote(Note note) {

        Note existingNote = noteRepository.findById(note.getId()).orElse(null);

		if(note.getName() != null) {
			existingNote.setName(note.getName());
		}
		
		if(note.getText() != null) {
			existingNote.setText(note.getText());
		}
		
		if(note.getArchived() != null) {
			existingNote.setArchived(note.getArchived());
		}

        return noteRepository.save(note);
    }
    public Note saveOrUpdate(Note note) {
        if(note.getId() == null){
            note.setArchived(false);
            return noteRepository.save(note);
        }
        return noteRepository.save(note);
        
    }

    public Note archiveNote (Integer id) {
        Optional<Note> note = noteRepository.findById(id);
        if(note.isPresent()){
            note.get().setArchived(true);
            noteRepository.save(note.get());
            return note.get();
        }
        return null;
        
    }

    public Note activateNote (Integer id) {
        Optional<Note> note = noteRepository.findById(id);
        if(note.isPresent()){
            note.get().setArchived(false);
            noteRepository.save(note.get());
            return note.get();
        }
        return null;
        
    }
}
