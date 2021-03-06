package com.team.app.service;

import java.util.List;

import com.team.app.domain.JwtToken;
import com.team.app.domain.LoraFrame;
import com.team.app.domain.TblToshibaKeyConfig;
import com.team.app.domain.TblUserInfo;
import com.team.app.domain.User;
import com.team.app.dto.UserLoginDTO;
import com.team.app.exception.AtAppException;

/**
 * 
 * @author Vikky
 *
 */
public interface ConsumerInstrumentService {
	
	public UserLoginDTO mobileLoginAuth(String username, String password) throws AtAppException;
	public UserLoginDTO getRefreshTokenOnBaseToken()throws AtAppException;
	public List<TblUserInfo> getUserInfosCount()throws AtAppException;
	public List<TblUserInfo> getUserInfos()throws AtAppException;
	public TblUserInfo getUserById(String userId)throws AtAppException;
	public TblUserInfo updateUser(TblUserInfo userInfo)throws AtAppException;
	public TblUserInfo getUserByEmailId(String emailId)throws AtAppException;

	public List<JwtToken> getJwtToken()throws Exception;

	public void updateJwt(JwtToken jwtT)throws Exception;
	
	public User getNSUserById(String usrId)throws Exception;
	
	public void updateNSUser(User u)throws Exception;
	public List<LoraFrame> getFramesByNodeNameAndID(String deviceId, String devEUI) throws Exception;
	public void setUpdateLoraFrames(String deviceId, String nodeName, String devEUI, String central, String peripheral)throws Exception;
	public LoraFrame getNamingPacket(String deviceId, String devEUI)throws Exception;
	public void setUpdateNodeName(String nodeName, String devEUI)throws Exception;
	public List<LoraFrame> getDeviceIdByDevEUI(String deviceId)throws Exception;
	public void deleteDevByDevEUI(String appId, String devEUI, String deviceId) throws Exception;
	public TblToshibaKeyConfig getKeyConfig(String keyAtappMobile)throws Exception;
	public LoraFrame getNamingPacket1(String deviceId, String devEUI)throws Exception;
	public void deleteDevEUI(String appId, String devEUI)throws Exception;
	public List<LoraFrame> getDevEUIByAppId(String appId)throws Exception;
	public boolean getLoraFrameFlagStatus(String devEUI, String deviceId)throws Exception;
	public void setLoraFrameFlagStatusForLed1(String devEUI, String deviceId, String led1)throws Exception;
	public void setLoraFrameFlagStatusForLed2(String devEUI, String deviceId, String led2)throws Exception;
	public void setLoraFrameFlagStatusForLed34(String devEUI, String deviceId, String led3, String led4, String flag)throws Exception;
	
	
	
}
