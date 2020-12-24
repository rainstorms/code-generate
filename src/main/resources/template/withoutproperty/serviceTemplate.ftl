package ${servicePackage};

import ${entityPackage}.${entity};
import ${daoPackage}.${entity}Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ${entity}Service {

   @Autowired ${entity}Dao dao;
   
   @Transactional
   public int add${entity}(${entity} ${entity?uncap_first}) {
   return dao.add${entity}(${entity?uncap_first});
   }
   
   @Transactional
   public int update${entity}(${entity} ${entity?uncap_first}) {
   return dao.update${entity}(${entity?uncap_first});
   }
 
   /**
   * 修改 ${entity?uncap_first} state
   *
   * @param id
   * @param oldState 原来的state 状态管理：原状态不对不能修改
   * @param newState 要改成的新的state
   * @return
   */
   @Transactional
   public int change${entity}State(String id, Integer newState) {
    return dao.change${entity}State(id, oldState, newState);
   }

   public ${entity} find${entity}(String id) {
    return dao.find${entity}(id);
   }

   public List<${entity}> query${entity}s() {
    return dao.query${entity}s();
   }

}
