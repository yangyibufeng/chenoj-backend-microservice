package com.yybf.chenojbackendmodel.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yybf.chenojbackendmodel.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.yybf.chenojbackendmodel.dto.question.JudgeConfig;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目封装类
 * 专门用于给前端返回信息
 *
 * @TableName question
 */
@TableName(value = "question")
@Data
public class QuestionVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题配置（json对象）
     */
    private JudgeConfig judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建题目人的信息
     */
    private UserVO userVO;

    /**
     * 包装类转对象
     * 因为有些属性在VO包装类里面是例如List或者JudgeConfig这种类型，
     * 而对象为了方便存入数据库，都是以String类型，所以将在两种类型之间提供转换
     * @param questionVO
     * @return
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<String> tagList = questionVO.getTags();
        if(tagList != null){
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        JudgeConfig voJudgeConfig = questionVO.getJudgeConfig();
        if(voJudgeConfig != null){
            question.setJudgeConfig(JSONUtil.toJsonStr(voJudgeConfig));
        }
        return question;
    }

    /**
     * 对象转包装类
     *
     * @param question
     * @return
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        List<String> tagList = JSONUtil.toList(question.getTags(), String.class);
        questionVO.setTags(tagList);
        JudgeConfig judgeConfigStr = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);
        questionVO.setJudgeConfig(judgeConfigStr);
        return questionVO;
    }

    private static final long serialVersionUID = 1L;
}