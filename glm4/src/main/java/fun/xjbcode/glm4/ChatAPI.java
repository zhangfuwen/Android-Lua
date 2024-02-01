package fun.xjbcode.glm4;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String Chat(String input) {

        final String url = "https://open.bigmodel.cn/api/paas/v4/chat/completions";
        try {
            String res = post(url, "{ \"model\":\"glm-4\", \"messages\":[{\"role\":\"user\", \"content\":\"hello\"}] }");
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
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
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