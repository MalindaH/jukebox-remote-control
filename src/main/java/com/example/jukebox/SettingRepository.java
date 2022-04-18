package com.example.jukebox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface SettingRepository extends JpaRepository<Setting, UUID> {
}
