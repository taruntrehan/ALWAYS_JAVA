/**
 * 
 */
package home.always.learning.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import home.always.learning.java.beans.JsTreeFileDetailsBean;

/**
 * @author ttrehan
 *
 */
public class JsTreeFileUtils {

	private List<JsTreeFileDetailsBean> returnFileList = new ArrayList<JsTreeFileDetailsBean>();
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		JsTreeFileUtils jsTreeFileUtils = new JsTreeFileUtils();
		Gson coreGson = new Gson();
		String rootFolderPath="/home/ttrehan/Work/code/java/ALWAYS_JAVA/ALWAYS_JAVA/ALWAYS_JAVA/Input";
		
		JsTreeFileDetailsBean jsTreeFileRootObj = new JsTreeFileDetailsBean();
		jsTreeFileRootObj.setId(UUID.randomUUID().toString());
		jsTreeFileRootObj.setParent("#");
		jsTreeFileRootObj.setText(rootFolderPath);
		File dir = new File(rootFolderPath);
		
		if(dir.isDirectory()) {
			jsTreeFileUtils.getReturnFileList().add(jsTreeFileRootObj);
			jsTreeFileUtils.traverseFolder(jsTreeFileRootObj , dir.getAbsolutePath());
		}else {
			System.out.println("Input is single file. nothing to do here.");
		}
		
		String jsonStr = coreGson.toJson(jsTreeFileUtils.getReturnFileList());
		System.out.println(jsonStr);
	}
	

	private void traverseFolder(JsTreeFileDetailsBean jsTreeParentObj , String folderPath) {
		File rootDirectory = new File(folderPath);
		File[] files = rootDirectory.listFiles();
		for (File file : files) {
			//System.out.println(file);
			JsTreeFileDetailsBean jsTreeFileIterObj = new JsTreeFileDetailsBean();
			jsTreeFileIterObj.setId(UUID.randomUUID().toString());
			jsTreeFileIterObj.setParent(jsTreeParentObj.getId());
			jsTreeFileIterObj.setText(file.getName());
			if(file.isDirectory()) {
				this.getReturnFileList().add(jsTreeFileIterObj);
				traverseFolder(jsTreeFileIterObj, file.getAbsolutePath());
			}else {
				this.getReturnFileList().add(jsTreeFileIterObj);
			}
		}
	}

	public List<JsTreeFileDetailsBean> getReturnFileList() {
		return returnFileList;
	}
	
	public void setReturnFileList(List<JsTreeFileDetailsBean> returnFileList) {
		this.returnFileList = returnFileList;
	}
}