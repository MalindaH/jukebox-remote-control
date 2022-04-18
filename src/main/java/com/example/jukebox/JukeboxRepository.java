package com.example.jukebox;

import org.springframework.data.jpa.repository.JpaRepository;

interface JukeboxRepository extends JpaRepository<Jukebox, Long> {
}
