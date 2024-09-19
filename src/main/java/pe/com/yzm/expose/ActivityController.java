package pe.com.yzm.expose;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.com.yzm.business.ActivityService;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.core.model.HeadersConstant;
import pe.com.yzm.expose.request.ActivityCreateRequest;
import pe.com.yzm.expose.request.ActivityUpdateRequest;
import pe.com.yzm.expose.response.ActivityResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: ActivityController <br/>
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
@RequestMapping("/actividad")
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/listar")
    public Flux<ActivityResponse> findAll(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return activityService.findAll(headerRequest);
    }

    @GetMapping("/obtener/{id}")
    public Mono<ActivityResponse> findById(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @PathVariable(value = "id") Long id
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return activityService.findById(headerRequest, id);
    }

    @PostMapping("/crear")
    public Mono<ActivityResponse> create(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @Valid @RequestBody ActivityCreateRequest activityCreateRequest
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return activityService.create(headerRequest, activityCreateRequest);
    }

    @PutMapping("/actualizar")
    public Mono<ActivityResponse> update(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @Valid @RequestBody ActivityUpdateRequest activityRequest
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return activityService.update(headerRequest, activityRequest);
    }

    @DeleteMapping("/eliminar/{id}")
    public Mono<Void> delete(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @PathVariable(value = "id") Long id
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return activityService.delete(headerRequest, id);
    }
    
}
