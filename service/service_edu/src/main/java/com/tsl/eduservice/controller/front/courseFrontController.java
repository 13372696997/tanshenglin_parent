package com.tsl.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsl.commonutils.JwtUtils;
import com.tsl.commonutils.Result;
import com.tsl.commonutils.orderVO.CourseWebVoOrder;
import com.tsl.eduservice.client.OrdersClient;
import com.tsl.eduservice.entity.EduCourse;
import com.tsl.eduservice.entity.EduTeacher;
import com.tsl.eduservice.entity.chapter.chapterVO;
import com.tsl.eduservice.entity.frontVO.CourseFrontVo;
import com.tsl.eduservice.entity.frontVO.CourseWebVo;
import com.tsl.eduservice.service.EduChapterService;
import com.tsl.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Api(description = "条件查询课程列表页面展示")
@RequestMapping("/eduservice/coursefront")
public class courseFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrdersClient ordersClient;

    /**
     * 生成订单调用的方法，根据课程id，获得课程的信息
     * @param
     * @param
     * @param
     * @return
     */
    @PostMapping("getCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String courseId){
        CourseWebVo webVo = eduCourseService.getCourseInfoById(courseId);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(webVo,courseWebVoOrder);
        return courseWebVoOrder;
    }


    //条件查询带分页（重点）
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(@PathVariable long page,
                                     @PathVariable long limit,
                                     @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> CoursePage = new Page<>(page,limit);
        Map<String,Object> map = eduCourseService.getCourseFrontList(CoursePage,courseFrontVo);

        //返回分页所有数据

        return Result.ok().data("map",map);
    }

    //返回课程详情
    @GetMapping("getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //编写sql语句查询
        CourseWebVo courseWebVo = eduCourseService.getCourseInfoById(courseId);

        //根据课程id查询章节和小节信息
        List<chapterVO> chapterVideoList = eduChapterService.getChapter(courseId);

        //进入详情页面的时候，根据课程id和会员id查询课程是否收费
        boolean buyCourse = ordersClient.isBuyCourse(courseId,JwtUtils.getMemberIdByJwtToken(request));

        return Result.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",buyCourse);
    }


}
