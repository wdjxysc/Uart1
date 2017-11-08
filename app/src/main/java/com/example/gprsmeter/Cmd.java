package com.example.gprsmeter;

import android.util.Log;

import com.example.parse.MeterStateConst;
import com.example.uartdemo.CRC16;
import com.example.uartdemo.StringTool;
import com.example.uartdemo.Tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.example.gprsmeter.Const.CMD_ID_CTS_EXCEPTION_UPLOAD;
import static com.example.gprsmeter.Const.CMD_ID_CTS_HEARTBEAT;
import static com.example.gprsmeter.Const.CMD_ID_CTS_METER_UPLOAD_PIC;
import static com.example.gprsmeter.Const.CMD_ID_CTS_REGISTER;
import static com.example.gprsmeter.Const.CMD_ID_CTS_REQUST_TIME;
import static com.example.gprsmeter.Const.CMD_ID_CTS_TEST;
import static com.example.gprsmeter.Const.CMD_ID_CTS_UPLOADDATA;
import static com.example.gprsmeter.Const.CMD_ID_CTS_UPLOAD_METER_GROUP_RECORD_INFO;
import static com.example.gprsmeter.Const.CMD_ID_CTS_UPLOAD_METER_INFO;
import static com.example.gprsmeter.Const.CMD_ID_CTS_UPLOAD_METER_RECORD_INFO;
import static com.example.gprsmeter.Const.CMD_ID_STC_CLS_VALVE;
import static com.example.gprsmeter.Const.CMD_ID_STC_EXCEPTION_CLEAR;
import static com.example.gprsmeter.Const.CMD_ID_STC_GET_HEARTBEAT_CYCLE;
import static com.example.gprsmeter.Const.CMD_ID_STC_GET_ICCID;
import static com.example.gprsmeter.Const.CMD_ID_STC_GET_METER_FREEZE_DAY;
import static com.example.gprsmeter.Const.CMD_ID_STC_GET_METER_RECORD_FREQUENCY;
import static com.example.gprsmeter.Const.CMD_ID_STC_GET_RSSI;
import static com.example.gprsmeter.Const.CMD_ID_STC_GET_SERVER_IP;
import static com.example.gprsmeter.Const.CMD_ID_STC_GET_TIME;
import static com.example.gprsmeter.Const.CMD_ID_STC_GET_UPLOAD_CYCLE;
import static com.example.gprsmeter.Const.CMD_ID_STC_GET_WAKEUP_UPLOAD_TIME;
import static com.example.gprsmeter.Const.CMD_ID_STC_OPN_VALVE;
import static com.example.gprsmeter.Const.CMD_ID_STC_READDATA;
import static com.example.gprsmeter.Const.CMD_ID_STC_READ_HISTORY_DATA_DAY;
import static com.example.gprsmeter.Const.CMD_ID_STC_READ_HISTORY_DATA_MONTH;
import static com.example.gprsmeter.Const.CMD_ID_STC_SET_DATA;
import static com.example.gprsmeter.Const.CMD_ID_STC_SET_ENABLE;
import static com.example.gprsmeter.Const.CMD_ID_STC_SET_HEARTBEAT_CYCLE;
import static com.example.gprsmeter.Const.CMD_ID_STC_SET_METER_FREEZE_DAY;
import static com.example.gprsmeter.Const.CMD_ID_STC_SET_METER_ID;
import static com.example.gprsmeter.Const.CMD_ID_STC_SET_METER_RECORD_FREQUENCY;
import static com.example.gprsmeter.Const.CMD_ID_STC_SET_SERVER_IP;
import static com.example.gprsmeter.Const.CMD_ID_STC_SET_TIME;
import static com.example.gprsmeter.Const.CMD_ID_STC_SET_UPLOAD_CYCLE;
import static com.example.gprsmeter.Const.CMD_ID_STC_SET_WAKEUP_UPLOAD_TIME;
import static com.example.gprsmeter.Const.CMD_ID_STC_TEST;
import static com.example.gprsmeter.Const.HEAD;
import static com.example.gprsmeter.Const.TAIL;

/**
 * Created by Administrator on 2017/8/30.
 *
 * @author wdjxysc
 */
public class Cmd {


