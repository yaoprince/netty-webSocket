package com.sky.mychat.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.Date;

public class TimeUtil {
    /**
     * 获取开始时间和结束时间
     *
     * @param type       1今天 2昨天 3本周 4上周 5本月 6上月 7指定时间
     * @param beginTimes 开始时间戳
     * @param endTimes   结束时间戳
     * @return 返回开始时间和结束时间
     */
    public static Date[] getBeginAndEndTime(Integer type, Long beginTimes, Long endTimes) {
        if (ObjectUtil.isEmpty(type)) {
            throw new RuntimeException("类型不能为空");
        }
        Date[] result = new Date[2];
        Date date = new Date();
        Date begin;
        Date end;
        switch (type) {
            // 今天
            case 1:
                begin = DateUtil.beginOfDay(date);
                end = DateUtil.endOfDay(date);
                break;
            //昨天
            case 2:
                DateTime yesterday = DateUtil.yesterday();
                begin = DateUtil.beginOfDay(yesterday);
                end = DateUtil.endOfDay(yesterday);
                break;
            //本周
            case 3:
                begin = DateUtil.beginOfWeek(date);
                end = DateUtil.endOfWeek(date);
                break;
            // 上周
            case 4:
                DateTime lastWeek = DateUtil.lastWeek();
                begin = DateUtil.beginOfWeek(lastWeek);
                end = DateUtil.endOfWeek(lastWeek);
                break;
            // 本月
            case 5:
                begin = DateUtil.beginOfMonth(date);
                end = DateUtil.endOfMonth(date);
                break;
            //上月
            case 6:
                DateTime lastMonth = DateUtil.lastMonth();
                begin = DateUtil.beginOfMonth(lastMonth);
                end = DateUtil.endOfMonth(lastMonth);
                break;
            //指定时间
            case 7:
                if (ObjectUtil.isEmpty(beginTimes) || ObjectUtil.isEmpty(endTimes)) {
                    throw new RuntimeException("开始或结束时间不能为空");
                }
                begin = new Date(beginTimes);
                end = new Date(endTimes);
                break;
            default:
                throw new RuntimeException("时间类型错误");
        }
        result[0] = begin;
        result[1] = end;
        return result;
    }
}
