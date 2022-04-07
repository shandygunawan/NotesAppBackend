package com.notes.controller;

import com.notes.entity.Note;
import com.notes.service.NoteService;
import com.notes.util.ResponseEntityHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteController {

    final static Logger logger = Logger.getLogger(NoteController.class);

    @Autowired
    private NoteService noteService;

    /*
        SINGULAR
    */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getNote(@PathVariable UUID id) {
        logger.info(this.getClass().getName() + " - getNote: " + id.toString());
        Note note = this.noteService.findById(id);
        return ResponseEntityHandler.generateResponse(HttpStatus.OK, "Successfully retrieved data!", note);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createNote(@RequestBody Note paramNote) {
        logger.info(this.getClass().getName() + " - createNote : " + paramNote.toString());
        Note createdNote = this.noteService.createNote(paramNote);
        return ResponseEntityHandler.generateResponse(HttpStatus.OK, "Successfully added data!", createdNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNote(@PathVariable UUID id, @RequestBody Note paramNote) {
        logger.info(this.getClass().getName() + " - updateNote : " + paramNote.toString());
        if(!id.equals(paramNote.getId())) {
            return ResponseEntityHandler.generateResponse(HttpStatus.BAD_REQUEST, "Note id in URL and Note id in request body is not match!", null);
        }
        Note updatedNote = this.noteService.updateNote(paramNote);

        if(updatedNote == null) {
            return ResponseEntityHandler.generateResponse(HttpStatus.BAD_REQUEST, "Note id is not exist!", updatedNote);
        } else {
            return ResponseEntityHandler.generateResponse(HttpStatus.OK, "Successfully updated data!", updatedNote);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNote(@RequestBody UUID id) {
        logger.info(this.getClass().getName() + " - deleteNote : " + id);
        this.noteService.deleteNoteById(id);
        return ResponseEntityHandler.generateResponse(HttpStatus.OK, "Successfully deleted data!", id);
    }

    /*
        MULTIPLE
    */
    @GetMapping("/")
    public ResponseEntity<Object> getAllNotes() {
        logger.info(this.getClass().getName() + " - getAllNotes");
        List<Note> notes = this.noteService.findAll();
        return ResponseEntityHandler.generateResponse(HttpStatus.OK, "Successfully retrieved data!", notes);
    }

    @DeleteMapping("/")
    public ResponseEntity<Object> deleteNotes(@RequestBody List<UUID> uuids) {
        logger.info(this.getClass().getName() + " - deleteNotes : " + uuids.toString());
        this.noteService.deleteNotesByIds(uuids);
        return ResponseEntityHandler.generateResponse(HttpStatus.OK, "Successfully deleted data!", uuids);
    }

}
