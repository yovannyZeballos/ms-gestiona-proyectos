package pe.com.yzm.expose;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.com.yzm.business.RoleService;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.core.model.HeadersConstant;
import pe.com.yzm.expose.response.RoleResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: RoleController <br/>
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
@RequestMapping("/rol")
public class RoleController {

  private final RoleService roleService;

  @GetMapping("/listar")
  public Flux<RoleResponse> findAllRoles(
      @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction
  ) {
    final var headerRequest = HeaderRequest.builder()
        .transactionId(idTransaction)
        .build();

    return roleService.findAllRoles(headerRequest);

  }

  @GetMapping("/obtener/{id}")
  public Mono<RoleResponse> findRoleById(
      @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
      @PathVariable(value = "id") Long id
  ) {
    final var headerRequest = HeaderRequest.builder()
        .transactionId(idTransaction)
        .build();

    return roleService.findByRoleId(headerRequest, id);

  }

}
