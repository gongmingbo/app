package app.apiservice;

import app.entity.user.User;
import app.packet.error.ErrorEnum;
import app.packet.request.LoginPacket;
import app.packet.response.CommonMessage;
import app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Collections;


@RestController
@ControllerAdvice
@RequestMapping(path = "/api")
public class Login {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()) ;

	@Autowired
	private UserService userService;

	@RequestMapping(path="/login",method=RequestMethod.POST)
	public CommonMessage login(@RequestBody LoginPacket packet){
		logger.info("mobile is: " + packet.getMobile());
		logger.info("password is: " + packet.getPassword());
		User user = userService.getUserByTelephone(packet.getMobile());
		CommonMessage message;
		if (user == null ){
			message = CommonMessage.failure(ErrorEnum.USER_NOT_EXISTS);
		}else if (!userService.validatePassword(packet.getMobile(), packet.getPassword())){
			message = CommonMessage.failure(ErrorEnum.USER_PASSWORD_INVALID);
		}else {
			String token = userService.getToken(user.getUserId(), packet.getDeviceId());
			message = CommonMessage.success(Collections.singletonMap("userId", user.getUserId()), token);
		}

		return message;
	}

	@RequestMapping(path="/register",method=RequestMethod.POST)
	public CommonMessage register(@RequestBody LoginPacket packet) throws UnsupportedEncodingException {
		User user = userService.getUserByTelephone(packet.getMobile());
		if (user != null){
			return CommonMessage.failure(ErrorEnum.USER_ALREADY_EXISTS);
		}else {
			boolean isValid = userService.valid(packet.getMobile(), packet.getMessageId(), packet.getSmsCode());
			if (!isValid){
				return CommonMessage.failure(ErrorEnum.SMS_CODE_ERROR);
			}else {
				userService.newUser(packet.getMobile(), packet.getPassword());
				return CommonMessage.success();
			}
		}
	}

	@RequestMapping(path="/reset-step1",method=RequestMethod.POST)
	public CommonMessage reset1(@RequestBody LoginPacket packet) throws UnsupportedEncodingException {
		User user = userService.getUserByTelephone(packet.getMobile());
		if (user == null){
			return CommonMessage.failure(ErrorEnum.USER_NOT_EXISTS);
		}else {
			boolean isValid = userService.valid(packet.getMobile(), packet.getMessageId(), packet.getSmsCode());
			if (!isValid){
				return CommonMessage.failure(ErrorEnum.SMS_CODE_ERROR);
			}else {
				String token = userService.getToken(user.getUserId(), packet.getDeviceId());
				return CommonMessage.success(Collections.singletonMap("userId", user.getUserId()), token);
			}
		}
	}

	@RequestMapping(path="/reset-step2",method=RequestMethod.POST)
	public CommonMessage reset2(@RequestBody LoginPacket packet) throws UnsupportedEncodingException {
		User user = userService.getUserByTelephone(packet.getMobile());
		String token = userService.validateAndRefreshToken(user.getUserId(), packet.getDeviceId(), packet.getToken());
		if (token != null) {
			user.setUserPassword(packet.getPassword());
			userService.save(user);
			return CommonMessage.success(null, token);
		}else {
			return CommonMessage.failure(ErrorEnum.TOKEN_INVALID);
		}

	}

	@RequestMapping(path="/smsCode",method=RequestMethod.POST)
	public CommonMessage generateSmsCode(@RequestBody LoginPacket packet) throws UnsupportedEncodingException {
		String messageId = userService.sendSmsCode(packet.getMobile());
		if (messageId != null){
			return CommonMessage.success(Collections.singletonMap("messageId", messageId));
		}else {
			return CommonMessage.failure(ErrorEnum.SMS_GENERATE_FAILED);
		}
	}

    @ExceptionHandler(value = Exception.class)
    public CommonMessage errorHandler(Exception ex) {
		ex.printStackTrace();
		return CommonMessage.failure(ErrorEnum.SERVER_INTERNAL_ERROR);
	}
}
