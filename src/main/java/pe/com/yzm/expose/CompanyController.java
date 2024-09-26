package pe.com.yzm.expose;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.yzm.business.CompanyService;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.core.model.HeadersConstant;
import pe.com.yzm.expose.request.CompanyCreateRequest;
import pe.com.yzm.expose.request.CompanyUpdateRequest;
import pe.com.yzm.expose.response.CompanyResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: ConpanyController <br/>
 * <b>Copyright</b>: 2024 Yovanny Zeballos Medina<br/>.
 *
 * @author 2024  Yovanny Zeballos Medina <br/>
 * <u>Developed by</u>: Yovanny Zeballos <br/>
 * <u>Changes:</u><br/>
 * <ul>
 *   <li>
 *     setiembre 05, 2024 Creación de Clase.
 *   </li>
 * </ul>
 */
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/empresa")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/listar")
    public Flux<CompanyResponse> findAllCompanies(
        @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction
    ) {
        final var headerRequest = HeaderRequest.builder()
            .transactionId(idTransaction)
            .build();
        return companyService.findAllCompany(headerRequest);
    }

    @GetMapping("/listar/usuario/{id}")
    public Flux<CompanyResponse> findAllCompaniesByUser(
            @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
            @PathVariable(value = "id") Long id
    ) {
        final var headerRequest = HeaderRequest.builder()
                .transactionId(idTransaction)
                .build();
        return companyService.findAllCompanyByUser(headerRequest, id);
    }

    @GetMapping("/obtener/{id}")
    public Mono<CompanyResponse> findCompanyById(
        @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
        @PathVariable(value = "id") Long id
    ) {
        final var headerRequest = HeaderRequest.builder()
            .transactionId(idTransaction)
            .build();
        return companyService.findByCompanyId(headerRequest, id);
    }

    @PostMapping("/crear")
    public Mono<CompanyResponse> create(
        @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
        @Valid @RequestBody CompanyCreateRequest companyCreateRequest
    ) {
        final var headerRequest = HeaderRequest.builder()
            .transactionId(idTransaction)
            .build();
        return companyService.saveCompany(headerRequest, companyCreateRequest);
    }

    @PutMapping("/actualizar")
    public Mono<CompanyResponse> update(
        @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
        @Valid @RequestBody CompanyUpdateRequest companyRequest
    ) {
        final var headerRequest = HeaderRequest.builder()
            .transactionId(idTransaction)
            .build();
        return companyService.updateCompany(headerRequest, companyRequest);
    }

    @DeleteMapping("/eliminar/{id}")
    public Mono<Void> delete(
        @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
        @PathVariable(value = "id") Long id
    ) {
        final var headerRequest = HeaderRequest.builder()
            .transactionId(idTransaction)
            .build();
        return companyService.deleteCompany(headerRequest, id);
    }
}


