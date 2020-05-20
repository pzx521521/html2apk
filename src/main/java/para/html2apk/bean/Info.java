package para.html2apk.bean;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Info {
    String type;
    String appTitle;
    String packageName;
    String version;
    String url;
}
