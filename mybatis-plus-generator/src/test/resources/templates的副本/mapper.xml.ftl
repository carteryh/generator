<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
    <result column="${field.name}" property="${field.propertyName}" />
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
<#list table.commonFields as field>
        ${field.name},
</#list>
        ${table.fieldNames}
    </sql>

</#if>

	<select id="selectPage" resultType="${package.Entity}.${entity}">
		select * from ${table.name} t
		<where>
			<#list table.fields as field>
			<#if !field.keyFlag>
			<#assign fieldName = field.name />
      		<#if field.columnType=='DATE'>
			<if test="p.${field.propertyName}Begin!=null">
				and t.${field.name} <![CDATA[ >= ]]> STR_TO_DATE(${'#'}{p.${field.propertyName}Begin},'%Y-%m-%d')
			</if>
			<if test="p.${field.propertyName}End!=null">
				and t.${field.name} <![CDATA[ < ]]> DATE_ADD(STR_TO_DATE(${'#'}{p.${field.propertyName}End},'%Y-%m-%d'), INTERVAL 1 day)
			</if>
			<#elseif field.columnType=='STRING'>
            <#if field.propertyName?contains("createBy") || field.propertyName?contains("updateBy") || field.propertyName?contains("status") || field.propertyName?contains("code") >
            <if test="p.${field.propertyName}!=null and p.${field.propertyName}!=''">
                and t.${field.name}=${'#'}{p.${field.propertyName}}
            </if>
            <#elseif field.comment?contains('字典')>
            <if test="p.${field.propertyName}!=null">
                and t.${field.name}=${'#'}{p.${field.propertyName}}
            </if>
            <#else>
            <if test="p.${field.propertyName}!=null and p.${field.propertyName}!=''">
                and t.${field.name} like CONCAT('%',${'#'}{p.${field.propertyName}},'%')
            </if>
            </#if>
			<#elseif field.columnType=='LONG'>
			<if test="p.${field.propertyName}!=null">
				and t.${field.name}=${'#'}{p.${field.propertyName}}
			</if>
			<#else>
			<if test="p.${field.propertyName}!=null">
				and t.${field.name}=${'#'}{p.${field.propertyName}}
			</if>
			</#if>
			</#if>
		</#list>
		</where>
	</select>
</mapper>
