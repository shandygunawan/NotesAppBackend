package com.notes.service;

import com.notes.entity.Note;
import com.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    /*
        SINGULAR
    */
    public Note findById(UUID id) {
        return this.noteRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Note createNote(Note note) {
        return this.noteRepository.save(note);
    }

    @Transactional
    public Note updateNote(Note note) {
        if(this.noteRepository.existsById(note.getId())) {
            return this.noteRepository.save(note);
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteNoteById(UUID id) {
        this.noteRepository.deleteById(id);
    }

    /*
        MULTIPLE
    */
    public List<Note> findAll() {
        return this.noteRepository.findAll();
    }

    @Transactional
    public void deleteNotesByIds(List<UUID> uuids) {
        this.noteRepository.deleteByIdIn(uuids);
    }
}
