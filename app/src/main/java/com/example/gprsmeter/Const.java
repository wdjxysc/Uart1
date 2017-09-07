package com.example.gprsmeter;

/**
 * Created by Administrator on 2017/8/30.
 *
 * @author wdjxysc
 */
public class Const {

    /**
     * 命令头
     */
    public final static int HEAD = 0x68;

    /**
     * 命令尾
     */
    public final static int TAIL = 0x16;

    /// <summary>
    /// 通用表ID
    /// </summary>
    public final static String METER_ID_AAAAAAAAAAAAAA = "AAAAAAAAAAAAAA";

    public final static int CMD_ID_STC_TEST            = 3002;//主站向从站发送通信测试指令
    public final static int CMD_ID_CTS_TEST             = 3001;//从站向主站发送通信测试指令
    public final static int CMD_ID_CTS_HEARTBEAT        = 3003;//从站向主站发送心跳包
    public final static int CMD_ID_CTS_REGISTER         = 3004;//从站和主站建立连接后，发送注册包，注册包每次登录时发送
    public final static int CMD_ID_STC_READDATA         = 3042;//从站自醒后，主站向从站发送单表抄表指令，从站将当天0时0分的抄表信息上传主站
    public final static int CMD_ID_CTS_UPLOADDATA       = 3043;//从站自醒后，从站向主站发送抄表数据，从站将当天0时0分的抄表信息上传主站
    public final static int CMD_ID_STC_READ_HISTORY_DATA_DAY = 3044;//从站自醒后，主站向从站发送单表历史数据查询指令，从站将该表最近60天抄表信息上传主站，响应报文返回时采用分拆成多个报文帧返回
    public final static int CMD_ID_STC_READ_HISTORY_DATA_MONTH = 3045;//从站自醒后，主站向从站发送单表历史数据查询指令，获取表具月用量历史数据，按保存10年计算120条记录，整个包为2K，响应报文采用分拆多个报文帧返回
    public final static int CMD_ID_CTS_UPLOAD_METER_INFO = 3046;//表具信息上报
    public final static int CMD_ID_CTS_UPLOAD_METER_RECORD_INFO = 3047;//物联网表采集信息下主动上报
    public final static int CMD_ID_CTS_UPLOAD_METER_GROUP_RECORD_INFO = 3048;//物联网表采集信息下主动批量上报

    public final static int CMD_ID_STC_SET_HEARTBEAT_CYCLE    = 3021;//物联网表心跳周期设置
    public final static int CMD_ID_STC_GET_HEARTBEAT_CYCLE    = 3022;//物联网表心跳周期查询
    public final static int CMD_ID_CTS_REQUST_TIME            = 3025;//物联网表时钟设置
    public final static int CMD_ID_STC_GET_TIME               = 3026;//物联网表时钟查询
    public final static int CMD_ID_STC_SET_SERVER_IP          = 3027;//物联网表主站IP设置
    public final static int CMD_ID_STC_GET_SERVER_IP          = 3028;//物联网表主站IP查询
    public final static int CMD_ID_STC_SET_UPLOAD_CYCLE       = 3023;//物联表上报周期设置
    public final static int CMD_ID_STC_GET_UPLOAD_CYCLE       = 3024;//物联表上报周期设置
    public final static int CMD_ID_STC_SET_WAKEUP_UPLOAD_TIME = 3029;//物联网表自醒上报时间设置
    public final static int CMD_ID_STC_GET_WAKEUP_UPLOAD_TIME = 3030;//物联网表自醒上报时间查询
    public final static int CMD_ID_STC_SET_METER_RECORD_FREQUENCY = 3031; //物联表采集频率设置
    public final static int CMD_ID_STC_GET_METER_RECORD_FREQUENCY = 3032; //物联表采集频率查询
    public final static int CMD_ID_STC_SET_METER_FREEZE_DAY = 3033;//物联表冻结日设置指令
    public final static int CMD_ID_STC_GET_METER_FREEZE_DAY = 3034;//物联表冻结日查询指令
    public final static int CMD_ID_STC_OPN_VALVE       = 3051;//主站向从站发送单表开阀指令
    public final static int CMD_ID_STC_CLS_VALVE       = 3052;//主站向从站发送单表关阀指令
    public final static int CMD_ID_CTS_EXCEPTION_UPLOAD = 3062;//物联网表异常事件上传
    public final static int CMD_ID_STC_EXCEPTION_CLEAR = 3065;//物联网表故障信息清除指令

