package ${package.Service};

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${superServiceClassPackage};
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
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {


	/**
	 * <p>
	 * Description:
	 * </p>
	 * @param ${entity?uncap_first}
	 */
	void insertObj(${entity} ${entity?uncap_first});

	/**
	 * <p>
	 * Description:
	 * </p>
	 * @param ${entity?uncap_first}
	 */
	void deleteObj(${entity} ${entity?uncap_first});

	/**
	 * <p>
	 * Description:
	 * </p>
	 * @param ${entity?uncap_first}
	 */
	void updateObj(${entity} ${entity?uncap_first});

	/**
	 * <p>
	 * Description:
	 * </p>
	 * @param ${entity?uncap_first}
	 * @return
	 */
	${entity} findOneByObj(${entity} ${entity?uncap_first});

	/**
	 * <p>
	 * Description:
	 * </p>
	 * @param ${entity?uncap_first}
	 * @return
	 */
	List<${entity}> findByObj(${entity} ${entity?uncap_first});

	/**
	 * <p>
	 * Description:
	 * </p>
	 * @param page
	 * @param params
	 * @return
	 */
	Page selectMyPage(Page page, Map<String, Object> params);

}
</#if>
