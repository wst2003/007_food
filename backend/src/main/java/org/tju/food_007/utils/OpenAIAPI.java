package org.tju.food_007.utils;

import cn.hutool.core.convert.ConvertException;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
//import jdk.internal.classfile.components.ClassPrinter;
import lombok.experimental.UtilityClass;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Astar
 * ClassName:OpenAIAPI.java
 * date:2023-03-03 16:49
 * Description:
 */
@UtilityClass
public class OpenAIAPI {
    /**
     * 聊天端点
     */
    String chatEndpoint = "https://api.openai.com/v1/chat/completions";

    String prompt = "我现在为你提供信息，你来生成一个针对“commodity”表的查询sql语句（在mysql中使用），记住仅给出sql语句就可以，不需要其他任何文字和信息。" +
            "表的信息如下：名为commodity，包含的属性有：com_ID作为商品的唯一标识符（主键）；com_name为商品名称；com_introduction提供商品介绍；" +
            "com_uploadDate记录商品上传日期的时间戳；com_left表示商品剩余数量；sto_ID关联到特定的存储或库存位置；com_type指示商品类型；com_oriPrice记录商品的原始价格；" +
            "praise_rate为商品获得的好评率。我后续将给出查询要求。";

    /**
     * api密匙
     */
//    String apiKey = "MYKEY";

    /**
     * 发送消息
     *
     * @param txt 内容
     * @return {@link String}
     */
    public String chat(String txt) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", "gpt-3.5-turbo");
        List<Map<String, String>> dataList = new ArrayList<>();
        dataList.add(new HashMap<String, String>(){{
            put("role", "system");
            put("content",prompt);
        }});
        dataList.add(new HashMap<String, String>(){{
            put("role", "user");
            put("content", txt);
        }});
        paramMap.put("messages", dataList);
        JSONObject message = null;

        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
            String body = HttpRequest.post(chatEndpoint)
//                    .header("Authorization", "Bearer "+apiKey)
                    .header("Content-Type", "application/json")
                    .body(JSONUtil.toJsonStr(paramMap))
                    .setProxy(proxy)
                    .execute()
                    .body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject result = choices.get(0, JSONObject.class, Boolean.TRUE);
            message = result.getJSONObject("message");
        } catch (HttpException e) {
            return "出现了异常";
        } catch (ConvertException e) {
            return "出现了异常";
        }
        return message.getStr("content");
    }

    public static void main(String[] args) {
        System.out.println(chat("Hello"));
    }
}
