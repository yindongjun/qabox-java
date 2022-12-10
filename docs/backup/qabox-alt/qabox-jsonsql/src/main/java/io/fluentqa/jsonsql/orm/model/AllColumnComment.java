/*Copyright (C) 2020 THL A29 Limited, a Tencent company.  All rights reserved.

This source code is licensed under the Apache License Version 2.0.*/


package io.fluentqa.jsonsql.orm.model;

import io.fluentqa.jsonsql.MethodAccess;

/**SQL Server 在 sys 下的字段(列名)
 * @author Lemon
 */
@MethodAccess(POST = {}, PUT = {}, DELETE = {})
public class AllColumnComment {
	public static final String TAG = "AllColumnComment";
	public static final String TABLE_NAME = "ALL_COL_COMMENTS";

}
