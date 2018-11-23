import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class FirstProcessTest {

    private RepositoryService repositoryService;
    private IdentityService identityService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;
    private String conditionTemplate = "${true}";// 默认只有一条流程

    //创建23张表
    @Before
    public void testCreateProcess() {
        ProcessEngineConfiguration pc = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("act_cfg.xml");
        ProcessEngine processEngine = pc.buildProcessEngine();
        if (processEngine != null) {
            System.out.println("创建成功");
        }
        repositoryService = processEngine.getRepositoryService();
        identityService = processEngine.getIdentityService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
    }

    //    //发布流程
//    @Test
//    public void testDeployProcess(){
//        repositoryService.createDeployment().addClasspathResource("hello.bpmn.xml").deploy();
////        ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().latestVersion().singleResult();
////        System.out.println(processDefinition.getKey());
//    }
//
//    @Test
//    public  void testQueryProcess(){
//        ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery()
//                .processDefinitionKey("myFirstProcess").latestVersion().singleResult();
//        System.out.println(processDefinition.getKey());
//    }

    //    // 任务查询对象，对应操作的数据库表是任务表(act_ru_task)
//    @Test
//    public void testQueryTask(){
//        TaskQuery query = taskService.createTaskQuery();
//        String name="zs";
//        query.taskAssignee(name);
//        List<Task> list=query.list();
//        for (Task task:list){
//            System.out.println(task.getId()+"    "+task.getName());
//        }
//
//    }
//
//    @Test
//    public void testCompleteProcess() {
//        String taskId = "204"; // 任务id
//        taskService.complete(taskId);
//    }
//
    //发布流程
//    @Test
//    public void testDeployProcess2() {
//        // 动态生成流程
//        String processDefinitionId = "dynamicProcess";
//        BpmnModel model = new BpmnModel();
//        Process process = new Process();
//        model.addProcess(process);
//        process.setId(processDefinitionId);
//
//
//        process.addFlowElement(createStartEvent());
//        process.addFlowElement(createExclusiveGateway("gateway", "排他网关"));
//        process.addFlowElement(createUserTask("task1", "第一个任务", "xy"));
//        process.addFlowElement(createUserTask("task2", "第二个任务", "yh"));
//        process.addFlowElement(createEndEvent());
//
//        process.addFlowElement(createSequenceFlow(null, "start", "gateway"));
//        process.addFlowElement(createSequenceFlow("${value>=3}", "gateway", "task1"));
//        process.addFlowElement(createSequenceFlow("${value<3}", "gateway", "task2"));
//
//        process.addFlowElement(createSequenceFlow(null, "task2", "end"));
//        process.addFlowElement(createSequenceFlow(null, "task1", "end"));
//
//
//        repositoryService.createDeployment().addBpmnModel("dynamic-model-" + processDefinitionId + ".bpmn", model)
//                .name("Dynamic-process-" + processDefinitionId).deploy();
//    }
//
//    @Test
//    public void testStartProcess2() {
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//                .processDefinitionKey("dynamicProcess").latestVersion().singleResult();//获取最新版本
//        System.out.println(processDefinition.getKey());
//        Map map = new HashMap();
//        map.put("value", 4);
//        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), map);
//        System.out.println(processInstance.getId() + "    " + processInstance.getProcessDefinitionId());
//    }
//
//    @Test
//    public void testQueryTask() {
//        TaskQuery query = taskService.createTaskQuery();
//        String name = "xy";
//        query.taskAssignee(name);
//        List<Task> list = query.list();
//        for (Task task : list) {
//            System.out.println(task.getId() + "    " + task.getName());
//            taskService.complete(task.getId());
//        }
//
//    }
//
//    //排他网关
//    protected ExclusiveGateway createExclusiveGateway(String id, String name) {
//        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
//        exclusiveGateway.setId(id);
//        exclusiveGateway.setName(name);
//        return exclusiveGateway;
//    }
//
//
//    protected UserTask createUserTask(String id, String name, String assignee) {
//        UserTask userTask = new UserTask();
//        userTask.setName(name);
//        userTask.setId(id);
//        userTask.setAssignee(assignee);
//        return userTask;
//    }
//
//    protected SequenceFlow createSequenceFlow(String expression, String from, String to) {
//        SequenceFlow flow = new SequenceFlow();
//        flow.setSourceRef(from);
//        flow.setTargetRef(to);
//        if (expression != null) {
//            flow.setConditionExpression(expression);
//        }
//        return flow;
//    }
//
//    protected StartEvent createStartEvent() {
//        StartEvent startEvent = new StartEvent();
//        startEvent.setId("start");
//        return startEvent;
//    }
//
//    protected EndEvent createEndEvent() {
//        EndEvent endEvent = new EndEvent();
//        endEvent.setId("end");
//        return endEvent;
//    }
//
//    //并行网关
//    protected ParallelGateway createParallelGateway(String id, String name) {
//        ParallelGateway parallelGateway = new ParallelGateway();
//        parallelGateway.setId(id);
//        parallelGateway.setName(name);
//        return parallelGateway;
//    }
//
//
    @Test
    public void testDeployParallelGateway() {
        // 动态生成流程
        String processDefinitionId = "dynamicProcess";
        BpmnModel model = new BpmnModel();
        Process process = new Process();
        model.addProcess(process);
        process.setId(processDefinitionId);

        //添加开始节点
        process.addFlowElement(createStartEvent());
        process.addFlowElement(createEndEvent());
        //增加并行网关
        process.addFlowElement(createParallelGateway("gateway1", "并行网关一"));
        process.addFlowElement(createParallelGateway("gateway2", "并行网关二"));
        process.addFlowElement(createUserTask("task1", "第一个任务", "ccc", true));
        process.addFlowElement(createUserTask("task2", "第二个任务", "${user}", false));
        process.addFlowElement(createUserTask("task3", "第三个任务", "jh", false));
        process.addFlowElement(createSequenceFlow("flow1", "连线一", "start", "task1"));
        process.addFlowElement(createSequenceFlow("flow2", "连线二", "task1", "gateway1"));
        process.addFlowElement(createSequenceFlow("flow3", "连线三", "gateway1", "task2"));
        process.addFlowElement(createSequenceFlow("flow4", "连线四", "gateway1", "task3"));
        process.addFlowElement(createSequenceFlow("flow5", "连线五", "task2", "gateway2"));
        process.addFlowElement(createSequenceFlow("flow6", "连线六", "task3", "gateway2"));
        process.addFlowElement(createSequenceFlow("flow7", "连线七", "gateway2", "end"));
        repositoryService.createDeployment().addBpmnModel("dynamic-model-" + processDefinitionId + ".bpmn", model)
                .name("Dynamic-process-" + processDefinitionId).deploy();
    }

    @Test
    public void testStartProcess() {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("dynamicProcess").latestVersion().singleResult();//获取最新版本
        System.out.println(processDefinition.getKey());
        identityService.setAuthenticatedUserId("ddd");
        Map map = new HashMap();
        map.put("user", "xy");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), "1", map);
    }

    // 任务查询对象，对应操作的数据库表是任务表(act_ru_task)
    @Test
    public void testQueryTask() {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey("3").singleResult();
        List<Task> list = taskService.createTaskQuery().taskCandidateUser("xy").list();
        for (Task task : list) {
            System.out.println(task.getName() + ",启动者:" + historyService.createHistoricProcessInstanceQuery().
                    processInstanceId(processInstance.getId()).singleResult().getStartUserId());
        }

    }

    @Test
    public void testCompleteProcess() {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey("3").singleResult();
        // 根据JNLNO查询Task
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId())
                .taskCandidateUser("ccc").singleResult();
        if (task != null) {
            // 当前用户声明占有任务
            taskService.claim(task.getId(), "ccc");

            // 当前用户声明占有任务
            taskService.complete(task.getId());
            System.out.println("任务完成");
        }

    }


    public ParallelGateway createParallelGateway(String id, String name) {
        ParallelGateway parallelGateway = new ParallelGateway();
        parallelGateway.setId(id);
        parallelGateway.setName(name);
        return parallelGateway;
    }

    public UserTask createUserTask(String id, String name, String user, boolean listener) {
        UserTask userTask = new UserTask();
        userTask.setId(id);
        userTask.setName(name);
        List users = new ArrayList();
        users.add(user);
        userTask.setCandidateUsers(users);
        if (listener) {
//            List<ExtensionElement> extensionElements = new ArrayList();
//            extensionElements.add(new ExtensionElement());
//            Map map = new HashMap();
//            map.put("start",extensionElements);
//            ExtensionElement extensionElement = new ExtensionElement();
//            TaskListener taskListener = new ExecuteListenerDemo();
//            List<TaskListener> taskListeners = new ArrayList();
//            taskListeners.add(taskListener);
//            userTask.setExtensionElements(map);
            ActivitiListener activitiListener = new ActivitiListener();
            activitiListener.setEvent("create");
            activitiListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
            activitiListener.setImplementation("ExecuteListenerDemo");
            List<ActivitiListener> activitiListeners = new ArrayList();
            activitiListeners.add(activitiListener);
            userTask.setTaskListeners(activitiListeners);
        }
        return userTask;
    }