    public static byte[] AssembleCmd(HashMap<String, Object> dic)
    {
        byte[] data = null;

        String meterId = dic.get(KEY_METER_ID).toString();
        int cmdId = Integer.parseInt(dic.get(KEY_CMD_ID).toString());
        String messageId = StringTool.padLeft(dic.get(KEY_MESSAGE_ID).toString(), 14, '0');

        int backCode = 0;

        //数据域
        String dataStr = "";
        int requestResponseFlag = 0x00;
        switch (cmdId)
        {
            case CMD_ID_STC_TEST:
                dataStr = meterId;
                break;
            case CMD_ID_CTS_TEST:
                backCode = Integer.parseInt(dic.get(KEY_BACK_CODE).toString());
                dataStr =  StringTool.padLeft(backCode+"",4,'0') + meterId;
                requestResponseFlag = 0x01;
                break;
            case CMD_ID_CTS_HEARTBEAT:
                backCode = Integer.parseInt(dic.get(KEY_BACK_CODE).toString());
                dataStr =  StringTool.padLeft(backCode+"",4,'0') + meterId;
                requestResponseFlag = 0x01;
                break;
            case CMD_ID_CTS_REGISTER:
                backCode = Integer.parseInt(dic.get(KEY_BACK_CODE).toString());
                dataStr =  StringTool.padLeft(backCode+"",4,'0') + meterId;
                requestResponseFlag = 0x01;
                break;
            case CMD_ID_STC_SET_HEARTBEAT_CYCLE:
                int cycleTime = Integer.parseInt(dic.get(KEY_HEARTBEAT_CYCLE).toString());
                dataStr = meterId + StringTool.reversalHexString(StringTool.padLeft(Integer.toHexString(cycleTime),4,'0')) ;
                break;
            case CMD_ID_STC_GET_HEARTBEAT_CYCLE:
                dataStr = meterId;
                break;
            case CMD_ID_CTS_REQUST_TIME:
                String timeStr =  Tools.getStringDateTime(new Date());
                backCode = Integer.parseInt(dic.get(KEY_BACK_CODE).toString());
                dataStr = StringTool.padLeft(backCode+"",4,'0')  + meterId + timeStr;
                requestResponseFlag = 0x01;
                break;
            case CMD_ID_STC_GET_TIME:
                dataStr = meterId;
                break;
            case CMD_ID_STC_SET_SERVER_IP:
                String portStr = StringTool.padLeft(dic.get(KEY_SERVER_PORT).toString(),6,'0');
                String ipStr = dic.get(KEY_SERVER_IP).toString();

                String[] ipList = ipStr.split("\\.");
                String ipByteStr = "";
                for (int i=0;i<ipList.length;i++){
                    ipByteStr += StringTool.padLeft(ipList[i], 3 ,'0');
                }
                dataStr = meterId + ipByteStr + portStr;
                break;
            case CMD_ID_STC_GET_SERVER_IP:
                dataStr = meterId;
                break;
            case CMD_ID_STC_SET_UPLOAD_CYCLE:
                UploadCycleType cycleType = (UploadCycleType)dic.get(KEY_UPLOAD_CYCLE_TYPE);
                String cycleTypeStr = UPLOAD_CYCLE_TYPE_DAY;
                switch (cycleType)
                {
                    case Day:
                        cycleTypeStr = UPLOAD_CYCLE_TYPE_DAY;
                        break;
                    case TenDay:
                        cycleTypeStr = UPLOAD_CYCLE_TYPE_TEN_DAY;
                        break;
                    case Month:
                        cycleTypeStr = UPLOAD_CYCLE_TYPE_MONTH;
                        break;
                }
                dataStr = meterId + "00" + cycleTypeStr;
                break;
            case CMD_ID_STC_GET_UPLOAD_CYCLE:
                dataStr = meterId;
                break;
            case CMD_ID_STC_SET_WAKEUP_UPLOAD_TIME:
                String wakeupTime = Tools.getStringTime((Date)dic.get(KEY_WAKEUP_TIME));
                int wakeupLong = Integer.parseInt(dic.get(KEY_WAKEUP_LONG).toString());
                int delay = Integer.parseInt(dic.get(KEY_WAKEUP_DELAY).toString());
                dataStr = meterId + wakeupTime + StringTool.reversalHexString(StringTool.padLeft(Integer.toHexString(wakeupLong),4,'0')) +
                        StringTool.reversalHexString(StringTool.padLeft(Integer.toHexString(delay),4,'0'));
                break;
            case CMD_ID_STC_GET_WAKEUP_UPLOAD_TIME:
                dataStr = meterId;
                break;
            case CMD_ID_STC_SET_METER_RECORD_FREQUENCY:
                int frequency = Integer.parseInt(dic.get(KEY_METER_RECORD_FREQUENCY).toString());
                dataStr = meterId + StringTool.padLeft(Integer.toHexString(frequency),2,'0');
                break;
            case CMD_ID_STC_GET_METER_RECORD_FREQUENCY:
                dataStr = meterId;
                break;
            case CMD_ID_STC_SET_METER_FREEZE_DAY:
                int day = Integer.parseInt(dic.get(KEY_METER_FREEZE_DAY).toString());
                dataStr = meterId + StringTool.padLeft(day+"",4,'0');
                break;
            case CMD_ID_STC_GET_METER_FREEZE_DAY:
                dataStr = meterId;
                break;
            case CMD_ID_STC_READDATA:
                dataStr = meterId;
                break;
            case CMD_ID_CTS_UPLOADDATA:
                backCode = Integer.parseInt(dic.get(KEY_BACK_CODE).toString());
                dataStr =  StringTool.padLeft(backCode+"",4,'0') + meterId;
                requestResponseFlag = 0x01;
                break;
            case CMD_ID_STC_READ_HISTORY_DATA_DAY:
                dataStr = meterId;
                break;
            case CMD_ID_STC_READ_HISTORY_DATA_MONTH:
                dataStr = meterId;
                break;
            case CMD_ID_CTS_UPLOAD_METER_INFO:
                backCode = Integer.parseInt(dic.get(KEY_BACK_CODE).toString());
                dataStr =  StringTool.padLeft(backCode+"",4,'0') + meterId;
                requestResponseFlag = 0x01;
                break;
            case CMD_ID_CTS_UPLOAD_METER_RECORD_INFO:
                backCode = Integer.parseInt(dic.get(KEY_BACK_CODE).toString());
                dataStr =  StringTool.padLeft(backCode+"",4,'0') + meterId;
                requestResponseFlag = 0x01;
                break;
            case CMD_ID_CTS_UPLOAD_METER_GROUP_RECORD_INFO:
                backCode = Integer.parseInt(dic.get(KEY_BACK_CODE).toString());
                dataStr =  StringTool.padLeft(backCode+"",4,'0') + meterId;
                requestResponseFlag = 0x01;
                break;
            case CMD_ID_STC_CLS_VALVE:
                dataStr = meterId;
                break;
            case CMD_ID_STC_OPN_VALVE:
                dataStr = meterId;
                break;
            case CMD_ID_STC_GET_ICCID:
                dataStr = meterId;
                break;
            case CMD_ID_STC_GET_RSSI:
                dataStr = meterId;
                break;
            case CMD_ID_CTS_EXCEPTION_UPLOAD:
                backCode = Integer.parseInt(dic.get(KEY_BACK_CODE).toString());
                dataStr =  StringTool.padLeft(backCode+"",4,'0') + meterId;
                requestResponseFlag = 0x01;
                break;
            case CMD_ID_STC_EXCEPTION_CLEAR:
                dataStr = meterId;
                break;
            case CMD_ID_STC_SET_TIME:
                Date dateTime = (Date)dic.get(KEY_DATA_TIME);
                dataStr = meterId + Tools.getStringDateTime(dateTime);
                break;
            case CMD_ID_STC_SET_DATA:
                float flow = Float.parseFloat(dic.get(KEY_METER_VALUE).toString());
                dataStr = meterId + Tools.Bytes2HexString(meterFlowToBytes(flow),meterFlowToBytes(flow).length);
                break;
            case CMD_ID_STC_SET_METER_ID:
                String newMeterId = dic.get(KEY_NEW_METER_ID).toString();
                dataStr = meterId + newMeterId;
                break;
            case CMD_ID_STC_SET_ENABLE:
                dataStr = meterId + "5555";
                break;
        }

        String cmdStr = StringTool.padLeft(Integer.toHexString(HEAD),2,'0')
                + "0000"
                + StringTool.padLeft(cmdId+"",4,'0')
                + "00" //主站发出
                + StringTool.padLeft(Integer.toHexString(requestResponseFlag),2,'0')
                + meterId
                + messageId
                + StringTool.reversalHexString(StringTool.padLeft(Integer.toHexString(dataStr.length()/2),4,'0'))
                + dataStr
                + "0000"
                + StringTool.padLeft(Integer.toHexString(TAIL),2,'0');

        cmdStr = cmdStr.substring(0, 2) + StringTool.reversalHexString(StringTool.padLeft(Integer.toHexString(cmdStr.length()/2),4,'0')) +
                cmdStr.substring(6, cmdStr.length());

        byte[] cmdData = Tools.HexString2Bytes(cmdStr);
        int crc16Int = CRC16.calcCrc16(cmdData,0, cmdData.length - 3);
        byte[] crc16 = new byte[2];
        crc16[0] = (byte) (crc16Int%256);
        crc16[1] = (byte) (crc16Int/256);
        System.arraycopy(crc16, 0, cmdData, cmdData.length - 3, crc16.length);

        data = cmdData;
        return data;
    }

