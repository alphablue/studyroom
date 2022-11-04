package com.fastcam.programming.dmaker.repository;

import com.fastcam.programming.dmaker.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

}
