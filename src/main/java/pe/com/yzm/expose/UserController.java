package pe.com.yzm.expose;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.yzm.business.UserService;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.core.model.HeadersConstant;
import pe.com.yzm.expose.request.UserCreateRequest;
import pe.com.yzm.expose.request.UserUpdateRequest;
import pe.com.yzm.expose.response.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: UserController <br/>
 * <b>Copyright</b>: 2024 Yovanny Zeballos Medina<br/>.
 *
 * @author 2024  Yovanny Zeballos Medina <br/>
 * <u>Developed by</u>: Yovanny Zeballos <br/>
 * <u>Changes:</u><br/>
 * <ul>
 *   <li>
 *     setiembre 04, 2024 Creación de Clase.
 *   </li>
 * </ul>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
@Validated
public class UserController {

  private final UserService userService;

  @GetMapping("/listar")
  public Flux<UserResponse> findAllUsers(
      @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction
  ) {
    final var headerRequest = HeaderRequest.builder()
        .transactionId(idTransaction)
        .build();
    return userService.findAllUsers(headerRequest);
  }

  @GetMapping("/obtener/{id}")
  public Mono<UserResponse> findUserById(
      @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
      @PathVariable(value = "id") Long id
  ) {
    final var headerRequest = HeaderRequest.builder()
        .transactionId(idTransaction)
        .build();
    return userService.findUserById(headerRequest, id);
  }

  @PostMapping("/crear")
  public Mono<UserResponse> create(
      @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
      @Valid @RequestBody UserCreateRequest userRequest
  ){
    final var headerRequest = HeaderRequest.builder()
        .transactionId(idTransaction)
        .build();
    return userService.saveUser(headerRequest, userRequest);
  }

  @PutMapping("/actualizar")
  public Mono<UserResponse> actualizar(
      @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
      @Valid @RequestBody UserUpdateRequest userRequest
  ){
    final var headerRequest = HeaderRequest.builder()
        .transactionId(idTransaction)
        .build();
    return userService.updateUser(headerRequest, userRequest);
  }

  @DeleteMapping("/eliminar/{id}")
  public Mono<Void> delete(
      @RequestHeader(value = HeadersConstant.TRANSACTION_ID) String idTransaction,
      @PathVariable(value = "id") Long id
  ){
    final var headerRequest = HeaderRequest.builder()
        .transactionId(idTransaction)
        .build();
    return userService.deleteUser(headerRequest, id);
  }

}
