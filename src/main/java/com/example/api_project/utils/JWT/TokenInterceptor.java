package com.example.api_project.utils.JWT;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

//自定义拦截器
public class TokenInterceptor implements HandlerInterceptor{
    /**
     * preHandle()返回true后，afterCompletion()才会执行
     */
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response,
                                Object handler, Exception arg3) throws Exception {
    }

    /**
     * 该方法在调用controller方法后，DispatcherServlet渲染视图之前被执行
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView model) throws Exception {
    }

    /**
     * 发起请求前调用
     * 该方法返回false的话，将不会往下执行
    */
    //拦截每个请求
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token");
//        System.out.println("preHandle....." + token);
        Result responseData;

        //token存在
        if(null != token) {
            User login = JWT.unsign(token, User.class);//解码
            String userId = request.getHeader("userId");//获取userId

            if(null != userId && null != login) {
                //一致，返回true 正常执行
                if(userId.equals(login.getUserId())) {
                    return true;
                }
                //解密token后的userId与用户传来的userId不一致，一般都是token过期
                else{
                    responseData = ResponseData.authError();
                    responseMessage(response, response.getWriter(), responseData);
                    return false;
                }
            }
            //有一个或者多个为空
            else
            {
                responseData = ResponseData.authError();
                responseMessage(response, response.getWriter(), responseData);
                return false;
            }
        }
        //token不存在
        else
        {
            responseData = ResponseData.authError();
            responseMessage(response, response.getWriter(), responseData);
            return false;
        }
    }

    //请求不通过，返回错误信息给客户端
    private void responseMessage(HttpServletResponse response, PrintWriter out, Result responseData) {
        response.setContentType("application/json; charset=utf-8");
        String json = JSONObject.toJSONString(responseData);
        out.print(json);
        out.flush();
        out.close();
    }

}
