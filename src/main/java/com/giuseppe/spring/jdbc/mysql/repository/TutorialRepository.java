package com.giuseppe.spring.jdbc.mysql.repository;

import java.util.List;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;

public interface TutorialRepository {
  int save(Tutorial book);

  int update(Tutorial book);

  Tutorial findById(Long id);

  int deleteById(Long id);

  // Chiamata senza orderBy/limit
  List<Tutorial> findAll();

  // Chiamata per orderBy/limit
  List<Tutorial> findAll(String orderBy, Integer limit);

  List<Tutorial> findByPublished(boolean published);

  // Chiamata senza orderBy/limit
  List<Tutorial> findByTitleContaining(String title);

  // Chiamata per orderBy/limit
  List<Tutorial> findByTitleContaining(String title, String orderBy, Integer limit);

  int deleteAll();
}
