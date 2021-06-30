package rain.generate;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import rain.config.DataSourceConfig;
import rain.config.GlobalConfig;
import rain.domain.TableInfo;
import rain.utils.CommonUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static rain.generate.CodeFactory.*;

@Slf4j
@NoArgsConstructor
public class CodeGenerate {

    private TableInfo tableInfo;
    private GlobalConfig globalConfig;
    private List<TableInfo> tableInfoList;
    private DataSourceConfig dataSourceConfig;

    public CodeGenerate(GlobalConfig globalConfig, DataSourceConfig dataSourceConfig) {
        this.globalConfig = globalConfig;
        this.dataSourceConfig = dataSourceConfig;
    }

    public Map<String, Object> buildData() {
        Map<String, Object> data = new HashMap<>();
        data.put("entityPackage", globalConfig.getEntityPackage());//实体的包名
        data.put("controllerPackage", globalConfig.getControllerPackage());
        data.put("servicePackage", globalConfig.getServicePackage());
        data.put("serviceImplPackage", globalConfig.getServiceImplPackage());
        data.put("mapperPackage", globalConfig.getMapperPackage());
        //移除表前缀，表名之间的下划线，得到实体类型
        String entity = CommonUtils.getNoUnderlineStr(CommonUtils.removePrefix(tableInfo.getName().toLowerCase(), globalConfig.getPrefix()));
        data.put("entity", StringUtils.capitalize(entity));//实体名称
        data.put("author", globalConfig.getAuthor());//创建作者
        data.put("projectName", globalConfig.getProjectName());//项目名称
        data.put("date", CommonUtils.getFormatTime("yyyy-MM-dd HH:mm:ss", new Date()));//创建时间
        data.put("table", tableInfo);//表信息
        return data;
    }

    private void initConfig() {
        if (dataSourceConfig == null)
            throw new RuntimeException("dataSourceConfig is null");

        if (globalConfig == null)
            throw new RuntimeException("globalConfig is null");

        tableInfoList = dataSourceConfig.getTablesInfo(globalConfig.getTableNames());
    }

    /**
     * 生成代码文件
     *
     * @return
     */
    public void generateFiles() {
        initConfig();

        for (TableInfo tableInfo : tableInfoList) {
            this.tableInfo = tableInfo;//当前需要生成的表
            log.info("------Code----Generation----[单表模型:" + tableInfo.getName() + "]------- 生成中。。。");
            try {
                CodeFactory codeFactory = new CodeFactory();
                codeFactory.setGlobalConfig(globalConfig);
                Map<String, Object> data = buildData();
                codeFactory.generateFile("entityTemplate.ftl", CodeType.entity, data);
                codeFactory.generateFile("controllerTemplate.ftl", CodeType.controller, data);
                codeFactory.generateFile("serviceTemplate.ftl", CodeType.service, data);
                codeFactory.generateFile("serviceImplTemplate.ftl", CodeType.serviceImpl, data);
                codeFactory.generateFile("mapperTemplate.ftl", CodeType.mapper, data);
                codeFactory.generateFile("daoTemplate.ftl", CodeType.dao, data);
                if (StringUtils.isNotBlank(globalConfig.getMapperXmlPath())) {
                    codeFactory.generateFile("mapperXmlTemplate.ftl", CodeType.mapperXml, data);
                }
                log.info("-------Code----Generation-----[单表模型：" + tableInfo.getName() + "]------ 生成完成。。。");
            } catch (Exception e) {
                e.printStackTrace();
                log.info("-------Code----Generation-----[单表模型：" + tableInfo.getName() + "]------ 生成失败。。。");
                return;
            }
        }
    }
}
