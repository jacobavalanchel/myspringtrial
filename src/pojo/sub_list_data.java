package pojo;
import lombok.Data;
import java.io.Serializable;

@Data
public class sub_list_data implements Serializable {
    private Integer id;
    private Integer areaid;
    private String areaName;
    private String townCode;
    private String townName;
    private String townName2;
    private String townCode2;
    private String villageName;
    private String location;
    private String longitude;
    private String latitude;
    private String mapAxis;
    private String submersibleType;
    private String superviUnit;
    private String superviPerson;
    private String phoneNum;
    private String code01;
    private String code02;
    private String code03;
    private String code04;
    private String code05;
    private String code06;
    private String code07;
    private String code08;
    private String code09;
    private String code010;
    private String code011;
    private String code012;
    private String bridgeName;
    private String address;
    private String subCode;
    private Integer effectNum;
    private String effectObj;
    private Integer floodNum;
    private String schedule;
    private equipList equipList= new equipList();


}
