package com.giuseppe.spring.jdbc.mysql.service.api;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;

import java.util.List;

/**
 * Interfaccia per la gestione delle operazioni CRUD dei tutorials.
 */
public interface ITutorialService {

    /**
     *Restituisce tutti i tutorials.
     */
    List<Tutorial> getAllTutorials(String title, String orderBy, Integer limit);

    /**
     *Restituisce un tutorial dato il suo id.
     */
    Tutorial getTutorialById(long id);

    /**
     *Crea un nuovo tutorial.
     */
    Tutorial createTutorial(Tutorial tutorial);

    /**
     *Aggiorna un tutorial esistente.
     */
    Tutorial updateTutorial(long id, Tutorial tutorial);

    /**
     *Elimina un tutorial dato il suo id.
     */
    Tutorial deleteTutorial(long id);

    /**
     *Elimina tutti i tutorials.
     */
    Tutorial deleteAllTutorials();

    /**
     *Restituisce solo i tutorials pubblicati.
     */
    List<Tutorial> findByPublished();
}
