package com.tsl.eduservice.controller;


import com.tsl.commonutils.Result;
import com.tsl.eduservice.entity.EduChapter;
import com.tsl.eduservice.entity.EduTeacher;
import com.tsl.eduservice.entity.chapter.chapterVO;
import com.tsl.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-03
 */
@RestController
@CrossOrigin
@Api(description = "课程管理，返回章节和小节")
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;
    @GetMapping("getChapterVideo/{courseId}")
    public Result getChapterVideo(@PathVariable String courseId){

        List<chapterVO> list = eduChapterService.getChapter(courseId);
        return Result.ok().data("allChapterVideo",list);
    }
    @ApiOperation("修改章节前的查询")
    @GetMapping("getChapter/{courseId}")
    public Result getChapterByCourseId(@PathVariable String chapterId){
        EduChapter chapter = eduChapterService.getOnechapter(chapterId);
        return Result.ok().data("chapter",chapter);
    }

    @PostMapping("addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter){
        boolean save = eduChapterService.save(eduChapter);
        if(save){
            return Result.ok().message("添加章节成功");
        }else {
            return Result.error().message("添加章节失败");
        }

    }

    @ApiOperation("修改章节信息")
    @PostMapping("updataChapter")
    public Result updatechapter(@RequestBody EduChapter eduChapter){
        boolean b = eduChapterService.updateById(eduChapter);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    @ApiOperation("删除章节信息")
    @DeleteMapping("{id}")
    public Result deletechapyer(@PathVariable String id){
        //需要判断，如果章节里面有小节，就不能删除，所以要查询小节表
        boolean flag =  eduChapterService.deleteChapter(id);
        if (flag){
            return Result.ok().message("删除章节成功");
        }else {
            return Result.error().message("删除章节失败");
        }
    }

}

