package com.ensolvers.notes_app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensolvers.notes_app.model.Note;
import com.ensolvers.notes_app.service.NoteService;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin
public class NoteController {
    
    @Autowired
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> getUsers(){
        List<Note> notes = noteService.getNotes();
        return new ResponseEntity<List<Note>> (notes, HttpStatus.OK);
        
    }

    @GetMapping("{id}")
	public ResponseEntity<Note> findNoteById(@PathVariable Integer id) {
		return new ResponseEntity<Note>(noteService.findNoteById(id), HttpStatus.OK);
	}

    @GetMapping("active")
	public ResponseEntity<List<Note>> getActiveNotes(){
		return new ResponseEntity<List<Note>>(noteService.findNotesByArchived(false), HttpStatus.OK);
	}

	@GetMapping("archived")
	public ResponseEntity<List<Note>> getArchivedNotes(){
		return new ResponseEntity<List<Note>>(noteService.findNotesByArchived(true), HttpStatus.OK);
	}

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note newNote = noteService.createNote(note);
        return new ResponseEntity<Note>(newNote, HttpStatus.CREATED);
    }

    @PostMapping("archive/{id}")
    public ResponseEntity<Note> archiveNote(@PathVariable Integer id) {
        Note note = noteService.archiveNote(id); 
        if(note == null) {
            return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(note, HttpStatus.OK);

    }

    @PostMapping("activate/{id}")
    public ResponseEntity<Note> activateNote(@PathVariable Integer id) {
        Note note = noteService.activateNote(id); 
        if(note == null) {
            return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(note, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Integer id){
        if(noteService.deleteNoteById(id)){
            return new ResponseEntity<String>("Note deleted", HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<String>("Note not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Integer id, @RequestBody Note note){
        Note updatedNote = noteService.updateNote(note);

        return new ResponseEntity<Note>(updatedNote, HttpStatus.OK);
    }
    @PutMapping()
    public ResponseEntity<Note> updateNote(@RequestBody Note note){
        Note updatedNote = noteService.saveOrUpdate(note);

        return new ResponseEntity<Note>(updatedNote, HttpStatus.OK);
    }
}