//
//    // 根据各个级别生成流程
//    private void genAuthLevels(Process process, Integer[] levels, Map<String, Object> config) {
//        int counter = 0;
//        boolean isParallelGateway = false;
//        // i 当前级别 j 当前级别第几人
//        for (int i = 0, j; i < levels.length; i++) {
//            int count = levels[i];
//            String id = "config-" + i;
//            String name = "ParallelGateway-" + i;
//            String idEnd = "config-end-" + i;
//            String nameEnd = "ParallelGateway-end-" + i;
//            process.addFlowElement(createParallelGateway(id, name));
//            process.addFlowElement(createParallelGateway(idEnd, nameEnd));
//            for (j = 0; j < count; j++) {
//                String taskId = "config" + config.get("id") + "-task" + i + "-" + j;
//                // 组的命名使用 如果和流程相关 processid + '-' + level的方式
//                //            如果和流程无关 cifseq + '-' + level 方式
//                // 因为processid是 cifseq + '-' + 自定义processId的方式 所以可以通过取前缀取到cifseq
//                String processId = process.getId();
//
//                UserTask userTask = createUserTask(taskId, "task" + i + "-" + j, "xy-" + j);
//                process.addFlowElement(userTask);
//                if (i == levels.length - 1 && j == count - 1) {
//                    // 最后一岗名称设置为last便于判断
//                    userTask.setName("last");
//                    // 最后一岗 流程结束 后面有需要可以使用监听器处理事件
//                    process.addFlowElement(createSequenceFlow(null, id, taskId));
//                    process.addFlowElement(createSequenceFlow(null, taskId, idEnd));
//                    process.addFlowElement(createSequenceFlow(null, idEnd, "end"));
//                } else {
//                    String nextTaskId;
//                    // 当前岗位最后一人
//                    if (j == count - 1) {
//                        if (levels[i + 1] == 0) {// 下一级人数为0
//                            // 最后一岗名称设置为last便于判断
//                            userTask.setName("last");
//                            // 最后一岗 流程结束 后面有需要可以使用监听器处理事件
//                            process.addFlowElement(createSequenceFlow(null, id, taskId));
//                            process.addFlowElement(createSequenceFlow(null, taskId, idEnd));
//                            process.addFlowElement(createSequenceFlow(null, idEnd, "end"));
//                            return;
//                        }
//                        nextTaskId = "config" + config.get("id") + "-task" + (i + 1) + "-0";
//                    } else {
//                        nextTaskId = "config" + config.get("id") + "-task" + i + "-" + (j + 1);
//                    }
//                    // 添加同意连线 进入下一任务
////                    process.addFlowElement(createSequenceFlowWithCondition("config" + config.get("id") + "flow" + (counter++), "", taskId, nextTaskId, "${AuthResult == 0}"));
//                    // 审批拒绝直接删除流程，不再连接end，简化流程
//                    process.addFlowElement(createSequenceFlow(null, id, taskId));
//                    process.addFlowElement(createSequenceFlow(null, taskId, idEnd));
//                    // process.addFlowElement(createSequenceFlow(null, taskId, nextTaskId));
//                    // 添加拒绝连线 流程结束
////                    process.addFlowElement(createSequenceFlowWithCondition("config" + config.get("id") + "flow" + (counter++), "", taskId, "end", "${AuthResult != 0}"));
//                }
//            }
//            if(i!=levels.length-1){
//                process.addFlowElement(createSequenceFlow(null, idEnd, "config-" + (i + 1)));
//            }
//
//        }
//    }


