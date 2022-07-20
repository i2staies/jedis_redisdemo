package com.itguigu.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {
    public static void main(String[] args) {

    }



    //1.生成随机验证码
    public String getCode(){
        Random random = new Random();
        String code= "";
        for (int i = 0; i < 6; i++) {
            code = code + String.valueOf(random.nextInt());
        }
        return code;
    }

    //2.每个手机每天只能发送三次，验证码放到redis中，并设置过期时间为2min
    public void verifyCode(String phone){
        Jedis jedis = new Jedis("192.168.122.3",6379);
        //拼接key
        //手机发送次数key
        String countkey = "VerifyCode"+phone+"Count";


        String count = jedis.get(countkey);
        if(count == null){
            jedis.setex(countkey,24*60*60, "1");
        }else if(Integer.parseInt(count) <= 2){
            jedis.incr(countkey);
        }else {
            System.out.println("今天的发送送次数已经超过三次");
            jedis.close();
        }

        //发送的验证码存储在redis里面
        String codeKey = "VerifyCode"+phone+"Count";
        String code = getCode();
        jedis.setex(codeKey, 60*2, code);
    }

    //3. 判断验证码是否正确
    public void test(String phone,String code){
        Jedis jedis = new Jedis("192.168.122.3",6379);
        String codeKey = "VerifyCode"+phone+"Count";
        String redisCode = jedis.get(codeKey);
        if(redisCode.equals(code)){
            System.out.println("成功");
        }else {
            System.out.println("失败");
        }
        jedis.close();
    }
}
