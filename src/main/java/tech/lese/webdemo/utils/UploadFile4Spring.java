package tech.lese.webdemo.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 * 20170830
 * @author tech.lese 
 */
public class UploadFile4Spring {
	
	public enum FileType{
		Excel("excel"),Image("image"),Word("word"),Music("music"),PDF("pdf"),Txt("txt"),Rar("rar"),
		Vedio("vedio"),Other("other");
		private String path ;
		private FileType(String path){
			this.path = path;
		}
		public String getPath(){
			return this.path;
		}
	}
	
	private enum SavePosition{
		Inner,Outer
	}
	
	/**
	 * 得到request对象
	 */
	private static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	//获取当前项目名称
	private static String getProjectName(){
		String projectName = getRequest().getContextPath();
		if(null == projectName || projectName.length() <=1){
			projectName = "";
		}else{
			//从位置1开始，去除“/”,实现上不去除不影响。
			projectName = projectName.substring(1);
		}
		return projectName;
	}
	
	//获取保存当前项目文件的资源目录名称
	private static String getProjectResourceName(){
		return getProjectName()+"_res";
	}
	
	
	 //获取项目父目录的绝对路径。
	private static String getProjectParentRealPath(){
		String path = getRequest().getServletContext().getRealPath("");
		return new File(path).getParent();
	}
	//获取项目绝对路径。
	private static String getProjectRealPath(){
		return getRequest().getServletContext().getRealPath("");
	}
	
	//获取保存文件的资源目录真实路径(项目父目录+资源目录名)
	private static String getOuterResourceRealPath(){
		return getProjectParentRealPath()+File.separator+getProjectResourceName();
	}
	
	private static String getInnerResourceRealPath(){
		return getProjectRealPath()+File.separator+getProjectResourceName();
	}
	
	/**
	 * 获取保存文件的路径
	 * @param filetype  根据文件类型生成二级目录
	 * @param position 保存位置，项目内，项目外
	 * @return 返回真实路径
	 */
	@Deprecated
	private static String getSaveFileRealPath(FileType fileType,SavePosition position){
		String path = getInnerResourceRealPath();
		if(SavePosition.Outer == position){
			path = getOuterResourceRealPath();
		}
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		path = path + File.separator+ fileType.getPath() + File.separator +  formater.format(new Date());
		File dir = new File(path);
		if (!dir.exists()) {
			try {
				dir.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}
	
	/**
	 * 
	 * @param file
	 * @param fileType 文件类型，枚举值
	 * @param position 资源文件夹目录位置。项目内或外部。枚举值
	 * @return 返回文件在资源目录内的完整路径。
	 */
	public static String uploadFileToRes(MultipartFile file,FileType fileType,SavePosition position){
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		String today = formater.format(new Date());
		
		String newFileName = getNewFileName(file.getOriginalFilename());
		String fullFileName = "/"+fileType.getPath()+"/"+today+"/"+newFileName;
		
		String saveFileRealPath = getOuterResourceRealPath();
		if(SavePosition.Inner == position){
			saveFileRealPath = getInnerResourceRealPath();
		}
		saveFileRealPath = saveFileRealPath + File.separator+ fileType.getPath() + File.separator +  today;
		
		try {
			copyFile(file.getInputStream(),saveFileRealPath,newFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fullFileName;
	}
	
	//默认的上传文件方法【默认保存外部】
	public static String uploadFileDefault(MultipartFile file,FileType fileType){
		return uploadFileToRes(file,fileType,SavePosition.Outer);
	}
	
	
	
	//拷贝文件到指定目录
	private static void copyFile(InputStream in, String saveFileRealPath, String newFileName)
			throws IOException {
		File file = new File(saveFileRealPath, newFileName);
		if (!file.exists()) {
			
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		FileUtils.copyInputStreamToFile(in, file);
	}
	
	
	
	
	
	
	
	
	
	
	 //获取文件扩展名
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	//获取保存的文件名，时间戳加随机数
	private static String getNewFileName(String oldFileName){
		String newFileName = "";
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
		newFileName = formater.format(new Date()) +"" +new Random().nextInt(100000)+getFileExt(oldFileName);
		return newFileName;
	}
	
	
	
}

