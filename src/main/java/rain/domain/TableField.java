package rain.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import rain.dbconverts.ColumnTypeEnum;

@Data
@NoArgsConstructor
public class TableField {
    private boolean keyFlag;
    private String name;
    private String type;
    private String propertyName;
    private ColumnTypeEnum columnType;
    private String comment;
}