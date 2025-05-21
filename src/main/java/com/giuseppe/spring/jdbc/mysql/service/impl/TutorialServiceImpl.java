package com.giuseppe.spring.jdbc.mysql.service.impl;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;
import com.giuseppe.spring.jdbc.mysql.repository.TutorialRepository;
import com.giuseppe.spring.jdbc.mysql.service.api.ITutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorialServiceImpl implements ITutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    @Override
    public List<Tutorial> getAllTutorials(String title, String orderBy, Integer limit) {
        if (title == null) {
            if (orderBy != null || limit != null) {
                return tutorialRepository.findAll(orderBy, limit);
            } else {
                return tutorialRepository.findAll();
            }
        } else {
            if (orderBy != null || limit != null) {
                return tutorialRepository.findByTitleContaining(title, orderBy, limit);
            } else {
                // Nel caso non siano presenti orderBy e limit si usa il metodo a un parametro
                return tutorialRepository.findByTitleContaining(title);
            }
        }
    }


    @Override
    public Tutorial getTutorialById(long id) {
        return tutorialRepository.findById(id);
    }

    @Override
    public Tutorial createTutorial(Tutorial tutorial) {
        Tutorial newTutorial = new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false);
        tutorialRepository.save(newTutorial);
        return newTutorial;
    }

    @Override
    public Tutorial updateTutorial(long id, Tutorial tutorial) {
        Tutorial existingTutorial = tutorialRepository.findById(id);
        if (existingTutorial != null) {
            existingTutorial.setId(id);
            existingTutorial.setTitle(tutorial.getTitle());
            existingTutorial.setDescription(tutorial.getDescription());
            existingTutorial.setPublished(tutorial.isPublished());
            tutorialRepository.update(existingTutorial);
            return existingTutorial;
        }
        return null;
    }

    @Override
    public Tutorial deleteTutorial(long id) {
        Tutorial tutorial = tutorialRepository.findById(id);
        if (tutorial != null) {
            tutorialRepository.deleteById(id);
            return tutorial;
        }
        return null;
    }

    @Override
    public Tutorial deleteAllTutorials() {
        tutorialRepository.deleteAll();
        return null;
    }

    @Override
    public List<Tutorial> findByPublished() {
        return tutorialRepository.findByPublished(true);
    }
}
