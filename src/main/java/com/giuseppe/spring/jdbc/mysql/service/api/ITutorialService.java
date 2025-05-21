package com.giuseppe.spring.jdbc.mysql.service.api;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;

import java.util.List;

/**
 * Interfaccia per la gestione delle operazioni CRUD sui tutorial.
 *
 * <p>Questa interfaccia definisce i metodi per creare, recuperare, aggiornare ed
 * eliminare tutorial dal sistema. Include inoltre metodi per filtrare i tutorial
 * in base a criteri specifici (ad esempio, filtraggio per titolo e visualizzazione
 * dei soli tutorial pubblicati) e per gestire ordinamenti e limitazioni dei risultati.</p>
 *
 * <p>Le implementazioni di questa interfaccia sono attese per interagire con il
 * data store sottostante tramite JDBC o tecnologie similari.</p>
 */
public interface ITutorialService {

    /**
     * Restituisce tutti i tutorials, con possibilità di filtrare per titolo,
     * ordinare i risultati e limitarne il numero.
     *
     * <p>
     * Se il parametro {@code title} è fornito, verranno restituiti soltanto i tutorial
     * il cui titolo contiene la stringa specificata. Il parametro {@code orderBy} consente
     * di ordinare i risultati in base a uno dei campi validi ("id", "title", "description", "published").
     * In caso di valore non valido, il fallback sarà l'ordinamento per "id". Se {@code limit} è
     * fornito ed è maggiore di zero, verrà restituito al massimo quel numero di record.
     * </p>
     *
     * @param title   Filtro opzionale: se non {@code null} viene utilizzato per cercare tutorial
     *                contenenti tale stringa nel titolo.
     * @param orderBy Campo opzionale per ordinare i risultati. I valori ammessi sono "id", "title",
     *                "description" e "published". Se viene fornito un valore non valido, si utilizza "id".
     * @param limit   Numero massimo opzionale di tutorial da restituire; se {@code null} o non maggiore di zero,
     *                non viene applicato alcun limite.
     * @return Una lista dei tutorials che soddisfano i criteri di ricerca. In assenza di corrispondenze
     *         viene restituita una lista vuota.
     */
    List<Tutorial> getAllTutorials(String title, String orderBy, Integer limit);

    /**
     * Restituisce un tutorial dato il suo id.
     *
     * @param id Id del tutorial da recuperare.
     * @return Il tutorial corrispondente all'id specificato, oppure {@code null} se non esiste.
     */
    Tutorial getTutorialById(long id);

    /**
     * Crea un nuovo tutorial.
     *
     * <p>Il nuovo tutorial verrà creato con i dati forniti nel parametro;
     * l'attributo {@code published} verrà impostato a {@code false} per default.</p>
     *
     * @param tutorial L'oggetto {@code Tutorial} contenente i dati (titolo e descrizione) da salvare.
     * @return Il tutorial creato.
     */
    Tutorial createTutorial(Tutorial tutorial);

    /**
     * Aggiorna un tutorial esistente.
     *
     * @param id       Id del tutorial da aggiornare.
     * @param tutorial L'oggetto {@code Tutorial} contenente i dati aggiornati (titolo, descrizione e stato di pubblicazione).
     * @return Il tutorial aggiornato se l'operazione ha successo, oppure {@code null} se non esiste un tutorial
     *         con l'identificatore specificato.
     */
    Tutorial updateTutorial(long id, Tutorial tutorial);

    /**
     * Elimina un tutorial dato il suo id.
     *
     * @param id Id del tutorial da eliminare.
     * @return Il tutorial eliminato se l'operazione ha successo, oppure {@code null} se non è stato trovato
     *         un tutorial con l'id specificato.
     */
    Tutorial deleteTutorial(long id);

    /**
     * Elimina tutti i tutorials presenti nel sistema.
     *
     * @return {@code null} in quanto non viene restituito alcun tutorial dopo l'eliminazione.
     */
    Tutorial deleteAllTutorials();

    /**
     * Recupera solo i tutorials pubblicati.
     *
     * @return Una lista dei tutorials pubblicati. Se nessun tutorial è pubblicato, viene restituita una lista vuota.
     */
    List<Tutorial> findByPublished();
}
