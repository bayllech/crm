package com.kaishengit.mapper;

import com.kaishengit.pojo.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by bayllech on 2017/5/27.
 */
public interface TaskMapper {
    List<Task> findTimeoutTasks(@Param("userid")Integer userid, @Param("today")String today);

    List<Task> findByUserID(@Param("userID")Integer userID, @Param("start")String start, @Param("end")String end);
}
