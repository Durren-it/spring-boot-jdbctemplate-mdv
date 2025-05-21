package com.giuseppe.spring.jdbc.mysql.service;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;
import com.giuseppe.spring.jdbc.mysql.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorialServiceImpl implements ITutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    @Override
    public List<Tutorial> getAllTutorials(String title) {
        if (title == null) {
            return tutorialRepository.findAll();
        } else {
            return tutorialRepository.findByTitleContaining(title);
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
