package com.notes.controller;

import com.notes.entity.Note;
import com.notes.entity.User;
import com.notes.model.dto.ErrorSchemaDTO;
import com.notes.model.dto.ResponseDTO;
import com.notes.model.request.RequestNoteCreate;
import com.notes.model.request.RequestNoteDeleteMultiple;
import com.notes.model.request.RequestNoteUpdate;
import com.notes.service.AuthService;
import com.notes.service.NoteService;
import com.notes.service.UserService;
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

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllNotes() {
        logger.info(this.getClass().getName() + " - getAllNotes");
        List<Note> notes = this.noteService.findAll();
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully retrieved data!"),
                notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getNoteById(@PathVariable UUID id) {
        logger.info(this.getClass().getName() + " - getNoteById: " + id.toString());
        Note note = this.noteService.findById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully retrieved data!"),
                note);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<ResponseDTO> getNotesByUsername(@PathVariable String username) {
        logger.info(this.getClass().getName() + " - getNotesByUsername: " + username);
        User user = this.userService.getUserByUsername(username);
        List<Note> notes = this.noteService.findByUserId(user.getId());
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully retrieved data!"),
                notes);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO> createNote(@RequestBody RequestNoteCreate requestNoteCreate) {
        logger.info(this.getClass().getName() + " - createNote : " + requestNoteCreate.toString());
        Note createdNote = this.noteService.createNote(requestNoteCreate);
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully added data!"),
                createdNote);
    }

    @PutMapping("")
    public ResponseEntity<ResponseDTO> updateNote(@RequestBody RequestNoteUpdate requestNoteUpdate) {
        logger.info(this.getClass().getName() + " - updateNote : " + requestNoteUpdate.toString());

        Note updatedNote = this.noteService.updateNote(requestNoteUpdate);

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

    @DeleteMapping("")
    public ResponseEntity<ResponseDTO> deleteNotes(@RequestBody RequestNoteDeleteMultiple requestNoteDeleteMultiple) {
        logger.info(this.getClass().getName() + " - deleteNotes : " + requestNoteDeleteMultiple.toString());
        this.noteService.deleteNotesByIds(requestNoteDeleteMultiple.getIds());
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully deleted data!"),
                requestNoteDeleteMultiple);
    }

}
