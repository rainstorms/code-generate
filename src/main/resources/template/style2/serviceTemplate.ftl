package ${servicePackage};

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mythware.framework.core.bean.param.PageParams;
import ${entityPackage}.entity.${entity}Entity;
import ${entityPackage}.param.${entity?uncap_first}.${entity}EditParams;
import ${entityPackage}.param.${entity?uncap_first}.${entity}ListParams;

public interface I${entity}Service {

void add${entity}(${entity}EditParams params);

void update${entity}(${entity}EditParams params);

${entity}Entity get${entity}ById(String id);

IPage`<`${entity}Entity> list${entity}s(PageParams pageParams, ${entity}ListParams params);
    }