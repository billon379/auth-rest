package fun.billon.auth.controller;

import fun.billon.auth.api.model.AuthInnerKeyModel;
import fun.billon.auth.api.model.AuthInnerRuleIpModel;
import fun.billon.auth.api.model.AuthOuterKeyModel;
import fun.billon.auth.service.IAuthService;
import fun.billon.common.constant.CommonStatusCode;
import fun.billon.common.exception.ParamException;
import fun.billon.common.model.ResultModel;
import fun.billon.common.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 权限控制模块。功能：1.统一授权；2.内部服务身份验证；3.内部服务ip过滤
 *
 * @author billon
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
public class AuthController {

    @Resource
    private IAuthService authService;

    /**
     * 获取token
     *
     * @param appId    外部应用id 必填
     * @param uid      要签名的用户id 必填
     * @param paramMap token中要存放的附加信息 选填
     * @return 使用jwt签发的token
     */
    @GetMapping("/token/{appId}/{uid}")
    public ResultModel<String> token(@PathVariable(value = "appId") String appId,
                                     @PathVariable(value = "uid") String uid,
                                     @RequestParam Map<String, String> paramMap) {
        return authService.token(appId, uid, paramMap);
    }

    /**
     * 新增外部应用密钥
     *
     * @param paramMap paramMap.platform            平台(1:android;2:iOS;3:H5) 必填
     *                 paramMap.name                外部应用名称 必填
     *                 paramMap.tokenExpTime        jwt生成的token过期时间,单位秒(s) 必填
     *                 paramMap.refreshTokenExpTime jwt刷新token的过期时间,单位秒(s) 必填
     * @return 外部应用密钥
     */
    @PostMapping(value = "/outer/key")
    public ResultModel<AuthOuterKeyModel> addOuterKey(@RequestParam Map<String, String> paramMap) {
        ResultModel<AuthOuterKeyModel> resultModel = new ResultModel<>();
        String[] paramArray = new String[]{"platform", "name", "tokenExpTime", "refreshTokenExpTime"};
        boolean[] requiredArray = new boolean[]{true, true, true, true};
        Class[] classArray = new Class[]{Integer.class, String.class, Integer.class, Integer.class};
        try {
            StringUtils.checkParam(paramMap, paramArray, requiredArray, classArray, null);
        } catch (ParamException e) {
            resultModel.setFailed(CommonStatusCode.PARAM_INVALID, e.getMessage());
            return resultModel;
        }
        int platform = Integer.parseInt(paramMap.get("platform"));
        String name = paramMap.get("name");
        int tokenExpTime = Integer.parseInt(paramMap.get("tokenExpTime"));
        int refreshTokenExpTime = Integer.parseInt(paramMap.get("refreshTokenExpTime"));
        return authService.addOuterKey(platform, name, tokenExpTime, refreshTokenExpTime);
    }

    /**
     * 获取外部应用密钥
     *
     * @param appId 外部应用id 必填
     * @return 外部应用密钥
     */
    @GetMapping(value = "/outer/key/{appId}")
    public ResultModel<AuthOuterKeyModel> outerKey(@PathVariable(value = "appId") String appId) {
        return authService.outerKey(appId);
    }

    /**
     * 获取内部服务密钥
     *
     * @param paramMap paramMap.name 服务名称 必填
     *                 paramMap.desc 描述 必填
     * @return 内部服务密钥
     */
    @PostMapping(value = "/inner/key")
    public ResultModel<AuthInnerKeyModel> addInnerKey(@RequestParam Map<String, String> paramMap) {
        ResultModel<AuthInnerKeyModel> resultModel = new ResultModel<>();
        String[] paramArray = new String[]{"name", "desc"};
        boolean[] requiredArray = new boolean[]{true, true};
        Class[] classArray = new Class[]{String.class, String.class};
        try {
            StringUtils.checkParam(paramMap, paramArray, requiredArray, classArray, null);
        } catch (ParamException e) {
            resultModel.setFailed(CommonStatusCode.PARAM_INVALID, e.getMessage());
            return resultModel;
        }
        String name = paramMap.get("name");
        String desc = paramMap.get("desc");
        return authService.addInnerKey(name, desc);
    }

    /**
     * 获取内部服务密钥
     *
     * @param sid 内部服务id 必填
     * @return 内部服务密钥
     */
    @GetMapping(value = "/inner/key/{sid}")
    public ResultModel<AuthInnerKeyModel> innerKey(@PathVariable(value = "sid") String sid) {
        return authService.innerKey(sid);
    }

    /**
     * 获取内部服务ip过滤规则
     *
     * @param sid 内部服务id 必填
     * @return 内部服务过滤规则
     */
    @GetMapping(value = "/inner/rule/ip/{sid}")
    public ResultModel<AuthInnerRuleIpModel> innerIpRule(@PathVariable(value = "sid") String sid) {
        return authService.innerIpRule(sid);
    }

}