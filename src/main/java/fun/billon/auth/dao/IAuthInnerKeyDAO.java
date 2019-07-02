package fun.billon.auth.dao;

import fun.billon.auth.api.constant.AuthCacheConstant;
import fun.billon.auth.api.model.AuthInnerKeyModel;
import fun.billon.common.cache.CacheType;
import fun.billon.common.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * 内部服务密钥DAO
 *
 * @author billon
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface IAuthInnerKeyDAO {

    /**
     * 新增内部服务
     *
     * @param authInnerKeyModel authInnerKeyModel.sid 内部服务的id 必填
     *                          authInnerKeyModel.name 内部服务模块名 必填
     *                          authInnerKeyModel.desc 描述 必填
     *                          authInnerKeyModel.secret 分配给内部服务的密钥 必填
     * @return 执行sql影响的数据条数
     */
    int insertAuthInnerKey(AuthInnerKeyModel authInnerKeyModel);

    /**
     * 根据sid获取内部服务
     *
     * @param authInnerKeyModel authInnerKeyModel.sid 内部服务的id 必填
     * @return 符合条件的数据
     */
    @Cacheable(namespace = AuthCacheConstant.CACHE_NAMESPACE_AUTH_INNTER_KEY_MODEL,
            key = AuthCacheConstant.CACHE_KEY_AUTH_INNER_KEY_MODEL_SID,
            type = AuthInnerKeyModel.class, cacheType = CacheType.HASH, expire = 60 * 60 * 24 * 30)
    AuthInnerKeyModel queryAuthInnerKeyByPK(AuthInnerKeyModel authInnerKeyModel);

}