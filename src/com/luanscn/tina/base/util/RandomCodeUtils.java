package com.luanscn.tina.base.util;
  
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;  
  
/** 
 * 验证码生成工具类 
 * @author penghy 
 * @date 2014-02-27 
 */  
public abstract class RandomCodeUtils {  
  
    private static Logger logger = Logger.getLogger(RandomCodeUtils.class);  
      
    public static final String RANDOM_CODE_KEY = "random"; //验证码放在session中的key  
      
    private static final int CODE_NUM = 4; //验证码字符个数  
      
    // 设置图形验证码中字符串的字体和大小    
    private static Font myFont = new Font("Arial", Font.BOLD, 16);     
    
    //随机字符数组  
    private static char[] charSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();  
    private static char[] charNumberSequence = "0123456789".toCharArray(); 
          
    private static Random random = new Random();  
      
 
      
    /** 
     * 检查用户输入的验证码是否正确 
     * @param request 
     * @param inputCode 
     * @return true: 正确, false:不正确 
     */  
    public static boolean checkRandomCode(HttpServletRequest request, String inputCode){  
        HttpSession session = request.getSession(false);  
        if(session != null && StringUtils.hasLength(inputCode)){  
            String code = (String) session.getAttribute(RANDOM_CODE_KEY);  
            logger.info("inputCode:"+inputCode.trim()+",code:"+code);  
            return inputCode.trim().equalsIgnoreCase(code);  
        }  
        return false;  
    }  
      
    /** 
     * 移除验证码 
     * @param request 
     * @param inputCode 
     */  
    public static void removeRandomCode(HttpServletRequest request){  
        HttpSession session = request.getSession(false);  
        if(session != null){  
           session.removeAttribute(RANDOM_CODE_KEY);  
        }  
    }  
      
    // 生成随机颜色    
    private static Color getRandomColor(int fc, int bc) {    
        Random random = new Random();    
        if (fc > 255)    
            fc = 255;    
        if (bc > 255)    
            bc = 255;    
        int r = fc + random.nextInt(bc - fc);    
        int g = fc + random.nextInt(bc - fc);    
        int b = fc + random.nextInt(bc - fc);    
        return new Color(r, g, b);    
    }    
      
    // 随机生成一个字符    
    private static String getRandomChar() {    
        int index = random.nextInt(charSequence.length);  
        return String.valueOf(charSequence[index]);  
    }  
    
    // 随机生成一个数字 
    private static String getRandomNumChar() {    
        int index = random.nextInt(charNumberSequence.length);  
        return String.valueOf(charNumberSequence[index]);  
    } 
    
    // 随机生成一个字符    
    public static String createRandomCode(int length) {    
        StringBuilder sRand = new StringBuilder(CODE_NUM);    
        for (int i = 0; i < length; i++) {    
            // 取得一个随机字符    
            String tmp = getRandomNumChar();    
            sRand.append(tmp);  
        }    
        
        return sRand.toString();
    }  
}  