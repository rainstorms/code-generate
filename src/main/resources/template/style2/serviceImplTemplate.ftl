package ${serviceImplPackage};

import ${entityPackage}.${entity};
import ${mapperPackage}.${entity}Mapper;
import ${servicePackage}.${entity}Service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ${entity}Service implements I${entity}Service {

@Override public void add${entity}(${entity}EditParams params) {

}

@Override public void update${entity}(${entity}EditParams params) {

}

@Override public ${entity}Entity get${entity}ById(String id) {
return null;
}

@Override
public IPage`<`${entity}Entity> list${entity}s(PageParams pageParams, ${entity}ListParams params) {
    return null;
    }

    }
