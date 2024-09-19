package pe.com.yzm.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.com.yzm.business.ProjectService;
import pe.com.yzm.core.exception.BusinessException;
import pe.com.yzm.core.logger.LoggerUtil;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.request.ProjectCreateRequest;
import pe.com.yzm.expose.request.ProjectUpdateRequest;
import pe.com.yzm.expose.response.ProjectResponse;
import pe.com.yzm.mapper.ProjectMapper;
import pe.com.yzm.repository.ProjectRepository;
import pe.com.yzm.util.ConstantMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <b>Class</b>: ProjectServiceImpl <br/>
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
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    // Reference to the ProjectRepository to perform CRUD operations
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    /**
     * Fetches all projects.
     *
     * @param headerRequest The header request containing necessary request metadata.
     * @return A Flux stream of ProjectResponse objects representing all projects.
     */
    @Override
    public Flux<ProjectResponse> findAllProjects(HeaderRequest headerRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), null);
        return projectRepository.findAll()
                .map(projectMapper::projectToProjectResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Fetches a project by its ID.
     *
     * @param headerRequest The header request containing necessary request metadata.
     * @param id            The ID of the project to fetch.
     * @return A Mono of ProjectResponse object representing the project found, or empty if no project is found.
     */
    @Override
    public Mono<ProjectResponse> findByProjectId(HeaderRequest headerRequest, Long id) {
        return projectRepository.findById(id)
                .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.PROJECT_GET_ERROR,
                        List.of(ConstantMessage.PROJECT_NOT_FOUND), HttpStatus.NOT_FOUND)))
                .map(projectMapper::projectToProjectResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Saves a new project.
     *
     * @param headerRequest  The header request containing necessary request metadata.
     * @param projectRequest The request object containing the details of the project to be saved.
     * @return A Mono of ProjectResponse object representing the saved project.
     */
    @Override
    public Mono<ProjectResponse> saveProject(HeaderRequest headerRequest, ProjectCreateRequest projectRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), projectRequest);
        return projectRepository.save(projectMapper.projectCreateRequestToProject(projectRequest))
                .map(projectMapper::projectToProjectResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Updates an existing project.
     *
     * @param headerRequest  The header request containing necessary request metadata.
     * @param projectRequest The request object containing the updated details of the project.
     * @return A Mono of ProjectResponse object representing the updated project.
     */
    @Override
    public Mono<ProjectResponse> updateProject(HeaderRequest headerRequest, ProjectUpdateRequest projectRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), projectRequest);
        return projectRepository.findById(projectRequest.getId())
                .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.PROJECT_UPDATE_ERROR,
                        List.of(ConstantMessage.PROJECT_NOT_FOUND), HttpStatus.NOT_FOUND)))
                .map(project -> projectMapper.projectUpdateRequestToProject(project, projectRequest))
                .flatMap(projectRepository::save)
                .map(projectMapper::projectToProjectResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Deletes a project by its ID.
     *
     * @param headerRequest The header request containing necessary request metadata.
     * @param id            The ID of the project to be deleted.
     * @return A Mono of Void indicating the completion of the deletion operation.
     */
    @Override
    public Mono<Void> deleteProject(HeaderRequest headerRequest, Long id) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), id);
        return projectRepository.findById(id)
                .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.PROJECT_DELETE_ERROR,
                        List.of(ConstantMessage.PROJECT_NOT_FOUND), HttpStatus.NOT_FOUND)))
                .flatMap(projectRepository::delete)
                .doOnSuccess(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), null))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }
}
