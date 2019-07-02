package fun.billon.auth.service;

import fun.billon.auth.api.model.AuthInnerKeyModel;
import fun.billon.auth.api.model.AuthInnerRuleIpModel;
import fun.billon.auth.api.model.AuthOuterKeyModel;
import fun.billon.common.model.ResultModel;

import java.util.Map;

/**
 * 授权服务
 *
 * @author billon
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IAuthService {

    /**
     * 新增内部服务密钥
     *
     * @param name 服务名称
     * @param desc 描述
     * @return 内部服务密钥信息
     */
    ResultModel<AuthInnerKeyModel> addInnerKey(String name, String desc);

    /**
     * 新增外部应用密钥
     *
     * @param platform            平台(1:android;2:iOS;3:H5)
     * @param name                外部应用名称
     * @param tokenExpTime        jwt生成的token过期时间,单位秒(s)
     * @param refreshTokenExpTime jwt刷新token的过期时间,单位秒(s)
     * @return 外部应用密钥
     */
    ResultModel<AuthOuterKeyModel> addOuterKey(int platform, String name, int tokenExpTime, int refreshTokenExpTime);

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
    ResultModel<String> token(String appId, String uid, Map<String, String> extras);

    /**
     * 外部应用密钥
     *
     * @param appId 外部应用id 必填
     * @return 外部应用密钥
     */
    ResultModel<AuthOuterKeyModel> outerKey(String appId);

    /**
     * 内部服务密钥
     *
     * @param sid 内部服务id 必填
     * @return 内部服务密钥
     */
    ResultModel<AuthInnerKeyModel> innerKey(String sid);

    /**
     * 内部服务ip过滤规则
     *
     * @param sid 内部服务id 必填
     * @return 内部服务ip过滤规则
     */
    ResultModel<AuthInnerRuleIpModel> innerIpRule(String sid);

}