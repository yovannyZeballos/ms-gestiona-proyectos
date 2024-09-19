package pe.com.yzm.business;

import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.request.ActivityCreateRequest;
import pe.com.yzm.expose.request.ActivityUpdateRequest;
import pe.com.yzm.expose.response.ActivityResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: ActivityService <br/>
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
public interface ActivityService {
    /**
     * Finds an activity by its ID.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @param id            The ID of the activity to find.
     * @return A Mono emitting the ActivityResponse for the found activity, or Mono.empty() if no activity was found.
     */
    Mono<ActivityResponse> findById(HeaderRequest headerRequest, Long id);

    /**
     * Finds all activities.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @return A Flux emitting the ActivityResponses for all found activities.
     */
    Flux<ActivityResponse> findAll(HeaderRequest headerRequest);

    /**
     * Creates a new activity.
     *
     * @param headerRequest   The request header containing metadata for the request.
     * @param activityRequest The request containing the data for the activity to create.
     * @return A Mono emitting the ActivityResponse for the created activity.
     */
    Mono<ActivityResponse> create(HeaderRequest headerRequest, ActivityCreateRequest activityRequest);

    /**
     * Updates an existing activity.
     *
     * @param headerRequest   The request header containing metadata for the request.
     * @param activityRequest The request containing the updated data for the activity.
     * @return A Mono emitting the ActivityResponse for the updated activity.
     */
    Mono<ActivityResponse> update(HeaderRequest headerRequest, ActivityUpdateRequest activityRequest);

    /**
     * Deletes an activity by its ID.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @param id            The ID of the activity to delete.
     * @return A Mono emitting completion signal once the activity is deleted, or Mono.empty() if no activity was found.
     */
    Mono<Void> delete(HeaderRequest headerRequest, Long id);
}
