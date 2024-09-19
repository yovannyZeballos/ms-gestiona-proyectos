package pe.com.yzm.business;


/**
 * <b>Class</b>: PasswordService <br/>
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
public interface PasswordService {

  /**
   * Encodes the provided raw password.
   *
   * @param rawPassword The raw password to be encoded.
   * @return The encoded password.
   */
  String encode(String rawPassword);

  /**
   * Checks if the provided raw password matches the encoded password.
   *
   * @param rawPassword     The raw password to be checked.
   * @param encodedPassword The encoded password to be matched with.
   * @return True if the raw password matches the encoded password, false otherwise.
   */
  Boolean matches(String rawPassword, String encodedPassword);
}
