package pe.com.yzm.business;

import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.request.ProjectCreateRequest;
import pe.com.yzm.expose.request.ProjectUpdateRequest;
import pe.com.yzm.expose.response.ProjectResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: ProjectService <br/>
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
public interface ProjectService {

    /**
     * Fetches all projects.
     *
     * @param headerRequest The header request containing necessary request metadata.
     * @return A Flux stream of ProjectResponse objects representing all projects.
     */
    Flux<ProjectResponse> findAllProjects(HeaderRequest headerRequest);

    /**
     * Fetches a project by its ID.
     *
     * @param headerRequest The header request containing necessary request metadata.
     * @param id            The ID of the project to fetch.
     * @return A Mono of ProjectResponse object representing the project found, or empty if no project is found.
     */
    Mono<ProjectResponse> findByProjectId(HeaderRequest headerRequest, Long id);

    /**
     * Saves a new project.
     *
     * @param headerRequest  The header request containing necessary request metadata.
     * @param projectRequest The request object containing the details of the project to be saved.
     * @return A Mono of ProjectResponse object representing the saved project.
     */
    Mono<ProjectResponse> saveProject(HeaderRequest headerRequest, ProjectCreateRequest projectRequest);

    /**
     * Updates an existing project.
     *
     * @param headerRequest  The header request containing necessary request metadata.
     * @param projectRequest The request object containing the updated details of the project.
     * @return A Mono of ProjectResponse object representing the updated project.
     */
    Mono<ProjectResponse> updateProject(HeaderRequest headerRequest, ProjectUpdateRequest projectRequest);

    /**
     * Deletes a project by its ID.
     *
     * @param headerRequest The header request containing necessary request metadata.
     * @param id            The ID of the project to be deleted.
     * @return A Mono of Void indicating the completion of the deletion operation.
     */
    Mono<Void> deleteProject(HeaderRequest headerRequest, Long id);
}
