package ${package.ServiceImpl};

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<#list table.fields as field>
<#if field.keyFlag>
        <#assign Key = field.propertyName>
</#if>
</#list>

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:${table.comment!}
 * </p>
 * @author ${author}
 * @date ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

	@Autowired
	${entity}Mapper ${entity?uncap_first}Mapper;

	@Override
	public ${entity} findById(Long ${Key?uncap_first}) {
		return ${entity?uncap_first}Mapper.selectById(${Key?uncap_first});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertObj(${entity} ${entity?uncap_first}) {
		${entity?uncap_first}Mapper.insert(${entity?uncap_first});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateObj(${entity} ${entity?uncap_first}) {
		${entity?uncap_first}Mapper.updateById(${entity?uncap_first});

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteObj(${entity} ${entity?uncap_first}) {
		${entity?uncap_first}Mapper.updateById(${entity?uncap_first});
	}

	@Override
	public ${entity} findOneByObj(${entity} ${entity?uncap_first}) {
		QueryWrapper<${entity}> qw = new QueryWrapper<${entity}>(${entity?uncap_first});
		return ${entity?uncap_first}Mapper.selectOne(qw);
	}

	@Override
	public List<${entity}> findByObj(${entity} ${entity?uncap_first}) {
		QueryWrapper<${entity}> qw = new QueryWrapper<${entity}>(${entity?uncap_first});
		return ${entity?uncap_first}Mapper.selectList(qw);
	}

	@Override
	public Page selectMyPage(Page page, Map<String, Object> params) {
		return ${entity?uncap_first}Mapper.selectMyPage(page, params);
	}

}
</#if>