    public static HashMap<String, Object> ParseData(byte[] data)
    {
        HashMap<String,Object> map = new HashMap<String, Object>();

        if (data == null) return null;

        if ((data[0] & 0xff) == HEAD && (data[data.length-1] & 0xff) == TAIL)
        {
            byte[] dataCrcPart = new byte[data.length-1];
            System.arraycopy(data,0,dataCrcPart,0,dataCrcPart.length);
            if (CRC16.crc16Pass(dataCrcPart))
            {
                byte[] dataLength = new byte[2];
                System.arraycopy(data, 1, dataLength, 0, dataLength.length);
                if ((dataLength[0] & 0xff) + ((dataLength[1] & 0xff) << 8) != data.length)
                {
                    map.put(KEY_RESULT, -1);
                    map.put(KEY_ERR_MESSAGE, "长度错误");
                    return map;
                }

                byte[] dataIdBytes = new byte[2];
                System.arraycopy(data, 3, dataIdBytes, 0, dataIdBytes.length);
                int dataId = Integer.parseInt(Tools.Bytes2HexString(dataIdBytes,dataIdBytes.length));
                map.put(KEY_CMD_ID, dataId);
                byte[] clientId = new byte[7];
                System.arraycopy(data, 7, clientId, 0, clientId.length);
                map.put(KEY_CLIENT_ID, Tools.Bytes2HexString(clientId,clientId.length));
                byte[] messageId = new byte[7];
                System.arraycopy(data, 14, messageId, 0, messageId.length);
                map.put(KEY_MESSAGE_ID, Tools.Bytes2HexString(messageId,messageId.length));
                byte sendDirectionByte = data[5];
                map.put(KEY_SEND_DIRECTION,
                        sendDirectionByte == 0x00 ? SendDirection.ServerToClient : SendDirection.ClientToServer);
                byte requestFlagByte = data[6];
                map.put(KEY_REQUEST_FLAG, requestFlagByte == 0x00 ? RequestFlag.Request : RequestFlag.Response);

                byte[] meterId = new byte[7];
                byte[] backCode = new byte[2];

                int pos = 23;
                try
                {
                    switch (dataId)
                    {
                        case CMD_ID_STC_TEST:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode, backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_CTS_TEST:
                            pos = 23;
                            System.arraycopy(data, 23, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            pos += meterId.length;

                            byte[] simType = new byte[1];
                            System.arraycopy(data, pos, simType, 0, simType.length);
                            map.put(KEY_SIM_CARD_TYPE, simType[0]);
                            pos += simType.length;

                            byte[] iccID = new byte[10];
                            System.arraycopy(data, pos, iccID, 0, iccID.length);
                            map.put(KEY_ICCID, Tools.Bytes2HexString(iccID,iccID.length));

                            break;
                        case CMD_ID_CTS_HEARTBEAT:
                            System.arraycopy(data, 23, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_CTS_REGISTER:
                            System.arraycopy(data, 23, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_STC_READDATA:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId, meterId.length));

                            byte[] dayBytes = new byte[4];
                            System.arraycopy(data, 32, dayBytes, 0, dayBytes.length);
                            byte[] timeBytes = new byte[3];
                            System.arraycopy(data, 36, timeBytes, 0, timeBytes.length);
                            String dateTimeStr = Tools.Bytes2HexString(dayBytes, dayBytes.length) +
                                    Tools.Bytes2HexString(timeBytes,timeBytes.length);
                            //DateTime dateTime = new OccurrenceDateTime(1970, 1, 1);
                            Date dateTime = new Date();
                            try
                            {
                                dateTime = Tools.getDateTimeByString(dateTimeStr);
                            }
                            catch (Exception ex)
                            {
                                Log.i("test", "返回错误的时间:" + ex.getMessage());
                            }
                            map.put(KEY_DATA_TIME, dateTime);

                            byte[] floatPartBytes = new byte[2];
                            System.arraycopy(data, 39, floatPartBytes, 0, 2);
                            float floatPart = ((float) floatPartBytes[0] + (float) floatPartBytes[1]*256)/1000;
                            byte[] intPartBytes = new byte[4];
                            System.arraycopy(data, 41, intPartBytes, 0, 4);
                            int intPart = Tools.bytesToInt(intPartBytes);
                            float meterValue = intPart + floatPart;
                            map.put(KEY_METER_VALUE, meterValue);

                            byte stateByte = data[45];
                            switch (stateByte)
                            {
                                case 0x00:
                                    map.put(KEY_METER_VALVE_STATE, MeterStateConst.STATE_VALVE.CLOSE);
                                    break;
                                case 0x01:
                                    map.put(KEY_METER_VALVE_STATE, MeterStateConst.STATE_VALVE.OPEN);
                                    break;
                                case 0x02:
                                    map.put(KEY_METER_VALVE_STATE, MeterStateConst.STATE_VALVE.ERROR);
                                    break;
                            }
                            break;
                        case CMD_ID_CTS_UPLOADDATA:
                            System.arraycopy(data, 23, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));

                            byte[] dayBytes1 = new byte[4];
                            System.arraycopy(data, 30, dayBytes1, 0, dayBytes1.length);
                            byte[] timeBytes1 = new byte[3];
                            System.arraycopy(data, 34, timeBytes1, 0, timeBytes1.length);
                            String dateTimeStr1 = Tools.Bytes2HexString(dayBytes1,dayBytes1.length) +
                                    Tools.Bytes2HexString(timeBytes1,timeBytes1.length);
                            Date dateTime1 = Tools.getDateTimeByString(dateTimeStr1);
                            map.put(KEY_DATA_TIME, dateTime1);

                            byte[] floatPartBytes1 = new byte[2];
                            System.arraycopy(data, 37, floatPartBytes1, 0, 2);
                            float floatPart1 = ((float) floatPartBytes1[0] + (float) floatPartBytes1[1]*256)/1000;
                            byte[] intPartBytes1 = new byte[4];
                            System.arraycopy(data, 39, intPartBytes1, 0, 4);
                            int intPart1 = Tools.bytesToInt(intPartBytes1);
                            float meterValue1 = intPart1 + floatPart1;

                            map.put(KEY_METER_VALUE, meterValue1);

                            byte stateByte1 = data[43];
                            switch (stateByte1)
                            {
                                case 0x00:
                                    map.put(KEY_METER_VALVE_STATE, MeterStateConst.STATE_VALVE.CLOSE);
                                    break;
                                case 0x01:
                                    map.put(KEY_METER_VALVE_STATE, MeterStateConst.STATE_VALVE.OPEN);
                                    break;
                                case 0x02:
                                    map.put(KEY_METER_VALVE_STATE, MeterStateConst.STATE_VALVE.ERROR);
                                    break;
                            }
                            break;

                        case CMD_ID_STC_READ_HISTORY_DATA_DAY:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));
                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            byte[] lastFlagBytes = new byte[1];
                            System.arraycopy(data, 32, lastFlagBytes, 0, lastFlagBytes.length);
                            map.put(KEY_LAST_FLAG, lastFlagBytes[0] == 1 ? LastFlag.Last : LastFlag.NotLast);
                            byte[] frameSer = new byte[2];
                            System.arraycopy(data, 33, frameSer, 0, frameSer.length);
                            map.put(KEY_HIS_DATA_FRAME_SER, frameSer[0] + frameSer[1]*256);
                            byte[] frameDataCount = new byte[2];
                            System.arraycopy(data, 35, frameDataCount, 0, frameDataCount.length);
                            int count = frameDataCount[0] + frameDataCount[1]*256;
                            map.put(KEY_HIS_DATA_FRAME_DATA_COUNT, count);

                            int itemLength = 13;
                            byte[] dataFrame = new byte[count*itemLength];
                            System.arraycopy(data, 37, dataFrame, 0, dataFrame.length);


                            break;
                        case CMD_ID_STC_READ_HISTORY_DATA_MONTH:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));
                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_CTS_UPLOAD_METER_INFO:
                            break;
                        case CMD_ID_STC_SET_HEARTBEAT_CYCLE:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_STC_GET_HEARTBEAT_CYCLE:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));
                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            byte[] heartbeatCycle = new byte[2];
                            System.arraycopy(data, 32, heartbeatCycle, 0, heartbeatCycle.length);
                            map.put(KEY_HEARTBEAT_CYCLE, Integer.parseInt(StringTool.reversalHexString(Tools.Bytes2HexString(heartbeatCycle,heartbeatCycle.length)), 16));
                            break;
                        case CMD_ID_CTS_REQUST_TIME:
                            System.arraycopy(data, 23, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_STC_GET_TIME:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));

                            byte[] meterTime = new byte[7];
                            System.arraycopy(data, 32, meterTime, 0, meterTime.length);
                            map.put(KEY_METER_TIME, Tools.getDateTimeByString(Tools.Bytes2HexString(meterTime,meterTime.length)));
                            break;
                        case CMD_ID_STC_SET_SERVER_IP:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_STC_GET_SERVER_IP:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));

                            byte[] serverIp = new byte[6];
                            byte[] serverPort = new byte[3];
                            System.arraycopy(data, 32, serverIp, 0, serverIp.length);
                            System.arraycopy(data, 38, serverPort, 0, serverPort.length);
                            String ipByteStr = Tools.Bytes2HexString(serverIp,serverIp.length);
                            String ipStr = "";
                            for (int i = 0; i < 4; i++)
                            {
                                ipStr += ipByteStr.substring(i*3, i*3 + 3);
                                if (i < 3) ipStr += ".";
                            }
                            map.put(KEY_SERVER_IP, ipStr);
                            map.put(KEY_SERVER_PORT, Tools.Bytes2HexString(serverPort,serverPort.length));
                            break;
                        case CMD_ID_STC_SET_UPLOAD_CYCLE:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_STC_GET_UPLOAD_CYCLE:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));

                            byte[] uploadCycle = new byte[2];
                            System.arraycopy(data, 32, uploadCycle, 0, uploadCycle.length);
                            UploadCycleType type = UploadCycleType.Unknown;
                            switch (uploadCycle[1])
                            {
                                case 11:
                                    type = UploadCycleType.Day;
                                    break;
                                case 21:
                                    type = UploadCycleType.TenDay;
                                    break;
                                case 31:
                                    type = UploadCycleType.Month;
                                    break;
                                default:
                                    map.put(KEY_ERR_MESSAGE, "上传周期返回数据错误");
                                    break;
                            }

                            map.put(KEY_UPLOAD_CYCLE_TYPE, type);
                            break;
                        case CMD_ID_STC_SET_WAKEUP_UPLOAD_TIME:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_STC_GET_WAKEUP_UPLOAD_TIME:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));

                            byte[] wakeupTimeBytes = new byte[3];
                            System.arraycopy(data, 32, wakeupTimeBytes, 0, wakeupTimeBytes.length);
                            Date wakeupTime = Tools.getTimeByString(Tools.Bytes2HexString(wakeupTimeBytes,wakeupTimeBytes.length));
                            map.put(KEY_WAKEUP_TIME, wakeupTime);
                            map.put(KEY_WAKEUP_LONG, data[35] + (data[36] << 8));
                            map.put(KEY_WAKEUP_DELAY, data[37] + (data[38] << 8));
                            break;
                        case CMD_ID_STC_SET_METER_RECORD_FREQUENCY:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_STC_GET_METER_RECORD_FREQUENCY:
                            pos = 23;
                            System.arraycopy(data, pos, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));
                            pos += backCode.length;

                            System.arraycopy(data, pos, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            pos += meterId.length;

                            byte[] freqency = new byte[1];
                            System.arraycopy(data, pos, freqency, 0, freqency.length);
                            map.put(KEY_METER_RECORD_FREQUENCY, (int)freqency[0]);
                            pos += freqency.length;

                            break;
                        case CMD_ID_STC_SET_METER_FREEZE_DAY:
                            pos = 23;
                            System.arraycopy(data, pos, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));
                            pos += backCode.length;

                            System.arraycopy(data, pos, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            pos += meterId.length;
                            break;
                        case CMD_ID_STC_GET_METER_FREEZE_DAY:
                            pos = 23;
                            System.arraycopy(data, pos, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));
                            pos += backCode.length;

                            System.arraycopy(data, pos, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            pos += meterId.length;

                            byte[] freezeDayBytes = new byte[2];
                            System.arraycopy(data, pos, freezeDayBytes, 0, freezeDayBytes.length);
                            int freezeDayInt = Integer.parseInt(Tools.Bytes2HexString(freezeDayBytes,freezeDayBytes.length));
                            map.put(KEY_METER_FREEZE_DAY, freezeDayInt);
                            pos += freezeDayBytes.length;
                            break;
                        case CMD_ID_CTS_UPLOAD_METER_RECORD_INFO:
                            pos = 23;
                            System.arraycopy(data, pos, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId, meterId.length));
                            pos += meterId.length;

                            byte[] datetimeFreeze = new byte[7];
                            System.arraycopy(data, pos, datetimeFreeze, 0, datetimeFreeze.length);
                            dateTimeStr = Tools.Bytes2HexString(datetimeFreeze,datetimeFreeze.length);
                            dateTime = new Date();
                            try
                            {
                                dateTime = Tools.getDateTimeByString(dateTimeStr);
                            }
                            catch (Exception ex)
                            {
                                Log.i("test", "ParseData: " + ex.getMessage());
                            }
                            map.put(KEY_METER_FREEZE_TIME, dateTime);
                            pos += datetimeFreeze.length;


                            byte[] freezeFlowBytes = new byte[6];
                            System.arraycopy(data, pos, freezeFlowBytes, 0, freezeFlowBytes.length);
                            double flow = meterFlowBytesToDouble(freezeFlowBytes);
                            map.put(KEY_METER_FREEZE_FLOW, flow);
                            pos += freezeFlowBytes.length;


                            byte[] datetimeNowBytes = new byte[7];
                            System.arraycopy(data, pos, datetimeNowBytes, 0, datetimeNowBytes.length);
                            String dateTimeNowStr = Tools.Bytes2HexString(datetimeNowBytes,datetimeNowBytes.length);
                            Date dateTimeNow = new Date();
                            try
                            {
                                dateTimeNow = Tools.getDateTimeByString(dateTimeNowStr);
                            }
                            catch (Exception ex)
                            {
                                Log.i("te", "ParseData: " + ex.getMessage());
                            }
                            map.put(KEY_METER_TIME, dateTimeNow);
                            pos += datetimeNowBytes.length;


                            byte[] nowFlowBytes = new byte[6];
                            System.arraycopy(data, pos, nowFlowBytes, 0, nowFlowBytes.length);
                            double nowFlow = meterFlowBytesToDouble(nowFlowBytes);
                            map.put(KEY_METER_VALUE, nowFlow);
                            pos += nowFlowBytes.length;


                            byte[] stateBytes = new byte[1];
                            System.arraycopy(data,pos,stateBytes,0,stateBytes.length);
                            switch (stateBytes[0])
                            {
                                case 0x00:
                                    map.put(KEY_METER_VALVE_STATE, MeterStateConst.STATE_VALVE.CLOSE);
                                    break;
                                case 0x01:
                                    map.put(KEY_METER_VALVE_STATE, MeterStateConst.STATE_VALVE.OPEN);
                                    break;
                                case 0x02:
                                    map.put(KEY_METER_VALVE_STATE, MeterStateConst.STATE_VALVE.ERROR);
                                    break;
                            }
                            pos += stateBytes.length;

                            byte[] rssiBytes1 = new byte[1];
                            System.arraycopy(data, pos, rssiBytes1, 0, rssiBytes1.length);
                            map.put(KEY_METER_RSSI, Integer.parseInt(Tools.Bytes2HexString(rssiBytes1,rssiBytes1.length)));
                            pos++;

                            byte[] batteryVoltageBytes = new byte[2];
                            System.arraycopy(data,pos,batteryVoltageBytes,0,batteryVoltageBytes.length);
                            float batteryVoltage = batteryVoltageBytes[0]/100.0f + batteryVoltageBytes[1];
                            map.put(KEY_METER_BATTERY_VOL,batteryVoltage);
                            pos += batteryVoltageBytes.length;

                            break;
                        case CMD_ID_CTS_UPLOAD_METER_GROUP_RECORD_INFO:
                            pos = 23;
                            System.arraycopy(data, pos, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            pos += meterId.length;

                            byte[] valveStateBytes = new byte[1];
                            System.arraycopy(data, pos, valveStateBytes, 0, valveStateBytes.length);
                            switch (valveStateBytes[0])
                            {
                                case 0x00:
                                    map.put(KEY_METER_VALVE_STATE, MeterStateConst.STATE_VALVE.CLOSE);
                                    break;
                                case 0x01:
                                    map.put(KEY_METER_VALVE_STATE, MeterStateConst.STATE_VALVE.OPEN);
                                    break;
                                case 0x02:
                                    map.put(KEY_METER_VALVE_STATE, MeterStateConst.STATE_VALVE.ERROR);
                                    break;
                            }
                            pos += valveStateBytes.length;


                            byte[] rssiBytes2 = new byte[1];
                            System.arraycopy(data, pos, rssiBytes2, 0, rssiBytes2.length);
                            map.put(KEY_METER_RSSI, Integer.parseInt(Tools.Bytes2HexString(rssiBytes2,rssiBytes2.length)));
                            pos += rssiBytes2.length;

                            byte[] powerType = new byte[1];
                            System.arraycopy(data,pos,powerType,0,powerType.length);
                            map.put(KEY_METER_POWER_TYPE,powerType[0]);
                            pos += powerType.length;

                            byte[] batteryVoltageBytes1 = new byte[2];
                            System.arraycopy(data, pos, batteryVoltageBytes1, 0, batteryVoltageBytes1.length);
                            float batteryVoltage1 = batteryVoltageBytes1[0] / 100.0f + batteryVoltageBytes1[1];
                            map.put(KEY_METER_BATTERY_VOL, batteryVoltage1);
                            pos += batteryVoltageBytes1.length;

                            byte[] dateBytes1 = new byte[4];
                            System.arraycopy(data, pos, dateBytes1, 0, dateBytes1.length);
                            String dateStr = Tools.Bytes2HexString(dateBytes1,dateBytes1.length);
                            Date date = new Date();
                            try
                            {
                                date = Tools.getDateByString(dateStr);
                            }
                            catch (Exception ex)
                            {
                                Log.i("test", "ParseData: " + ex.getMessage());
                            }
                            map.put(KEY_METER_RECORD_DATE, date);
                            pos += dateBytes1.length;

                            byte[] dataDetailCountBytes1 = new byte[2];
                            System.arraycopy(data, pos, dataDetailCountBytes1, 0, dataDetailCountBytes1.length);
                            int dataDetailCount = dataDetailCountBytes1[0] + (dataDetailCountBytes1[1] << 8);
                            map.put(KEY_METER_DATA_DETAIL_COUNT, dataDetailCount);
                            pos += dataDetailCountBytes1.length;

                            ArrayList<HashMap<String,Object>> dataDetails = new ArrayList<HashMap<String, Object>>();
                            String dataDetailsStr = "";
                            for (int i = 0; i < dataDetailCount; i++)
                            {
                                HashMap<String, Object> dataDetail = new HashMap<String, Object>();
                                byte[] flowBytes = new byte[6];
                                System.arraycopy(data, pos, flowBytes, 0, flowBytes.length);
                                double flow1 = meterFlowBytesToDouble(flowBytes);
                                dataDetail.put(KEY_METER_VALUE, flow1);
                                pos += flowBytes.length;

                                byte[] timeBytes2 = new byte[2];
                                System.arraycopy(data, pos, timeBytes2, 0, timeBytes2.length);
                                String timeByte2Str = Tools.Bytes2HexString(timeBytes2,timeBytes2.length);
                                dataDetail.put(KEY_METER_TIME, timeByte2Str);
                                pos += timeBytes2.length;

                                dataDetails.add(dataDetail);
                                dataDetailsStr += flow1 + "-" + timeByte2Str;
                                if (i < dataDetailCount - 1)
                                {
                                    dataDetailsStr += "|";
                                }
                            }
                            map.put(KEY_METER_DATA_DETAILS, dataDetailsStr);

                            byte[] errDetailCountBytes = new byte[1];
                            System.arraycopy(data, pos, errDetailCountBytes, 0, errDetailCountBytes.length);
                            int errDetailCount = errDetailCountBytes[0];
                            map.put(KEY_METER_ERR_DETAIL_COUNT, dataDetailCount);
                            pos += errDetailCountBytes.length;

                            ArrayList<HashMap<String,Object>> errDetails = new ArrayList<HashMap<String, Object>>();
                            String errDetailsStr = "";
                            for (int i = 0; i < errDetailCount; i++)
                            {
                                HashMap<String, Object> errDetail = new HashMap<String, Object>();
                                byte[] dateTimeBytes = new byte[7];
                                System.arraycopy(data, pos, dateTimeBytes, 0, dateTimeBytes.length);
                                Date dateTime3 = Tools.getDateTimeByString(Tools.Bytes2HexString(dateTimeBytes,dateTimeBytes.length));
                                pos += dateTimeBytes.length;
                                errDetail.put(KEY_METER_ERR_TIME, dateTime3);

                                byte[] exceptionBytes = new byte[4];
                                System.arraycopy(data, pos, exceptionBytes, 0, exceptionBytes.length);
                                String str = Tools.Bytes2HexString(new byte[] { exceptionBytes[2], exceptionBytes[3] },2);
                                int errInt = Integer.parseInt(str);
                                pos += exceptionBytes.length;
                                errDetail.put(KEY_METER_ERR_CODE, errInt);

                                errDetails.add(errDetail);

                                errDetailsStr += Tools.Bytes2HexString(dateTimeBytes,dateTimeBytes.length) + "-" + str;
                                if (i < errDetailCount - 1)
                                {
                                    errDetailsStr += "|";
                                }
                            }
                            map.put(KEY_METER_ERR_DETAILS, errDetailsStr);

                            break;
                        case CMD_ID_CTS_METER_UPLOAD_PIC:

                            break;
                        case CMD_ID_STC_CLS_VALVE:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_STC_OPN_VALVE:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_CTS_EXCEPTION_UPLOAD:
                            System.arraycopy(data, 23, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId, meterId.length));

                            byte[] dateTimeBytes2 = new byte[7];
                            System.arraycopy(data, 30, dateTimeBytes2, 0, dateTimeBytes2.length);
                            String dateTimeStr2 = Tools.Bytes2HexString(dateTimeBytes2,dateTimeBytes2.length);
                            Date dateTime2 = Tools.getDateTimeByString(dateTimeStr2);
                            map.put(KEY_DATA_TIME, dateTime2);

                            byte[] exceptionTypeBytes = new byte[4];
                            System.arraycopy(data, 37, exceptionTypeBytes, 0, exceptionTypeBytes.length);
                            map.put(KEY_METER_EXCEPTION_TYPE,
                                    Integer.parseInt(Tools.Bytes2HexString(new byte[]{exceptionTypeBytes[2],exceptionTypeBytes[3]},2)));
                            break;
                        case CMD_ID_STC_SET_TIME:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_STC_SET_DATA:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_STC_SET_METER_ID:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_STC_SET_ENABLE:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_STC_EXCEPTION_CLEAR:
                            System.arraycopy(data, 23, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));

                            System.arraycopy(data, 25, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            break;
                        case CMD_ID_STC_GET_ICCID:
                            pos = 23;
                            System.arraycopy(data, pos, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));
                            pos += backCode.length;

                            System.arraycopy(data, pos, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId, meterId.length));
                            pos += meterId.length;

                            byte[] simtype = new byte[1];
                            System.arraycopy(data, pos, simtype, 0, simtype.length);
                            map.put(KEY_SIM_CARD_TYPE, simtype[0]);
                            pos += simtype.length;

                            byte[] iccid = new byte[10];
                            System.arraycopy(data, pos, iccid, 0, iccid.length);
                            map.put(KEY_ICCID, Tools.Bytes2HexString(iccid, iccid.length));
                            break;
                        case CMD_ID_STC_GET_RSSI:
                            pos = 23;
                            System.arraycopy(data, pos, backCode, 0, backCode.length);
                            map.put(KEY_BACK_CODE, Integer.parseInt(Tools.Bytes2HexString(backCode,backCode.length)));
                            pos += backCode.length;

                            System.arraycopy(data, pos, meterId, 0, meterId.length);
                            map.put(KEY_METER_ID, Tools.Bytes2HexString(meterId,meterId.length));
                            pos += meterId.length;

                            byte[] rssiBytes = new byte[1];
                            System.arraycopy(data, pos, rssiBytes, 0, rssiBytes.length);
                            pos += rssiBytes.length;
                            map.put(KEY_METER_RSSI, Integer.parseInt(Tools.Bytes2HexString(rssiBytes,rssiBytes.length)));

                            break;
                    }
                }
                catch (Exception ex)
                {
                    map.put(KEY_RESULT, -1);
                    map.put(KEY_ERR_MESSAGE, "数据解析失败：" + ex.getMessage());
                    return map;
                }
            }
            else
            {
                map.put(KEY_RESULT, -1);
                map.put(KEY_ERR_MESSAGE, "校验错误");
                return map;
            }
        }
        else
        {
            map.put(KEY_RESULT, -1);
            map.put(KEY_ERR_MESSAGE, "数据错误");
            return map;
        }

        return map;
    }


    /// <summary>
    /// 流量转换为需要的格式  四字节整数部分，两字节小数部分，三位小数
    /// </summary>
    /// <param name="flow"></param>
    /// <returns></returns>
    public static byte[] meterFlowToBytes(float flow)
    {
            /*--2byte小数--/--4byte整数--*/  /*HEX*/ /*三位小数*/

        String flowStr = Float.toString(flow);
        String[] strings = flowStr.split("\\.");

        int flowInt = Integer.parseInt(strings[0]);
        int flowDec = Integer.parseInt(strings[1]);

        String str = Tools.Bytes2HexString(Tools.intToByte(flowDec), Tools.intToByte(flowDec).length).substring(0, 4) + Tools.Bytes2HexString(Tools.intToByte(flowInt),Tools.intToByte(flowInt).length);
        byte[] result = Tools.HexString2Bytes(str);

        return result;
    }

    /// <summary>
    /// 流量转换为需要的格式  四字节整数部分，两字节小数部分，三位小数
    /// </summary>
    /// <param name="flowBytes"></param>
    /// <returns></returns>
    public static double meterFlowBytesToDouble(byte[] flowBytes)
    {
            /*--2byte小数--/--4byte整数--*/
            /*HEX*/
            /*三位小数*/
        byte[] intPartBytes = new byte[4];
        System.arraycopy(flowBytes,2,intPartBytes,0,intPartBytes.length);
        int intPart = intPartBytes[0] + (intPartBytes[1] << 8) + (intPartBytes[2] << 16) + (intPartBytes[3] << 24);

        byte[] decPartBytes = new byte[2];
        System.arraycopy(flowBytes, 0, decPartBytes, 0, decPartBytes.length);
        int decPart = decPartBytes[0] + (decPartBytes[1] << 8);

        double result = intPart + ((double) decPart)/1000.0;
        return result;
    }



    public final static String KEY_METER_ID = "KEY_METER_ID";
    public final static String KEY_NEW_METER_ID = "KEY_NEW_METER_ID";
    public final static String KEY_CLIENT_ID = "KEY_CLIENT_ID";
    public final static String KEY_CMD_ID = "KEY_CMD_ID";
    public final static String KEY_SEND_DIRECTION = "KEY_SEND_DIRECTION";
    public final static String KEY_REQUEST_FLAG = "KEY_REQUEST_FLAG";
    public final static String KEY_MESSAGE_ID = "KEY_MESSAGE_ID";
    public final static String KEY_BACK_CODE = "KEY_BACK_CODE";
    public final static String KEY_RESULT = "KEY_RESULT";
    public final static String KEY_ERR_MESSAGE = "KEY_ERR_MESSAGE";
    public final static String KEY_DATA_TIME = "KEY_DATA_TIME";
    public final static String KEY_METER_VALUE = "KEY_METER_VALUE";
    public final static String KEY_METER_VALVE_STATE = "KEY_METER_VALVE_STATE";
    public final static String KEY_METER_EXCEPTION_TYPE = "KEY_METER_EXCEPTION_TYPE";
    public final static String KEY_SIM_CARD_TYPE = "KEY_SIM_CARD_TYPE";
    public final static String KEY_ICCID = "KEY_ICCID";

    public final static String KEY_HEARTBEAT_CYCLE = "KEY_HEARTBEAT_CYCLE";
    public final static String KEY_METER_TIME = "KEY_METER_TIME";
    public final static String KEY_SERVER_IP = "KEY_SERVER_IP";
    public final static String KEY_SERVER_PORT = "KEY_SERVER_PORT";
    public final static String KEY_UPLOAD_CYCLE_TYPE = "KEY_UPLOAD_CYCLE_TYPE";
    public final static String KEY_WAKEUP_TIME = "KEY_WAKEUP_TIME";
    public final static String KEY_WAKEUP_LONG = "KEY_WAKEUP_LONG";
    public final static String KEY_WAKEUP_DELAY = "KEY_WAKEUP_DELAY";

    public final static String KEY_METER_RECORD_FREQUENCY = "KEY_METER_RECORD_FREQUENCY";
    public final static String KEY_METER_FREEZE_DAY = "KEY_METER_FREEZE_DAY";
    public final static String KEY_METER_FREEZE_TIME = "KEY_METER_FREEZE_TIME";
    public final static String KEY_METER_FREEZE_FLOW = "KEY_METER_FREEZE_FLOW";


    public final static String KEY_LAST_FLAG = "KEY_LAST_FLAG";
    public final static String KEY_HIS_DATA_FRAME_SER = "KEY_HIS_DATA_FRAME_SER";
    public final static String KEY_HIS_DATA_FRAME_DATA_COUNT = "KEY_HIS_DATA_FRAME_DATA_COUNT";
    public final static String KEY_HIS_DATA_FRAME_DATAS = "KEY_HIS_DATA_FRAME_DATAS";

    public final static String KEY_METER_INFO = "KEY_METER_INFO";

    public final static String KEY_METER_RSSI = "KEY_METER_RSSI";
    public final static String KEY_METER_BATTERY_VOL = "KEY_METER_BATTERY_VOL";
    public final static String KEY_METER_POWER_TYPE = "KEY_METER_POWER_TYPE";
    public final static String KEY_METER_RECORD_DATE = "KEY_METER_RECORD_DATE";
    public final static String KEY_METER_DATA_DETAIL_COUNT = "KEY_METER_DATA_DETAIL_COUNT";
    public final static String KEY_METER_DATA_DETAILS = "KEY_METER_DATA_DETAILS";
    public final static String KEY_METER_ERR_DETAIL_COUNT = "KEY_METER_ERR_DETAIL_COUNT";
    public final static String KEY_METER_ERR_DETAILS = "KEY_METER_ERR_DETAILS";
    public final static String KEY_METER_ERR_TIME = "KEY_METER_ERR_TIME";
    public final static String KEY_METER_ERR_CODE = "KEY_METER_ERR_CODE";

    public final static String DATE_FORMAT = "yyyyMMdd";
    public final static String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    public final static String TIME_FORMAT = "HHmmss";

    private final static String UPLOAD_CYCLE_TYPE_DAY = "11";//每天上报
    private final static String UPLOAD_CYCLE_TYPE_TEN_DAY = "21";//每旬上报
    private final static String UPLOAD_CYCLE_TYPE_MONTH = "31";//每月上报




    public enum UploadCycleType
    {
        Day,
        TenDay,
        Month,
        Unknown
    }

    public enum SendDirection
    {
        ServerToClient,
        ClientToServer
    }

    public enum RequestFlag
    {
        Request,
        Response
    }

    public enum LastFlag
    {
        NotLast,
        Last
    }

}
