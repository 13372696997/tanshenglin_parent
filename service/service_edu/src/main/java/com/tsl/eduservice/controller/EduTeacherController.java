package com.tsl.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsl.commonutils.Result;
import com.tsl.eduservice.entity.EduTeacher;
import com.tsl.eduservice.entity.VO.teacherQuery;
import com.tsl.eduservice.service.EduTeacherService;
import com.tsl.servicebase.ExceptionHandler.tanShengLinException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author tanShengLin
 * @since 2020-08-19
 */
@RestController//里面有这两个注解：@Controller @ResponseBody，所以这个注解的作用是返回json数据和交给spring管理
@Api(description = "讲师管理")
@RequestMapping("/eduservice/teacher")
@CrossOrigin//解决跨域
public class EduTeacherController {

    //把service注入就可以了，service接口的实现类里面继承了一个类，实现了对mapper的注入
    @Autowired
    private EduTeacherService eduTeacherService;

    //查询教师表所有的数据
    //rest风格：对不同的操作用不同的请求方式，添加put请求，查询get请求，修改post请求，删除delete请求
    @ApiOperation("查询所有讲师信息")
    @GetMapping("findAll")//表示get请求,rest风格
    public Result EduTeacherController(){
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        return Result.ok().data("items",teacherList);
    }
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("{id}")
    public Result removeTeacher(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable String id){

        boolean b = eduTeacherService.removeById(id);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    /**
     * 分页查询
     */
    @ApiOperation("分页查询")
    @GetMapping("pageTeacher/{current}/{limit}")
    public Result pageListTeacher(@ApiParam(name = "current",value = "当前页",required = true)@PathVariable Integer current,@PathVariable Integer limit){

        try{
            int a = 2/0;
        }catch (Exception e){
            //执行自定义异常
            throw new tanShengLinException(20001,"执行了我自己定义的异常");
        }finally {
            System.out.println("finally语句在return执行之后，return结果返回之前执行");
        }


        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        IPage<EduTeacher> page = eduTeacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();//总的条数
        List records = pageTeacher.getRecords();

        return Result.ok().data("total",total).data("rows",records);
}
    @ApiOperation("分页查询带条件")
    @PostMapping ("pageTeacherCondition/{current}/{limit}")
    //@RequestBody:这个注解必须要用post提交
    public Result pageTeacherCondition(@PathVariable Integer current , @PathVariable Integer limit ,@RequestBody(required = false) teacherQuery tQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //构建条件
        QueryWrapper wrapper = new QueryWrapper();

        //warpper
        //动态sql原理
        String name = tQuery.getName();
        Integer level = tQuery.getLevel();
        String beginTime = tQuery.getBeginTime();
        String endTime = tQuery.getEndTime();
        //StringUtils是spring包下的
        //注意前面那个有冒号的要对应数据库里面的字段

        //ge:大于等于 gt：大于
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(beginTime)){
            wrapper.ge("gmt_create",beginTime);
        }
        if (!StringUtils.isEmpty(endTime)){
            wrapper.le("gmt_create",endTime);
        }
        //添加排序
            wrapper.orderByDesc("gmt_create");
        IPage<EduTeacher> page = eduTeacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();//总的条数
        List records = pageTeacher.getRecords();
        return Result.ok().data("total",total).data("rows",records);
    }

        @ApiOperation("添加讲师")
        @PostMapping ("addTeacher")
        public Result addTeacher(@RequestBody EduTeacher eduTeacher){
            boolean save = eduTeacherService.save(eduTeacher);
            if(save){
                return Result.ok();
            }else {
                return Result.error();
            }
        }

        @ApiOperation("修改前的查询")
        @GetMapping("getTeacherById/{id}")
        public Result getTeacherById(@PathVariable String id){
            EduTeacher teacher = eduTeacherService.getById(id);
            if(teacher.equals(null)){
                return Result.error().message("查询失败");
            }else {
                return Result.ok().data("teacher",teacher);
            }

        }

        @ApiOperation("修改讲师信息")
        @PostMapping("updateTeacher")
        public Result updateTeacher(@RequestBody EduTeacher eduTeacher){
            boolean b = eduTeacherService.updateById(eduTeacher);
            if(b){
                return Result.ok();
            }else {
                return Result.error();
            }
        }


}

