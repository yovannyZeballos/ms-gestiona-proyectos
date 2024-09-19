package pe.com.yzm.expose;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.com.yzm.business.HourBagService;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.core.model.HeadersConstant;
import pe.com.yzm.expose.request.HourBagCreateRequest;
import pe.com.yzm.expose.request.HourBagUpdateRequest;
import pe.com.yzm.expose.response.HourBagResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: HourBagController <br/>
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
@RequestMapping("/bolsa-horas")
public class HourBagController {

    private final HourBagService hourBagService;

    /**
     * Handles GET requests to retrieve all HourBag entities.
     *
     * @param idTransaction The transaction ID from the request header.
     * @return A Flux emitting all HourBagResponse entities.
     */
    @GetMapping("/listar")
    public Flux<HourBagResponse> findAll(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return hourBagService.findAll(headerRequest);
    }

    /**
     * Handles GET requests to retrieve all active HourBag entities.
     *
     * @param idTransaction The transaction ID from the request header.
     * @return A Flux emitting all active HourBagResponse entities.
     */
    @GetMapping("/listar-activos")
    public Flux<HourBagResponse> findAllActives(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return hourBagService.findAllActive(headerRequest);
    }

    /**
     * Handles POST requests to create a new HourBag entity.
     *
     * @param idTransaction        The transaction ID from the request header.
     * @param hourBagCreateRequest The request body containing the data for the new HourBag.
     * @return A Mono emitting the created HourBagResponse entity.
     */
    @PostMapping("/crear")
    public Mono<HourBagResponse> create(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @Valid @RequestBody HourBagCreateRequest hourBagCreateRequest
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return hourBagService.create(headerRequest, hourBagCreateRequest);
    }

    /**
     * Handles PUT requests to update an existing HourBag entity.
     *
     * @param idTransaction  The transaction ID from the request header.
     * @param hourBagRequest The request body containing the updated data for the HourBag.
     * @return A Mono emitting the updated HourBagResponse entity.
     */
    @PutMapping("/actualizar")
    public Mono<HourBagResponse> update(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @Valid @RequestBody HourBagUpdateRequest hourBagRequest
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return hourBagService.update(headerRequest, hourBagRequest);
    }

    /**
     * Handles DELETE requests to delete an existing HourBag entity.
     *
     * @param idTransaction The transaction ID from the request header.
     * @param id            The ID of the HourBag to delete.
     * @return A Mono emitting completion signal when the HourBag is deleted.
     */
    @DeleteMapping("/eliminar/{id}")
    public Mono<Void> delete(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @PathVariable(value = "id") Long id
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return hourBagService.delete(headerRequest, id);
    }
}
