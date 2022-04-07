package com.notes.repository;

import com.notes.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {

    public void deleteByIdIn(List<UUID> uuids);
}
