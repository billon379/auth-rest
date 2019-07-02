package fun.billon.auth.dao;

import fun.billon.auth.api.constant.AuthCacheConstant;
import fun.billon.auth.api.model.AuthInnerRuleIpModel;
import fun.billon.common.cache.CacheType;
import fun.billon.common.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * 内部服务ip过滤规则DAO
 *
 * @author billon
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface IAuthInnerRuleIpDAO {

    /**
     * 新增内部服务ip过滤规则
     *
     * @param authInnerRuleIpModel authInnerRuleIpModel.sid 内部服务id 必填
     *                             authInnerRuleIpModel.ip 可以访问该模块的内部服务的ip地址，多个ip之间使用","号分隔 必填
     * @return 执行sql影响的数据条数
     */
    int insertAuthInnerRuleIp(AuthInnerRuleIpModel authInnerRuleIpModel);

    /**
     * 根据应用Id获取内部服务ip过滤规则
     *
     * @param authInnerRuleIpModel authInnerRuleIpModel.sid 内部服务id 必填
     * @return 符合条件的数据
     */
    @Cacheable(namespace = AuthCacheConstant.CACHE_NAMESPACE_AUTH_INNTER_RULE_IP_MODEL,
            key = AuthCacheConstant.CACHE_KEY_AUTH_INNER_RULE_IP_MODEL_SID,
            type = AuthInnerRuleIpModel.class, cacheType = CacheType.HASH, expire = 60 * 60 * 24 * 30)
    AuthInnerRuleIpModel queryAuthInnerRuleIpByPK(AuthInnerRuleIpModel authInnerRuleIpModel);

}