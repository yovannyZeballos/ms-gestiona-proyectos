package pe.com.yzm.mapper;

import lombok.experimental.UtilityClass;
import org.mapstruct.Mapper;
import pe.com.yzm.expose.request.UserCreateRequest;
import pe.com.yzm.expose.request.UserUpdateRequest;
import pe.com.yzm.expose.response.UserResponse;
import pe.com.yzm.model.User;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

  public abstract UserResponse userToUserResponse(User user);

  public abstract User userCreateRequestToUser(UserCreateRequest userRequest);

  public User userUpdateRequestToUser(User userUpdate, UserUpdateRequest userRequest) {
    userUpdate.setUserId(userRequest.getUserId());
    userUpdate.setId(userRequest.getId());
    userUpdate.setRoleId(userRequest.getRoleId());
    userUpdate.setName(userRequest.getName());
    return userUpdate;
  }
}
