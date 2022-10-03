package com.baomidou.mybatisplus.generator.samples;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.engine.CustomFreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * H2 代码生成
 *
 * @author hubin,lanjerry
 * @since 1.0
 */
public class OracleCodeGeneratorTest2 {

    /**
     * 执行初始化数据库脚本
     */
//    @BeforeAll
//    public static void before() throws SQLException {
//        Connection conn = DATA_SOURCE_CONFIG.getConn();
//        InputStream inputStream = OracleCodeGeneratorTest.class.getResourceAsStream("/sql/init.sql");
//        ScriptRunner scriptRunner = new ScriptRunner(conn);
//        scriptRunner.setAutoCommit(true);
//        scriptRunner.runScript(new InputStreamReader(inputStream));
//        conn.close();
//    }

    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
        .Builder("jdbc:oracle:thin:@10.0.0.10:1521:HELOWIN", "GY_FIRE", "GY_FIRE")
//        .Builder("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE;MODE=MYSQL", "sa", "")
        .build();

    /**
     * 策略配置
     */
    private StrategyConfig.Builder strategyConfig() {
        return new StrategyConfig.Builder().addInclude("T_GOOD_SUMMARY"); // 设置需要生成的表名
    }

    /**
     * 全局配置
     */
    private GlobalConfig.Builder globalConfig() {
        return new GlobalConfig.Builder().fileOverride();
    }

    /**
     * 包配置
     */
    private PackageConfig.Builder packageConfig() {
        return new PackageConfig.Builder();
    }

    /**
     * 模板配置
     */
    private TemplateConfig.Builder templateConfig() {
//        return new ExtendTemplateConfig.Builder();
        return new TemplateConfig.Builder();
    }

    /**
     * 子类模板配置
     */
    private ExtendTemplateConfig.Builder extendTemplateConfig() {
        return new ExtendTemplateConfig.Builder();
    }

    /**
     * 注入配置
     */
    private InjectionConfig.Builder injectionConfig() {
        // 测试自定义输出文件之前注入操作，该操作再执行生成代码前 debug 查看
        return new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
        });
    }

    /**
     * 简单生成
     */
    @Test
    public void testSimple() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.global(globalConfig().build());
        generator.execute();
    }

    /**
     * 过滤表前缀（后缀同理，支持多个）
     * result: t_simple -> simple
     */
    @Test
    public void testTablePrefix() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().addTablePrefix("t_", "c_").build());
        generator.global(globalConfig().build());
        generator.execute();
    }

    /**
     * 过滤字段后缀（前缀同理，支持多个）
     * result: deleted_flag -> deleted
     */
    @Test
    public void testFieldSuffix() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().addFieldSuffix("_flag").build());
        generator.global(globalConfig().build());
        generator.execute();
    }

    /**
     * 乐观锁字段设置
     * result: 新增@Version注解
     * 填充字段设置
     * result: 新增@TableField(value = "xxx", fill = FieldFill.xxx)注解
     */
    @Test
    public void testVersionAndFill() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().entityBuilder()
            .versionColumnName("version") // 基于数据库字段
            .versionPropertyName("version")// 基于模型属性
            .addTableFills(new Column("create_time", FieldFill.INSERT))    //基于数据库字段填充
            .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))    //基于模型属性填充
            .build());
        generator.global(globalConfig().build());
        generator.execute();
    }

    /**
     * 逻辑删除字段设置
     * result: 新增@TableLogic注解
     * 忽略字段设置
     * result: 不生成
     */
    @Test
    public void testLogicDeleteAndIgnoreColumn() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().entityBuilder()
            .logicDeleteColumnName("deleted") // 基于数据库字段
            .logicDeletePropertyName("deleteFlag")// 基于模型属性
            .addIgnoreColumns("age") // 基于数据库字段
            .build());
        generator.global(globalConfig().build());
        generator.execute();
    }

    /**
     * 自定义模板生成的文件名称
     * result: TSimple -> TSimpleEntity
     */
    @Test
    public void testCustomTemplateName() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig()
            .entityBuilder().formatFileName("%sEntity")
            .mapperBuilder().formatMapperFileName("%sDao").formatXmlFileName("%sXml")
            .controllerBuilder().formatFileName("%sAction")
            .serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImp")
            .build());
        generator.global(globalConfig().build());
        generator.execute();
    }

    /**
     * 自定义模板生成的文件路径
     *
     * @see OutputFile
     */
    @Test
    public void testCustomTemplatePath() {
        // 设置自定义路径
        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.mapperXml, "/Users/carter/Desktop/mybaits-plus/");
        pathInfo.put(OutputFile.entity, "/Users/carter/Desktop/mybaits-plus/entity/");
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.packageInfo(packageConfig().pathInfo(pathInfo).build());
        generator.global(globalConfig().build());
        generator.execute();
    }

    /**
     * 自定义模板
     */
    @Test
    public void testCustomTemplate() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());

        StrategyConfig strategyConfig = GeneratorBuilder.strategyConfigBuilder()
            .enableCapitalMode()
            .enableSkipView()
            .entityBuilder()
            .enableChainModel()
            .enableLombok()
            .controllerBuilder()
            .enableHyphenStyle()
            .enableRestStyle()
            .mapperBuilder()
            .enableMapperAnnotation()
            .enableBaseResultMap()
            .enableBaseColumnList()
            .build();

        generator.strategy(strategyConfig);


        PackageConfig packageConfig = new PackageConfig.Builder()
            .parent("com.cloud")
            .moduleName("supply")
            .controller("controller")
            .service("service")
            .serviceImpl("service.impl")
            .entity("model")
            .mapper("mapper")
