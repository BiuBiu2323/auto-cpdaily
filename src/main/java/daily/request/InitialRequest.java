package daily.request;

import daily.pojo.UserConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

/**
 * @author Neo.Zzj
 * @date 2020/10/31
 */
@Slf4j
public class InitialRequest {

    /**
     * 初始化配置文件
     *
     * @return UserConfig
     */
    public static UserConfig initialConfig() {
        return readDailyProps();
    }

    public static void initialServerChan(String scKey) {
        ServerChanRequest.initial(scKey);
    }

    private static UserConfig readDailyProps() {
        Properties props = new Properties();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("daily.properties");
        try {
            UserConfig userConfig = new UserConfig();
            props.load(is);
            userConfig.setUsername(props.getProperty("username"));
            userConfig.setPassword(props.getProperty("password"));
            userConfig.setLongitude(props.getProperty("longitude"));
            userConfig.setLatitude(props.getProperty("latitude"));
            userConfig.setPosition(props.getProperty("position"));
            userConfig.setScKey(props.getProperty("scKey"));
            userConfig.setActiveAttendance(true);
            userConfig.setActiveAttendance(Boolean.parseBoolean(props.getProperty("activeAttendance")));
            randomLocation(userConfig);
            return userConfig;
        } catch (IOException e) {
            log.info("读取daily.properties错误: ", e);
            return null;
        }
    }

    /**
     * random location 误差 -100，+100
     * @param userConfig
     */
    private static void randomLocation(UserConfig userConfig){
        float r1 = (float) (Math.random() / 10000);
        float r2 = (float) (Math.random() / 10000);
        float longitude = Float.parseFloat(userConfig.getLongitude());
        longitude = Math.random() % 2 == 0 ? longitude - r1 : longitude + r1;
        userConfig.setLongitude(String.format("%f",longitude));
        float latitude = Float.parseFloat(userConfig.getLatitude());
        latitude = Math.random() % 2 == 0 ? latitude - r2 : latitude + r2;
        userConfig.setLatitude(String.format("%f", latitude));
    }
}