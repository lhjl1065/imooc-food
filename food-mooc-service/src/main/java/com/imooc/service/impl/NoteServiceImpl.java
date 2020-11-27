package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.NoteMapper;
import com.imooc.pojo.Note;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.bo.NoteBo;
import com.imooc.pojo.vo.NoteVo;
import com.imooc.service.NoteService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * NoteServiceImpl TODO
 *
 * @author linHu daXia
 * @date 2020/11/17 20:01
 */
@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteMapper noteMapper;
    @Override
    @Transactional
    public void saveNote(NoteBo noteBo) {
        Note note = new Note();
        BeanUtils.copyProperties(noteBo,note);
        note.setCreatedTime(new Date());
        noteMapper.insert(note);
    }

    @Override
    public void updateNote(NoteBo noteBo) {
        Note note = new Note();
        BeanUtils.copyProperties(noteBo,note);
        note.setUpdatedTime(new Date());
        noteMapper.updateByPrimaryKeySelective(note);
    }

    @Override
    public Note noteDetail(Integer id) {
        return noteMapper.selectByPrimaryKey(id);
    }

    @Override
    public PagedGridResult ListNotes(
        String titleKeyword,
        String contentKeyword,
        Date fromDate,
        Date toDate,
        Integer page,
        Integer pageSize)
    {
        if (page==null){
            page=1;
        }
        if (pageSize==null){
            pageSize=10;
        }
        if (StringUtils.isBlank(titleKeyword)){
            titleKeyword=null;
        }
        if (StringUtils.isBlank(contentKeyword)){
            contentKeyword=null;
        }
        PageHelper.startPage(page,pageSize,"CREATED_TIME desc");
        String contentKeywordTmp=contentKeyword;
        Example example = new Example(Note.class);
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(titleKeyword)){
            titleKeyword="%"+titleKeyword+"%";
        }
        if (StringUtils.isNotBlank(contentKeyword)){
            contentKeyword="%"+contentKeyword+"%";
        }
        criteria.andLike("title",titleKeyword);
        criteria.andLike("content",contentKeyword);
        criteria.andBetween("createdTime",fromDate,toDate);
        List<Note> notes = noteMapper.selectByExample(example);
        return setterPagedGridResult(notes,page,contentKeywordTmp);
    }
    private PagedGridResult setterPagedGridResult(List<Note> list,Integer page,String contentKeyword){
        PageInfo<Note> pageInfo = new PageInfo<>(list);
        List<NoteVo> noteVoList = list.stream().map(note -> {
            NoteVo noteVo = new NoteVo();
            BeanUtils.copyProperties(note, noteVo);
            if (StringUtils.isBlank(contentKeyword)){
                noteVo.setNumber(-1);
            }
            else{
                Integer frequency = getFrequency(note.getContent(), contentKeyword);
                noteVo.setNumber(frequency);
            }
            return noteVo;
        }).collect(Collectors.toList());
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(page);
        pagedGridResult.setRecords(pageInfo.getTotal());
        pagedGridResult.setRows(noteVoList);
        pagedGridResult.setTotal(pageInfo.getPages());
        return  pagedGridResult;
    }

    private Integer getFrequency(String content,String contentKeyword) {
        int frequency = 0;
        int index= 0 ;
        while ((index=content.indexOf(contentKeyword,index))>=0){
            frequency+=1;
            index+=contentKeyword.length();
        }
        return frequency;
    }
}
