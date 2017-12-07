package com.tsb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsb.model.App;
import com.tsb.model.Organization;
import com.tsb.model.UserAuth;
import com.tsb.model.UserInfo;
import com.tsb.service.IOrgService;
import com.tsb.service.IUserAuthService;
import com.tsb.utils.ChangZhouUtil;
import com.tsb.utils.CheckUtil;
import com.tsb.utils.PropertyUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于常州青果与服务体系认证，并获取应用
 *
 * @author xuchq 2017.11.20
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserAuthService userAuthService;
    @Resource
    private IOrgService orgService;

    /**
     * 1.拿到php提供的token，根据该token获取userid
     * 2.根据userid查询t_s_user_auth表，看是否在"服务体系"认证过
     * 3.若未认证过，则则根据userid查询信息，跳转到认证页面
     * 4.若已认证过，则调用"服务体系"相关接口，组装应用页面
     *
     * @param request
     * @param response
     * @throws IOException
     * @return
     */
    @RequestMapping("/accessUser.tsb")
    public String accessUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String ACCESS_TOKEN = ChangZhouUtil.getAccessToken();
        if (CheckUtil.checkEmptyObject(ACCESS_TOKEN)) {
            System.out.printf("errorMessage:不能获取到服务体系token");
//            session.setAttribute("errorMessage", "不能获取到服务体系token，请联系管理员。");
            session.setAttribute("errorMessage", "系统繁忙，请稍候重试。");
            return "error";
        }
        System.out.println("ACCESS_TOKEN:" + ACCESS_TOKEN);
        //在session中绑定"服务体系"ACCESS_TOKEN
        session.setAttribute("ACCESS_TOKEN", ACCESS_TOKEN);
//        1.拿到php提供的token，根据该token获取userid
        String tsbToken = request.getParameter("tsbToken");
        if (CheckUtil.checkEmptyObject(tsbToken)) {
            System.out.printf("errorMessage:tsbToken为空");
            session.setAttribute("errorMessage", "系统繁忙，请稍候重试。");
            return "error";
        }
        String userId = null;
        try {
            userId = ChangZhouUtil.getUseridByToken(tsbToken);
        } catch (Exception e) {
            System.out.printf("errorMessage:通过token获取userid为空,请刷新页面重试");
            session.setAttribute("errorMessage", "系统繁忙，请稍候重试。");
            return "error";
        }
        if (CheckUtil.checkEmptyObject(userId)) {
            System.out.printf("errorMessage:通过token获取userid为空");
            session.setAttribute("errorMessage", "系统繁忙，请稍候重试。");
            return "error";
        }
//        2.根据userid查询t_s_user_auth表，看是否在"服务体系"认证过
        UserAuth userAuth = userAuthService.selectUserAuth(userId);
//        3.若未认证过，则根据userid查询信息，跳转到认证页面
        if (null == userAuth) {
            UserInfo userInfo = ChangZhouUtil.getUserByUserid(userId);
            session.setAttribute("userInfo", userInfo);
            return "check";
        }
