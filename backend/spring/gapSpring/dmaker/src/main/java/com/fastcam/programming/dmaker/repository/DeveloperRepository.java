package com.fastcam.programming.dmaker.repository;

import com.fastcam.programming.dmaker.code.StatusCode;
import com.fastcam.programming.dmaker.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    Optional<Developer> findByMemberId(String memberId);
    List<Developer> findDevelopersByStatusCodeEquals(StatusCode statusCode);
}
