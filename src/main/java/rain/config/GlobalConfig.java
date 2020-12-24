package rain.config;

import lombok.Data;

@Data
public class GlobalConfig {
    private String systemEncoding = "utf-8";//文件编码
    private String templatePath = "/template";//模板

    private String prefix;//表前缀
    private String author="";//作者
    private String projectName="";//项目名称
    private String outputDir;//文件输出路径，不配置的话默认输出当前项目的resources/code目录下

    private String[] tableNames;//表名
    private String entityPackage = "entity";//实体包
    private String controllerPackage = "controller";//控制层包名
    private String servicePackage = "service";//service层包名
    private String serviceImplPackage = "service.Impl";//serviceImpl层包名

    private String daoPackage = "dao";//持久层包名

    private String mapperPackage = "mapper";//dao层包名
    private String mapperXmlPath = "";//dao层xml路径

}
