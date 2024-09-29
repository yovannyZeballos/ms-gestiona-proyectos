package pe.com.yzm.expose;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.com.yzm.business.ProjectService;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.core.model.HeadersConstant;
import pe.com.yzm.expose.request.ProjectCreateRequest;
import pe.com.yzm.expose.request.ProjectUpdateRequest;
import pe.com.yzm.expose.response.ProjectCompanyResponse;
import pe.com.yzm.expose.response.ProjectResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: ProjectController <br/>
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
@RestController
@RequestMapping("/proyecto")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/listar/user/{id}")
    public Flux<ProjectCompanyResponse> findAllProjects(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @PathVariable(value = "id") Long id
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return projectService.findAllProjectsByUserId(headerRequest, id);
    }

    @GetMapping("/obtener/{id}")
    public Mono<ProjectResponse> findProjectById(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @PathVariable(value = "id") Long id
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return projectService.findByProjectId(headerRequest, id);
    }

    @PostMapping("/crear")
    public Mono<ProjectResponse> create(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @Valid @RequestBody ProjectCreateRequest projectCreateRequest
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return projectService.saveProject(headerRequest, projectCreateRequest);
    }

    @PutMapping("/actualizar")
    public Mono<ProjectResponse> update(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @Valid @RequestBody ProjectUpdateRequest projectRequest
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return projectService.updateProject(headerRequest, projectRequest);
    }

    @DeleteMapping("/eliminar/{id}")
    public Mono<Void> delete(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @PathVariable(value = "id") Long id
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return projectService.deleteProject(headerRequest, id);
    }
}
