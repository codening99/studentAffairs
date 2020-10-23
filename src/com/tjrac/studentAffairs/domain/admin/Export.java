package com.tjrac.studentAffairs.domain.admin;

/**
 * 导出excel模板类
 *
 * @author ZeNing
 * @create 2020-10-17 17:01
 */
public class Export {

    private Integer excel_id;
    private String keyName;
    private String columnName;
    private String example;

    public Export() {
    }

    public Export(String keyName, String columnName, String example) {
        this.keyName = keyName;
        this.columnName = columnName;
        this.example = example;
    }

    public Export(Integer excel_id, String keyName, String columnName, String example) {
        this.excel_id = excel_id;
        this.keyName = keyName;
        this.columnName = columnName;
        this.example = example;
    }

    public Integer getExcel_id() {
        return excel_id;
    }

    public void setExcel_id(Integer excel_id) {
        this.excel_id = excel_id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @Override
    public String toString() {
        return "Export{" +
                "excel_id=" + excel_id +
                ", keyName='" + keyName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", example='" + example + '\'' +
                '}';
    }
}
