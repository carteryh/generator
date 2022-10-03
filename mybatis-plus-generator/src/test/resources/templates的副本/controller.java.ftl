package ${package.Controller};

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import com.common.response.R;
import com.common.util.BeanCloneUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Other}.${entity}AddDTO;
import ${package.Other}.${entity}UpdDTO;
import ${package.Other}.${entity}PageIn;
import ${package.Other}.${entity}PageOut;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:${table.comment!} 业务请求
 * </p>
 * @author ${author}
 * @date ${date}
 */
<#list table.fields as field>
<#if field.keyFlag>
       <#assign Key = field.propertyName>
</#if>
</#list>
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/${table.entityPath?lower_case}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
@Api(value = "${table.entityPath?lower_case}", tags = "${table.comment!}")
public class ${table.controllerName} {
</#if>

	@Autowired
	private ${table.serviceName} ${table.serviceName?uncap_first};

	@GetMapping("/{${Key}}")
	@ApiOperation(value = "通过${Key}获取")
	public R<${entity}> info(@PathVariable(name = "${Key}", required = true) Long ${Key}) {
		${entity} ${entity?uncap_first} = ${table.serviceName?uncap_first}.findById(${Key});
		return R.ok(${entity?uncap_first});
	}

	@PostMapping("/page")
	@ApiOperation(value = "分页")
	public R<Page> page(@RequestBody ${entity}PageIn pageIn) {
		Map<String, Object> params = BeanCloneUtils.copyTo(pageIn, Map.class);
		Page page = ${table.serviceName?uncap_first}.selectMyPage(new Page(pageIn.getPage(), pageIn.getSize()), params);
		List<${entity}PageOut> list = BeanCloneUtils.copyTo(page.getRecords(), ${entity}PageOut.class);
		page.setRecords(list);
		return R.ok(page);
	}

	@PostMapping
	@ApiOperation(value = "添加")
	public R add(@RequestBody @Valid ${entity}AddDTO dto) {
		${entity} add = BeanCloneUtils.copyTo(dto, ${entity}.class);
		${table.serviceName?uncap_first}.insertObj(add);
		return R.ok("ok");
	}

	@PutMapping
	@ApiOperation(value = "修改")
	public R upd(@RequestBody @Valid ${entity}UpdDTO dto) {
		${entity} modify = BeanCloneUtils.copyTo(dto, ${entity}.class);
		${table.serviceName?uncap_first}.updateObj(modify);
		return R.ok("ok");
	}

	@DeleteMapping("/{${Key}}")
	@ApiOperation(value = "删除")
	public R del(@PathVariable(name = "${Key}", required = true) Long ${Key}) {
		${entity} del = new ${entity}();
		del.set${Key?cap_first}(${Key});
		${table.serviceName?uncap_first}.deleteObj(del);
		return R.ok("ok");
	}

}
</#if>
