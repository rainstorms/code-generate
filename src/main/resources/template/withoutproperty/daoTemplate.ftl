package ${daoPackage};

import ${entityPackage}.${entity};
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface ${entity}Dao {

    String select${entity}Prefix = "SELECT  ID id ,TITLE title ,INTRODUCTION introduction" +
    " ,CONTENT content ,SHOW_TIME showTime ,STATE state ,CREATE_TIME createTime ,UPDATE_TIME updateTime " +
    "    FROM  website_${entity?uncap_first}} ";

    @Select(select${entity}Prefix + " WHERE ID = #{id}")
    ${entity} find${entity}(String id);

    @Insert("INSERT INTO website_${entity?uncap_first}} ( ID          , TITLE   , INTRODUCTION    , CONTENT " +
    "                         , SHOW_TIME   , STATE   , CREATE_TIME     , UPDATE_TIME )" +
    "                  VALUES (#{id}        ,#{title} ,#{introduction}  ,#{content}" +
    "                         ,#{showTime}  ,#{state} ,NOW()            ,NOW()        )")
    int add${entity}(${entity} ${entity?uncap_first}});

    @Update("update website_${entity?uncap_first}} " +
    "   SET TITLE        = #{title}   " +
    "      ,INTRODUCTION = #{introduction}   " +
    "      ,CONTENT      = #{content}   " +
    "      ,SHOW_TIME    = #{showTime}   " +
    "      ,STATE        = #{state}   " +
    "      ,UPDATE_TIME  = NOW()   " +
    " WHERE ID           = #{id}")
    int update${entity}(${entity} ${entity?uncap_first}});

}