//            .mapperXml("mapper.xml")
            .other("dto")
            .pathInfo(new HashMap<>())
//            .pathInfo(pathInfo)
            .build();

        String path = "/Users/carter/Desktop/mybaits-plus/";
        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.controller, (path + packageConfig.getParent() + '/' + packageConfig.getController()).replaceAll("\\.", "/"));
        pathInfo.put(OutputFile.service, (path + packageConfig.getParent() + '/' + packageConfig.getService()).replaceAll("\\.", "/"));
        pathInfo.put(OutputFile.serviceImpl, (path + packageConfig.getParent() + '/' + packageConfig.getServiceImpl()).replaceAll("\\.", "/"));
        pathInfo.put(OutputFile.entity, (path + packageConfig.getParent() + '/' + packageConfig.getEntity()).replaceAll("\\.", "/"));
        pathInfo.put(OutputFile.mapper, (path + packageConfig.getParent() + '/' + packageConfig.getMapper()).replaceAll("\\.", "/"));
        pathInfo.put(OutputFile.other, (path + packageConfig.getParent() + '/' + packageConfig.getOther()).replaceAll("\\.", "/"));
        pathInfo.put(OutputFile.mapperXml, (path + "resources/sqlmaps"));

        packageConfig.getPathInfo().putAll(pathInfo);

        generator.packageInfo(packageConfig);


        Map<String, String> customMap = new HashMap<>();
        customMap.put("Dto.java", "templates/dto.java.ftl");
        customMap.put("AddDTO.java", "templates/adddto.java.ftl");
        customMap.put("Upd.java", "templates/upddto.java.ftl");
        customMap.put("PageIn.java", "templates/pagein.java.ftl");
        customMap.put("PageOut.java", "templates/pageout.java.ftl");

        InjectionConfig injectionConfig = new InjectionConfig.Builder()
            .beforeOutputFile((tableInfo, objectMap) -> {
                System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
            })
//            .customMap(Collections.singletonMap("dto66", "dto6"))
            .customFile(customMap)
            .build();
        generator.injection(injectionConfig);



        // todo
//            templateConfig()
        generator.template(
            templateConfig()
            .controller("/templates/controller.java")
            .service("/templates/service.java")
            .serviceImpl("/templates/serviceImpl.java")
            .entity("/templates/model.java")
//            .dtos("/templates/dto.java")
            .mapperXml("/templates/mapper.xml")
            .build());
        generator.global(globalConfig().enableSwagger()
//            .outputDir("/Users/carter/Desktop/mybaits-plus/")
                .outputDir(path)
            .build());


     /*
        模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
       .templateEngine(new BeetlTemplateEngine())
       .templateEngine(new FreemarkerTemplateEngine())
     */
        generator.execute(new CustomFreemarkerTemplateEngine());

//        entityLombokModel
    }

    /**
     * 自定义注入属性
     */
    @Test
    public void testCustomMap() {
        // 设置自定义属性
        Map<String, Object> map = new HashMap<>();
        map.put("abc", 123);
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.template(templateConfig()
            .entity("/templates/entity1.java")
            .build());
        generator.injection(injectionConfig().customMap(map).build());
        generator.global(globalConfig().build());
        generator.execute();
    }

    /**
     * 自定义文件
     * key为文件名称，value为文件路径
     * 输出目录为 other
     */
    @Test
    public void testCustomFile() {
        // 设置自定义输出文件
        Map<String, String> customFile = new HashMap<>();
        customFile.put("test.txt", "/templates/test.vm");
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.injection(injectionConfig().customFile(customFile).build());
        generator.global(globalConfig().build());
        generator.execute();
    }
}
