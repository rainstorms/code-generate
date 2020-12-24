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
                path.append("/java/").append(globalConfig.getControllerPackage()).append("/")
                        .append(entityName).append("Controller").append(".java");
                break;
            case service:
                path.append("/java/").append(globalConfig.getServicePackage()).append("/")
                        .append(entityName).append("Service").append(".java");
                break;
            case serviceImpl:
                path.append("/java/").append(globalConfig.getServiceImplPackage()).append("/")
                        .append(entityName).append("ServiceImpl").append(".java");
                break;
            case mapper:
                path.append("/java/").append(globalConfig.getMapperPackage()).append("/")
                        .append(entityName).append("Mapper").append(".java");
                break;
            case mapperXml:
                path.append("/resources/").append(globalConfig.getMapperXmlPath()).append("/")
                        .append(entityName).append("Mapper").append(".xml");
                break;
            case entity:
                path.append("/java/").append(globalConfig.getEntityPackage()).append("/")
                        .append(entityName).append(".java");
                break;
            case dao:
                path.append("/java/").append(globalConfig.getDaoPackage()).append("/")
                        .append(entityName).append("Mapper").append(".xml");
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
