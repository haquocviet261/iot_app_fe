package com.project.smartfrigde.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class Validation {
    public static Long extractUserID(String jwt) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static final String PREF_NAME = "my_shared_preferences";
    public static final String KEY_FOOD_LIST = "food_list";
    public static final String KEY_DEVICE_ITEMS = "device_items";
    public static final String KEY_FOOD_ITEMS = "food_items";
    public static final String KEY_FOOD_CONSUMED = "food_consumed";
    public static final String KEY_FOOD_CONSUMED_END_DAY = "food_consumed";

    public static final String KEY_BMI = "bmi_user";
    public static final String KEY_DEVICE = "device_list";
    public static final String STOMP_WEB_SOCKET_URL = "ws://haquocviet261.click:8888/ws";
    //192.168.1.14
    public static final String WEB_SOCKET_URL = "ws://haquocviet261.click:8888/handle";
    public static final String WEB_SERVICE_URL = "https://jsonplaceholder.typicode.com/";
    public static final int TYPE_USER = 0;
    public static final int TYPE_BOT = 1;
    public static final int TYPE_LOADING = 2;
    public static final String APIKEY = "AIzaSyDd8JllNQLsL-bC-cxhz5J-u-Dld6lcb7U";
    public static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public static final String FOOD_RECOMMEND = "Bạn hãy gửi cho tôi 1 chuỗi json về đề xuất thức ăn và cách nấu gồm name và description và recipe , trong recipe gồm 1 list object có 2 thuộc tính là step và instruction, đây là mẫu ([{\n" +
            "    \"name\": \"Bánh mì kẹp thịt\",\n" +
            "    \"description\": \"Món ăn phổ biến với thịt bò xay, rau và nước sốt ngon miệng.\",\n" +
            "    \"recipe\": [\n" +
            "        {\n" +
            "            \"step\": 1,\n" +
            "            \"instruction\": \"Trộn thịt bò xay với muối, tiêu, hành tây băm nhỏ và gia vị yêu thích.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 2,\n" +
            "            \"instruction\": \"Tạo hình viên thịt và chiên trên chảo nóng cho đến khi chín vàng đều.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 3,\n" +
            "            \"instruction\": \"Cắt bánh mì thành hai phần, nướng nhẹ hoặc chiên giòn.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 4,\n" +
            "            \"instruction\": \"Chuẩn bị rau như cà chua, dưa chuột, hành tây, xà lách.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 5,\n" +
            "            \"instruction\": \"Cho thịt viên vào bánh mì, thêm rau, nước sốt và phô mai (nếu thích).\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 6,\n" +
            "            \"instruction\": \"Đóng bánh mì lại và thưởng thức.\"\n" +
            "        }\n" +
            "    ]\n" +
            "},{\n" +
            "    \"name\": \"Bánh mì kẹp thịt\",\n" +
            "    \"description\": \"Món ăn phổ biến với thịt bò xay, rau và nước sốt ngon miệng.\",\n" +
            "    \"recipe\": [\n" +
            "        {\n" +
            "            \"step\": 1,\n" +
            "            \"instruction\": \"Trộn thịt bò xay với muối, tiêu, hành tây băm nhỏ và gia vị yêu thích.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 2,\n" +
            "            \"instruction\": \"Tạo hình viên thịt và chiên trên chảo nóng cho đến khi chín vàng đều.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 3,\n" +
            "            \"instruction\": \"Cắt bánh mì thành hai phần, nướng nhẹ hoặc chiên giòn.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 4,\n" +
            "            \"instruction\": \"Chuẩn bị rau như cà chua, dưa chuột, hành tây, xà lách.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 5,\n" +
            "            \"instruction\": \"Cho thịt viên vào bánh mì, thêm rau, nước sốt và phô mai (nếu thích).\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 6,\n" +
            "            \"instruction\": \"Đóng bánh mì lại và thưởng thức.\"\n" +
            "        }\n" +
            "    ]\n" +
            "}]) và đừng nói gì cả";

        public static String FOOD_FOR_lUNCH = "Bạn hãy gửi cho tôi 1 chuỗi json về đề xuất món ăn trưa của việt nam và cách nấu gồm name và description và recipe , trong recipe gồm 1 list object có 2 thuộc tính là step và instruction, đây là mẫu ([{\n" +
            "    \"name\": \"Bánh mì kẹp thịt\",\n" +
            "    \"description\": \"Món ăn phổ biến với thịt bò xay, rau và nước sốt ngon miệng.\",\n" +
            "    \"recipe\": [\n" +
            "        {\n" +
            "            \"step\": 1,\n" +
            "            \"instruction\": \"Trộn thịt bò xay với muối, tiêu, hành tây băm nhỏ và gia vị yêu thích.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 2,\n" +
            "            \"instruction\": \"Tạo hình viên thịt và chiên trên chảo nóng cho đến khi chín vàng đều.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 3,\n" +
            "            \"instruction\": \"Cắt bánh mì thành hai phần, nướng nhẹ hoặc chiên giòn.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 4,\n" +
            "            \"instruction\": \"Chuẩn bị rau như cà chua, dưa chuột, hành tây, xà lách.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 5,\n" +
            "            \"instruction\": \"Cho thịt viên vào bánh mì, thêm rau, nước sốt và phô mai (nếu thích).\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 6,\n" +
            "            \"instruction\": \"Đóng bánh mì lại và thưởng thức.\"\n" +
            "        }\n" +
            "    ]\n" +
            "},{\n" +
            "    \"name\": \"Bánh mì kẹp thịt\",\n" +
            "    \"description\": \"Món ăn phổ biến với thịt bò xay, rau và nước sốt ngon miệng.\",\n" +
            "    \"recipe\": [\n" +
            "        {\n" +
            "            \"step\": 1,\n" +
            "            \"instruction\": \"Trộn thịt bò xay với muối, tiêu, hành tây băm nhỏ và gia vị yêu thích.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 2,\n" +
            "            \"instruction\": \"Tạo hình viên thịt và chiên trên chảo nóng cho đến khi chín vàng đều.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 3,\n" +
            "            \"instruction\": \"Cắt bánh mì thành hai phần, nướng nhẹ hoặc chiên giòn.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 4,\n" +
            "            \"instruction\": \"Chuẩn bị rau như cà chua, dưa chuột, hành tây, xà lách.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 5,\n" +
            "            \"instruction\": \"Cho thịt viên vào bánh mì, thêm rau, nước sốt và phô mai (nếu thích).\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 6,\n" +
            "            \"instruction\": \"Đóng bánh mì lại và thưởng thức.\"\n" +
            "        }\n" +
            "    ]\n" +
            "}]) và đừng nói gì cả";
    public static String FOOD_FOR_DINNER = "Bạn hãy gửi cho tôi 1 chuỗi json về đề xuất món ăn tối của việt nam và cách nấu gồm name và description và recipe , trong recipe gồm 1 list object có 2 thuộc tính là step và instruction, đây là mẫu ([{\n" +
            "    \"name\": \"Bánh mì kẹp thịt\",\n" +
            "    \"description\": \"Món ăn phổ biến với thịt bò xay, rau và nước sốt ngon miệng.\",\n" +
            "    \"recipe\": [\n" +
            "        {\n" +
            "            \"step\": 1,\n" +
            "            \"instruction\": \"Trộn thịt bò xay với muối, tiêu, hành tây băm nhỏ và gia vị yêu thích.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 2,\n" +
            "            \"instruction\": \"Tạo hình viên thịt và chiên trên chảo nóng cho đến khi chín vàng đều.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 3,\n" +
            "            \"instruction\": \"Cắt bánh mì thành hai phần, nướng nhẹ hoặc chiên giòn.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 4,\n" +
            "            \"instruction\": \"Chuẩn bị rau như cà chua, dưa chuột, hành tây, xà lách.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 5,\n" +
            "            \"instruction\": \"Cho thịt viên vào bánh mì, thêm rau, nước sốt và phô mai (nếu thích).\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 6,\n" +
            "            \"instruction\": \"Đóng bánh mì lại và thưởng thức.\"\n" +
            "        }\n" +
            "    ]\n" +
            "},{\n" +
            "    \"name\": \"Bánh mì kẹp thịt\",\n" +
            "    \"description\": \"Món ăn phổ biến với thịt bò xay, rau và nước sốt ngon miệng.\",\n" +
            "    \"recipe\": [\n" +
            "        {\n" +
            "            \"step\": 1,\n" +
            "            \"instruction\": \"Trộn thịt bò xay với muối, tiêu, hành tây băm nhỏ và gia vị yêu thích.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 2,\n" +
            "            \"instruction\": \"Tạo hình viên thịt và chiên trên chảo nóng cho đến khi chín vàng đều.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 3,\n" +
            "            \"instruction\": \"Cắt bánh mì thành hai phần, nướng nhẹ hoặc chiên giòn.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 4,\n" +
            "            \"instruction\": \"Chuẩn bị rau như cà chua, dưa chuột, hành tây, xà lách.\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 5,\n" +
            "            \"instruction\": \"Cho thịt viên vào bánh mì, thêm rau, nước sốt và phô mai (nếu thích).\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"step\": 6,\n" +
            "            \"instruction\": \"Đóng bánh mì lại và thưởng thức.\"\n" +
            "        }\n" +
            "    ]\n" +
            "}]) và đừng nói gì cả";
    public static Boolean isValidNumber(String number){
        for (int i = 0; i < number.length(); i++) {
            if (Character.isLetter(number.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