//    @Test
//    public void testDeployParallelGateway1() {
////        // 动态生成流程
//        String processDefinitionId = "dynamicProcess";
////        BpmnModel model = new BpmnModel();
////        Process process = new Process();
////        model.addProcess(process);
////        process.setId(processDefinitionId);
////        Integer[] levels = {10, 2, 6, 4};
////        Map config = new HashMap();
////        config.put("id", 0);
////        process.addFlowElement(createStartEvent());
////        process.addFlowElement(createEndEvent());
////        process.addFlowElement(createSequenceFlow(null, "start", "config-" + 0));
////        genAuthLevels(process, levels, config);
////        repositoryService.createDeployment().addBpmnModel("dynamic-model-" + processDefinitionId + ".bpmn", model)
////                .name("Dynamic-process-" + processDefinitionId).deploy();
//        Integer[] l = {1, 2, 3, 4, 5};
//        Integer[] m = {1, 5, 3, 4, 5};
//        Integer[] m1 = {1, 1, 3, 0, 0};
//        List configs = new ArrayList();
//        Map config = new HashMap();
//        config.put("id", "0");
//        config.put("AuthCountList", l);
//
//        Map config1 = new HashMap();
//        config1.put("id", "1");
//        config1.put("AuthCountList", m);
//
//        Map config2 = new HashMap();
//        config2.put("id", "2");
//        config2.put("AuthCountList", m1);
//
//        configs.add(config);
//        configs.add(config1);
//        configs.add(config2);
//        createAuthConfig(processDefinitionId, configs);
//
//
//    }


