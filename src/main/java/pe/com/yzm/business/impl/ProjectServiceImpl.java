package pe.com.yzm.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.com.yzm.business.CompanyService;
import pe.com.yzm.business.ProjectService;
import pe.com.yzm.core.exception.BusinessException;
import pe.com.yzm.core.logger.LoggerUtil;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.request.ProjectCreateRequest;
import pe.com.yzm.expose.request.ProjectUpdateRequest;
import pe.com.yzm.expose.response.ProjectCompanyResponse;
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
    private final CompanyService companyService;

    /**
     * Fetches all projects that belong to a user.
     *
     * This method first fetches all companies that belong to the user specified by the userId parameter.
     * Then, for each company, it fetches all projects that belong to that company.
     * The result is a Flux of ProjectResponse objects, each representing a project that belongs to a company of the user.
     *
     * @param headerRequest The header request containing necessary request metadata.
     * @param userId The ID of the user for whom to fetch the projects.
     * @return A Flux of ProjectResponse objects representing the projects found.
     */
    @Override
    public Flux<ProjectCompanyResponse> findAllProjectsByUserId(HeaderRequest headerRequest, Long userId) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), userId);

        return companyService.findAllCompanyByUser(headerRequest, userId)
                .flatMap(company -> projectRepository.findAllByCompanyId(company.getId())
                        .map(project -> projectMapper.projectToProjectCompanyResponse(project, company.getName())))
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
