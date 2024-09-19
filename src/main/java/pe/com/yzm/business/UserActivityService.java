package pe.com.yzm.business;

import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.request.UserActivityCreateRequest;
import pe.com.yzm.expose.request.UserActivityUpdateRequest;
import pe.com.yzm.expose.response.UserActivityResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: UserActivityService <br/>
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
public interface UserActivityService {
    /**
     * Finds a UserActivity by its ID.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @param id            The ID of the UserActivity to find.
     * @return A Mono emitting the UserActivityResponse for the found UserActivity, or Mono.empty() if no UserActivity was found.
     */
    Mono<UserActivityResponse> findById(HeaderRequest headerRequest, Long id);

    /**
     * Finds all UserActivities.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @return A Flux emitting the UserActivityResponses for all found UserActivities.
     */
    Flux<UserActivityResponse> findAll(HeaderRequest headerRequest);

    /**
     * Creates a new UserActivity.
     *
     * @param headerRequest       The request header containing metadata for the request.
     * @param userActivityRequest The request containing the data for the UserActivity to create.
     * @return A Mono emitting the UserActivityResponse for the created UserActivity.
     */
    Mono<UserActivityResponse> create(HeaderRequest headerRequest, UserActivityCreateRequest userActivityRequest);

    /**
     * Updates an existing UserActivity.
     *
     * @param headerRequest       The request header containing metadata for the request.
     * @param userActivityRequest The request containing the updated data for the UserActivity.
     * @return A Mono emitting the UserActivityResponse for the updated UserActivity.
     */
    Mono<UserActivityResponse> update(HeaderRequest headerRequest, UserActivityUpdateRequest userActivityRequest);

    /**
     * Deletes a UserActivity by its ID.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @param id            The ID of the UserActivity to delete.
     * @return A Mono emitting completion signal once the UserActivity is deleted, or Mono.empty() if no UserActivity was found.
     */
    Mono<Void> delete(HeaderRequest headerRequest, Long id);
}
