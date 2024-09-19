package pe.com.yzm.business;

import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.response.RoleResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: RoleService <br/>
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
public interface RoleService {

  /**
   * Retrieves a Rol entity by its ID.
   *
   * @param roleId the ID of the Rol entity to retrieve
   * @return a Mono that emits the Rol entity if found, or Mono.empty() if not found
   */
  Mono<RoleResponse> findByRoleId(HeaderRequest headerRequest, Long roleId);

  /**
   * Retrieves all Rol entities.
   *
   * @return a Flux that emits all Rol entities
   */
  Flux<RoleResponse> findAllRoles(HeaderRequest headerRequest);

}
