package com.giuseppe.spring.jdbc.mysql.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;

@Repository
public class JdbcTutorialRepository implements TutorialRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public int save(Tutorial tutorial) {
    return jdbcTemplate.update("INSERT INTO tutorials (title, description, published) VALUES(?,?,?)",
            tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished());
  }

  @Override
  public int update(Tutorial tutorial) {
    return jdbcTemplate.update("UPDATE tutorials SET title=?, description=?, published=? WHERE id=?",
            tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), tutorial.getId());
  }

  @Override
  public Tutorial findById(Long id) {
    try {
      Tutorial tutorial = jdbcTemplate.queryForObject("SELECT * FROM tutorials WHERE id=?",
          BeanPropertyRowMapper.newInstance(Tutorial.class), id);

      return tutorial;
    } catch (IncorrectResultSizeDataAccessException e) {
      return null;
    }
  }

  @Override
  public int deleteById(Long id) {
    return jdbcTemplate.update("DELETE FROM tutorials WHERE id=?", id);
  }

  @Override
  public List<Tutorial> findAll(String orderBy, Integer limit) {
    String sql = "SELECT * from tutorials";
    if (orderBy != null && !orderBy.isEmpty()) {
      // Valida che orderBy abbia un valore atteso, altrimenti usa un default
      if (!orderBy.equalsIgnoreCase("id") &&
              !orderBy.equalsIgnoreCase("title") &&
              !orderBy.equalsIgnoreCase("description") &&
              !orderBy.equalsIgnoreCase("published")) {
        orderBy = "id";  // fallback
      }
      sql += " ORDER BY " + orderBy;
    }
    if (limit != null && limit > 0) {
      sql += " LIMIT " + limit;
    }
    return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Tutorial.class));
  }

  // Implementazione della chiamata senza orderBy/limit
  @Override
  public List<Tutorial> findAll() {
    String sql = "SELECT * FROM tutorials";
    return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Tutorial.class));
  }

  @Override
  public List<Tutorial> findByPublished(boolean published) {
    return jdbcTemplate.query("SELECT * from tutorials WHERE published=?",
        BeanPropertyRowMapper.newInstance(Tutorial.class), published);
  }

  @Override
  public List<Tutorial> findByTitleContaining(String title, String orderBy, Integer limit) {
    String sql = "SELECT * from tutorials WHERE title LIKE ?";

    // Aggiunge ORDER BY se specificato
    if (orderBy != null && !orderBy.isEmpty()) {
      // Validazione su orderBy per evitare SQL injection, ammessi solo alcuni nomi di colonna
      if (!orderBy.equalsIgnoreCase("id") &&
              !orderBy.equalsIgnoreCase("title") &&
              !orderBy.equalsIgnoreCase("description") &&
              !orderBy.equalsIgnoreCase("published")) {
        orderBy = "id";  // fallback di default
      }
      sql += " ORDER BY " + orderBy;
    }

    // Aggiunge LIMIT se specificato
    if (limit != null && limit > 0) {
      sql += " LIMIT " + limit;
    }

    return jdbcTemplate.query(
            sql,
            BeanPropertyRowMapper.newInstance(Tutorial.class),
            "%" + title + "%"
    );
  }

  // Implementazione della chiamata senza orderBy/limit
  @Override
  public List<Tutorial> findByTitleContaining(String title) {
    return findByTitleContaining(title, null, null);
  }

  @Override
  public int deleteAll() {
    return jdbcTemplate.update("DELETE from tutorials");
  }
}
