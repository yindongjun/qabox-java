/*Copyright (C) 2020 THL A29 Limited, a Tencent company.  All rights reserved.

This source code is licensed under the Apache License Version 2.0.*/


package io.fluentqa.jsonsql.orm.model;

import io.fluentqa.jsonsql.MethodAccess;

/**表属性
 * @author Lemon
 */
@MethodAccess(POST = {}, PUT = {}, DELETE = {})
public class Table {
	public static final String TAG = "Table";
	public static final String TABLE_NAME = "tables";

}
