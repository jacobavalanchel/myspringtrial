package pojo;
import lombok.Data;

import java.io.Serializable;

@Data
public class equipList implements Serializable {
    private Integer subId; //漫水点id
    private String subCode; //漫水点编码
    private String name; //设备名称
    private String deviceId; //设备编码
    private String model; //型号
    private String standard; //设计标准
    private String remark; //备注
    private String factory; //厂家

}
