package com.baomidou.mybatisplus.generator.samples;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.engine.CustomFreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * H2 代码生成
 *
 * @author hubin,lanjerry
 * @since 1.0
 */
public class OracleCodeGeneratorTest {


    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
        .Builder("jdbc:oracle:thin:@1.207.122.222:8030:racdb", "GYLY_BHD", "GYLY_BHD")
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
     * 自定义模板
     */
    @Test
    public void testCustomTemplate() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
            .addInclude(            "BS_UNIT",
                "BUS_DICTIONARY",
                "BUS_PROTECTED_AREA",
                "BUS_PROTECTED_AREA_TREE",
                "T_MONITOR_1",
                "BUS_AIR",
                "BM_BIOANIMALTYPE",
                "BM_BIOPLANTTYPE") // 对应表 sys_user
            .addTablePrefix("t_")
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
            .moduleName("natural")
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
        customMap.put("DTO.java", "templates/dto.java.ftl");
        customMap.put("AddDTO.java", "templates/adddto.java.ftl");
        customMap.put("UpdDTO.java", "templates/upddto.java.ftl");
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

}
