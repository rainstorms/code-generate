package rain;


import rain.config.DataSourceConfig;
import rain.config.GlobalConfig;
import rain.generate.CodeGenerate;

public class Demo {
    public static void main(String[] args) {
        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //自定义模板路径
        globalConfig.setTemplatePath("/template/style2");
        globalConfig.setAuthor("rain");
        globalConfig.setProjectName("实验");
        //实体包名
        globalConfig.setEntityPackage("com.mythware.laboratory.bean");
        //mapper包名
        globalConfig.setMapperPackage("com.xinhuo.demo.mapper");
        //mapper的xml路径
        globalConfig.setMapperXmlPath("mapper");
        //service包名
        globalConfig.setServicePackage("com.xinhuo.demo.service");
        globalConfig.setServiceImplPackage("com.xinhuo.demo.service.impl");
        globalConfig.setControllerPackage("com.mythware.laboratory.app.admin.controller");
        globalConfig.setDaoPackage("com.mythware.laboratory.dao");

        //需要生成的实体
        globalConfig.setTableNames(new String[]{"experiment"});
        //生成的实体移除前缀
        globalConfig.setPrefix("website_");

        //文件输出路径，不配置的话默认输出当前项目的resources/code目录下
//        globalConfig.setOutputDir("D://WorkSpace/IDEA/diary/src/main/java/");

        //数据库配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://{ip}:{port}/{dbname}?useUnicode=true&&characterEncoding=UTF-8&connectTimeout=1000&autoReconnect=true&useSSL=false");
        //填写自己的数据库账号
        dsc.setUsername("root");
        //填写自己的数据库密码
        dsc.setPassword("123456");
        CodeGenerate codeGenerate = new CodeGenerate(globalConfig, dsc);
        //生成代码
        codeGenerate.generateFiles();
    }
}
