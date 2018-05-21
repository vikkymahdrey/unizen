package com.team.app.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.team.app.constant.AppConstants;
import com.team.app.dao.FrameDao;
import com.team.app.dao.JwtTokenDao;
import com.team.app.dao.ToshibaKeyConfigDao;
import com.team.app.dao.UserDao;
import com.team.app.dao.UserInfoDao;
import com.team.app.domain.JwtToken;
import com.team.app.domain.LoraFrame;
import com.team.app.domain.TblToshibaKeyConfig;
import com.team.app.domain.TblUserInfo;
import com.team.app.domain.User;
import com.team.app.dto.UserLoginDTO;
import com.team.app.exception.AtAppException;
import com.team.app.logger.AtLogger;
import com.team.app.service.ConsumerInstrumentService;
import com.team.app.utils.JWTKeyGenerator;

/**
 * 
 * @author Vikky
 *
 */
@Service("consumerInstrumentServiceImpl")
public class ConsumerInstrumentServiceImpl implements ConsumerInstrumentService {

	private final AtLogger logger = AtLogger.getLogger(ConsumerInstrumentServiceImpl.class);
	
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private ToshibaKeyConfigDao  appKeyConfigDao;
	
	@Autowired
	private JwtTokenDao jwtTokenDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private FrameDao frameDao;
	
	
	
	
	
	@Transactional
	public UserLoginDTO mobileLoginAuth(String username, String password)  throws AtAppException{
		
		UserLoginDTO  userLoginDto=null;
		   userLoginDto=new UserLoginDTO();
			
		//validating account exist or not
		TblUserInfo existinguser=userInfoDao.getUserByUserAndPwd(username,password);
		if(existinguser!=null){
			userLoginDto.setStatusDesc("account login successfully");
			userLoginDto.setUserId(existinguser.getId());
		}
		
		TblToshibaKeyConfig appKeyConfig = appKeyConfigDao.getKeyConfigValue(AppConstants.KEY_ATAPP_MOBILE);
		
		if(null != appKeyConfig && (appKeyConfig.getIs_Enabled()!= null && 
				appKeyConfig.getIs_Enabled().equalsIgnoreCase(AppConstants.IND_Y))) {
			
			long ttlMillis=TimeUnit.MINUTES.toMillis(15);
			long ttlBaseMillis=TimeUnit.MINUTES.toMillis(30);
			
			//long ttlMillis=TimeUnit.DAYS.toMillis(30);
			//long ttlBaseMillis=TimeUnit.DAYS.toMillis(60);
			
			logger.debug("ttlMillisVal",ttlMillis);
			logger.debug("ttlBaseMillisVal",ttlBaseMillis);
			
			UserLoginDTO accessToken = JWTKeyGenerator.createJWTAccessToken(appKeyConfig.getToshiba_key_value(), AppConstants.TOKEN_LOGN_ID,
					AppConstants.SUBJECT_SECURE, ttlMillis);
			
			UserLoginDTO baseToken = JWTKeyGenerator.createJWTBaseToken(appKeyConfig.getToshiba_key_value(), AppConstants.TOKEN_LOGN_ID,
					AppConstants.SUBJECT_SECURE, ttlBaseMillis);
			
									
			userLoginDto.setAccessToken(accessToken.getApiToken());
			userLoginDto.setAccessTokenExpDate(accessToken.getAccessTokenExpDate());
			
			userLoginDto.setBaseToken(baseToken.getBaseToken());
			userLoginDto.setBaseTokenExpDate(baseToken.getBaseTokenExpDate());
			userLoginDto.setStatusCode(HttpStatus.OK.toString());
		}else{
			throw new AtAppException(" Auth key not enabled in system /mobileLoginAuth ", HttpStatus.NOT_FOUND);
		}	
		
		
		return userLoginDto;
	}



