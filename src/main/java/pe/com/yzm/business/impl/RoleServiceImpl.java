package pe.com.yzm.business.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.com.yzm.business.RoleService;
import pe.com.yzm.core.exception.BusinessException;
import pe.com.yzm.core.logger.LoggerUtil;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.response.RoleResponse;
import pe.com.yzm.mapper.RoleMapper;
import pe.com.yzm.repository.RoleRepository;
import pe.com.yzm.util.ConstantMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * <b>Class</b>: RoleServiceImpl <br/>
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
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;

  @Override
  public Mono<RoleResponse> findByRoleId(HeaderRequest headerRequest, Long roleId) {
    LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), roleId);
    return roleRepository.findById(roleId)
        .switchIfEmpty(Mono.error(BusinessException
            .createException(ConstantMessage.ERROR_GET_ROLE,
                Collections.singletonList(ConstantMessage.ROLE_NOT_FOUND), HttpStatus.NOT_FOUND)))
        .map(roleMapper::toRoleResponse)
        .doOnNext(response ->
            LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
        .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
  }

  @Override
  public Flux<RoleResponse> findAllRoles(HeaderRequest headerRequest) {
    LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), null);
    return roleRepository.findAll()
        .map(roleMapper::toRoleResponse)
        .doOnNext(response ->
            LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
        .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
  }
}
