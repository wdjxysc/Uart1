package com.example.gprsmeter;

import com.example.uartdemo.SerialPortTool;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/30.
 *
 * @author wdjxysc
 */
public class GprsMeterOperation {

    private final int CommTimeOut = 2000;
    SerialPortTool serialPortTool;

    public GprsMeterOperation(){
        try {
            serialPortTool = new SerialPortTool(SerialPortTool.HANDLE_COMM_PORT,SerialPortTool.HANDLE_COMM_PORT_BR);
            serialPortTool.openPort(SerialPortTool.PowerLevel.POWER_RFID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /// <summary>
    /// 设置服务器地址
    /// </summary>
    /// <param name="meterId"></param>
    /// <param name="ip"></param>
    /// <param name="port"></param>
    /// <returns></returns>
    public HashMap<String, Object> writeServerIp(String meterId, String ip, int port)
    {
        HashMap<String,Object> map = new HashMap<String, Object>();


        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_SET_SERVER_IP);
        param.put(Cmd.KEY_SERVER_IP, ip);
        param.put(Cmd.KEY_SERVER_PORT, port);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        map = Cmd.ParseData(recvData);

        return map;
    }

    /// <summary>
    /// 获取服务器地址
    /// </summary>
    /// <param name="meterId"></param>
    /// <returns></returns>
    public HashMap<String, Object> readServerIp(String meterId)
    {
        HashMap<String,Object> dic = new HashMap<String, Object>();

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_GET_SERVER_IP);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);

        return dic;
    }

