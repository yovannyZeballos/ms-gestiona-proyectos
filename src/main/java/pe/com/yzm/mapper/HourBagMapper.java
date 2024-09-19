package pe.com.yzm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.com.yzm.expose.request.ActivityUpdateRequest;
import pe.com.yzm.expose.request.HourBagCreateRequest;
import pe.com.yzm.expose.request.HourBagUpdateRequest;
import pe.com.yzm.expose.response.HourBagResponse;
import pe.com.yzm.model.Activity;
import pe.com.yzm.model.HourBag;

/**
 * <b>Class</b>: HourBagMapper <br/>
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
@Mapper(componentModel = "spring")
public abstract class HourBagMapper {

    public abstract HourBagResponse hourBagToHourBagResponse(HourBag hourBag);

    @Mapping(target = "consumedHours", constant = "0")
    @Mapping(target = "status", constant = "true")
    public abstract HourBag hourBagCreateRequestToHourBag(HourBagCreateRequest hourRequest);

    public HourBag hourBagUpdateRequestToHourBag(HourBag hourBag, HourBagUpdateRequest hourBagRequest) {
        hourBag.setHours(hourBagRequest.getHours());
        hourBag.setHourCost(hourBagRequest.getHourCost());
        hourBag.setId(hourBagRequest.getId());
        hourBag.setRegistrationDate(hourBagRequest.getRegistrationDate());
        return hourBag;
    }
}
