package pojo;
import lombok.Data;

import java.io.Serializable;

@Data
public class equipDetail implements Serializable {
    private int subId; //漫水点id
    private String deviceId; //传感器编号
    private String name; //传感器名称
    private String model; //型号
    private String standard; //设计标准
    private String remark; //备注
    private String factory; //设备厂家
    private String subCode; //漫水点编码
    private String liquid; //超声波液位
    private String voltage; //电池电压
    private String tall; //空高
    private String signal; //信号值
    private String snapTime; //更新时间


}
