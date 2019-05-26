package com.tensquare.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

/**
 * token解析 认证 -测试
 */
public class ParseJJwt {
    public static void main(String[] args) {
        //setSigningKey（盐）
        //parseClaimsJws(token令牌)
        Claims claims = Jwts.parser().setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzMjAxNTA1MTAwNSIsInN1YiI6IuW8oOWzpSIsImlhdCI6MTU1ODc2NjU3MSwicm9sZSI6ImFkbWluIiwiZXhwIjoxNTU4NzY2NjMxfQ.miNDR-0RE-Ta0AKvh0vSbL92LZUioOIm-IRishcjcdI")
                .getBody();
        System.out.println("用户id: "+claims.getId());
        System.out.println("用户名: "+claims.getSubject());
        System.out.println("自定义的用户角色: "+claims.get("role"));
        System.out.println("签发时间: "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("设置过期时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
    }
}
