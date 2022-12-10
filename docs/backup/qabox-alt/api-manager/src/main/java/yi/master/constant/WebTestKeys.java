package yi.master.constant;

/**
 * web自动化相关常量类
 * @author xuwangcheng
 * @version 2018.08.23
 *
 */
public interface WebTestKeys {

	/**
	 * 元素类型
	 */
	enum ElementType {
		/**
		 * 网站
		 */
		website,
		/**
		 * 模块
		 */
		module,
		/**
		 * 功能
		 */
		feature,
		/**
		 * 页面
		 */
		page,
		/**
		 * 页面frame/iframe
		 */
		frame,
		/**
		 * 节点
		 */
		tag,
		/**
		 * url地址
		 */
		url
	}

	/**
	 * 定位类型
	 */
	enum ByType {
		ClassName,
		Id,
		LinkText,
		Name,
		TagName,
		XPath,
		/**
		 * 局部文字链接匹配
		 */
		PartialLinkText,
		CssSelector,
		/**
		 * 获取当前窗口的标题
		 */
		title,
		/**
		 * 获取当前页面的弹出框
		 */
		alert,
		/**
		 * 获取当前的浏览器地址栏地址
		 */
		currentUrl
	}

	/**
	 * 用例类型
	 */
	enum CaseType {
		/**
		 * 普通用例
		 */
		common,
		/**
		 * 用例片段
		 */
		snippet;
	}

	/**
	 * 测试浏览器类型
	 */
	enum BrowserType {
		chrome,
		ie,
		firefox,
		opera
	}


	/**
	 * 步骤执行关键字
	 */
	enum StepKeyWord {
		/**
		 * 打开浏览器地址
		 */
		open,
		/**
		 * 检查
		 */
		check,
		/**
		 * 执行-用例片段
		 */
		action,
		/**
		 * 执行js脚本
		 */
		script,
		/**
		 * 输入
		 */
		input,
		/**
		 * 获取值并保存下来
		 */
		save,
		/**
		 * 点击
		 */
		click,
		/**
		 * 移动到指定元素上并悬停
		 */
		hover,
		/**
		 * 取消-确认框
		 */
		alertCancel,
		/**
		 * /确认框-确认
		 */
		alertConfirm,
		/**
		 * 关闭alert
		 */
		alertClose,
		/**
		 * 输入到对话框，默认会确认
		 */
		alertInput,
		/**
		 * 上传文件
		 */
		upload,
		/**
		 * 滑动
		 */
		slide
	}

	/**
	 * 取值关键字
	 */
	enum GetValueKeyWord {
		/**
		 * 键盘组合按键
		 */
		keyboard,
		/**
		 * 普通的字符串
		 */
		string,
		/**
		 * 全局变量
		 */
		globalVariable,
		/**
		 * 正则表达式
		 */
		regexp,
		/**
		 * 元素的属性
		 */
		attribute,
		/**
		 * 测试变量
		 */
		saveVariable,
		/**
		 * 数据库取值，该值应该为数据库id
		 */
		dbSql
	}
}