//    public void createAuthConfig(String processDefinitionId, List<Map<String, Object>> configs) {
//        // 动态生成流程
//        BpmnModel model = new BpmnModel();
//        Process process = new Process();
//        model.addProcess(process);
//        process.setId(processDefinitionId);
//        initProcess(process, configs);
//
//        // 发布流程
//        // 流程定义不建议修改 可以使用新增版本号的方式 重新deploy可自动增加
//        repositoryService.createDeployment().addBpmnModel("dynamic-model-" + processDefinitionId + ".bpmn", model)
//                .name("Dynamic-process-" + processDefinitionId).deploy();
//    }
//
//
//    // 初始化Process
//    private void initProcess(Process process, List<Map<String, Object>> configs) {
//        // 通过textannotion方式存储原始配置json
//        TextAnnotation annotation = new TextAnnotation();
//        annotation.setText("ddd");
//        annotation.setId("JSON");
//        process.addArtifact(annotation);
//        // 起始点
//        process.addFlowElement(createStartEvent());
//        // 结束点
//        process.addFlowElement(createEndEvent());
//        // 配置网关 负责路由配置
//        process.addFlowElement(createExclusiveGateway());
//        // 连接起始点和网关
//        process.addFlowElement(createSequenceFlow("startflow", "启动流程", "start", "configGateway"));
//        Iterator<Map<String, Object>> it = configs.iterator();
//        // 生成不同分支
//        while (it.hasNext()) {
//            genConfigBranch(process, it.next());
//        }
//    }
//
//    // 生成配置分支
//    private void genConfigBranch(Process process, Map<String, Object> configEntry) {
//        String condition = transferTemplate(conditionTemplate, configEntry);
//        String id = (String) configEntry.get("id");
//        Integer[] levels = (Integer[]) configEntry.get("AuthCountList");
//        // 连接网关与当前配置第一个任务
//        // 任务id命名规范 'config'+config id+'_'+ 'task' + level + index   例如 config0_task0_0
//        // flow 命名规范  config0_flow0
//        // flow name 用来存储 AuthCountList
//        FlowElement flowElement = createSequenceFlowWithCondition("config" + id + "-flow0",
//                "hh", "configGateway", "config" + id + "-task0-0", condition);
//        process.addFlowElement(flowElement);
//        // 设置监听器
//        //flowElement.setExecutionListeners(listeners);
//
//        genAuthLevels(process, levels, configEntry);
//    }
//
//    // 根据各个级别生成流程
//    private void genAuthLevels(Process process, Integer[] levels, Map<String, Object> config) {
//        int counter = 0;
//        // i 当前级别 j 当前级别第几人
//        for (int i = 0, j; i < levels.length; i++) {
//            int count = levels[i];
//            for (j = 0; j < count; j++) {
//                String taskId = "config" + config.get("id") + "-task" + i + "-" + j;
//                // 组的命名使用 如果和流程相关 processid + '-' + level的方式
//                //            如果和流程无关 cifseq + '-' + level 方式
//                // 因为processid是 cifseq + '-' + 自定义processId的方式 所以可以通过取前缀取到cifseq
//                String processId = process.getId();
//                // String nextGroupId = WorkFlowUtil.genGroupId(processId, i + 1);
//                List group = new ArrayList();
//                if (config.get("Div") != null) {//企网按照审核组方式审核   后管采用直接指定审核人方式  这里之前
//                    //写后管的时候是按照两个审核组去写的，一个是部门组的人，另一个是机构组的人,所以这里多加了一个组传进去  后来发现用审核组写不行
//                    // 需要直接指定审核人  在这里这个新加的组就拿来区分是企网的，还是后管的 也就是说现在这个地方类似于一个标志的作用了，也可以改成别的标志
//                    //例如：BBMS之类的，如果是企网 group里面只有一个组别   如果是后管的话  应该有两个组别，但是这两个组别里面现在都没有放东西
//                    // 如果以后需要后管用审核组方式就可以直接set就行，所以把参数改为list
//                    // 然后判断有几个组  一个组的就是企网  两个的就是后管
//
//                    //再次修改  修改为符合多个节点的情况  候选组别为${users_i+1)}  当多级审核时  需设置监听  在完成节点时候进行下一级别
//                    //审核的人设置  也就是走一级设置一步
//                    // String processDefinitionIdDiv = WorkFlowUtil.genGroupId((String) config.get("Div"), i + 1);
//                    String users = "${" + "users_" + (i + 1) + "}";
//                    // group.add(processDefinitionIdDiv);//后管需要   直接指定审核人方式
//                    group.add(users);
//                }
//                //group.add(nextGroupId);//保持企网的功能不变  更改传入参数为List
//                UserTask userTask = createUserTask(taskId, "task" + i + "-" + j, group);
//                process.addFlowElement(userTask);
//                if (i == levels.length - 1 && j == count - 1) {
//                    // 最后一岗名称设置为last便于判断
//                    userTask.setName("last");
//                    // 最后一岗 流程结束 后面有需要可以使用监听器处理事件
//                    process.addFlowElement(createSequenceFlow("config" + config.get("id") + "flow" + (counter++), "", taskId, "end"));
//                } else {
//                    String nextTaskId;
//                    // 当前岗位最后一人
//                    if (j == count - 1) {
//                        if (levels[i + 1] == 0) {// 下一级人数为0
//                            // 最后一岗名称设置为last便于判断
//                            userTask.setName("last");
//                            // 最后一岗 流程结束 后面有需要可以使用监听器处理事件
//                            process.addFlowElement(createSequenceFlow("config" + config.get("id") + "flow" + (counter++), "", taskId, "end"));
//                            return;
//                        }
//                        nextTaskId = "config" + config.get("id") + "-task" + (i + 1) + "-0";
//                    } else {
//                        nextTaskId = "config" + config.get("id") + "-task" + i + "-" + (j + 1);
//                    }
//                    // 添加同意连线 进入下一任务
////                    process.addFlowElement(createSequenceFlowWithCondition("config" + config.get("id") + "flow" + (counter++), "", taskId, nextTaskId, "${AuthResult == 0}"));
//                    // 审批拒绝直接删除流程，不再连接end，简化流程
//                    process.addFlowElement(createSequenceFlow("config" + config.get("id") + "flow" + (counter++), "", taskId, nextTaskId));
//                    // 添加拒绝连线 流程结束
////                    process.addFlowElement(createSequenceFlowWithCondition("config" + config.get("id") + "flow" + (counter++), "", taskId, "end", "${AuthResult != 0}"));
//                }
//            }
//        }
//    }

