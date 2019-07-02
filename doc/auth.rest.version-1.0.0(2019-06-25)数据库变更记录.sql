#创建数据库(auth)
CREATE DATABASE IF NOT EXISTS auth;

#切换数据库
USE auth;

#创建内部服务密钥表(auth_inner_key)
CREATE TABLE auth_inner_key
(
    sid         VARCHAR(32) PRIMARY KEY
        COMMENT '内部服务id',
    name        VARCHAR(50)  NOT NULL
        COMMENT '服务名称',
    `desc`      VARCHAR(100) NOT NULL
        COMMENT '描述',
    secret      VARCHAR(32)  NOT NULL
        COMMENT '密钥',
    create_time DATETIME DEFAULT NOW()
        COMMENT '记录创建时间',
    delete_time DATETIME COMMENT '删除时间'
);

#创建内部服务ip过滤规则表(auth_inner_rule_ip)
CREATE TABLE auth_inner_rule_ip
(
    sid         VARCHAR(32) PRIMARY KEY
        COMMENT '内部服务id',
    ip          VARCHAR(1024) NOT NULL
        COMMENT '允许访问该模块的ip,多个ip之间使用","号分隔',
    create_time DATETIME DEFAULT NOW()
        COMMENT '记录创建时间',
    delete_time DATETIME COMMENT '删除时间'
);

#创建外部应用密钥表(auth_outer_key)
CREATE TABLE auth_outer_key
(
    app_id                 VARCHAR(32) PRIMARY KEY
        COMMENT '外部应用id',
    platform               TINYINT     NOT NULL
        COMMENT '平台(1:android;2:iOS;3:H5)',
    name                   VARCHAR(32) NOT NULL
        COMMENT '外部应用名称',
    app_secret             VARCHAR(32) NOT NULL
        COMMENT '给外部应用分配的密钥',
    token_exp_time         INTEGER     NOT NULL
        COMMENT 'jwt生成的token过期时间',
    refresh_token_exp_time INTEGER     NOT NULL
        COMMENT 'jwt刷新token的过期时间',
    create_time            DATETIME DEFAULT NOW()
        COMMENT '记录创建时间',
    delete_time            DATETIME COMMENT '删除时间'
);

#新增内部服务
INSERT INTO auth_inner_key (sid, name, `desc`, secret)
VALUES ('20dcb39d555a161517f40772c7ecd372', 'auth', '授权模块', '15a18ba352172929662e2230d0af99f4');
INSERT INTO auth_inner_key (sid, name, `desc`, secret)
VALUES ('db346fa8dbc252c1a941d10b1dd30488', 'member', '用户模块', '28f264f9b3bf2130070b2e155088de06');
INSERT INTO auth_inner_key (sid, name, `desc`, secret)
VALUES ('691ddc59efede215d886e7528780c64b', 'forum', '社区模块', 'b0fd00a9515ea919e7aafb12499023c6');
INSERT INTO auth_inner_key (sid, name, `desc`, secret)
VALUES ('e26591383fd45d4e43a0ea6cd78a0551', 'im', 'im模块', 'a64ae3dd2cb16bc58ed351b4fac39d44');
INSERT INTO auth_inner_key (sid, name, `desc`, secret)
VALUES ('597fd6f8c448098b196e72fd54846c5b', 'app-web', 'app接口模块', 'd815c41b5109fb9269a4da8655f4de6f');
INSERT INTO auth_inner_key (sid, name, `desc`, secret)
VALUES ('e6b85f22ee50523d8fe41eea27a8b94a', 'master-web', '管理后台接口模块', 'e9cab13c9038d8b70d93579a543213ad');

#新增内部服务ip过滤规则
INSERT INTO auth_inner_rule_ip (sid, ip)
VALUES ('20dcb39d555a161517f40772c7ecd372', '127.0.0.1,192.168.*.*,10.*.*.*');
INSERT INTO auth_inner_rule_ip (sid, ip)
VALUES ('db346fa8dbc252c1a941d10b1dd30488', '127.0.0.1,192.168.*.*,10.*.*.*');
INSERT INTO auth_inner_rule_ip (sid, ip)
VALUES ('691ddc59efede215d886e7528780c64b', '127.0.0.1,192.168.*.*,10.*.*.*');
INSERT INTO auth_inner_rule_ip (sid, ip)
VALUES ('e26591383fd45d4e43a0ea6cd78a0551', '127.0.0.1,192.168.*.*,10.*.*.*');
INSERT INTO auth_inner_rule_ip (sid, ip)
VALUES ('597fd6f8c448098b196e72fd54846c5b', '127.0.0.1,192.168.*.*,10.*.*.*');
INSERT INTO auth_inner_rule_ip (sid, ip)
VALUES ('e6b85f22ee50523d8fe41eea27a8b94a', '127.0.0.1,192.168.*.*,10.*.*.*');

#新增外部应用
INSERT INTO auth_outer_key (app_id, platform, name, app_secret, token_exp_time, refresh_token_exp_time)
VALUES ('360c9549df58480eb31e8a2c2429e2be', 2, 'app(ios)', '894d8e7c9aa47c6eaf05858db8a89665', 604800, 1209600);
INSERT INTO auth_outer_key (app_id, platform, name, app_secret, token_exp_time, refresh_token_exp_time)
VALUES ('1732655c601a4b822c530896cd9e8ce6', 3, 'master-web(H5)', '1bab424598b8dcd6fd23c7750539d67d', 604800, 1209600);