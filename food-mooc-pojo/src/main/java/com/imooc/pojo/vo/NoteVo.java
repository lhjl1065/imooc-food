package com.imooc.pojo.vo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * NoteVo TODO
 *
 * @author linHu daXia
 * @date 2020/11/18 11:08
 */
@Getter
@Setter
public class NoteVo {
    /**
     * id号
     */
    private Integer id;

    /**
     * 创建时间
     */
    @Column(name = "CREATED_TIME")
    private Date createdTime;

    /**
     * 更新时间
     */
    @Column(name = "UPDATED_TIME")
    private Date updatedTime;

    /**
     * 标题
     */
    private String title;

    /**
     * 出现次数
     */
    private Integer number;
}
