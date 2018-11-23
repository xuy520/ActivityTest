package pdf;


import java.util.List;
import java.util.Map;

public class ParagraphContent {
    private String title;
    private int columNum;
    private int rowNum;
    private List<ParagraphContent> content;

    private String name;
    private String des;

    public ParagraphContent() {

    }

    public ParagraphContent(String name, String des) {
        this.name = name;
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColumNum() {
        return columNum;
    }

    public void setColumNum(int columNum) {
        this.columNum = columNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public List<ParagraphContent> getContent() {
        return content;
    }

    public void setContent(List<ParagraphContent> content) {
        this.content = content;
    }
}