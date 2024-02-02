package fun.xjbcode.glm4;

import com.google.gson.annotations.Expose;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.PropertyNamingStrategy;
//import com.zhipu.oapi.ClientV4;
//import com.zhipu.oapi.Constants;
//import com.zhipu.oapi.core.httpclient.ApacheHttpClientTransport;
//import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
//import com.zhipu.oapi.service.v4.model.ChatCompletionRequestMixIn;
//import com.zhipu.oapi.service.v4.model.ChatFunction;
//import com.zhipu.oapi.service.v4.model.ChatFunctionCall;
//import com.zhipu.oapi.service.v4.model.ChatFunctionCallMixIn;
//import com.zhipu.oapi.service.v4.model.ChatFunctionMixIn;
//import com.zhipu.oapi.service.v4.model.ChatFunctionParameters;
//import com.zhipu.oapi.service.v4.model.ChatMessage;
//import com.zhipu.oapi.service.v4.model.ChatMessageRole;
//import com.zhipu.oapi.service.v4.model.ChatTool;
//import com.zhipu.oapi.service.v4.model.ChatToolType;
//import com.zhipu.oapi.service.v4.model.ModelApiResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.squareup.moshi.Moshi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import top.pulselink.chatglm.ChatClient;

public class ChatAPI {

    private static final String API_KEY_SECRET = "13a72711b63f05f6232f6d90918c4fc8.sDdZzEm03aRIrujv";

//    private static final ClientV4 client = new ClientV4.Builder(API_KEY_SECRET).build();
//
//    private static final ObjectMapper mapper = defaultObjectMapper();

    private static final String requestIdTemplate = "mycompany-%d";

    class Message {
        private String role="user";
        private String content ="";
    }
    class PostObject {
        private String stream="true";
        private String model="glm-4";
        private List<Object> tools=new ArrayList<>();
        private List<Message> messages = new ArrayList<>();
    }

    private PostObject object = new PostObject();
//    public static ObjectMapper defaultObjectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//        mapper.addMixIn(ChatFunction.class, ChatFunctionMixIn.class);
//        mapper.addMixIn(ChatCompletionRequest.class, ChatCompletionRequestMixIn.class);
//        mapper.addMixIn(ChatFunctionCall.class, ChatFunctionCallMixIn.class);
//        return mapper;
//    }
    public ChatAPI() {

    }

    public interface ChatCallback {
        public void onFailure(String msg);
        public void onResponse(String msg);
        public void onFinish();
    }

    private ChatCallback m_callback;
    public void setCallback(ChatCallback callback) {
        m_callback = callback;
    }

