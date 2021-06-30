package rain.generate;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import rain.config.GlobalConfig;


import java.io.*;
import java.util.Locale;
import java.util.Map;

@Data
public class CodeFactory {
    private GlobalConfig globalConfig;

    /**
     * 把配置数据注入模版，生成代码文件
     *
     * @param templateFileName
     * @param type
     * @param data
     * @throws TemplateException
     * @throws IOException
     */
    public void generateFile(String templateFileName, CodeType type, Map<String, Object> data) throws TemplateException, IOException {
        String entityName = data.get("entity").toString();
        String fileNamePath = getCodePath(type, entityName);//获取生成的文件路径
        if (fileNamePath.endsWith("src/main/resources/code")) return;

        System.out.println("fileNamePath:" + fileNamePath);
        String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
        Template template = getConfiguration().getTemplate(templateFileName);//获取模版信息
        System.out.println(fileDir);
        File directory = new File(fileDir + "/");
        FileUtils.forceMkdir(directory);
        Writer out = new OutputStreamWriter(
                new FileOutputStream(fileNamePath), globalConfig.getSystemEncoding());//生成的文件编码
        template.process(data, out);//结合模版生成代码文件
        out.close();
    }

    public Configuration getConfiguration() {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(super.getClass(), globalConfig.getTemplatePath());
        cfg.setLocale(Locale.CHINA);
        cfg.setDefaultEncoding("UTF-8");
        return cfg;
    }

    public static String getProjectPath() {
        return System.getProperty("user.dir").replace("\\", "/") + "/";
    }

    /**
     * 获取代码生成的文件路径
     *
     * @param type
     * @param entityName
     * @return
     */
    public String getCodePath(CodeType type, String entityName) {
        StringBuilder path = new StringBuilder();

        //开头，项目路径
        if (StringUtils.isEmpty(this.globalConfig.getOutputDir())) {
            String projectPath = getProjectPath();//没有设置outputDir的话默认用当前项目resources/code路径下
            path.append(projectPath).append("src/main/resources/code");//项目名
        } else {
            path.append(this.globalConfig.getOutputDir());//项目名
        }

        switch (type) {
            case controller:
                String controllerPackage = globalConfig.getControllerPackage();
                if (StringUtils.isNotBlank(controllerPackage))
                    path.append("/java/").append(controllerPackage).append("/")
                            .append(entityName).append("Controller").append(".java");
                break;
            case service:
                String servicePackage = globalConfig.getServicePackage();
                if (StringUtils.isNotBlank(servicePackage))
                    path.append("/java/").append(servicePackage).append("/")
                            .append(entityName).append("Service").append(".java");
                break;
            case serviceImpl:
                String serviceImplPackage = globalConfig.getServiceImplPackage();
                if (StringUtils.isNotBlank(serviceImplPackage))
                    path.append("/java/").append(serviceImplPackage).append("/")
                            .append(entityName).append("ServiceImpl").append(".java");
                break;
            case mapper:
                String mapperPackage = globalConfig.getMapperPackage();
                if (StringUtils.isNotBlank(mapperPackage))
                    path.append("/java/").append(mapperPackage).append("/")
                            .append(entityName).append("Mapper").append(".java");
                break;
            case mapperXml:
                String mapperXmlPath = globalConfig.getMapperXmlPath();
                if (StringUtils.isNotBlank(mapperXmlPath))
                    path.append("/resources/").append(mapperXmlPath).append("/")
                            .append(entityName).append("Mapper").append(".xml");
                break;
            case entity:
                String entityPackage = globalConfig.getEntityPackage();
                if (StringUtils.isNotBlank(entityPackage)) {
                    path.append("/java/").append(entityPackage).append("/")
                            .append(entityName).append("Entity").append(".java");
                }
                break;
            case dao:
                String daoPackage = globalConfig.getDaoPackage();
                if (StringUtils.isNotBlank(daoPackage)) {
                    path.append("/java/").append(daoPackage).append("/I")
                            .append(entityName).append("Dao").append(".java");
                }
                break;
            default:
                throw new IllegalArgumentException("type is not found");

        }

        return path.toString();
    }

    public enum CodeType {
        controller, service, serviceImpl, mapper, mapperXml, entity, dao;
    }
}
