package pe.com.yzm.mapper;

import org.mapstruct.Mapper;
import pe.com.yzm.expose.request.ActivityCreateRequest;
import pe.com.yzm.expose.request.ActivityUpdateRequest;
import pe.com.yzm.expose.response.ActivityResponse;
import pe.com.yzm.model.Activity;

/**
 * <b>Class</b>: ActivityMapper <br/>
 * <b>Copyright</b>: 2024 Yovanny Zeballos Medina<br/>.
 *
 * @author 2024  Yovanny Zeballos Medina <br/>
 * <u>Developed by</u>: Yovanny Zeballos <br/>
 * <u>Changes:</u><br/>
 * <ul>
 *   <li>
 *     setiembre 06, 2024 Creación de Clase.
 *   </li>
 * </ul>
 */
@Mapper(componentModel = "spring")
public abstract class ActivityMapper {

    public abstract ActivityResponse activityToActivityResponse(Activity activity);
    public abstract Activity activityCreateRequestToActivity(ActivityCreateRequest activityRequest);

    public Activity activityUpdateRequestToActivity(Activity activity, ActivityUpdateRequest activityRequest) {
        activity.setUserActivityId(activityRequest.getUserActivityId());
        activity.setProjectId(activityRequest.getProjectId());
        activity.setCode(activityRequest.getCode());
        activity.setId(activityRequest.getId());
        activity.setDescription(activityRequest.getDescription());
        activity.setEffortHours(activityRequest.getEffortHours());
        activity.setHourBagId(activityRequest.getHourBagId());
        return activity;
    }
}
