package pe.com.yzm.mapper;

import org.mapstruct.Mapper;
import pe.com.yzm.expose.request.ActivityCreateRequest;
import pe.com.yzm.expose.request.ActivityUpdateRequest;
import pe.com.yzm.expose.request.UserActivityCreateRequest;
import pe.com.yzm.expose.request.UserActivityUpdateRequest;
import pe.com.yzm.expose.response.UserActivityResponse;
import pe.com.yzm.model.Activity;
import pe.com.yzm.model.UserActivity;

@Mapper(componentModel = "spring")
public abstract class UserActivityMapper {
    public abstract UserActivityResponse userActivityToUserActivityResponse(UserActivity userActivity);

    public abstract UserActivity activityCreateRequestToActivity(UserActivityCreateRequest userActivityRequest);

    public UserActivity userActivityUpdateRequestToUserActivity(UserActivity userActivity, UserActivityUpdateRequest userActivityRequest) {
        userActivity.setId(userActivityRequest.getId());
        userActivity.setUserId(userActivityRequest.getUserId());
        userActivity.setName(userActivityRequest.getName());
        return userActivity;
    }

}
