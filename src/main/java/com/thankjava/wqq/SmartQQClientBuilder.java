package com.thankjava.wqq;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thankjava.toolkit.reflect.ReflectHelper;
import com.thankjava.wqq.consts.ConfigParams;
import com.thankjava.wqq.consts.ConstsParams;
import com.thankjava.wqq.core.action.LoginAction;
import com.thankjava.wqq.entity.LoginResult;
import com.thankjava.wqq.extend.ActionListener;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.extend.NotifyListener;
import com.thankjava.wqq.factory.ActionFactory;

/**
 * SmartQQClient 实力创建工具指南
 * 
 * @author acexy
 *
 */
public class SmartQQClientBuilder {

	private static final Logger logger = LoggerFactory.getLogger(SmartQQClientBuilder.class);

	private SmartQQClientBuilder() {
	}

	private static WQQClient client = null;

	/**
	 * 声明需要自定义参数化 SmartQQClient
	 * 
	 * @param notifyListener
	 * @return
	 */
	public static SmartQQClientBuilder custom(NotifyListener notifyListener) {

		client = new WQQClient();

		if (notifyListener == null) {
			throw new NullPointerException("notifyListener can not be null");
		}

		ReflectHelper.setFieldVal(client, "listener", notifyListener);

		return new SmartQQClientBuilder();
	}

	/**
	 * 设置单次请求与异常的最大重试次数
	 * 
	 * @param times
	 * @return
	 */
	public SmartQQClientBuilder setExceptionRetryMaxTimes(int times) {
		if (times < 1) {
			throw new IllegalArgumentException("exceptionRetryMaxTimes should >= 1");
		}
		ConfigParams.EXCEPTION_RETRY_MAX_TIME = times;
		return this;
	}

	/**
	 * 登录成功后立即获取一些信息并缓存下来(自己的信息，讨论组，好友信息)
	 * 
	 * @return
	 */
	public SmartQQClientBuilder autoGetInfoAfterLogin() {
		ConfigParams.INIT_LOGIN_INFO = true;
		return this;
	}

	/**
	 * 创建SmartQQClient实例并登录
	 * 
	 * @param qrCodePathDirName
	 *            获取登录二维码后，指定二维码所生成的目录位置
	 * @param loginListener
	 *            登录完毕后的回调函数，函数会返回一个LoginResult的登录状态反馈值
	 * @return
	 */
	public SmartQQClient create(final String qrCodePathDirName, final CallBackListener loginListener) {

		LoginAction loginAction = ActionFactory.getInstance(LoginAction.class);
		loginAction.login(new CallBackListener() {

			@Override
			public void onListener(ActionListener actionListener) {
				try {
					String qrPath = qrCodePathDirName + File.separator + ConstsParams.QR_CODE_NAME;
					ImageIO.write((BufferedImage) actionListener.getData(), ConstsParams.QR_CODE_TYPE,
							new File(qrPath));
					logger.info("登录二维码已创建至: " + qrPath);
				} catch (IOException e) {
					logger.error("创建登录二维码异常", e);
					loginListener.onListener(new ActionListener(LoginResult.exception));
				}
			}
		}, new CallBackListener() {

			@Override
			public void onListener(ActionListener actionListener) {
				loginListener.onListener(actionListener);
			}
		});
		return client;
	}
}