//    protected UserTask createUserTask(String id, String name, List groups) {
//        UserTask userTask = new UserTask();
//        userTask.setName(name);
//        userTask.setId(id);
////        List<String> groups = new ArrayList<>();
////        groups.add(assignee);
//
//        if (groups.size() > 1) {//后管审核流程  企网没有直接指定人这种方式 所以这里加了一个if  后管的走这里  企网的走else
//            String users = (String) groups.get(1);//设置审核组的人 占位符先占着   启动流程时候再添加对应审核的人
//            List user = new ArrayList();
//            user.add(users);
//            userTask.setCandidateUsers(user);
//        } else {
//            userTask.setCandidateGroups(groups);//else可要可不要   如果不要说明  组内人和指定人都可以审核
//        }
//        return userTask;
//    }

    // 创建条件连接
    private SequenceFlow createSequenceFlowWithCondition(String id, String name, String from, String to, String condition) {
        SequenceFlow flow = new SequenceFlow();
        flow.setId(id);
        flow.setName(name);
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        if (condition != null) {
            flow.setConditionExpression(condition);
        }
        return flow;
    }

    // 创建连接
    protected SequenceFlow createSequenceFlow(String id, String name, String from, String to) {
        return createSequenceFlowWithCondition(id, name, from, to, null);
    }

    // 创建起始点
    protected StartEvent createStartEvent() {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("start");
        return startEvent;
    }

    // 创建结束点
    protected EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        return endEvent;
    }

    // 创建排他网关
    private ExclusiveGateway createExclusiveGateway() {
        ExclusiveGateway gateway = new ExclusiveGateway();
        gateway.setId("configGateway");
        gateway.setName("Exclusive Gateway");
        //gateway.setExclusive(true);
        return gateway;
    }

    /**
     * 根据模板动态生成条件
     */
