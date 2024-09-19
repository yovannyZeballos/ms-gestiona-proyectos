package pe.com.yzm.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.com.yzm.model.User;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: UserRepository <br/>
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
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

  Mono<Boolean> existsByEmail(String email);
  Mono<User> findByEmail(String email);
}
