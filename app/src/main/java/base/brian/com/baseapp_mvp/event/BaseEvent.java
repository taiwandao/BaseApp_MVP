package base.brian.com.baseapp_mvp.event;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
public class BaseEvent {
    public int rtnCode;  //接口是否返回成功  1成功  0失败
    public String rtnMsg;  //提示信息

    public int total;  //列表总数

    public int eventType;  //事件类型
    public int tabPosition;  //tab位置

    public boolean hasType = false;

    public Throwable throwable;

    public BaseEvent() {
    }

    public BaseEvent(int eventType) {
        this.eventType = eventType;
        hasType = true;
    }
}

