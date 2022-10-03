///*
// * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
// * <p>
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not
// * use this file except in compliance with the License. You may obtain a copy of
// * the License at
// * <p>
// * https://www.apache.org/licenses/LICENSE-2.0
// * <p>
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// * License for the specific language governing permissions and limitations under
// * the License.
// */
//package com.baomidou.mybatisplus.generator.config;
//
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import org.jetbrains.annotations.NotNull;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * 模板路径配置项
// *
// * @author tzg hubin
// * @since 2017-06-17
// */
//public class ExtendTemplateConfig2 extends TemplateConfig {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ExtendTemplateConfig2.class);
//
//    /**
//     * 设置实体模板路径
//     */
//    private String entity;
//
//    /**
//     * 设置实体模板路径(kotlin模板)
//     */
//    private String entityKt;
//
//    /**
//     * 设置控制器模板路径
//     */
//    private String controller;
//
//    /**
//     * 设置Mapper模板路径
//     */
//    private String mapper;
//
//    /**
//     * 设置MapperXml模板路径
//     */
//    private String xml;
//
//    /**
//     * 设置Service模板路径
//     */
//    private String service;
//
//    /**
//     * 设置ServiceImpl模板路径
//     */
//    private String serviceImpl;
//
//    /**
//     * 是否禁用实体模板（默认 false）
//     */
//    private boolean disableEntity;
//
//    private String dtos;
//    private String dto;
//    private String addDto;
//    private String updDto;
//    private String pageinDto;
//    private String pageoutDto;
//
//
//    /**
//     * 不对外爆露
//     */
//    private ExtendTemplateConfig2() {
//        this.entity = ConstVal.TEMPLATE_ENTITY_JAVA;
//        this.entityKt = ConstVal.TEMPLATE_ENTITY_KT;
//        this.controller = ConstVal.TEMPLATE_CONTROLLER;
//        this.mapper = ConstVal.TEMPLATE_MAPPER;
//        this.xml = ConstVal.TEMPLATE_XML;
//        this.service = ConstVal.TEMPLATE_SERVICE;
//        this.serviceImpl = ConstVal.TEMPLATE_SERVICE_IMPL;
//
//        this.dto = ConstVal.TEMPLATE_DTO;
//        this.addDto = ConstVal.TEMPLATE_ADD_DTO;
//        this.updDto = ConstVal.TEMPLATE_UPDATE_DTO;
//        this.pageinDto = ConstVal.TEMPLATE_PAGEIN_DTO;
//        this.pageoutDto = ConstVal.TEMPLATE_PAGEOUT_DTO;
//    }
//
//    /**
//     * 当模板赋值为空时进行日志提示打印
//     *
//     * @param value        模板值
//     * @param templateType 模板类型
//     */
//    private void logger(String value, TemplateType templateType) {
//        if (StringUtils.isBlank(value)) {
//            LOGGER.warn("推荐使用disable(TemplateType.{})方法进行默认模板禁用.", templateType.name());
//        }
//    }
//
//    /**
//     * 获取实体模板路径
//     *
//     * @param kotlin 是否kotlin
//     * @return 模板路径
//     */
//    public String getEntity(boolean kotlin) {
//        if (!this.disableEntity) {
//            if (kotlin) {
//                return StringUtils.isBlank(this.entityKt) ? ConstVal.TEMPLATE_ENTITY_KT : this.entityKt;
//            }
//            return StringUtils.isBlank(this.entity) ? ConstVal.TEMPLATE_ENTITY_JAVA : this.entity;
//        }
//        return null;
//    }
//
//    /**
//     * 禁用模板
//     *
//     * @param templateTypes 模板类型
//     * @return this
//     * @since 3.3.2
//     */
//    public ExtendTemplateConfig2 disable(@NotNull TemplateType... templateTypes) {
//        if (templateTypes != null && templateTypes.length > 0) {
//            for (TemplateType templateType : templateTypes) {
//                switch (templateType) {
//                    case ENTITY:
//                        this.entity = null;
//                        this.entityKt = null;
//                        //暂时没其他多的需求,使用一个单独的boolean变量进行支持一下.
//                        this.disableEntity = true;
//                        break;
//                    case CONTROLLER:
//                        this.controller = null;
//                        break;
//                    case MAPPER:
//                        this.mapper = null;
//                        break;
//                    case XML:
//                        this.xml = null;
//                        break;
//                    case SERVICE:
//                        this.service = null;
//                        break;
//                    case SERVICEIMPL:
//                        this.serviceImpl = null;
//                        break;
//                    case DTOS:
//                        this.dto = null;
//                        this.addDto = null;
//                        this.updDto = null;
//                        this.pageinDto = null;
//                        this.pageoutDto = null;
//                        break;
//                    default:
//                }
//            }
//        }
//        return this;
//    }
//
//    /**
//     * 禁用全部模板
//     *
//     * @return this
//     * @since 3.5.0
//     */
//    public ExtendTemplateConfig2 disable() {
//        return disable(TemplateType.values());
//    }
//
//    public String getService() {
//        return service;
//    }
//
//    public String getServiceImpl() {
//        return serviceImpl;
//    }
//
//    public String getMapper() {
//        return mapper;
//    }
//
//    public String getXml() {
//        return xml;
//    }
//
//    public String getController() {
//        return controller;
//    }
//
//    public String getDtos() {
//        return dtos;
//    }
//
//    public void setDtos(String dtos) {
//        this.dtos = dtos;
//    }
//
//    public String getDto() {
//        return dto;
//    }
//
//    public void setDto(String dto) {
//        this.dto = dto;
//    }
//
//    public String getAddDto() {
//        return addDto;
//    }
//
//    public void setAddDto(String addDto) {
//        this.addDto = addDto;
//    }
//
//    public String getUpdDto() {
//        return updDto;
//    }
//
//    public void setUpdDto(String updDto) {
//        this.updDto = updDto;
//    }
//
//    public String getPageinDto() {
//        return pageinDto;
//    }
//
//    public void setPageinDto(String pageinDto) {
//        this.pageinDto = pageinDto;
//    }
//
//    public String getPageoutDto() {
//        return pageoutDto;
//    }
//
//    public void setPageoutDto(String pageoutDto) {
//        this.pageoutDto = pageoutDto;
//    }
//
//    /**
//     * 模板路径配置构建者
//     *
//     * @author nieqiurong 3.5.0
//     */
//    public static class Builder implements IConfigBuilder<ExtendTemplateConfig2> {
//
//        private final ExtendTemplateConfig2 templateConfig;
//
//        /**
//         * 默认生成一个空的
//         */
//        public Builder() {
//            this.templateConfig = new ExtendTemplateConfig2();
//        }
//
//        /**
//         * 禁用所有模板
//         *
//         * @return this
//         */
//        public Builder disable() {
//            this.templateConfig.disable();
//            return this;
//        }
//
//        /**
//         * 禁用模板
//         *
//         * @return this
//         */
//        public Builder disable(@NotNull TemplateType... templateTypes) {
//            this.templateConfig.disable(templateTypes);
//            return this;
//        }
//
//        /**
//         * 设置实体模板路径(JAVA)
//         *
//         * @param entityTemplate 实体模板
//         * @return this
//         */
//        public Builder entity(@NotNull String entityTemplate) {
//            this.templateConfig.disableEntity = false;
//            this.templateConfig.entity = entityTemplate;
//            return this;
//        }
//
//        /**
//         * 设置实体模板路径(kotlin)
//         *
//         * @param entityKtTemplate 实体模板
//         * @return this
//         */
//        public Builder entityKt(@NotNull String entityKtTemplate) {
//            this.templateConfig.disableEntity = false;
//            this.templateConfig.entityKt = entityKtTemplate;
//            return this;
//        }
//
//        /**
//         * 设置service模板路径
//         *
//         * @param serviceTemplate     service接口模板路径
//         * @return this
//         */
//        public Builder service(@NotNull String serviceTemplate) {
//            this.templateConfig.service = serviceTemplate;
//            return this;
//        }
//
//        /**
//         * 设置serviceImpl模板路径
//         *
//         * @param serviceImplTemplate service实现类模板路径
//         * @return this
//         */
//        public Builder serviceImpl(@NotNull String serviceImplTemplate) {
//            this.templateConfig.serviceImpl = serviceImplTemplate;
//            return this;
//        }
//
//        /**
//         * 设置mapper模板路径
//         *
//         * @param mapperTemplate mapper模板路径
//         * @return this
//         */
//        public Builder mapper(@NotNull String mapperTemplate) {
//            this.templateConfig.mapper = mapperTemplate;
//            return this;
//        }
//
//        /**
//         * 设置mapperXml模板路径
//         *
//         * @param mapperXmlTemplate xml模板路径
//         * @return this
//         */
//        public Builder mapperXml(@NotNull String mapperXmlTemplate) {
//            this.templateConfig.xml = mapperXmlTemplate;
//            return this;
//        }
//
//        /**
//         * 设置控制器模板路径
//         *
//         * @param controllerTemplate 控制器模板路径
//         * @return this
//         */
//        public Builder controller(@NotNull String controllerTemplate) {
//            this.templateConfig.controller = controllerTemplate;
//            return this;
//        }
//
//        /**
//         * 设置dtos模板路径
//         *
//         * @param dtosTemplate 控制器模板路径
//         * @return this
//         */
//        public Builder dtos(@NotNull String dtosTemplate) {
//            this.templateConfig.dtos = dtosTemplate;
//
//            this.templateConfig.dto = ConstVal.TEMPLATE_DTO;
//            this.templateConfig.addDto = ConstVal.TEMPLATE_ADD_DTO;
//            this.templateConfig.updDto = ConstVal.TEMPLATE_UPDATE_DTO;
//            this.templateConfig.pageinDto = ConstVal.TEMPLATE_PAGEIN_DTO;
//            this.templateConfig.pageoutDto = ConstVal.TEMPLATE_PAGEOUT_DTO;
//            return this;
//        }
//
//        /**
//         * 构建模板配置对象
//         *
//         * @return 模板配置对象
//         */
//        @Override
//        public ExtendTemplateConfig2 build() {
//            return this.templateConfig;
//        }
//    }
//}
