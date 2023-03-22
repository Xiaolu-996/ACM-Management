package com.example.demo.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;


class jasyptUtil {

	public static void main(String[] args) {
		String username = encrypt("root");
		String password = encrypt("990324");
		System.out.println(username);
		System.out.println(password);
	}

	/**
	 * 加密
	 * @param plaintext 明文密码     * @return
	 */
	public static String encrypt(String plaintext) {
		//加密工具
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		//加密配置
		EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		// 算法类型
		config.setAlgorithm("PBEWithMD5AndDES");
		//生成秘钥的公钥
		config.setPassword("PEB123@321BEP");
		//应用配置
		encryptor.setConfig(config);
		//加密
		String ciphertext = encryptor.encrypt(plaintext);
		return ciphertext;
	}

	/**
	 * 解密
	 *
	 * @param ciphertext 待解密秘钥
	 * @return
	 */
	public static String decrypt(String ciphertext) {
		//加密工具
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		//加密配置
		EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		config.setAlgorithm("PBEWithMD5AndDES");
		//生成秘钥的公钥
		config.setPassword("PEB123@321BEP");
		//应用配置
		encryptor.setConfig(config);
		//解密
		String pText = encryptor.decrypt(ciphertext);
		return pText;
	}

}
