/*Copyright (C) 2020 THL A29 Limited, a Tencent company.  All rights reserved.

This source code is licensed under the Apache License Version 2.0.*/


package io.fluentqa.jsonsql.orm.exception;

/**未登录
 * @author Lemon
 */
public class NotLoggedInException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotLoggedInException(String msg, Throwable t) {
		super(msg, t);
	}
	public NotLoggedInException(String msg) {
		super(msg);
	}
	public NotLoggedInException(Throwable t) {
		super(t);
	}
	
}