    /// <summary>
    /// 设置表心跳周期
    /// </summary>
    /// <param name="meterId"></param>
    /// <param name="second">心跳周期时间 单位秒</param>
    /// <returns></returns>
    public HashMap<String, Object> writeHeartbeatCycle(String meterId, int second)
    {
        HashMap<String,Object> dic = new HashMap<String, Object>();

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_SET_HEARTBEAT_CYCLE);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        param.put(Cmd.KEY_HEARTBEAT_CYCLE, second);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);

        return dic;
    }

    /// <summary>
    /// 获取心跳周期
    /// </summary>
    /// <param name="meterId"></param>
    /// <returns></returns>
    public HashMap<String, Object> readHeartbeatCycle(String meterId)
    {
        HashMap<String,Object> dic = new HashMap<String, Object>();

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_GET_HEARTBEAT_CYCLE);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);

        return dic;
    }

    /// <summary>
    /// 设置表时间
    /// </summary>
    /// <param name="meterId"></param>
    /// <param name="Date"></param>
    /// <returns></returns>
    public HashMap<String, Object> writeTime(String meterId, Date Date)
    {
        HashMap<String,Object> dic = new HashMap<String, Object>();

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_SET_TIME);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        param.put(Cmd.KEY_DATA_TIME, Date);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);

        return dic;
    }

    /// <summary>
    /// 获取表时间
    /// </summary>
    /// <param name="meterId"></param>
    /// <returns></returns>
    public HashMap<String, Object> readTime(String meterId)
    {
        HashMap<String,Object> dic = new HashMap<String, Object>();

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_GET_TIME);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);

        return dic;
    }

    /// <summary>
    /// 设置上报周期
    /// </summary>
    /// <param name="meterId"></param>
    /// <param name="uploadCycleType">上报周期模式</param>
    /// <returns></returns>
    public HashMap<String, Object> writeUploadCycle(String meterId, Cmd.UploadCycleType uploadCycleType)
    {
        HashMap<String,Object> dic = new HashMap<String, Object>();

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_SET_UPLOAD_CYCLE);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        param.put(Cmd.KEY_UPLOAD_CYCLE_TYPE, uploadCycleType);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);

        return dic;
    }

    /// <summary>
    /// 获取上报周期
    /// </summary>
    /// <param name="meterId"></param>
    /// <returns></returns>
    public HashMap<String, Object> readUploadCycle(String meterId)
    {
        HashMap<String,Object> dic = new HashMap<String, Object>();

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_GET_UPLOAD_CYCLE);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);


        return dic;
    }

    /// <summary>
    /// 设置物联网表自醒上报时间
    /// 实时在线物联表，主站向从站发送自醒上报时间设置指令
    /// 非实时在线物联表，从站自醒时，主站向从站发送自醒周期设置指令
    /// </summary>
    /// <param name="meterId"></param>
    /// <param name="wakeupTime">自醒时间</param>
    /// <param name="wakeupLong">自醒时长</param>
    /// <param name="delay">延迟时间</param>
    /// <returns></returns>
    public HashMap<String, Object> writeWakeupUploadTime(String meterId, Date wakeupTime, int wakeupLong, int delay)
    {
        HashMap<String,Object> dic;

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_SET_WAKEUP_UPLOAD_TIME);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        param.put(Cmd.KEY_WAKEUP_TIME, wakeupTime);
        param.put(Cmd.KEY_WAKEUP_LONG, wakeupLong);
        param.put(Cmd.KEY_WAKEUP_DELAY, delay);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);
        return dic;
    }

    /// <summary>
    /// 读取表自醒上报时间
    /// </summary>
    /// <param name="meterId"></param>
    /// <returns></returns>
    public HashMap<String, Object> readWakeupUploadTime(String meterId)
    {
        HashMap<String,Object> dic;

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_GET_WAKEUP_UPLOAD_TIME);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);

        return dic;
    }


    /// <summary>
    /// 设置物联网表底数
    /// </summary>
    /// <param name="meterId"></param>
    /// <param name="flow"></param>
    /// <returns></returns>
    public HashMap<String, Object> setMeterValue(String meterId, float flow)
    {
        HashMap<String,Object> dic;

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_SET_DATA);
        param.put(Cmd.KEY_METER_VALUE, flow);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);


        return dic;
    }

    /// <summary>
    /// 设置物联网表地址
    /// </summary>
    /// <param name="meterId"></param>
    /// <param name="newMeterId"></param>
    /// <returns></returns>
    public HashMap<String, Object> setNewMeterId(String meterId, String newMeterId)
    {
        HashMap<String,Object> dic;

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_SET_METER_ID);
        param.put(Cmd.KEY_NEW_METER_ID, newMeterId);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);

        return dic;
    }


    /// <summary>
    /// 设置物联网表采集频率
    /// </summary>
    /// <param name="meterId"></param>
    /// <param name="frequency">频率</param>
    /// <returns></returns>
    public HashMap<String, Object> setMeterFreqency(String meterId, int frequency)
    {
        HashMap<String,Object> dic;

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_SET_METER_RECORD_FREQUENCY);
        param.put(Cmd.KEY_METER_RECORD_FREQUENCY, frequency);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);

        return dic;
    }

    /// <summary>
    /// 查询采集频率
    /// </summary>
    /// <param name="meterId"></param>
    /// <returns></returns>
    public HashMap<String, Object> readMeterFreqency(String meterId)
    {
        HashMap<String,Object> dic;

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_GET_METER_RECORD_FREQUENCY);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);

        return dic;
    }


    /// <summary>
    /// 设置物联网表冻结日
    /// </summary>
    /// <param name="meterId"></param>
    /// <param name="frequency">频率</param>
    /// <returns></returns>
    public HashMap<String, Object> setMeterFreezeDay(String meterId, int day)
    {
        HashMap<String,Object> dic = new HashMap<String, Object>();

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_SET_METER_FREEZE_DAY);
        param.put(Cmd.KEY_METER_FREEZE_DAY, day);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);


        return dic;
    }

    /// <summary>
    /// 查询采集频率
    /// </summary>
    /// <param name="meterId"></param>
    /// <returns></returns>
    public HashMap<String, Object> readMeterFreezeDay(String meterId)
    {
        HashMap<String,Object> dic = new HashMap<String, Object>();

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_GET_METER_FREEZE_DAY);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);

        return dic;
    }

    /// <summary>
    /// 设置物联网表出厂启用
    /// </summary>
    /// <param name="meterId"></param>
    /// <returns></returns>
    public HashMap<String, Object> setMeterEnable(String meterId)
    {
        HashMap<String,Object> dic = new HashMap<String, Object>();

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_SET_ENABLE);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);

        return dic;
    }

    /// <summary>
    /// 读表数据
    /// </summary>
    /// <param name="meterId"></param>
    /// <returns></returns>
    public HashMap<String, Object> readMeterData(String meterId)
    {
        HashMap<String,Object> dic = new HashMap<String, Object>();

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_READDATA);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);


        return dic;
    }


    /// <summary>
    /// 开阀
    /// </summary>
    /// <param name="meterId"></param>
    /// <returns></returns>
    public HashMap<String, Object> openMeterValve(String meterId)
    {
        HashMap<String,Object> dic = new HashMap<String, Object>();

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_OPN_VALVE);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);


        return dic;
    }

    /// <summary>
    /// 关阀
    /// </summary>
    /// <param name="meterId"></param>
    /// <returns></returns>
    public HashMap<String, Object> closeMeterValve(String meterId)
    {
        HashMap<String,Object> dic = new HashMap<String, Object>();

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put(Cmd.KEY_METER_ID, meterId);
        param.put(Cmd.KEY_CMD_ID, Const.CMD_ID_STC_CLS_VALVE);
        param.put(Cmd.KEY_MESSAGE_ID, 0);
        byte[] sendData = Cmd.AssembleCmd(param);

        byte[] recvData = new byte[0];
        try {
            recvData = serialPortTool.sendAndRevDataComm(sendData, CommTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dic = Cmd.ParseData(recvData);

        return dic;
    }
}
