package com.zzw.judgeai.controller;

import com.mybatisflex.core.paginate.Page;
import com.zzw.judgeai.annotation.AuthCheck;
import com.zzw.judgeai.common.BaseResponse;
import com.zzw.judgeai.common.DeleteRequest;
import com.zzw.judgeai.common.ResultUtils;
import com.zzw.judgeai.constant.UserConstant;
import com.zzw.judgeai.exception.ErrorCode;
import com.zzw.judgeai.exception.ThrowUtils;
import com.zzw.judgeai.model.dto.UserLoginRequest;
import com.zzw.judgeai.model.dto.UserRegisterRequest;
import com.zzw.judgeai.model.entity.User;

import com.zzw.judgeai.model.vo.LoginUserVO;
import com.zzw.judgeai.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户 控制层。
 *
 * @author Zzw
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 保存用户。
     *
     * @param user 用户
     * @return {@code true} 保存成功，{@code false} 保存失败
     */
    @PostMapping("/save")
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * 根据主键删除用户。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return userService.removeById(id);
    }

    /**
     * 根据主键更新用户。
     *
     * @param user 用户
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    public boolean update(@RequestBody User user) {
        return userService.updateById(user);
    }

    /**
     * 查询所有用户。
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    /**
     * 根据主键获取用户。
     *
     * @param id 用户主键
     * @return 用户详情
     */
    @GetMapping("/getInfo/{id}")
    public User getInfo(@PathVariable Long id) {
        return userService.getById(id);
    }

    /**
     * 分页查询用户。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public Page<User> page(Page<User> page) {
        return userService.page(page);
    }



    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }



    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求
     * @param request          请求对象
     * @return 脱敏后的用户登录信息
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }


    /**
     * 获取当前登录用户
     *
     * @param request 请求对象
     * @return 脱敏后的用户信息
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(loginUser));
    }



    /**
     * 用户注销
     *
     * @param request 请求对象
     * @return 注销结果
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }



//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    @PostMapping("/delete")
//    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
//        // 仅管理员可用
//    }

//    @PostMapping("/list/page/vo")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
//        ThrowUtils.throwIf(userQueryRequest == null, ErrorCode.PARAMS_ERROR);
//        long pageNum = userQueryRequest.getPageNum();
//        long pageSize = userQueryRequest.getPageSize();
//        Page<User> userPage = userService.page(Page.of(pageNum, pageSize),
//                userService.getQueryWrapper(userQueryRequest));
//        // 数据脱敏
//        Page<UserVO> userVOPage = new Page<>(pageNum, pageSize, userPage.getTotalRow());
//        List<UserVO> userVOList = userService.getUserVOList(userPage.getRecords());
//        userVOPage.setRecords(userVOList);
//        return ResultUtils.success(userVOPage);
//    }


}

