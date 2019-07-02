package fun.billon.auth.service;

import fun.billon.common.encrypt.MD5;
import fun.billon.common.util.StringUtils;
import org.junit.Test;

import java.util.Date;

/**
 * 授权服务测试
 *
 * @author billon
 * @version 1.0.0
 * @since 1.0.0
 */
public class AuthServiceTest {

    @Test
    public void testAddAuthInnerKey() {
        String name = "master-web";
        String desc = "管理后台接口模块";
        String sid = MD5.encode(name + desc + new Date().toString() + StringUtils.random(10, false));
        String secret = MD5.encode(sid + new Date().toString() + StringUtils.random(10, false));
        System.out.println("sid:" + sid);
        System.out.println("secret:" + secret);
    }

    @Test
    public void testAddAuthOuterKey() {
        int tokenExpTime = 604800;
        int refreshTokenExpTime = 1209600;
        int platform = 3;
        String name = "master-web(H5)";
        String appId = MD5.encode(name + platform + tokenExpTime + refreshTokenExpTime + new Date().toString() + StringUtils.random(10, false));
        String appSecret = MD5.encode(appId + StringUtils.random(10, false));
        System.out.println("appId:" + appId);
        System.out.println("appSecret:" + appSecret);
    }

}