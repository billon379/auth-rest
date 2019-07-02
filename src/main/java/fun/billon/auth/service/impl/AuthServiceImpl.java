package fun.billon.auth.service.impl;

import fun.billon.auth.api.model.AuthInnerKeyModel;
import fun.billon.auth.api.model.AuthInnerRuleIpModel;
import fun.billon.auth.api.model.AuthOuterKeyModel;
import fun.billon.auth.dao.IAuthInnerKeyDAO;
import fun.billon.auth.dao.IAuthInnerRuleIpDAO;
import fun.billon.auth.dao.IAuthOuterKeyDAO;
import fun.billon.auth.service.IAuthService;
import fun.billon.common.constant.CommonStatusCode;
import fun.billon.common.encrypt.MD5;
import fun.billon.common.model.ResultModel;
import fun.billon.common.util.JwtUtils;
import fun.billon.common.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 授权服务
 *
 * @author billon
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Resource
    private IAuthOuterKeyDAO authOuterKeyDAO;

    @Resource
    private IAuthInnerKeyDAO authInnerKeyDAO;

    @Resource
    private IAuthInnerRuleIpDAO authInnerRuleIpDAO;

    /**
     * 新增内部服务密钥
     *
     * @param name 服务名称 必填
     * @param desc 描述 必填
     * @return 内部服务密钥信息
     */
    @Override
    public ResultModel<AuthInnerKeyModel> addInnerKey(String name, String desc) {
        ResultModel<AuthInnerKeyModel> resultModel = new ResultModel<>();
        String sid = MD5.encode(name + desc + new Date().toString() + StringUtils.random(10, false));
        String secret = MD5.encode(sid + new Date().toString() + StringUtils.random(10, false));
        AuthInnerKeyModel authInnerKeyModel = new AuthInnerKeyModel(sid, name, desc, secret);
        authInnerKeyDAO.insertAuthInnerKey(authInnerKeyModel);
        resultModel.setData(authInnerKeyDAO.queryAuthInnerKeyByPK(authInnerKeyModel));
        return resultModel;
    }

    /**
     * 新增外部应用密钥
     *
     * @param platform            平台(1:android;2:iOS;3:H5) 必填
     * @param name                外部应用名称 必填
     * @param tokenExpTime        jwt生成的token过期时间,单位秒(s) 必填
     * @param refreshTokenExpTime jwt刷新token的过期时间,单位秒(s) 必填
     * @return 外部应用密钥
     */
    @Override
    public ResultModel<AuthOuterKeyModel> addOuterKey(int platform, String name, int tokenExpTime, int refreshTokenExpTime) {
        ResultModel<AuthOuterKeyModel> resultModel = new ResultModel<>();
        String appId = MD5.encode(name + platform + tokenExpTime + refreshTokenExpTime + new Date().toString() + StringUtils.random(10, false));
        String appSecret = MD5.encode(appId + StringUtils.random(10, false));
        AuthOuterKeyModel authOuterKeyModel = new AuthOuterKeyModel(appId, platform, name, appSecret, tokenExpTime, refreshTokenExpTime);
        authOuterKeyDAO.insertAuthOuterKey(authOuterKeyModel);
        resultModel.setData(authOuterKeyDAO.queryAuthOuterKeyByPK(authOuterKeyModel));
        return resultModel;
    }

    /**
     * 签发token。使用JWT签发token。包含如下字段：
     * alg:HS256(加密方式HS256)
     * sub:#{uid}(主题,保存用户uid)
     * iss:#{appId}(签发者，外部应用id)
     * iat:#{date}(签发时间)
     * exp:#{date}(过期时间)
     * ext:#{extras}(附加信息,额外添加的token信息)
     *
     * @param appId  外部应用id 必填
     * @param uid    用户id 必填
     * @param extras 扩展信息
     * @return 生成的token
     */
    @Override
    public ResultModel<String> token(String appId, String uid, Map<String, String> extras) {
        ResultModel<String> resultModel = new ResultModel<>();
        /*
         * 根据appId获取外部应用密钥信息
         */
        AuthOuterKeyModel authOuterKeyModel = new AuthOuterKeyModel();
        authOuterKeyModel.setAppId(appId);
        authOuterKeyModel = authOuterKeyDAO.queryAuthOuterKeyByPK(authOuterKeyModel);
        if (authOuterKeyModel == null) {
            resultModel.setCode(CommonStatusCode.APPID_INVALID);
            resultModel.setMsg("appId无效，请联系管理员");
            return resultModel;
        }

        /*
         * 签发token
         */
        String token = JwtUtils.sign(authOuterKeyModel.getAppSecret(), uid, authOuterKeyModel.getAppId(),
                authOuterKeyModel.getTokenExpTime(), extras);
        resultModel.setData(token);
        return resultModel;
    }

    /**
     * 外部应用密钥
     *
     * @param appId 外部应用id 必填
     * @return 外部应用密钥
     */
    @Override
    public ResultModel<AuthOuterKeyModel> outerKey(String appId) {
        ResultModel<AuthOuterKeyModel> resultModel = new ResultModel<>();
        /*
         * 根据appId获取应用密钥
         */
        AuthOuterKeyModel authOuterKeyModel = new AuthOuterKeyModel();
        authOuterKeyModel.setAppId(appId);
        authOuterKeyModel = authOuterKeyDAO.queryAuthOuterKeyByPK(authOuterKeyModel);

        // 未获取到令牌信息
        if (authOuterKeyModel == null) {
            resultModel.setCode(CommonStatusCode.APPID_INVALID);
            resultModel.setMsg("appId无效，请联系管理员");
            return resultModel;
        }

        // 成功，返回令牌信息
        resultModel.setCode(ResultModel.RESULT_SUCCESS);
        resultModel.setData(authOuterKeyModel);
        return resultModel;
    }

    /**
     * 内部服务密钥
     *
     * @param sid 内部服务id 必填
     * @return 内部服务密钥
     */
    @Override
    public ResultModel<AuthInnerKeyModel> innerKey(String sid) {
        ResultModel<AuthInnerKeyModel> resultModel = new ResultModel<>();
        /*
         * 根据sid获取內部服务密钥
         */
        AuthInnerKeyModel authInnerKeyModel = new AuthInnerKeyModel();
        authInnerKeyModel.setSid(sid);
        authInnerKeyModel = authInnerKeyDAO.queryAuthInnerKeyByPK(authInnerKeyModel);

        // 未获取到令牌信息
        if (authInnerKeyModel == null) {
            resultModel.setCode(CommonStatusCode.SID_INVALID);
            resultModel.setMsg("sid无效，请联系管理员");
            return resultModel;
        }

        // 成功，返回令牌信息
        resultModel.setCode(ResultModel.RESULT_SUCCESS);
        resultModel.setData(authInnerKeyModel);
        return resultModel;
    }

    /**
     * 内部服务ip过滤规则
     *
     * @param sid sid 内部服务id 必填 必填
     * @return 内部服务ip过滤规则
     */
    @Override
    public ResultModel<AuthInnerRuleIpModel> innerIpRule(String sid) {
        ResultModel<AuthInnerRuleIpModel> resultModel = new ResultModel<>();
        /*
         * 根据条件获取内部服务ip过滤规则对应的主键
         */
        AuthInnerRuleIpModel authInnerRuleIpModel = new AuthInnerRuleIpModel();
        authInnerRuleIpModel.setSid(sid);
        authInnerRuleIpModel = authInnerRuleIpDAO.queryAuthInnerRuleIpByPK(authInnerRuleIpModel);

        // 未获取到服务信息
        if (authInnerRuleIpModel == null) {
            resultModel.setCode(CommonStatusCode.SID_INVALID);
            resultModel.setMsg("sid无效，请联系管理员");
            return resultModel;
        }

        // 成功，返回ip过滤规则
        resultModel.setCode(ResultModel.RESULT_SUCCESS);
        resultModel.setData(authInnerRuleIpModel);
        return resultModel;
    }

}