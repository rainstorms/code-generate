package rain.dbconverts;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OracleDbConvert implements IDbConvert {

    public ColumnTypeEnum processTypeConvert(String fieldType) {
        String t = fieldType.toUpperCase();
        if (t.contains("CHAR")) {
            return ColumnTypeEnum.STRING;
        } else if (!t.contains("DATE") && !t.contains("TIMESTAMP")) {
            if (t.contains("NUMBER")) {
                if (t.matches("NUMBER\\(+\\d\\)")) {
                    return ColumnTypeEnum.INTEGER;
                } else {
                    return t.matches("NUMBER\\(+\\d{2}+\\)") ? ColumnTypeEnum.LONG : ColumnTypeEnum.DOUBLE;
                }
            } else if (t.contains("FLOAT")) {
                return ColumnTypeEnum.FLOAT;
            } else if (t.contains("clob")) {
                return ColumnTypeEnum.CLOB;
            } else if (t.contains("BLOB")) {
                return ColumnTypeEnum.OBJECT;
            } else if (t.contains("binary")) {
                return ColumnTypeEnum.BYTE_ARRAY;
            } else {
                return t.contains("RAW") ? ColumnTypeEnum.BYTE_ARRAY : ColumnTypeEnum.STRING;
            }
        } else {
            return ColumnTypeEnum.DATE;
        }
    }
}
