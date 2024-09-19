package pe.com.yzm.business;

import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.request.UserCreateRequest;
import pe.com.yzm.expose.request.UserUpdateRequest;
import pe.com.yzm.expose.response.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: UserService <br/>
 * <b>Copyright</b>: 2024 Yovanny Zeballos Medina<br/>.
 *
 * @author 2024  Yovanny Zeballos Medina <br/>
 * <u>Developed by</u>: Yovanny Zeballos <br/>
 * <u>Changes:</u><br/>
 * <ul>
 *   <li>
 *     setiembre 03, 2024 Creación de Clase.
 *   </li>
 * </ul>
 */
public interface UserService {

  Mono<UserResponse> findUserById(HeaderRequest headerRequest, Long userId);

  Flux<UserResponse> findAllUsers(HeaderRequest headerRequest);

  Mono<UserResponse> saveUser(HeaderRequest headerRequest, UserCreateRequest user);

  Mono<UserResponse> updateUser(HeaderRequest headerRequest, UserUpdateRequest user);

  Mono<Void> deleteUser(HeaderRequest headerRequest, Long userId);


}
