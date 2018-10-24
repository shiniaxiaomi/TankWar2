import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {

	static Properties props=new Properties();
	
	static {
		try {
			props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tankWar.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//PropertyMgr.getProperty("reProduceTankCount");//
	public static int getProperty(String key) {//获取配置文件中的数据---key是要获取的变量名称
		return Integer.valueOf(props.getProperty(key));
	}
}
