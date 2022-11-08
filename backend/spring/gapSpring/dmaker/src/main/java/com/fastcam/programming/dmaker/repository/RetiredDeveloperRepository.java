package com.fastcam.programming.dmaker.repository;

import com.fastcam.programming.dmaker.entity.Developer;
import com.fastcam.programming.dmaker.entity.RetiredDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetiredDeveloperRepository extends JpaRepository<RetiredDeveloper, Long> {

}