//        4.若已认证过，则调用"服务体系"相关接口，组装应用页面
        else {
            return toAppList(session, ACCESS_TOKEN, userId);
        }
    }

    private String toAppList(HttpSession session, String ACCESS_TOKEN, String userId) {
        Map<String, String> interchange = ChangZhouUtil.sessionInterchange(userId, ACCESS_TOKEN);
        String usessionId = interchange.get("usessionId");
        if ("usessionId_fail".equals(usessionId)) {
            System.out.printf("errorMessage:第三方会话交换失败");
            session.setAttribute("errorMessage", "系统繁忙，请稍候重试。");
            return "error";
//            return "第三方会话交换失败";
        } else {
            List<App> appList = ChangZhouUtil.getAppList(usessionId, ACCESS_TOKEN);
            session.setAttribute("appList", appList);
            session.setAttribute("userId", userId);
            return "appList";
        }

    }

    /**
     * 服务体系通过该接口向第三方应用校验用户登录的临时会话。
     * 第三方应用根据规范开发接口提供给体系使用
     * 【第三方会话交换会话】中会使用该接口验证第三方应用的用户登录信息
     *
     * @param userAuth
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getUserByLocalSession.tsb")
    public void getUserByLocalSession(@RequestBody UserAuth userAuth, HttpServletResponse response) throws IOException {
//        request.setCharacterEncoding("UTF-8");
        Map<String, Object> result = new HashMap<>();
        try {
            response.setCharacterEncoding("UTF-8");
//        String userId = request.getParameter("localSession");
            UserInfo user = ChangZhouUtil.getUserByUserid(userAuth.getLocalSession());
            String name = user.getByname() + user.getForename();
            String idCardNo = user.getCertificateid();
            String userIdentity = user.getOccupation();
            Map<String, String> userResult = new HashMap<>();
            userResult.put("name", name);
            userResult.put("idCardNo", idCardNo);
            userResult.put("userIdentity", userIdentity);
            result.put("retCode", "000000");
            result.put("retDesc", "success");
            result.put("userInfo", userResult);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("retCode", "000001");
            result.put("retDesc", "fail");
            result.put("userInfo", "获取用户信息出错，请检查入参或联系管理员");
        }
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(result));
        System.out.println(mapper.writeValueAsString(result));
        response.getWriter().close();
    }

    /**
     * 获取手机验证码
     *
     * @param request
     * @param response
     */
    @RequestMapping("/getPhoneVerifyCode.tsb")
    public void getPhoneVerifyCode(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String mobile = request.getParameter("mobile");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(ChangZhouUtil.getPhoneVerifyCode(mobile, (String) request.getSession().getAttribute("ACCESS_TOKEN")));
            /*if (ChangZhouUtil.getPhoneVerifyCode(mobile, (String) request.getSession().getAttribute("ACCESS_TOKEN"))) {
                writer.write("发送短信成功");
            } else {
                writer.write("发送短信失败，请联系管理员.");
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    /**
     * 请求获取ticket，重定向到第三方应用
     *
     * @param request
     * @param response
     */
    @RequestMapping("/toThird.tsb")
    public String toThird(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String appUrl = request.getParameter("appUrl");
        System.out.println("appurl:" + appUrl);
        Map<String, String> interchange = ChangZhouUtil.sessionInterchange((String) session.getAttribute("userId"), (String) session.getAttribute("ACCESS_TOKEN"));
        String ticket = interchange.get("ticket");
        if (!CheckUtil.checkEmptyObject(ticket)) {
            String targetUrl = appUrl + "&ticket=" + ticket + "&sysCode=" + PropertyUtil.getProperty("sysCode");
            response.sendRedirect(targetUrl);
            return null;
        } else {
            System.out.printf("errorMessage:第三方会话交换失败");
            session.setAttribute("errorMessage", "系统繁忙，请稍候重试。");
            return "error";
        }
    }

    /**
     * 用户认证
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userCheck.tsb")
    public String userCheck(/*@RequestBody UserInfo userInfo2,*/HttpServletRequest request, HttpServletResponse response) {
//        PrintWriter writer = null;
//        userInfo2.getOccupation();
        HttpSession session = request.getSession();
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
//            writer = response.getWriter();
            String schoolname = request.getParameter("schoolname");

            List<Organization> organizations = orgService.selectOrg(new Organization(schoolname));
            if (organizations.size() != 1) {
                session.setAttribute("errorMessage", "填写的学校/机构名不合法，请确认。");
//                writer.write("填写的学校/机构名不合法，请确认");
            } else {
                String orgCode = organizations.get(0).getOrgCode();
                UserInfo userInfo = genegrateUserInfo(request, schoolname, orgCode);
                String result = ChangZhouUtil.validateUser(userInfo, (String) request.getSession().getAttribute("ACCESS_TOKEN"));
//                writer.write(result);
                if ("000000".equals(result)) {
//                    writer.write("恭喜，认证成功。");
                    //认证成功后，更新我方青果系统用户数据,并在t_s_user_auth中插入一条
                    ChangZhouUtil.updateUser(userInfo);
                    userAuthService.insertUserAuth(new UserAuth(userInfo.getSid()));
                    // 请求"服务体系"，获取用户权限下的应用
                    String targetPage = toAppList(request.getSession(), (String) request.getSession().getAttribute("ACCESS_TOKEN"), userInfo.getSid());
                    return targetPage;
//                    writer.write(targetPage);
                } else if ("300017".equals(result)) {
//                    writer.write("请不要重复认证。");
                    session.setAttribute("errorMessage", "请不要重复认证");
                } else if ("300007".equals(result)) {
//                    writer.write("验证码不匹配");
                    session.setAttribute("errorMessage", "验证码不匹配");
                } else if ("300018".equals(result)) {
//                    writer.write("该手机号已经被其他实名用户使用");
                    session.setAttribute("errorMessage", "该手机号已经被其他实名用户使用");
                } else if ("300011".equals(result)) {
                    session.setAttribute("errorMessage", "您输入的身份证号重复认证，请重新填写");
                }else {
                    session.setAttribute("errorMessage", "认证失败，请检查填写是否正确，或联系管理员。");
//                    writer.write("认证失败，请检查填写是否正确，或联系管理员。");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", e.toString());
        }
        /*finally {
//            writer.close();
        }*/
        return "error";
//        return "redirect:/views/error.jsp";
    }

    private UserInfo genegrateUserInfo(HttpServletRequest request, String schoolname, String orgCode) {
        String sid = request.getParameter("sid");
        String name = request.getParameter("name");
        String lgnname = request.getParameter("lgnname");
        String certificateid = request.getParameter("certificateid");
        String occupation = request.getParameter("occupation");
        String mobile = request.getParameter("mobile");
        String verifyCode = request.getParameter("verifyCode");
        return new UserInfo(sid, lgnname, name, certificateid, occupation, schoolname, orgCode, mobile, verifyCode);
    }

    /*@POST
    @Path("/test")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public void accessUser2(TsbToken tsbToken) throws IOException {
        System.out.println("tsbToken:"+tsbToken.getTokenId());
    }*/
   /* @RequestMapping("/userCheck2.tsb")
    public String userCheck2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = null;
//        HttpSession session = request.getSession();
//        try {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        writer = response.getWriter();
        return "";
    }*/
}