    public String Chat(String input) {

        final String url = "https://open.bigmodel.cn/api/paas/v4/chat/completions";
        try {
            Gson gson = new Gson();
            Message msg = new Message();
            msg.content = input;
            object.messages.add(msg);
            String tool = "{" +
                    "\"type\":\"function\","+
                    "\"name\":\"lua_eval\","+
                    "\"description\":\"执行lua脚本\","+
                    "\"parameters\":{" +
                            "\"type\":\"object\","+
                            "\"properties\":{" +
                                "\"script\":{" +
                                    "\"type\":\"string\","+
                                    "\"description\":\"要执行的lua脚本\","+
                                "},"+
                            "},"+
                        "},"+
                    "}";
            String tool1="{\n" +
                    "   \"type\":\"function\",\n" +
                    "   \"function\":{\n" +
                    "      \"name\":\"lua_eval\",\n" +
                    "      \"description\":\"执行lua脚本\",\n" +
                    "      \"parameters\":{\n" +
                    "         \"type\":\"object\",\n" +
                    "         \"properties\":{\n" +
                    "            \"script\":{\n" +
                    "               \"type\":\"string\",\n" +
                    "               \"description\":\"要执行的lua脚本\"\n" +
                    "            }\n" +
                    "         }\n" +
                    "      }\n" +
                    "   }\n" +
                    "}";

            System.out.println("tool is:"+tool);
            Class<Object> x = Object.class;
            Object o = gson.fromJson(tool1, x);
            object.tools.add(o);
            String json = gson.toJson(object);
            String res = post(url, json);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();

    String generate_token(String apiKey, int  expSeconds) {
        Map<String, Object> headers = new HashMap<>();
        String[] temp = apiKey.split("\\.");
        String id = temp[0];
        String key = temp[1];
        headers.put("alg", "HS256");
        headers.put("sign_type", "SIGN");

        long now = System.currentTimeMillis();
        long exp = now + expSeconds*1000;

        return JWT.create().withHeader(headers).withClaim("api_key", id)
                        .withClaim("timestamp", new Date(now))
                                . withClaim("exp", new Date(exp))
                .sign(Algorithm.HMAC256(key));
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        String token=generate_token(API_KEY_SECRET, 128000);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", token)
                .post(body)
                .build();
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        }
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if(m_callback!=null) {
                    m_callback.onFailure(e.getMessage());
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                InputStream ins = response.body().byteStream();
                byte[] all_buffer = new byte[32000];
                int readLen = ins.read(all_buffer);
                int totalReadLen = 0;
                String lastContent=null;
                while (readLen > 0) {
                    totalReadLen+=readLen;
                    String res = new String(all_buffer);
                    String allContent="";

                    String[] segments= res.split("\n\n");
                    Gson gson = new Gson();
                    for(String seg : segments) {
                        System.out.println("seg:" + seg);
                        try {
                            if(seg.startsWith("data:")) {
                                String payload =seg.substring(6);
                                Map<String, Object> obj = gson.fromJson(payload, new TypeToken<Map<String, Object>>() {
                                });
                                List<Map<String, Object>> choices = (List<Map<String, Object>>) obj.get("choices");
                                Map<String, Object> choice1 = choices.get(0);
                                Map<String, Object> msg = (Map<String, Object>) choice1.get("delta");
                                String content = (String)msg.get("content");
                                List<Object> tool_calls = (List<Object>)msg.get("tool_calls");
                                if(content!=null) {
                                    allContent+=content;
                                }
                                if (tool_calls!=null && tool_calls.size()!=0) {
                                    Map<String, Object> tool_call = (Map<String,Object>)tool_calls.get(0);
                                    if (tool_call.get("type").equals("function")) {
                                        Map<String,String> function = (Map<String,String>)tool_call.get("function");
                                        String name = function.get("name");
                                        String arguments = function.get("arguments");
                                        Map<String,String> argumentMap = gson.fromJson(arguments, new TypeToken<Map<String, String>>(){});
                                        allContent+= "name--" +name+"\n";
                                        allContent+= "arguments--" +argumentMap.get("script")+"\n";

                                    }
                                    allContent+= tool_calls.toString();
                                }
                                System.out.println("success:" + seg);
                            } else {
//                                m_callback.onFinish();;
                                System.out.println("failed to parse: "+seg);
                            }
                        } catch (Exception e) {
//                            m_callback.onResponse(allContent);
//                            m_callback.onFinish();;
                            System.out.println("failed to parse: "+seg);
                        }
                    }
                    m_callback.onResponse(allContent);
                    System.out.println("allContent:" + allContent);
                    System.out.println("res:" + res);
                    try{
                        Thread.currentThread().sleep(200);
                    }catch (Exception e) {

                    }
                    readLen = ins.read(all_buffer, totalReadLen, all_buffer.length-totalReadLen);
                    lastContent = allContent;
                }
                if (lastContent!=null) {
                    Message msg = new Message();
                    msg.role="assistant";
                    msg.content = lastContent;
                    object.messages.add(msg);
                }

            }
        });
        return "";
    }

//    public String Chat(String input) {
//        ChatClient chats = new ChatClient(API_KEY_SECRET.split(".")[0]);      //Initial ChatClient (Instantiation)
//        chats.AsyncInvoke(input);                     //Assign the question you entered to the synchronised request
//        return chats.getResponseMessage();  //Print out ChatGLM's response
//    }

//    public String Chat(String input) {
//        List<ChatMessage> messages = new ArrayList<>();
//        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "ChatGLM和你哪个更强大");
//        messages.add(chatMessage);
//        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
//        // 函数调用参数构建部分
//        List<ChatTool> chatToolList = new ArrayList<>();
//        ChatTool chatTool = new ChatTool();
//        chatTool.setType(ChatToolType.FUNCTION.value());
//        ChatFunctionParameters chatFunctionParameters = new ChatFunctionParameters();
//        chatFunctionParameters.setType("object");
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("location", new HashMap<String, Object>() {{
//            put("type", "string");
//            put("description", "城市，如：北京");
//        }});
//        properties.put("unit", new HashMap<String, Object>() {{
//            put("type", "string");
//            put("enum", new ArrayList<String>() {{
//                add("celsius");
//                add("fahrenheit");
//            }});
//        }});
//        chatFunctionParameters.setProperties(properties);
//        ChatFunction chatFunction = ChatFunction.builder()
//                .name("get_weather")
//                .description("Get the current weather of a location")
//                .parameters(chatFunctionParameters)
//                .build();
//        chatTool.setFunction(chatFunction);
//        chatToolList.add(chatTool);
//        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
//                .model(Constants.ModelChatGLM4)
//                .stream(Boolean.FALSE)
//                .invokeMethod(Constants.invokeMethod)
//                .messages(messages)
//                .requestId(requestId)
//                .tools(chatToolList)
//                .toolChoice("auto")
//                .build();
//        ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
//        String ret="";
//        try {
//            ret = mapper.writeValueAsString(invokeModelApiResp);
//            System.out.println("model output:" + ret);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return ret;
//    }
}