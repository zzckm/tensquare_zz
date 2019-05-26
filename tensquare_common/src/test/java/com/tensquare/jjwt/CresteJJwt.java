package com.tensquare.jjwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * 测试：JWT Token令牌生成
 */
public class CresteJJwt {
    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("32015051005")
                .setSubject("张峥")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"itcast")
                .claim("role","admin")
                .setExpiration(new Date(new Date().getTime()+60000));//一分钟 600000毫秒  设置过期时间（可以不设置）
                                //上述signWith（签名算法，secret盐）
                            //compact 生成一个字符串
        System.out.println(jwtBuilder.compact());
    }
}
