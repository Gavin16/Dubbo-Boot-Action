package demo.dubbo.dto.response;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class QuestionOptionDto implements Serializable {

    @NotNull(message = "面试题文件路径不能为空")
    private String path;

    private String source;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "QuestionOptionDto{" +
                "path='" + path + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
