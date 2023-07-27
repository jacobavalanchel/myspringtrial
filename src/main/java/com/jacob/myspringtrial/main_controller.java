package com.jacob.myspringtrial;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pojo.equipDetail;
import pojo.sub_list_data;

import java.util.Objects;

import static com.jacob.myspringtrial.Utils.md5.getMD5Str;
import static java.lang.System.currentTimeMillis;

@RestController
@Slf4j
// the line below redirects the requests to trial
@RequestMapping("/trial")




public class main_controller {

    public static JSONObject sub_url_map = new JSONObject();
    static{
        sub_url_map.put("get_token","/road-open/open/login");
        sub_url_map.put("get_sub_list","/road-open/submersible/getSubList");
        //sub_url_map.put("get_sub_list","/road-open/submersible/getSubList");

    }
    protected final String account = "please_fill_in_this_field";
    protected final String ip_port = "please_fill_in_this_field";
    protected final String password = "please_fill_in_this_field";

    public String get_token(){

        //param
        JSONObject param_json= new JSONObject();
        param_json.put("account",account);
        new JSONObject();
        JSONObject signature;
        signature = generate_signature();
        param_json.put("sign", signature.get("sign").toString());
        param_json.put("timeMillis", signature.get("timeMillis").toString());

        //get_token from return body
        JSONObject body = post(sub_url_map.get("get_token").toString(), param_json);
        String String_token;
        assert body != null;
        String_token = body.get("data.token").toString();
        log.info(String_token);
        return String_token;
    }
    @RequestMapping("/getSignature")
    public JSONObject generate_signature(){
        // generate timestamp
        long timeMillis =currentTimeMillis();
        // concat params -> String
        String concat_String = account + password + timeMillis;
        log.info(String.format("concatenated=%s",concat_String));
        // String -> md5
        String md5_String = getMD5Str(concat_String);
        log.info(String.format("MD5 Generated=%s",md5_String));
        // md5 + time -> return_type
        JSONObject sign_and_millis = new JSONObject();
        sign_and_millis.put("sign",md5_String);
        sign_and_millis.put("timeMillis", String.valueOf(timeMillis));
        return sign_and_millis;
    }
    @RequestMapping("/get_sub_list")
    private sub_list_data get_sub_list(){

        JSONObject raw_data_json;

        //post request -> raw_data_json
        raw_data_json = (JSONObject) Objects.requireNonNull(post("get_sub_list", null)).get("data");
        //deserialize object

        return JSONObject.toJavaObject(raw_data_json,sub_list_data.class);
    }

    @RequestMapping("/update_equip")
    //传感器 新增/编辑接口
    private String update_equip(
            @RequestParam(name="subId",required = true) int subId,
            @RequestParam(name="subCode",required = true) String subCode,
            @RequestParam(name="equipList",required = true) String equipList
    ){
        //set request params
        JSONObject request_param = new JSONObject();
        request_param.put("subId",subId);
        request_param.put("subCode",subCode);
        request_param.put("equipList",equipList);

        //get returns
        JSONObject returns_json = (JSONObject) Objects.requireNonNull(post("update_equip", request_param)).get("data");
        return (String) returns_json.get("desc");
    }

    @RequestMapping("/put_equip_detail")
    //更新设备状态
    private String update_equip(
            @RequestParam(name="subId",required =true) Integer subId,
            @RequestParam(name="deviceId",required =true) String deviceId,
            @RequestParam(name="name",required =true) String name,
            @RequestParam(name="model",required =true) String model,
            @RequestParam(name="standard",required =false) String standard,
            @RequestParam(name="remark",required =false) String remark,
            @RequestParam(name="factory",required =true) String factory,
            @RequestParam(name="subCode",required =true) String subCode,
            @RequestParam(name="liquid",required =true) String liquid,
            @RequestParam(name="voltage",required =true) String voltage,
            @RequestParam(name="tall",required =true) String tall,
            @RequestParam(name="signal",required =true) String signal,
            @RequestParam(name="snapTime",required =true) String snapTime
    ){
        //set request params
        equipDetail equip_detail_to_put = new equipDetail();

        equip_detail_to_put.setSubId(subId);
        equip_detail_to_put.setDeviceId(deviceId);
        equip_detail_to_put.setName(name);
        equip_detail_to_put.setModel(model);
        equip_detail_to_put.setStandard(standard);
        equip_detail_to_put.setRemark(remark);
        equip_detail_to_put.setFactory(factory);
        equip_detail_to_put.setSubCode(subCode);
        equip_detail_to_put.setLiquid(liquid);
        equip_detail_to_put.setVoltage(voltage);
        equip_detail_to_put.setTall(tall);
        equip_detail_to_put.setSignal(signal);
        equip_detail_to_put.setSnapTime(snapTime);


        //get returns
        JSONObject returns_json = (JSONObject) Objects.requireNonNull(Objects.requireNonNull(post("update_equip", (JSONObject) JSONObject.toJSON(equip_detail_to_put))).get("data"));
        return (String) returns_json.get("desc");
    }


    private JSONObject post(String sub_url_index, JSONObject param){

        boolean is_token_getting=false;
        if (Objects.equals(sub_url_index, "get_token")) is_token_getting = true;
        //url
        String full_url = "http://" + ip_port + sub_url_map.get(sub_url_index);;
        log.info("full_url= " + full_url);

        //header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json"));
        if(!is_token_getting){//Headers does not come with the key "token" when obtaining a token.
            headers.set("token",get_token());
        }


        //httpEntity
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(param,headers);

        //post_get_token
        RestTemplate client = new RestTemplate();
        try{
            JSONObject body = client.postForEntity(full_url, httpEntity, JSONObject.class).getBody();
            assert body != null : "return body is NULL";
            if(Objects.equals(String.valueOf(body.get("code")), "200")){
                log.info("response obtained");
                log.info("desc returned:"+ body.get("desc"));
                return body;
            }
        }catch (RestClientException exception){
            log.error(String.valueOf(exception));
        }
        return null;
    }

}
