package ${controllerPackage};

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mythware.framework.core.bean.model.PageModel;
import com.mythware.framework.core.bean.param.PageParams;
import com.mythware.framework.core.bean.response.TResponse;
import com.mythware.framework.extension.mybatis.plus.PageUtils;
import ${entityPackage}.entity.${entity}Entity;
import ${entityPackage}.param.${entity?uncap_first}.${entity}EditParams;
import ${entityPackage}.param.${entity?uncap_first}.${entity}ListParams;
import com.mythware.laboratory.service.I${entity}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@Api(tags = {"${projectName}"})
@RestController
@RequestMapping("/${entity?uncap_first}Controller")
public class ${entity}Controller {

@Autowired
private I${entity}Service ${entity?uncap_first}Service;

@ApiOperation("创建和修改")
@PostMapping(value = "/edit-${entity?uncap_first}")
//	@RequiresAuthentication
public TResponse`<`String> edit${entity}(@Validated @RequestBody ${entity}EditParams params) {
    if (StringUtils.isBlank(params.getId()))
    ${entity?uncap_first}Service.add${entity}(params);
    else
    ${entity?uncap_first}Service.update${entity}(params);

    return new TResponse<>(null);
    }

    @ApiOperation("单个查询 - 根据id")
    @GetMapping(value = "/get-by-id")
    //	@RequiresAuthentication
    public TResponse`<`${entity}Entity> get${entity}ById(@NotEmpty String id) {
        ${entity}Entity ${entity?uncap_first} = ${entity?uncap_first}Service.get${entity}ById(id);
        return new TResponse<>(${entity?uncap_first});
        }

        @ApiOperation("查询列表")
        @PostMapping(value = "/list-${entity?uncap_first}s")
        //	@RequiresAuthentication
        public TResponse`<`PageModel`<`${entity}Entity>> list${entity}s(
            @Validated @RequestBody PageParams pageParams,
            @Validated @RequestBody ${entity}ListParams params) {
            IPage`<`${entity}Entity> ${entity?uncap_first}s = ${entity?uncap_first}Service.list${entity}s(pageParams, params);
                return new TResponse<>(PageUtils.convert(${entity?uncap_first}s));
                }


                }
