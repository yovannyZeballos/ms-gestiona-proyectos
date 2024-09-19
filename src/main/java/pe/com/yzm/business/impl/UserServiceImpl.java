package pe.com.yzm.business.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.com.yzm.business.PasswordService;
import pe.com.yzm.business.UserService;
import pe.com.yzm.core.exception.BusinessException;
import pe.com.yzm.core.logger.LoggerUtil;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.request.UserCreateRequest;
import pe.com.yzm.expose.request.UserUpdateRequest;
import pe.com.yzm.expose.response.UserResponse;
import pe.com.yzm.mapper.UserMapper;
import pe.com.yzm.model.User;
import pe.com.yzm.repository.UserRepository;
import pe.com.yzm.util.ConstantMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <b>Class</b>: UserServiceImpl <br/>
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
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordService passwordService;
  private final UserMapper userMapper;

  @Override
  public Mono<UserResponse> findUserById(HeaderRequest headerRequest, Long userId) {
    LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), userId);
    return userRepository.findById(userId)
        .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.ERROR_GET_USER,
            List.of(ConstantMessage.USER_NOT_FOUND), HttpStatus.NOT_FOUND)))
        .map(userMapper::userToUserResponse)
        .doOnNext(response ->
            LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
        .doOnError( error -> {
          LoggerUtil.logError(headerRequest.getTransactionId(), error);
        });
  }

  @Override
  public Flux<UserResponse> findAllUsers(HeaderRequest headerRequest) {
    LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), null);
    return userRepository.findAll()
        .map(userMapper::userToUserResponse)
        .doOnNext(response ->
            LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
        .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
  }

  @Override
  public Mono<UserResponse> saveUser(HeaderRequest headerRequest, UserCreateRequest userRequest) {
    LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), userRequest);
    return validateSaveUser(userRequest)
        .map(userMapper::userCreateRequestToUser)
        .flatMap(user -> {
          user.setPassword(passwordService.encode(userRequest.getPassword()));
          return userRepository.save(user);
        })
        .map(userMapper::userToUserResponse)
        .doOnNext(response ->
            LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
        .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
  }


  @Override
  public Mono<UserResponse> updateUser(HeaderRequest headerRequest, UserUpdateRequest userRequest) {
    LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), userRequest);
    return findUserAndValidate(userRequest.getId(), ConstantMessage.ERROR_UPDATE_USER)
        .map(user -> userMapper.userUpdateRequestToUser(user, userRequest))
        .flatMap(userRepository::save)
        .map(userMapper::userToUserResponse)
        .doOnNext(response ->
            LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
        .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
  }

  @Override
  public Mono<Void> deleteUser(HeaderRequest headerRequest, Long userId) {
    LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), userId);
    return findUserAndValidate(userId, ConstantMessage.ERROR_DELETE_USER)
        .flatMap(userRepository::delete)
        .doOnSuccess(response ->
            LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), null))
        .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
  }

  private Mono<UserCreateRequest> validateSaveUser(UserCreateRequest userRequest) {
    return userRepository.existsByEmail(userRequest.getEmail())
        .flatMap(exist -> exist ? Mono.error(BusinessException.createException(ConstantMessage.ERROR_SAVE_USER,
            List.of(String.format(ConstantMessage.USER_EXIST, userRequest.getEmail())), HttpStatus.BAD_REQUEST)) : Mono.just(userRequest));
  }

  private Mono<User> findUserAndValidate(Long userId, String message) {
    return userRepository.findById(userId)
        .switchIfEmpty(Mono.error(BusinessException.createException(message,
            List.of(ConstantMessage.USER_NOT_FOUND), HttpStatus.BAD_REQUEST)));
  }

}
