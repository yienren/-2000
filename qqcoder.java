package qqcoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.HashMap;

/**
 * Created by HKH on 2018/4/15.
 */
public class qqcoder {

    public static String testGet(String addr) throws IOException {

        String p = addr;

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("wd", p);
//        map.put("key", "C63BZ-AAEKG-DR2QV-IX2JG-OV72Q-4ZBEV");
        map.put("key", "FBOBZ-VODWU-C7SVF-B2BDI-UK3JE-YBFUS");
        map.put("qt", "poi");
        HttpUtil util = new HttpUtil();
        try {
//            String ss=util.post("http://map.tianditu.com/data/query?postStr=",p);
//            String json = util.doGet("http://apis.map.qq.com/ws/geocoder/v1/?", map, "UTF-8");
            String json = util.doGet("http://apis.map.qq.com/jsapi?", map);
            JSONObject s = JSONObject.fromObject(json);
            JSONArray pois = s.getJSONObject("detail").getJSONArray("pois");
            JSONObject poi = JSONObject.fromObject(pois.get(0));
//            System.out.println(addr+" "+poi.getString("addr")+" "+new Navi().gcj2wgs(poi.getDouble("pointx"), poi.getDouble("pointy")));
            System.out.println(addr+" "+poi.getString("addr")+" "+new CGCS2000().gcj2cgcs2000(poi.getDouble("pointx"), poi.getDouble("pointy")));
        } catch (Exception e) {
            System.out.println(addr+"99999");
            e.printStackTrace();
        }
        return null;
    }
    public static void readTxtFile(String filePath){
        try {
            String encoding="utf-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
//                    System.out.println(lineTxt);
                    String json1 = qqcoder.testGet(lineTxt);
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            testGet("广西壮族自治区南宁市兴宁区三塘镇三塘镇中心小学");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath = "D:\\zb.txt";
        readTxtFile(filePath);
    }
}
