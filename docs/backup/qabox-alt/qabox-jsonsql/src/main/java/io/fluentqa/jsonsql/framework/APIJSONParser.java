/*Copyright ©2016 TommyLemon(https://github.com/TommyLemon/APIJSON)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package io.fluentqa.jsonsql.framework;

import static io.fluentqa.jsonsql.framework.APIJSONConstant.DEFAULTS;
import static io.fluentqa.jsonsql.framework.APIJSONConstant.FORMAT;
import static io.fluentqa.jsonsql.framework.APIJSONConstant.VERSION;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

import io.fluentqa.jsonsql.NotNull;
import io.fluentqa.jsonsql.RequestMethod;
import io.fluentqa.jsonsql.orm.AbstractParser;
import io.fluentqa.jsonsql.orm.FunctionParser;
import io.fluentqa.jsonsql.orm.Parser;
import io.fluentqa.jsonsql.orm.SQLConfig;
import io.fluentqa.jsonsql.orm.SQLExecutor;
import io.fluentqa.jsonsql.orm.Verifier;


/**请求解析器
 * @author Lemon
 */
public class APIJSONParser<T extends Object> extends AbstractParser<T> {
	public static final String TAG = "APIJSONParser";

	@NotNull
	public static APIJSONCreator<? extends Object> APIJSON_CREATOR;
	static {
		APIJSON_CREATOR = new APIJSONCreator<>();
	}
	
	
	public APIJSONParser() {
		super();
	}
	public APIJSONParser(RequestMethod method) {
		super(method);
	}
	public APIJSONParser(RequestMethod method, boolean needVerify) {
		super(method, needVerify);
	}

	private HttpSession session;
	public HttpSession getSession() {
		return session;
	}
	public APIJSONParser<T> setSession(HttpSession session) {
		this.session = session;
		setVisitor(APIJSONVerifier.getVisitor(session));
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Parser<T> createParser() {
		return (Parser<T>) APIJSON_CREATOR.createParser();
	}
	@Override
	public FunctionParser createFunctionParser() {
		return APIJSON_CREATOR.createFunctionParser();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Verifier<T> createVerifier() {
		return (Verifier<T>) APIJSON_CREATOR.createVerifier();
	}
	
	@Override
	public SQLConfig createSQLConfig() {
		return APIJSON_CREATOR.createSQLConfig();
	}
	@Override
	public SQLExecutor createSQLExecutor() {
		return APIJSON_CREATOR.createSQLExecutor();
	}


	@Override
	public JSONObject parseResponse(JSONObject request) {
		//补充format
		if (session != null && request != null) {
			if (request.get(FORMAT) == null) {
				request.put(FORMAT, session.getAttribute(FORMAT));
			}
			if (request.get(DEFAULTS) == null) {
				JSONObject defaults = (JSONObject) session.getAttribute(DEFAULTS);
				Set<Map.Entry<String, Object>> set = defaults == null ? null : defaults.entrySet();

				if (set != null) {
					for (Map.Entry<String, Object> e : set) {
						if (e != null && request.get(e.getKey()) == null) {
							request.put(e.getKey(), e.getValue());
						}
					}
				}
			}
		}
		return super.parseResponse(request);
	}

	private FunctionParser functionParser;
	public FunctionParser getFunctionParser() {
		return functionParser;
	}
	@Override
	public Object onFunctionParse(String key, String function, String parentPath, String currentName, JSONObject currentObject) throws Exception {
		if (functionParser == null) {
			functionParser = createFunctionParser();
			functionParser.setMethod(getMethod());
			functionParser.setTag(getTag());
			functionParser.setVersion(getVersion());
			functionParser.setRequest(requestObject);
			
			if (functionParser instanceof APIJSONFunctionParser) {
				((APIJSONFunctionParser) functionParser).setSession(getSession());
			}
		}
		functionParser.setKey(key);
		functionParser.setParentPath(parentPath);
		functionParser.setCurrentName(currentName);
		functionParser.setCurrentObject(currentObject);
		
		return functionParser.invoke(function, currentObject);
	}


	@Override
	public APIJSONObjectParser createObjectParser(JSONObject request, String parentPath, SQLConfig arrayConfig
			, boolean isSubquery, boolean isTable, boolean isArrayMainTable) throws Exception {

		return new APIJSONObjectParser(getSession(), request, parentPath, arrayConfig, isSubquery, isTable, isArrayMainTable) {

			//			@Override
			//			protected APIJSONSQLConfig newQueryConfig() {
			//				if (itemConfig != null) {
			//					return itemConfig;
			//				}
			//				return super.newQueryConfig();
			//			}

			//导致最多评论的(Strong 30个)的那个动态详情界面Android(82001)无姓名和头像，即User=null
			//			@Override
			//			protected void onComplete() {
			//				if (response != null) {
			//					putQueryResult(path, response);//解决获取关联数据时requestObject里不存在需要的关联数据
			//				}
			//			}

		}.setMethod(getMethod()).setParser(this);
	}



	@Override
	public void onVerifyContent() throws Exception {
		//补充全局缺省版本号  //可能在默认为1的前提下这个请求version就需要为0  requestObject.getIntValue(VERSION) <= 0) {
		HttpSession session = getSession();
		if (session != null && requestObject.get(VERSION) == null) {
			requestObject.put(VERSION, session.getAttribute(VERSION));
		}
		super.onVerifyContent();
	}


	//	//可重写来设置最大查询数量
	//	@Override
	//	public int getMaxQueryCount() {
	//		return 50;
	//	}
	

}
