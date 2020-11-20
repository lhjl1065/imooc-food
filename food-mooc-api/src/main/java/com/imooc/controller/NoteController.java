package com.imooc.controller;

import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.pojo.Note;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.bo.NoteBo;
import com.imooc.service.NoteService;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * NoteController TODO
 *
 * @author linHu daXia
 * @date 2020/11/17 19:38
 */
@RestController
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @PostMapping
    public IMOOCJSONResult saveNote(@RequestBody NoteBo noteBo){
        if (StringUtils.isBlank(noteBo.getTitle())||StringUtils.isBlank(noteBo.getContent())){
            return IMOOCJSONResult.errorMsg("标题或者内容不能为空");
        }
        noteService.saveNote(noteBo);
        return IMOOCJSONResult.ok("存储成功");
    }
    @PutMapping
    public IMOOCJSONResult updateNote(@RequestBody NoteBo noteBo){
        if (StringUtils.isBlank(noteBo.getTitle())||StringUtils.isBlank(noteBo.getContent())||noteBo.getId()==null){
            return IMOOCJSONResult.errorMsg("标题或者内容以及id不能为空");
        }
        noteService.updateNote(noteBo);
        return IMOOCJSONResult.ok("更新成功");
    }
    @GetMapping
    public IMOOCJSONResult NoteDetail(@RequestParam Integer id){
        Note note=noteService.noteDetail(id);
        return IMOOCJSONResult.ok(note);
    }
    @GetMapping("/list")
    public IMOOCJSONResult ListNotes(
        @RequestParam(required = false) String titleKeyword,
        @RequestParam(required = false) String contentKeyword,
        @DateTimeFormat(pattern = "yyyy-MM-dd")Date fromDate,
        @DateTimeFormat(pattern = "yyyy-MM-dd")Date toDate,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer pageSize){
        PagedGridResult pagedGridResult=noteService.ListNotes(titleKeyword,contentKeyword,fromDate,toDate,page,pageSize);
        return IMOOCJSONResult.ok(pagedGridResult);
    }
}
