package ${controllerPackage};

import ${entityPackage}.${entity};
import ${servicePackage}.${entity}Service;
import com.xinhuo.demo.Constants;
import com.xinhuo.demo.common.model.PageResponseMsg;
import com.xinhuo.demo.common.model.ResponseMsg;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/${entity?uncap_first}")
public class ${entity}Controller {

    @Autowired
    private ${entity}Service ${entity?uncap_first}Service;

    /**
    * 添加和修改 ${entity?uncap_first}
    *
    * @param ${entity?uncap_first}Dto
    * @return
    */
    @PostMapping("/edit${entity}")
    public ResultVo edit${entity}(@RequestBody Edit${entity}Dto ${entity?uncap_first}Dto) {
    // 校验参数
    ResultVo result = checkEdit${entity}(${entity?uncap_first}Dto);
    if (null != result) return result;

    if (null == ${entity?uncap_first}Dto.getId()) { // 没有id 说明是新的${entity?uncap_first}
    ${entity} ${entity?uncap_first} = ${entity?uncap_first}Dto.toAdd${entity}();
    int i = service.add${entity}(${entity?uncap_first});
    return i > 0 ? ResultVo.ok("添加成功") : ResultVo.fail(501, "添加失败");
    }

    ${entity} ${entity?uncap_first} = ${entity?uncap_first}Dto.to${entity}();
    int i = service.update${entity}(${entity?uncap_first});
    return i > 0 ? ResultVo.ok("修改成功") : ResultVo.fail(501, "修改失败");
    }


    /**
    * 详情
    *
    * @param id
    * @return
    */
    @GetMapping("/find${entity}/{id}")
    public ${entity}Vo find${entity}(@PathVariable("id") String id) {
    ${entity} ${entity?uncap_first} = service.find${entity}(id);
    return ${entity}Vo.convert(${entity?uncap_first});
    }

    /**
    * 按状态查询 ${entity?uncap_first}
    *
    * @return
    */
    @PostMapping("/query${entity}esByState")
    public Query${entity}esByStateVo query${entity}esByState(@RequestBody Query${entity}esByStateDto dto) {


    PageInfo<${entity}> ${entity?uncap_first} = service.query${entity}esByState(state, dto.getPage());
    return Query${entity}esByStateVo.convert(${entity?uncap_first});
    }

    /**
    * 删除 ${entity?uncap_first}
    *
    * @param id
    * @return
    */
    @GetMapping("/delete${entity}/{id}")
    public ResultVo delete${entity}(@PathVariable("id") String id) {

        int i = service.change${entity}State(id, validStates, ${entity}.${entity}State.DELETE.getCode());
        return i > 0 ? ResultVo.ok("删除成功") : ResultVo.fail("删除失败");
    }
}
