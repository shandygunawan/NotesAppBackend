package com.notes.service;

import com.notes.entity.Note;
import com.notes.entity.User;
import com.notes.model.request.RequestNoteCreate;
import com.notes.model.request.RequestNoteUpdate;
import com.notes.repository.NoteRepository;
import com.notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    /*
        GET
    */
    public List<Note> findAll() {
        return this.noteRepository.findAll();
    }

    public Note findById(UUID id) {
        return this.noteRepository.findById(id).orElseThrow();
    }

    public List<Note> findByUserId(UUID id) {
        return this.noteRepository.findByUserId(id);
    }


    /*
        CREATE
    */
    @Transactional
    public Note createNote(RequestNoteCreate requestNoteCreate) {
        User user = userRepository.findByUsername(this.getCurrentLoggedInUsername());

        Note newNote = new Note();
        newNote.setTitle(requestNoteCreate.getTitle());
        newNote.setContent(requestNoteCreate.getContent());
        newNote.setUser(user);

        return this.noteRepository.save(newNote);
    }


    /*
        UPDATE
    */
    @Transactional
    public Note updateNote(RequestNoteUpdate requestNoteUpdate) {
        if(this.noteRepository.existsById(requestNoteUpdate.getId())) {
            Note note = this.noteRepository.getById(requestNoteUpdate.getId());
            note.setTitle(requestNoteUpdate.getTitle());
            note.setContent(requestNoteUpdate.getContent());
            return this.noteRepository.save(note);
        } else {
            return null;
        }
    }


    /*
        DELETE
    */
    @Transactional
    public void deleteNotesByIds(List<UUID> uuids) {
        this.noteRepository.deleteByIdIn(uuids);
    }

    /*
        UTILS
    */
    private String getCurrentLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
