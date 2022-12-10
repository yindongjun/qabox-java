/*Copyright (C) 2020 THL A29 Limited, a Tencent company.  All rights reserved.

This source code is licensed under the Apache License Version 2.0.*/


package io.fluentqa.jsonsql.orm;

import java.util.List;

/**来访者
 * @author Lemon
 */
public interface Visitor<T> {

	T getId();

	List<T> getContactIdList();

}