	public UserLoginDTO getRefreshTokenOnBaseToken() throws AtAppException {
		UserLoginDTO userLoginDTO=null;
		try{
		
			TblToshibaKeyConfig appKeyConfig = appKeyConfigDao.getKeyConfigValue(AppConstants.KEY_ATAPP_MOBILE);
		
			if(null != appKeyConfig && (appKeyConfig.getIs_Enabled() != null && 
					appKeyConfig.getIs_Enabled().equalsIgnoreCase(AppConstants.IND_Y))) {
			
			userLoginDTO=new UserLoginDTO();
			userLoginDTO.setStatusCode(HttpStatus.OK.toString());
				
			//long ttlMillis=TimeUnit.DAYS.toMillis(30);
			//long ttlBaseMillis=TimeUnit.DAYS.toMillis(60);
			long ttlMillis=TimeUnit.MINUTES.toMillis(10);
			long ttlBaseMillis=TimeUnit.DAYS.toMillis(30);		
	
						
			UserLoginDTO newAccessToken = JWTKeyGenerator.createJWTAccessToken(appKeyConfig.getToshiba_key_value(), AppConstants.TOKEN_LOGN_ID,
					AppConstants.SUBJECT_SECURE, ttlMillis);
			
			UserLoginDTO newBaseToken = JWTKeyGenerator.createJWTBaseToken(appKeyConfig.getToshiba_key_value(), AppConstants.TOKEN_LOGN_ID,
					AppConstants.SUBJECT_SECURE, ttlBaseMillis);
			
			userLoginDTO.setAccessToken(newAccessToken.getApiToken());
			userLoginDTO.setAccessTokenExpDate(newAccessToken.getAccessTokenExpDate());
			
			userLoginDTO.setBaseToken(newBaseToken.getBaseToken());
			userLoginDTO.setBaseTokenExpDate(newBaseToken.getBaseTokenExpDate());
						
			}
		}catch(Exception e){
			throw new AtAppException("Getting problem while renewing the token",HttpStatus.EXPECTATION_FAILED);
		}
		return userLoginDTO;
	}


	
	public List<TblUserInfo> getUserInfosCount() throws AtAppException {
		return userInfoDao.getUserInfosCount();
	}




	public List<TblUserInfo> getUserInfos() throws AtAppException {
		return userInfoDao.getUserInfos();
	}


	public TblUserInfo getUserById(String userId) throws AtAppException {
		return userInfoDao.getUserById(userId);
	}




	public TblUserInfo updateUser(TblUserInfo userInfo) throws AtAppException {
		return userInfoDao.save(userInfo);
	}




	public TblUserInfo getUserByEmailId(String emailId) throws AtAppException {
		return userInfoDao.getUserByEmailId(emailId);
	}



	public List<JwtToken> getJwtToken() throws Exception {
		return jwtTokenDao.getJwtToken();
	}



	public void updateJwt(JwtToken jwtT) throws Exception {
		jwtTokenDao.save(jwtT);
		
	}


	public User getNSUserById(String usrId) throws Exception {
		return userDao.getNSUserById(usrId);
	}



	
	public void updateNSUser(User u) throws Exception {
		userDao.save(u);		
	}



	
	public List<LoraFrame> getFramesByNodeNameAndID(String deviceId, String devEUI) throws Exception {
		return frameDao.getFramesByNodeNameAndID(deviceId,devEUI);
	}



	public void setUpdateLoraFrames(String deviceId, String nodeName,String devEUI,String central,String peripheral) throws Exception {
		frameDao.setUpdateLoraFrames(central,peripheral,nodeName,deviceId,devEUI);
	}



	public LoraFrame getNamingPacket(String deviceId, String devEUI) throws Exception {
			return frameDao.getNamingPacket(deviceId,devEUI);
	}



	
	public void setUpdateNodeName(String nodeName,String devEUI) throws Exception {
		frameDao.setUpdateNodeName(nodeName,devEUI);
	}



	public List<LoraFrame> getDeviceIdByDevEUI(String deviceId) throws Exception {
		// TODO Auto-generated method stub
		return frameDao.getDeviceIdByDevEUI(deviceId.trim());
	}



	public void deleteDevByDevEUI(String appId, String devEUI, String deviceId) throws Exception {
		frameDao.deleteDevByDevEUI(appId,devEUI,deviceId);
		
	}



	
	public TblToshibaKeyConfig getKeyConfig(String keyAtappMobile) throws Exception {
	
		return appKeyConfigDao.getKeyConfigValue(keyAtappMobile);
	}



	public LoraFrame getNamingPacket1(String deviceId, String devEUI) throws Exception {
		return frameDao.getNamingPacket1(deviceId,devEUI);
	}



	
	public void deleteDevEUI(String appId, String devEUI) throws Exception {
		frameDao.deleteDevEUI(appId,devEUI);
		
	}



	
	public List<LoraFrame> getDevEUIByAppId(String appId) throws Exception {
		return frameDao.getDevEUIByAppId(appId);
	}





	

	
}