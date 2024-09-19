package pe.com.yzm.mapper;

import org.mapstruct.Mapper;
import pe.com.yzm.expose.response.RoleResponse;
import pe.com.yzm.model.Role;

/**
 * <b>Class</b>: RoleMapper <br/>
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
@Mapper(componentModel = "spring")
public abstract class RoleMapper {

  /**
   * Converts a Role entity to a RoleResponse DTO.
   * <p>
   * This method is used when we want to send data to the client. The Role entity
   * contains all the data about a role, but we may not want to expose all this
   * information to the client. Therefore, we create a RoleResponse DTO containing
   * only the data we want to send, and this method handles the conversion from
   * Role to RoleResponse.
   *
   * @param role the Role entity to convert
   * @return a RoleResponse DTO containing the data to be sent to the client
   */
  public abstract RoleResponse toRoleResponse(Role role);

}
