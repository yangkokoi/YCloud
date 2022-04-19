/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: QuartzService
 * Author:   Young
 * Date:     2020/5/20 18:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.scheduler;


import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.core.entity.Task;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/5/20
 * @since 1.0.0
 */
@Service
public class SchedulerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("SchedulerFactory")
    private SchedulerFactory schedulerFactory;

    /**
     * @param task
     * @Description: 添加一个定时任务
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public String addJob(Task task) throws ClassNotFoundException {
        try {
            Class jobClass = Class.forName(task.getClassName());
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(task.getId().toString(), task.getName());
            if (!Objects.isNull(sched.getTrigger(triggerKey))) {
                // 任务已存在，无需新增
                logger.info("任务“{}”已存在，创建操作取消", task.getName());
                return "任务" + task.getName() + "已存在，创建操作取消";
            }
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(task.getId().toString(), task.getName())
                    .usingJobData("task", task.toString())
                    // 传递参数
                    .build();

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(task.getId().toString(), task.getName());
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(task.getCron()));
            // 创建Trigger对象
            Trigger trigger = triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            sched.scheduleJob(jobDetail, trigger);

            // 启动
            if (!sched.isShutdown()) {
                sched.start();
                logger.info("任务“{}”启动成功，CRON=[{}]", task.getName(), task.getCron());
                return "任务“{}”启动成功，CRON=[{}]";
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.warn("ClassName: " + task.getClassName() + " is not found");
            throw new CanShowException("任务启动失败", "ClassName: " + task.getClassName() + " is not found");
        }
        logger.info("任务“{}”创建出现问题", task.getName());
        return "任务" + task.getName() + "创建出现问题";
    }

    /**
     * @param task
     * @Description: 修改一个任务的触发时间
     */
    public String modifyJobTime(Task task) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(task.getId().toString(), task.getName());
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                // 如果触发器为空，则添加任务
                return addJob(task);
            }

            String oldTime = trigger.getExpression();

            if (!oldTime.equalsIgnoreCase(task.getCron())) {
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(task.getId().toString(), task.getName());
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(task.getCron()));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                sched.rescheduleJob(triggerKey, (Trigger) trigger);
                /** 方式一 ：调用 rescheduleJob 结束 */

                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                //Class<? extends Job> jobClass = jobDetail.getJobClass();
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
                /** 方式二 ：先删除，然后在创建一个新的Job 结束 */


                logger.info("任务“{}”修改成功，CRON=[{}]", task.getName(), task.getCron());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("任务“{}”修改失败，CRON=[{}]", task.getName(), task.getCron());
        }
        return "任务修改出现问题";
    }

    /**
     * @param task
     * @Description: 移除一个任务
     */
    public String removeJob(Task task) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();

            // TriggerKey triggerKey = TriggerKey.triggerKey(task.getId().toString(), task.getName());
            TriggerKey triggerKey = TriggerKey.triggerKey(task.getId().toString());

            sched.pauseTrigger(triggerKey); // 停止触发器
            sched.unscheduleJob(triggerKey); // 移除触发器
            sched.deleteJob(JobKey.jobKey(task.getId().toString(), task.getName())); // 删除任务

            logger.info("任务“{}”删除成功", task.getName());
            return "任务删除成功";
        } catch (Exception e) {
            logger.warn("任务{}删除失败，ERROR=[{}]", task.getName(), e.getMessage());
        }
        return "任务删除失败";
    }

    public Set<JobKey> lookJobs(int type) {
        Set<JobKey> jobKeys = null;
        try {
            jobKeys = schedulerFactory.getScheduler().getJobKeys(GroupMatcher.anyJobGroup());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        // if (type == 0) {
        //
        // } else if (type == 1) {
        //
        // } else if (type == 2) {
        //
        // }
        return jobKeys;
    }

    /**
     * @Description:启动所有定时任务
     */
    public String startJobs() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.start();
            return "启动所有任务成功";
        } catch (SchedulerException e) {
            return "启动所有任务失败";
        }
    }

    /**
     * @Description:停止所有定时任务
     */
    public int shutdownJobs() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
            logger.info("暂停所有任务成功");
            return 1;
        } catch (SchedulerException e) {
            logger.info("暂停所有任务成功");
            return 0;
        }
    }

}