package com.yybf.chenojbackendmodel.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yybf.chenojbackendmodel.codesandbox.JudgeInfo;
import com.yybf.chenojbackendmodel.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交封装类
 * 专门用于给前端返回信息
 *
 * @TableName question
 */
@TableName(value = "question")
@Data
public class QuestionSubmitVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息（json对象）
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败
     */
    private Integer status;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 提交用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * （提交）用户信息
     */
    private UserVO userVO;

    /**
     * 对应题目信息
     */
    private QuestionVO questionVO;

    /**
     * 包装类转对象
     * 因为有些属性在VO包装类里面是例如JudgeInfo这种类型，
     * 而对象为了方便存入数据库，都是以String类型，所以将在两种类型之间提供转换
     * @param questionVO 封装类
     * @return
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionVO, questionSubmit);
        JudgeInfo judgeInfoObj = questionVO.getJudgeInfo();
        if(judgeInfoObj != null){
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoObj));
        }
        return questionSubmit;
    }

    /**
     * 对象转包装类
     *
     * @param questionSubmit 正常类
     * @return
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        String judgeInfo = questionSubmit.getJudgeInfo();
        questionSubmitVO.setJudgeInfo(JSONUtil.toBean(judgeInfo,JudgeInfo.class));
        return questionSubmitVO;
    }

    private static final long serialVersionUID = 1L;
}