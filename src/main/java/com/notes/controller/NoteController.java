package com.notes.controller;

import com.notes.entity.Note;
import com.notes.model.dto.ErrorSchemaDTO;
import com.notes.model.dto.ResponseDTO;
import com.notes.service.NoteService;
import com.notes.util.Constant;
import com.notes.util.ResponseHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<ResponseDTO> getNote(@PathVariable UUID id) {
        logger.info(this.getClass().getName() + " - getNote: " + id.toString());
        Note note = this.noteService.findById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully retrieved data!"),
                note);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO> createNote(@RequestBody Note paramNote) {
        logger.info(this.getClass().getName() + " - createNote : " + paramNote.toString());
        Note createdNote = this.noteService.createNote(paramNote);
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully added data!"),
                createdNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateNote(@PathVariable UUID id, @RequestBody Note paramNote) {
        logger.info(this.getClass().getName() + " - updateNote : " + paramNote.toString());
        if(!id.equals(paramNote.getId())) {
            String errorMessage = "Note id in URL and Note id in request body is not match!";
            logger.error(this.getClass().getName() + "- ERROR - updateNote : " + errorMessage);
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    new ErrorSchemaDTO(Constant.ErrorCode.ERROR_BAD_REQUEST, errorMessage),
                    null);
        }
        Note updatedNote = this.noteService.updateNote(paramNote);

        if(updatedNote == null) {
            String errorMessage = "Note id is not exist!";
            logger.error(this.getClass().getName() + "- ERROR - updateNote : " + errorMessage);
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    new ErrorSchemaDTO(Constant.ErrorCode.ERROR_BAD_REQUEST, errorMessage),
                    null);
        } else {
            return ResponseHandler.generateResponse(HttpStatus.OK,
                    new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully updated data!"),
                    updatedNote);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteNote(@PathVariable UUID id) {
        logger.info(this.getClass().getName() + " - deleteNote : " + id);
        this.noteService.deleteNoteById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully deleted data!"),
                id);
    }

    /*
        MULTIPLE
    */
    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllNotes() {
        logger.info(this.getClass().getName() + " - getAllNotes");
        List<Note> notes = this.noteService.findAll();
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully retrieved data!"),
                notes);
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseDTO> deleteNotes(@RequestBody List<UUID> uuids) {
        logger.info(this.getClass().getName() + " - deleteNotes : " + uuids.toString());
        this.noteService.deleteNotesByIds(uuids);
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully deleted data!"),
                uuids);
    }

}
