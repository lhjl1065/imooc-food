package com.imooc.service;

import com.imooc.pojo.Note;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.bo.NoteBo;
import java.util.Date;

public interface NoteService {

    /**
     * 存储笔记
     * @param noteBo
     */
    void saveNote(NoteBo noteBo);

    void updateNote(NoteBo noteBo);

    Note noteDetail(Integer id);

    PagedGridResult ListNotes(String titleKeyword, String contentKeyword, Date fromDate, Date toDate, Integer page, Integer pageSize);
}
