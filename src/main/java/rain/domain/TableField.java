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

    public String getPropertyType() {
        return null != this.columnType ? this.columnType.getType() : null;
    }

    public String getCapitalName() {
        return this.propertyName.substring(0, 1).toUpperCase() + this.propertyName.substring(1);
    }
}