//    private String transferTemplate(String template, Map<String, Object> map) {
//        template = template.replaceAll(" ", "");
//        String[] strs = template.split("\\|");
//        StringBuffer result = new StringBuffer();
//        for (int i = 0; i < strs.length; i++) {
//            if (strs[i].startsWith("@")) {
//                result.append(String.valueOf(map.get(strs[i].substring(1, strs[i].length()))));
//            } else {
//                result.append(strs[i]);
//            }
//        }
//        return new String(result);
//    }
    @Test
    public void groupCreateTest() {
        Group group = identityService.newGroup("hello");
        group.setName("审核组");
        group.setType("1");
        identityService.saveGroup(group);

    }

    public void createUser(IdentityService identityService, String userId, String name) {
        User user = identityService.newUser(userId);
        user.setFirstName(name);
        identityService.saveUser(user);
    }

    @Test
    public void startProcessTest() {
        String processId = "dynamicProcess";
//        createUser(identityService, "1", "张三");
//        createUser(identityService, "2", "李四");
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processId).latestVersion().singleResult();
//        if(processDefinition!=null){
        //repositoryService.addCandidateStarterUser(processDefinition.getId(),"1");
        List<ProcessDefinition> users = repositoryService.createProcessDefinitionQuery().startableByUser("1").list();
        System.out.println(users.size());
        //}

    }


}