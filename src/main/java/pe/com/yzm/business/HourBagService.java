package pe.com.yzm.business;

import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.request.HourBagCreateRequest;
import pe.com.yzm.expose.request.HourBagUpdateRequest;
import pe.com.yzm.expose.response.HourBagResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: HourBagService <br/>
 * <b>Copyright</b>: 2024 Yovanny Zeballos Medina<br/>.
 *
 * @author 2024  Yovanny Zeballos Medina <br/>
 * <u>Developed by</u>: Yovanny Zeballos <br/>
 * <u>Changes:</u><br/>
 * <ul>
 *   <li>
 *     setiembre 06, 2024 Creación de Clase.
 *   </li>
 * </ul>
 */
public interface HourBagService {


    /**
     * Finds all active HourBags.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @return A Flux emitting the HourBagResponses for all found active HourBags.
     */
    Flux<HourBagResponse> findAllActive(HeaderRequest headerRequest);

    /**
     * Finds all HourBags.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @return A Flux emitting the HourBagResponses for all found HourBags.
     */
    Flux<HourBagResponse> findAll(HeaderRequest headerRequest);

    /**
     * Creates a new HourBag.
     *
     * @param headerRequest  The request header containing metadata for the request.
     * @param hourBagRequest The request containing the data for the HourBag to create.
     * @return A Mono emitting the HourBagResponse for the created HourBag.
     */
    Mono<HourBagResponse> create(HeaderRequest headerRequest, HourBagCreateRequest hourBagRequest);

    /**
     * Updates an existing HourBag.
     *
     * @param headerRequest  The request header containing metadata for the request.
     * @param hourBagRequest The request containing the updated data for the HourBag.
     * @return A Mono emitting the HourBagResponse for the updated HourBag.
     */
    Mono<HourBagResponse> update(HeaderRequest headerRequest, HourBagUpdateRequest hourBagRequest);

    /**
     * Deletes a HourBag by its ID.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @param id            The ID of the HourBag to delete.
     * @return A Mono emitting completion signal once the HourBag is deleted, or Mono.empty() if no HourBag was found.
     */
    Mono<Void> delete(HeaderRequest headerRequest, Long id);
}
