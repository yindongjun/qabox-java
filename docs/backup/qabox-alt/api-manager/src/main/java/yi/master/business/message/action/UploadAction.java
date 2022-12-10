package yi.master.business.message.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.bean.ReturnJSONObject;
import yi.master.constant.ReturnCodeConsts;
import yi.master.util.FrameworkUtil;
import yi.master.util.ParameterMap;
import yi.master.constant.CustomSettingVariable;
import yi.master.util.upload.Upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 文件上传Action
 * @author xuwangcheng
 * @version 20171205,1.0.0.0
 *
 */
@Controller
@Scope("prototype")
public class UploadAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ReturnJSONObject jsonObject = new ReturnJSONObject();
	
	private File file;

	/**
	 * 提交过来的file的名字
	 */
	private String fileFileName;
    
    private String downloadFileName;

	/**
	 * 提交过来的file的MIME类型
	 */
	private String fileFileContentType;

    /**
     * 0 - 普通
     * 1 - 全局变量文件
     */
	private String type;
    
    public String upload() {
    	int returnCode = ReturnCodeConsts.SUCCESS_CODE;
    	String msg = "文件上传成功!";
    	
    	if (file == null) {
    		returnCode = ReturnCodeConsts.NO_FILE_UPLOAD_CODE;
    		msg = "未发现上传的文件!";
    	} else {
    	    String parentPath = null;
    	    if ("1".equals(type)) {
                parentPath = CustomSettingVariable.GLOBAL_VARIABLE_FILE_SAVE_PATH;
            }
    		String fps = Upload.singleUpload(file, this.getFileFileName(), parentPath);
    		
    		if (fps == null) {
    			returnCode = ReturnCodeConsts.SYSTEM_ERROR_CODE;
        		msg = "上传文件失败,请重试!";
    		} else {
    			jsonObject.setData(new ParameterMap().put("path", fps)
						.put("relativePath", fps.replace(FrameworkUtil.getProjectPath() + File.separator , "")));
    		}   		
    	}
    	
    	jsonObject.setMsg(msg);
    	jsonObject.setReturnCode(returnCode);
    	return SUCCESS;
    }
    
    public InputStream getDownloadStream() throws FileNotFoundException {
    	String filePath = FrameworkUtil.getProjectPath() + File.separator + downloadFileName;
    	this.setFileFileName(downloadFileName.substring(downloadFileName.lastIndexOf(File.separator) + 1));
    	InputStream is = new FileInputStream(new File(filePath)); 
    	return is;
    }
    
    public String download() {
    	return "download";
    }

	public ReturnJSONObject getJsonObject() {
		return jsonObject;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	public void setFileFileContentType(String fileFileContentType) {
		this.fileFileContentType = fileFileContentType;
	}
	
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	@JSON(serialize=false)
	public File getFile() {
		return file;
	}
	
	@JSON(serialize=false)
	public String getFileFileName() {
		return fileFileName;
	}
	@JSON(serialize=false)
	public String getFileFileContentType() {
		return fileFileContentType;
	}
	
	@JSON(serialize=false)
	public String getDownloadFileName() {
		return downloadFileName;
	}

    public void setType(String type) {
        this.type = type;
    }

}
