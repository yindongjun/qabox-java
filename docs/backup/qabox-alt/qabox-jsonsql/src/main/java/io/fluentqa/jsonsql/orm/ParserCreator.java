/*Copyright (C) 2020 THL A29 Limited, a Tencent company.  All rights reserved.

This source code is licensed under the Apache License Version 2.0.*/


package io.fluentqa.jsonsql.orm;

import io.fluentqa.jsonsql.NotNull;

/**SQL相关创建器
 * @author Lemon
 */
public interface ParserCreator<T extends Object> {
	
	@NotNull
	Parser<T> createParser();
	
	@NotNull
	FunctionParser createFunctionParser();
}
