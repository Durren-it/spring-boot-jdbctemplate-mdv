package com.giuseppe.spring.jdbc.mysql.controller;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;
import com.giuseppe.spring.jdbc.mysql.service.api.ITutorialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST responsabile della gestione degli endpoint relativi ai tutorials.
 * Fornisce API REST per operazioni CRUD (creazione, recupero, aggiornamento, eliminazione)
 * e funzionalità aggiuntive per il filtraggio (per titolo), l'ordinamento e la limitazione dei risultati.
 *
 * <p>
 * Endpoints disponibili:
 * <ul>
 *   <li>GET /api/tutorials - Recupera tutti i tutorials con eventuali filtri, ordinamento e limiti.</li>
 *   <li>GET /api/tutorials/{id} - Recupera un tutorial in base all'id.</li>
 *   <li>POST /api/tutorials - Crea un nuovo tutorial.</li>
 *   <li>PUT /api/tutorials/{id} - Aggiorna un tutorial esistente.</li>
 *   <li>DELETE /api/tutorials/{id} - Elimina un tutorial specifico.</li>
 *   <li>DELETE /api/tutorials - Elimina tutti i tutorials.</li>
 *   <li>GET /api/tutorials/published - Recupera solo i tutorials pubblicati.</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping("/api")
public class TutorialController {

  private final ITutorialService tutorialService;

  /**
   * Costruttore per l'iniezione del service.
   *
   * @param tutorialService il servizio per la gestione dei tutorial
   */
  public TutorialController(ITutorialService tutorialService) {
    this.tutorialService = tutorialService;
  }

  /**
   * Recupera tutti i tutorials dal database.
   * È possibile applicare un filtro per il titolo, ordinare i risultati in base a un campo specificato
   * e limitare il numero di record restituiti.
   *
   * @param title   Filtro opzionale per cercare tutorial che contengano la stringa specificata nel titolo.
   * @param orderBy Campo opzionale per ordinare i risultati. Valori ammessi: "id", "title", "description", "published".
   *                Se il valore passato non è valido, viene usato il fallback "id".
   * @param limit   Numero massimo opzionale di tutorial da restituire.
   * @return ResponseEntity contenente la lista dei tutorials e lo status HTTP 200 se risultati trovati,
   *         oppure HTTP 204 se la lista è vuota.
   */
  @GetMapping("/tutorials")
  public ResponseEntity<List<Tutorial>> getAllTutorials(
          @RequestParam(required = false) String title,
          @RequestParam(required = false) String orderBy,
          @RequestParam(required = false) Integer limit) {
    List<Tutorial> tutorials = tutorialService.getAllTutorials(title, orderBy, limit);
    if (tutorials.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(tutorials, HttpStatus.OK);
  }

  /**
   * Recupera un tutorial in base al suo id.
   *
   * @param id Id del tutorial da recuperare.
   * @return ResponseEntity contenente il tutorial trovato con status HTTP 200,
   *         oppure HTTP 404 se il tutorial non esiste.
   */
  @GetMapping("/tutorials/{id}")
  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
    Tutorial tutorial = tutorialService.getTutorialById(id);
    if (tutorial != null) {
      return new ResponseEntity<>(tutorial, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Crea un nuovo tutorial.
   * L'attributo "published" verrà inizializzato a false.
   *
   * @param tutorial Oggetto Tutorial contenente i dati da salvare.
   * @return ResponseEntity contenente il tutorial creato e lo status HTTP 201,
   *         oppure HTTP 500 in caso di errore durante la creazione.
   */
  @PostMapping("/tutorials")
  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
    try {
      Tutorial created = tutorialService.createTutorial(tutorial);
      return new ResponseEntity<>(created, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Aggiorna un tutorial esistente.
   *
   * @param id       Id del tutorial da aggiornare.
   * @param tutorial Oggetto Tutorial contenente i dati aggiornati.
   * @return ResponseEntity contenente il tutorial aggiornato con status HTTP 200,
   *         oppure una stringa di messaggio d'errore con status HTTP 404 se il tutorial non viene trovato.
   */
  @PutMapping("/tutorials/{id}")
  public ResponseEntity<?> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
    Tutorial updated = tutorialService.updateTutorial(id, tutorial);
    if (updated != null) {
      return new ResponseEntity<>(updated, HttpStatus.OK);
    } else {
      String errorMessage = "Cannot find tutorial with id=" + id;
      return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Elimina un tutorial specificato tramite il suo identificativo.
   *
   * @param id Id del tutorial da eliminare.
   * @return ResponseEntity contenente un messaggio di successo con status HTTP 200 se l'eliminazione ha successo,
   *         oppure un messaggio d'errore con status HTTP 404 se il tutorial non esiste,
   *         o HTTP 500 in caso di errore durante l'eliminazione.
   */
  @DeleteMapping("/tutorials/{id}")
  public ResponseEntity<String> deleteTutorial(@PathVariable("id") long id) {
    try {
      Tutorial deleted = tutorialService.deleteTutorial(id);
      if (deleted != null) {
        return new ResponseEntity<>("Tutorial was deleted successfully.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Cannot find Tutorial with id=" + id, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return new ResponseEntity<>("Cannot delete tutorial.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Elimina tutti i tutorials presenti nel database.
   *
   * @return ResponseEntity contenente un messaggio di successo con status HTTP 200 se l'operazione ha successo,
   *         oppure HTTP 500 in caso di errore.
   */
  @DeleteMapping("/tutorials")
  public ResponseEntity<String> deleteAllTutorials() {
    try {
      tutorialService.deleteAllTutorials();
      return new ResponseEntity<>("All tutorials were deleted successfully.", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Cannot delete tutorials.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Recupera tutti i tutorials che sono stati pubblicati.
   *
   * @return ResponseEntity contenente la lista dei tutorials pubblicati con status HTTP 200,
   *         oppure HTTP 204 se non vengono trovati record.
   */
  @GetMapping("/tutorials/published")
  public ResponseEntity<List<Tutorial>> findByPublished() {
    try {
      List<Tutorial> tutorials = tutorialService.findByPublished();
      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