    public final static int CMD_ID_CTS_METER_UPLOAD_PIC = 3101;//摄像直读表采集图片信息下主动上报

    /*以下命令主要用于串口设置*/
    public final static int CMD_ID_STC_SET_TIME = 2015;//物联网表时钟设置
    public final static int CMD_ID_STC_SET_DATA = 2016;//物联网表底数设置
    public final static int CMD_ID_STC_SET_METER_ID = 2018;//物联网表地址设置
    public final static int CMD_ID_STC_SET_ENABLE = 2019;//物联网表出厂启用


    //异常类型
    public final static int ERR_FORCE_CLS_VALVE = 1001;//强行关阀
    public final static int ERR_GAS_LEAK        = 1002;//燃气泄漏
    public final static int ERR_RDSW_BAD        = 1003;//干簧管坏
    public final static int ERR_MEMORY_BAD      = 1004;//存储器坏
    public final static int ERR_STRG_MAGIC      = 1005;//强磁干扰
    public final static int ERR_METER_DEAD      = 1006;//死表故障
    public final static int ERR_VAVLE_LEAK      = 1007;//阀门漏气
    public final static int ERR_CANCEL_CLS      = 1008;//取消强关
    public final static int ERR_BATTERY_BAD     = 1009;//电池故障


    //指令成功
    public final static int BACK_CODE_SUCCESS                 = 0;//指令成功
    //报文解析错误
    public final static int BACK_CODE_DATA_EMPTY              = 101;//报文为空
    public final static int BACK_CODE_DECRYPT_ERR             = 102;//报文解密失败
    public final static int BACK_CODE_CHECK_ERR               = 103;//报文校验失败
    public final static int BACK_CODE_FUNCCODE_EMPTY          = 104;//报文功能码为空
    public final static int BACK_CODE_FUNCCODE_UNKNOW         = 105;//报文功能码未知
    public final static int BACK_CODE_DATA_LOSE               = 106;//报文域不完整
    public final static int BACK_CODE_NECESSARY_LOSE          = 107;//报文必添域空
    public final static int BACK_CODE_DATALENGTH_ANALYSIS_ERR = 108;//报文长度域不能解析为整数
    public final static int BACK_CODE_DATALENGTH_WRONG        = 109;//报文长度域数值与报文长度不等
    public final static int BACK_CODE_CHILDDATA_EMPTY         = 110;//报文记录子域空
    public final static int BACK_CODE_CHILDDATA_NUM_WRONG     = 111;//报文记录子域中记录数与报文中的记录总数不等
    public final static int BACK_CODE_CHILDDATA_LOSE          = 112;//报文记录子域不完整
    public final static int BACK_CODE_DATA_DATETIME_ERR       = 113;//报文日期、时间有误，不能正确解析
    public final static int BACK_CODE_DATAID_EMPTY            = 114;//报文ID为空
    public final static int BACK_CODE_CLIENTID_EMPTY          = 115;//从站编号为空
    public final static int BACK_CODE_BCD_ERR                 = 116;//BCD码格式转换错误
    public final static int BACK_CODE_NUMDATA_ERR             = 117;//数值格式转换错误
    //DTU或物联网表业务相关
    public final static int BACK_CODE_LOGIN_ERR               = 1101;//DTU或物联网表登录异常
    public final static int BACK_CODE_CHECKTIME_ERR           = 1201;//DTU或物联网表对时异常
    public final static int BACK_CODE_READDATA_ERR            = 1301;//DTU或物联网表抄表异常
    public final static int BACK_CODE_OPN_VAVLE_ERR           = 1401;//DTU或物联网表开阀异常
    public final static int BACK_CODE_CLS_VALVE_ERR           = 1402;//DTU或物联网表关阀异常
    public final static int BACK_CODE_UPLOAD_EVEN_ERR         = 1501;//DTU或物联网表事件上传异常
    //集中器业务相关
    //...
    //物联网表业务相关
    //...
}
