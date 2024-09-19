package pe.com.yzm.expose;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.com.yzm.business.UserActivityService;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.core.model.HeadersConstant;
import pe.com.yzm.expose.request.UserActivityCreateRequest;
import pe.com.yzm.expose.request.UserActivityUpdateRequest;
import pe.com.yzm.expose.response.UserActivityResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: UserActivityController <br/>
 * <b>Copyright</b>: 2024 Yovanny Zeballos Medina<br/>.
 *
 * @author 2024  Yovanny Zeballos Medina <br/>
 * <u>Developed by</u>: Yovanny Zeballos <br/>
 * <u>Changes:</u><br/>
 * <ul>
 *   <li>
 *     setiembre 07, 2024 Creación de Clase.
 *   </li>
 * </ul>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario-actividad")
public class UserActivityController {

    private final UserActivityService userActivityService;

    @GetMapping("/listar")
    public Flux<UserActivityResponse> findAll(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return userActivityService.findAll(headerRequest);
    }

    @GetMapping("/obtener/{id}")
    public Mono<UserActivityResponse> findById(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @PathVariable(value = "id") Long id
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return userActivityService.findById(headerRequest, id);
    }

    @PostMapping("/crear")
    public Mono<UserActivityResponse> create(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @Valid @RequestBody UserActivityCreateRequest userActivityCreateRequest
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return userActivityService.create(headerRequest, userActivityCreateRequest);
    }

    @PutMapping("/actualizar")
    public Mono<UserActivityResponse> update(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @Valid @RequestBody UserActivityUpdateRequest userActivityRequest
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return userActivityService.update(headerRequest, userActivityRequest);
    }

    @DeleteMapping("/eliminar/{id}")
    public Mono<Void> delete(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @PathVariable(value = "id") Long id
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return userActivityService.delete(headerRequest, id);
    }
    
}
