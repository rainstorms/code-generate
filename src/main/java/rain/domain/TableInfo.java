package rain.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TableInfo {
    private String name;
    private List<TableField> fields;
}
