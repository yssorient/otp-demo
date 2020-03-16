package com.wjs.otpdemo;

import com.wjs.dao.UserDao;
import com.wjs.dao.UserVo;
import com.wjs.email.EmailBaseLogic;
import com.wjs.util.QRUtil;
import com.wjs.util.TotpUtil;

public class RegistTest {
	
	private static String f_temp="D://";
	
	public static void main(String[] args) {
		
		String userName = "test";
		String email = "609061217@qq.com";
		
		UserVo vo = new UserVo();
		vo.setUsername(userName);
		// ����OTP��Ϣ
		String secretBase32 = TotpUtil.getRandomSecretBase32(64);
		vo.setOtpSk(secretBase32);
		
		// ֪ͨ�û�
		sendRegisterMail(userName, email, secretBase32);
		
		// ���ݿ������û�
		UserDao dao = new UserDao();
		dao.add(vo);
		
	}
	

	
	 private static void sendRegisterMail(final String operCode, final String email, final String secretBase32){
	        String host = "otptest@wjs.com";

	        String totpProtocalString = TotpUtil.generateTotpString(operCode, host, secretBase32);

	        String filePath = f_temp;
	        String fileName = Long.toString(System.currentTimeMillis()) + ".png";
	        
	        try{
	            QRUtil.generateMatrixPic(totpProtocalString, 150, 150, filePath, fileName);
	        }catch (Exception e){
	            throw new RuntimeException("���ɶ�ά��ͼƬʧ��:" + e.getMessage());
	        }


	        String content = "�û�����"+operCode+"</br>"
	                +"ϵͳʹ������ + ��̬����˫������֤�ķ�ʽ��¼��</br>�밴���·�ʽ�����ֻ���̬���</br>��׿�û�����<a href='http://otp.aliyun.com/updates/shenfenbao.apk'>����</a>��"
	                +"</br>ƻ���ֻ���AppStore����������ݱ�����Alibaba�������ذ�װ��ͨ��ɨ�����¶�ά�뼤�̬���</br>"
	                +"<img src=\"cid:image\">";
	        EmailBaseLogic emailBaseLogic = new EmailBaseLogic();
//	        String to, String title, String content, String imagePath
	        emailBaseLogic.sendWithPic(email,"�˻�����֪ͨ", content, filePath + "/" + fileName);
	    }

}
