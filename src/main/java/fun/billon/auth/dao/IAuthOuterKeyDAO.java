package fun.billon.auth.dao;

import fun.billon.auth.api.constant.AuthCacheConstant;
import fun.billon.auth.api.model.AuthOuterKeyModel;
import fun.billon.common.cache.CacheType;
import fun.billon.common.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * 外部应用密钥DAO
 *
 * @author billon
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface IAuthOuterKeyDAO {

    /**
     * 新增外部应用
     *
     * @param authOuterKeyModel authOuterKeyModel.appId 应用id 必填
     *                          authOuterKeyModel.platform 平台(1:android;2:iOS) 必填
     *                          authOuterKeyModel.name 应用名称 必填
     *                          authOuterKeyModel.desc 描述 必填
     *                          authOuterKeyModel.appSecret 给应用分配的appSecret 必填
     *                          authOuterKeyModel.tokenExpTime token过期时间 必填
     *                          authOuterKeyModel.refreshTokenExpTime 刷新token的过期时间 必填
     * @return 执行sql影响的数据条数
     */
    int insertAuthOuterKey(AuthOuterKeyModel authOuterKeyModel);

    /**
     * 根据应用id获取外部应用密钥
     *
     * @param authOuterKeyModel authOuterKeyModel.appId 主键 必填
     * @return 根据应用id获取外部应用密钥
     */
    @Cacheable(namespace = AuthCacheConstant.CACHE_NAMESPACE_AUTH_OUTER_KEY_MODEL,
            key = AuthCacheConstant.CACHE_KEY_AUTH_OUTER_KEY_MODEL_APPID,
            type = AuthOuterKeyModel.class, cacheType = CacheType.HASH, expire = 60 * 60 * 24 * 30)
    AuthOuterKeyModel queryAuthOuterKeyByPK(AuthOuterKeyModel authOuterKeyModel);

}