package rain;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Test {
    public static void main(String[] args) {

        try {

            Map data = new HashMap();
               String entity = "a";
            data.put("entity", StringUtils.capitalize(entity));//实体名称

            String fileNamePath = "src/main/resources/rain/model/A.java";//获取生成的文件路径
            System.out.println("fileNamePath:" + fileNamePath);
            String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
            Template template = getConfiguration().getTemplate("voTemplate.ftl");//获取模版信息
//        String pathname = fileDir + "/";
//        new File(pathname).mkdir();
            FileUtils.forceMkdir(new File(fileDir + "/"));
            Writer out = new OutputStreamWriter(
                    new FileOutputStream(fileNamePath), "utf-8");//生成的文件编码
            template.process(data, out);//结合模版生成代码文件
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Configuration getConfiguration() {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(cfg.getClass(), "/template/withoutproperty");
        System.out.println("---"+cfg.getClass().getResource("/template/withoutproperty").toString());
        cfg.setLocale(Locale.CHINA);
        cfg.setDefaultEncoding("UTF-8");
        return cfg;
    }
}
