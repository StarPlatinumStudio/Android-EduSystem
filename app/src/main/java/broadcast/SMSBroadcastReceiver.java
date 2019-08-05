package broadcast;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.pockettutorms.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
public class SMSBroadcastReceiver extends BroadcastReceiver {

    private static final String action = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(action)) {
            Object[] pduses = (Object[]) intent.getExtras().get("pdus");
            String mobile = "";
            String content = "";
            String time = "";
            System.out.println(pduses.length);
            for (Object pdus : pduses) {
                byte[] pdusmessage = (byte[]) pdus;
                SmsMessage sms = SmsMessage.createFromPdu(pdusmessage);
                mobile = sms.getOriginatingAddress();// 发送短信的手机号码
                content = sms.getMessageBody(); // 短信内容
                Date date = new Date(sms.getTimestampMillis());// 发送日                期
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                time = format.format(date); // 得到发送时间
            }
            Toast.makeText(context, context.getResources().getString(R.string.app_name), Toast.LENGTH_LONG).show();
            String show = "发送者:" + mobile + " 内容:" + content + "      时间:" + time;
            Toast.makeText(context, show, Toast.LENGTH_LONG).show();
        }
        Log.e("TAG","拦截短信");
        abortBroadcast();
    }

}