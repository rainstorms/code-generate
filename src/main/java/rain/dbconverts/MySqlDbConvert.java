package rain.dbconverts;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MySqlDbConvert implements IDbConvert {

    public ColumnTypeEnum processTypeConvert(String fieldType) {
        String t = fieldType.toLowerCase();
        if (!t.contains("char") && !t.contains("text")) {
            if (t.contains("bigint")) {
                return ColumnTypeEnum.LONG;
            } else if (t.contains("int")) {
                return ColumnTypeEnum.INTEGER;
            } else if (!t.contains("date") && !t.contains("time") && !t.contains("year")) {
                if (t.contains("text")) {
                    return ColumnTypeEnum.STRING;
                } else if (t.contains("bit")) {
                    return ColumnTypeEnum.BOOLEAN;
                } else if (t.contains("decimal")) {
                    return ColumnTypeEnum.BIG_DECIMAL;
                } else if (t.contains("clob")) {
                    return ColumnTypeEnum.CLOB;
                } else if (t.contains("blob")) {
                    return ColumnTypeEnum.BLOB;
                } else if (t.contains("binary")) {
                    return ColumnTypeEnum.BYTE_ARRAY;
                } else if (t.contains("float")) {
                    return ColumnTypeEnum.FLOAT;
                } else if (t.contains("double")) {
                    return ColumnTypeEnum.DOUBLE;
                } else {
                    return !t.contains("json") && !t.contains("enum") ? ColumnTypeEnum.STRING : ColumnTypeEnum.STRING;
                }
            } else {
                return ColumnTypeEnum.DATE;
            }
        } else {
            return ColumnTypeEnum.STRING;
        }
    }
}

