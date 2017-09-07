package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parse.Cmd;
import com.example.parse.Const;
import com.example.parse.ParseData;
import com.example.thirdclass.svprogresshud.SVProgressHUD;
import com.example.uart.R;
import com.example.uartdemo.SerialPortTool;
import com.example.uartdemo.Tools;

import java.io.IOException;
import java.util.HashMap;

public class SetPriceActivity extends Activity implements View.OnClickListener {
    /**
     * UI
     */
    EditText editTextInfo;
    EditText meterIdEditText;
    EditText price1EditText;
    EditText amount1EditText;
    EditText price2EditText;
    EditText amount2EditText;
    EditText price3EditText;
    EditText enableDateEditText;

    EditText userIdEditText;
    EditText balanceEditText;
    EditText totalUseEditText;
    EditText meterStateEditText;

    Button readPriceListBtn;
    Button writePriceListBtn;
    Button readUserInfoBtn;

    /**
     * 发送的命令
     */
    byte[] cmd;

    /**
     * 串口工具
     */
    SerialPortTool serialPortTool;

    Handler handler = new Handler();

    int timeout = 15000;//skyshoot模块 中继模式下时间比较长 设为26s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_price);

        initView();

        openCommPort();
    }

    /**
     * 打开串口
     */
    private void openCommPort() {
        try {
            serialPortTool = new SerialPortTool(SerialPortTool.HANDLE_COMM_PORT,SerialPortTool.HANDLE_COMM_PORT_BR);
            serialPortTool.openPort(SerialPortTool.PowerLevel.POWER_RFID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if(serialPortTool!= null){
            serialPortTool.closePort(SerialPortTool.PowerLevel.POWER_RFID);
            serialPortTool = null;
        }
        super.onDestroy();
    }

    private void initView() {

        editTextInfo = (EditText) findViewById(R.id.editTextInfo);
        meterIdEditText = (EditText) findViewById(R.id.meterIdEditText);
        price1EditText = (EditText) findViewById(R.id.price1EditText);
        amount1EditText = (EditText) findViewById(R.id.amount1EditText);
        price2EditText = (EditText) findViewById(R.id.price2EditText);
        amount2EditText = (EditText) findViewById(R.id.amount2EditText);
        price3EditText = (EditText) findViewById(R.id.price3EditText);
        enableDateEditText = (EditText) findViewById(R.id.enableDateEditText);
        readPriceListBtn = (Button) findViewById(R.id.readPriceListBtn);
        writePriceListBtn = (Button) findViewById(R.id.writePriceListBtn);
        readUserInfoBtn = (Button) findViewById(R.id.readUserInfoBtn);

        userIdEditText = (EditText)findViewById(R.id.userIdEditText);
        balanceEditText = (EditText)findViewById(R.id.balanceEditText);
        totalUseEditText = (EditText)findViewById(R.id.totalUseEditText);
        meterStateEditText = (EditText)findViewById(R.id.meterStateEditText);

        readPriceListBtn.setOnClickListener(this);
        writePriceListBtn.setOnClickListener(this);
        readUserInfoBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        String meterAddressText = meterIdEditText.getText().toString();
        if (meterAddressText.length() != 14) {
            Toast.makeText(getApplicationContext(), "气表地址应为 14 位BCD码", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            map.put(Cmd.KEY_NODE_ID, Long.parseLong(meterAddressText.substring(8, 14), 16));
            map.put(Cmd.KEY_METER_ID, meterAddressText);
            map.put(Cmd.KEY_RELAY_ID, Const.rf_relay_id);
        }catch (Exception ex){
            Toast.makeText(getApplication(),"输入错误",Toast.LENGTH_SHORT).show();
            return;
        }

        final int id = view.getId();
        switch (id){
            case R.id.readUserInfoBtn:
                map.put(Cmd.KEY_DATA_TYPE,Const.DATAID_READ_USER_METER_DATA);
                break;
            case R.id.readPriceListBtn:
                map.put(Cmd.KEY_DATA_TYPE,Const.DATAID_READ_PRICE_LIST);
                break;
            case R.id.writePriceListBtn:
                map.put(Cmd.KEY_DATA_TYPE,Const.DATAID_WRITE_PRICE_LIST);
                try {
                    float price1 = Float.parseFloat(price1EditText.getText().toString());
                    int amount1 = Integer.parseInt(amount1EditText.getText().toString());
                    float price2 = Float.parseFloat(price2EditText.getText().toString());
                    int amount2 = Integer.parseInt(amount2EditText.getText().toString());
                    float price3 = Float.parseFloat(price3EditText.getText().toString());
                    int enableDate = Integer.parseInt(enableDateEditText.getText().toString());

                    map.put(Cmd.KEY_PRICE_1,price1);
                    map.put(Cmd.KEY_AMOUNT_1,amount1);
                    map.put(Cmd.KEY_PRICE_2,price2);
                    map.put(Cmd.KEY_AMOUNT_2,amount2);
                    map.put(Cmd.KEY_PRICE_3,price3);
                    map.put(Cmd.KEY_PRICE_ENABLE_DATE,enableDate);

                }catch (Exception e){
                    Toast.makeText(getApplication(),"输入错误",Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }

        cmd = Cmd.AssembleCmd(map);
        editTextInfo.append("\nSEND----" + Tools.Bytes2HexString(cmd, cmd.length));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    switch (Const.rf_transmission_type){
                        case RF_TRANSMISSION_TYPE_NO_RELAY:
                            timeout = 15000;
                            break;
                        case RF_TRANSMISSION_TYPE_RELAY:
                            timeout = 26000;
                            break;
                    }

                    final byte[] revdata = serialPortTool.sendAndRevData(cmd, timeout);

                    if (revdata == null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                dismissSVProgressHUD();
                                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            editTextInfo.append("\nRECV----" + Tools.Bytes2HexString(revdata, revdata.length));
                        }
                    });


                    final HashMap resultmap = ParseData.ParseDataToMap(revdata);

                    if (resultmap.size() != 0) {
                        if (resultmap.get(Cmd.KEY_ERR_MESSAGE) != null) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), resultmap.get(Cmd.KEY_ERR_MESSAGE).toString(), Toast.LENGTH_SHORT).show();
                                    dismissSVProgressHUD();
                                }
                            });
                        } else {
                            if (id == readPriceListBtn.getId()) {
                                Log.i("wdj", "readValueBtn Clicked!");
                                final String price1str = resultmap.get(Cmd.KEY_PRICE_1).toString();
                                final String amount1str = resultmap.get(Cmd.KEY_AMOUNT_1).toString();
                                final String price2str = resultmap.get(Cmd.KEY_PRICE_2).toString();
                                final String amount2str = resultmap.get(Cmd.KEY_AMOUNT_2).toString();
                                final String price3str = resultmap.get(Cmd.KEY_PRICE_3).toString();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        price1EditText.setText(price1str);
                                        amount1EditText.setText(amount1str);
                                        price2EditText.setText(price2str);
                                        amount2EditText.setText(amount2str);
                                        price3EditText.setText(price3str);
                                    }
                                });
                            } else if (id == writePriceListBtn.getId()) {

                            } else if(id == readUserInfoBtn.getId()){
                                final String userId = resultmap.get(Cmd.KEY_USER_ID).toString();
                                final String balance = resultmap.get(Cmd.KEY_BALANCE).toString();
                                final String totalUse = resultmap.get(Cmd.KEY_TOTAL_USE).toString();
                                final String valvestatestr = "阀门:" + resultmap.get(Cmd.KEY_VALVE_STATE).toString();
                                final String battarystatestr = "|3.6V:" + resultmap.get(Cmd.KEY_BATTERY_3_6_STATE).toString() + "|6V:" + resultmap.get(Cmd.KEY_BATTERY_6_STATE).toString();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        userIdEditText.setText(userId);
                                        balanceEditText.setText(balance);
                                        totalUseEditText.setText(totalUse);
                                        meterStateEditText.setText(valvestatestr + battarystatestr);
                                    }
                                });

                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    String result = resultmap.get(Cmd.KEY_RESULT).toString();
                                    if (result.equals("1")) {
                                        Toast.makeText(getApplication(), "成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplication(), "失败", Toast.LENGTH_SHORT).show();
                                    }
                                    dismissSVProgressHUD();
                                }
                            });
                        }
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "map.size=0", Toast.LENGTH_SHORT).show();
                                dismissSVProgressHUD();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dismissSVProgressHUD();
                    }
                });
            }
        }).start();
    }

    private void showSVProgressHUD(){
        SVProgressHUD.show(this);
    }
    private void dismissSVProgressHUD(){
        SVProgressHUD.dismiss(this);
    }

    private void readPrice(HashMap<String,Object> map){
        cmd = Cmd.AssembleCmd(map);
        try {
            byte[] recv = serialPortTool.sendAndRevData(cmd,15000);
            HashMap<String, Object> result = ParseData.ParseDataToMap(recv);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void writePrice(HashMap<String,Object> map){
        cmd = Cmd.AssembleCmd(map);
        try {
            byte[] recv = serialPortTool.sendAndRevData(cmd, 15000);
            if(recv != null) {
                HashMap<String, Object> result = ParseData.ParseDataToMap(recv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
