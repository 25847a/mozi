package com.fadl.common.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fadl.common.DateUtil;
import com.fadl.common.ReadProperties;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.log.entity.Backup;
import com.fadl.log.service.BackupService;

@Service
public class MySQLDatabaseBackup {
	@Autowired
	ConfigService configService;
	@Autowired
	BackupService backupService;
	private static Logger logger = LoggerFactory.getLogger(MySQLDatabaseBackup.class);
	
	/** 
     * Java代码实现MySQL数据库导出 
     *  
     * @author GaoHuanjie 
     * @param hostIP MySQL数据库所在服务器地址IP 
     * @param userName 进入数据库所需要的用户名 
     * @param password 进入数据库所需要的密码 
     * @param savePath 数据库导出文件保存路径 
     * @param fileName 数据库导出文件文件名 
     * @param databaseName 要导出的数据库名 
     * @return 返回true表示导出成功，否则返回false。 
	 * @throws Exception 
     */
    public  boolean exportDatabaseTool() throws Exception {
    	Config config=configService.getConfig("database_backup", "backup");
    	String savePath=config.getValue();
        File saveFile = new File(savePath);  
        if (!saveFile.exists()) {// 如果目录不存在  
            saveFile.mkdirs();// 创建文件夹  
        }  
        if(!savePath.endsWith(File.separator)){  
            savePath = savePath + File.separator;  
        }  
          
        PrintWriter printWriter = null;  
        BufferedReader bufferedReader = null;  
        try {  
        	String fileName=getNewFileName();
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));  
            Process process = Runtime.getRuntime().exec(" mysqldump -h" + ReadProperties.getValue("hostIP") + " -u" + ReadProperties.getValue("duserName") + " -p" + ReadProperties.getValue("dpassword") + " --set-charset=UTF8 " + ReadProperties.getValue("databaseName"));  
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");  
            bufferedReader = new BufferedReader(inputStreamReader);  
            String line;  
            while((line = bufferedReader.readLine())!= null){  
                printWriter.println(line);  
            }  
            printWriter.flush();  
            if(process.waitFor() == 0){//0 表示线程正常终止。  
            	 Backup backup=new Backup();
                 backup.setName(fileName);
                 backup.setPath(savePath+fileName);
                 backupService.insert(backup);//添加备份记录
                return true;  
            }  
        }catch (IOException e) {  
        	logger.error("MySQLDatabaseBackup>>>>>>>>>>exportDatabaseTool>>>>>>>>>>>>>>>>>>",e);
        } finally {  
            try {  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
                if (printWriter != null) {  
                    printWriter.close();  
                }  
            } catch (IOException e) {  
            	logger.error("MySQLDatabaseBackup>>>>>>>>>>exportDatabaseTool>>>>>>>>>>>>>>>>>>",e);
            }  
        }  
        return false;  
    }  
    
    /**
     * 获取备份文件名
     */
    public  String getNewFileName(){
    	return DateUtil.getSystemTimex(new Date())+".sql";
    }
    /**
     * 删除备份文件
     */
    public  boolean delFile(Long id) {	
    	Backup backup=backupService.selectById(id);
    	if(backup!=null){
    		String filePath = backup.getPath();			
        	filePath = filePath.toString();			
        	File myDelFile = new File(filePath);			
        	myDelFile.delete();		
        	return backupService.deleteById(id);
    	}
		return false;
    }
   
}
