package com.example.uart;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.gprsmeter.Cmd;
import com.example.gprsmeter.Const;
import com.example.gprsmeter.GprsMeterOperation;
import com.example.uartdemo.StringTool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GprsSettingsActivity extends Activity {

    GprsMeterOperation gprsMeterOperation;

    @BindView(R.id.meterIdEditText)
    EditText meterIdEditText;
    @BindView(R.id.meterIdSetButton)
    Button meterIdSetButton;
    @BindView(R.id.ipEditText)
    EditText ipEditText;
    @BindView(R.id.portEditText)
    EditText portEditText;
    @BindView(R.id.ipSetButton)
    Button ipSetButton;
    @BindView(R.id.heartbeatEditText)
    EditText heartbeatEditText;
    @BindView(R.id.heartbeatSetButton)
    Button heartbeatSetButton;
    @BindView(R.id.timeTextView)
    TextView timeTextView;
    @BindView(R.id.dateTextView)
    TextView dateTextView;
    @BindView(R.id.timeSetButton)
    Button timeSetButton;
    @BindView(R.id.dayRadioButton)
    RadioButton dayRadioButton;
    @BindView(R.id.tenDayRadioButton)
    RadioButton tenDayRadioButton;
    @BindView(R.id.monthRadioButton)
    RadioButton monthRadioButton;
    @BindView(R.id.uploadCircleSetButton)
    Button uploadCircleSetButton;
    @BindView(R.id.awakeTimeTextView)
    TextView awakeTimeTextView;
    @BindView(R.id.wakeupDuringTimeEditText)
    EditText wakeupDuringTimeEditText;
    @BindView(R.id.lastedTimeEditText)
    EditText lastedTimeEditText;
    @BindView(R.id.awakeUploadTimeSetButton)
    Button awakeUploadTimeSetButton;
    @BindView(R.id.flowEditText)
    EditText flowEditText;
    @BindView(R.id.flowSetButton)
    Button flowSetButton;
    @BindView(R.id.frequencySpinner)
    Spinner frequencySpinner;
    @BindView(R.id.frequencySetButton)
    Button frequencySetButton;
    @BindView(R.id.freezeDaySpinner)
    Spinner freezeDaySpinner;
    @BindView(R.id.freezeDayButton)
    Button freezeDayButton;
    @BindView(R.id.setMeterEnableButton)
    Button setMeterEnableButton;
    @BindView(R.id.oneKeySetButton)
    Button oneKeySetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gprs_settings);

        ButterKnife.bind(this);

        gprsMeterOperation = new GprsMeterOperation();

        initView();
    }

    private void initView() {
        List<String> frequencyListStr = new ArrayList<String>();
        frequencyListStr.add("0");
        frequencyListStr.add("1");
        frequencyListStr.add("2");
        frequencyListStr.add("3");

        List<String> freezeDayListStr = new ArrayList<String>();
        for (int i = 1; i < 29; i++) {
            freezeDayListStr.add(i + "");
        }

        ArrayAdapter frequencyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, frequencyListStr);
        frequencySpinner.setAdapter(frequencyAdapter);

        ArrayAdapter freezeDayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, freezeDayListStr);
        freezeDaySpinner.setAdapter(freezeDayAdapter);

        updateTimeInView(new Date());


    }

    private void updateTimeInView(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateTextView.setText(simpleDateFormat.format(date));

        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
        timeTextView.setText(simpleTimeFormat.format(date));
    }

    @OnClick({R.id.meterIdSetButton, R.id.ipSetButton, R.id.heartbeatSetButton,
            R.id.dateTextView, R.id.timeTextView, R.id.dayRadioButton, R.id.tenDayRadioButton,
            R.id.monthRadioButton, R.id.uploadCircleSetButton, R.id.awakeTimeTextView,
            R.id.awakeUploadTimeSetButton, R.id.flowSetButton, R.id.frequencySetButton,
            R.id.freezeDayButton, R.id.setMeterEnableButton, R.id.timeSetButton,
            R.id.oneKeySetButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.meterIdSetButton:
                setMeterId();
                break;
            case R.id.ipSetButton:
                setIp();
                break;
            case R.id.heartbeatSetButton:
                setHeartbeat();
                break;
            case R.id.dateTextView:
                chooseDate();
                break;
            case R.id.timeTextView:
                chooseTime();
                break;
            case R.id.timeSetButton:
                setTime();
                break;
            case R.id.dayRadioButton:
                break;
            case R.id.tenDayRadioButton:
                break;
            case R.id.monthRadioButton:
                break;
            case R.id.uploadCircleSetButton:
                setUploadCircle();
                break;
            case R.id.awakeTimeTextView:
                chooseAwakeTime();
                break;
            case R.id.awakeUploadTimeSetButton:
                setAwakeUploadTime();
                break;
            case R.id.flowSetButton:
                setFlow();
                break;
            case R.id.frequencySetButton:
                setFrequency();
                break;
            case R.id.freezeDayButton:
                setFreezeDay();
                break;
            case R.id.setMeterEnableButton:
                setMeterEnable();
                break;
            case R.id.oneKeySetButton:
                oneKeySet();
                break;
        }
    }


    private void setMeterId() {
        String newMeterId = meterIdEditText.getText().toString();
        String meterId = Const.METER_ID_AAAAAAAAAAAAAA;

        HashMap<String, Object> result = gprsMeterOperation.setNewMeterId(meterId, newMeterId);

        showResult(result);
    }

    private void setIp() {
        String meterId = meterIdEditText.getText().toString();
        String serverIp = ipEditText.getText().toString();
        int port = Integer.parseInt(portEditText.getText().toString());

        HashMap<String, Object> result = gprsMeterOperation.writeServerIp(meterId, serverIp, port);

        showResult(result);
    }


    private void setHeartbeat() {
        String meterId = meterIdEditText.getText().toString();
        int second = Integer.parseInt(heartbeatEditText.getText().toString());

        HashMap<String, Object> result = gprsMeterOperation.writeHeartbeatCycle(meterId, second);

        showResult(result);
    }

    private void chooseDate() {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateTextView.setText(StringTool.padLeft((year + ""), 4, '0') + "-"
                        + StringTool.padLeft((monthOfYear + ""), 2, '0') + "-"
                        + StringTool.padLeft((dayOfMonth + ""), 2, '0'));
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE)).show();
    }


    private void setTime() {
        String meterId = meterIdEditText.getText().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTextView.getText().toString() + " " + timeTextView.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date == null) return;
        HashMap<String, Object> result = gprsMeterOperation.writeTime(meterId, date);

        showResult(result);
    }


    private void setUploadCircle() {
        String meterId = meterIdEditText.getText().toString();

        Cmd.UploadCycleType uploadCycleType = Cmd.UploadCycleType.Day;
        if (dayRadioButton.isChecked()) {
            uploadCycleType = Cmd.UploadCycleType.Day;
        } else if (tenDayRadioButton.isChecked()) {
            uploadCycleType = Cmd.UploadCycleType.TenDay;
        } else if (monthRadioButton.isChecked()) {
            uploadCycleType = Cmd.UploadCycleType.Month;
        }

        HashMap<String, Object> result = gprsMeterOperation.writeUploadCycle(meterId, uploadCycleType);

        showResult(result);
    }

    private void chooseTime() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeTextView.setText(StringTool.padLeft((hourOfDay + ""), 2, '0') + ":" + StringTool.padLeft(minute + "", 2, '0') + ":00");
            }
        }, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), true).show();
    }

    private void chooseAwakeTime() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                awakeTimeTextView.setText(StringTool.padLeft((hourOfDay + ""), 2, '0') + ":" + StringTool.padLeft(minute + "", 2, '0') + ":00");
            }
        }, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), true).show();
    }

    private void setAwakeUploadTime() {
        String meterId = meterIdEditText.getText().toString();

        String dateTimeStr = dateTextView.getText().toString() + " " + timeTextView.getText().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTime = null;
        try {
            dateTime = simpleDateFormat.parse(dateTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dateTime == null) return;

        int wakeupLong = Integer.parseInt(wakeupDuringTimeEditText.getText().toString());
        int delay = Integer.parseInt(lastedTimeEditText.getText().toString());

        HashMap<String, Object> result = gprsMeterOperation.writeWakeupUploadTime(meterId, dateTime, wakeupLong, delay);

        showResult(result);
    }

    private void setFlow() {
        String meterId = meterIdEditText.getText().toString();

        float flow = Float.parseFloat(flowEditText.getText().toString());

        HashMap<String, Object> result = gprsMeterOperation.setMeterValue(meterId, flow);

        showResult(result);
    }

    private void setFrequency() {
        String meterId = meterIdEditText.getText().toString();

        int frequency = 0;

        frequency = frequencySpinner.getSelectedItemPosition();

        HashMap<String, Object> result = gprsMeterOperation.setMeterFreqency(meterId, frequency);

        showResult(result);
    }

    private void setFreezeDay() {
        String meterId = meterIdEditText.getText().toString();

        int freezeDay = 1;//1-28

        freezeDay = freezeDaySpinner.getSelectedItemPosition();

        HashMap<String, Object> result = gprsMeterOperation.setMeterFreezeDay(meterId, freezeDay);

        showResult(result);
    }

    private void setMeterEnable() {
        String meterId = meterIdEditText.getText().toString();

        HashMap<String, Object> result = gprsMeterOperation.setMeterEnable(meterId);

        showResult(result);
    }

    private void readMeterData() {
        String meterId = meterIdEditText.getText().toString();

        HashMap<String, Object> result = gprsMeterOperation.readMeterData(meterId);

        showResult(result);
    }

    /**
     * 重燃一键设置  写ID  写时钟   抄表   读ICCID(掌机上不用记录，省去该步骤)   出厂启用
     */
    private void oneKeySet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                setMeterId();
                setTime();
                readMeterData();
                setMeterEnable();
            }
        }).start();
    }

    private void showResult(final HashMap<String, Object> result) {
        final Context context = this;

        if (result != null && result.containsKey(Cmd.KEY_BACK_CODE)) {
            String str = "";
            String logStr = "";
            switch (Integer.parseInt(result.get(Cmd.KEY_CMD_ID).toString())){
                case Const.CMD_ID_STC_READDATA:
                    logStr += "-抄表";
                    logStr += result.get(Cmd.KEY_METER_VALUE).toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            flowEditText.setText(result.get(Cmd.KEY_METER_VALUE).toString());
                        }
                    });
                    break;
                case Const.CMD_ID_STC_SET_METER_ID:
                    logStr += "-写表ID";
                    break;
                case Const.CMD_ID_STC_SET_TIME:
                    logStr += "-写表时钟";
                    break;
                case Const.CMD_ID_STC_SET_UPLOAD_CYCLE:
                    logStr += "-设置上报周期";
                    break;
                case Const.CMD_ID_STC_SET_WAKEUP_UPLOAD_TIME:
                    logStr += "-设置自醒上报时间";
                    break;
                case Const.CMD_ID_STC_SET_DATA:
                    logStr += "-设置表底数";
                    break;
                case Const.CMD_ID_STC_SET_METER_RECORD_FREQUENCY:
                    logStr += "-设置采集频率";
                    break;
                case Const.CMD_ID_STC_SET_METER_FREEZE_DAY:
                    logStr += "-设置冻结日";
                    break;
            }

            if (result.get(Cmd.KEY_BACK_CODE).equals(Const.BACK_CODE_SUCCESS)) {

                str += "成功" + logStr;
            } else {
                str += "失败" + result.get(Cmd.KEY_BACK_CODE);
            }

            final String finalStr = str;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, finalStr, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "失败-超时", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
