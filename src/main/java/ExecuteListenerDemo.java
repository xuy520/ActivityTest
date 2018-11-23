
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author xuyue_2017@csii.com.cn
 * @ClassName: ExecuteListenerDemo
 * @Description:
 * @date 2018/11/10 12:49
 * Copyright (c) CSII.
 * All Rights Reserved.
 */  

public class ExecuteListenerDemo implements TaskListener{


    public void notify(DelegateTask delegateTask) {
        System.out.println("监听开始:"+delegateTask.getExecution().getProcessInstanceBusinessKey());

    }
}
