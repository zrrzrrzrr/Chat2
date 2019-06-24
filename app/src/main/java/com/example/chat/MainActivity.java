package com.example.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.editor.SDKManager;
import com.example.chat.adapters.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTxtShow;
    private TextView mTxtSource;
    private RecyclerView recyclerView;
    private List<String> msgs;
//    private List<String> list;
    private ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTxtShow = (TextView) findViewById(R.id.txt_show);
        mTxtSource = (TextView) findViewById(R.id.txt_source);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        msgs = new ArrayList<>();
        StringBuilder msg = new StringBuilder();
        msg.append("this is a demo,you can input the face keyword in the FaceManager.java .It looks like").append(" [得意][偷笑][擦汗][酷]");
        msg.append("and so on .");
        msgs.add(msg.toString());
        msgs.add("你好！");
        msgs.add("[微笑][激动]");
        msgs.add("Long live the People's Republic of China！[微笑]");
        msgs.add("Talk is cheap,show me the code.");
        msgs.add("[拥抱]");
        msgs.add("[闭嘴]");
        msgs.add("[乒乓球]");
        msgs.add("Talk is cheap,show me the code.");

        chatAdapter = new ChatAdapter(this,msgs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        SmileyParser parser = new SmileyParser(this);
        String source = "秋风";
        mTxtShow.setText("收信人："+source);
        String content = "[机器人]";
        mTxtSource.setText(parser.replace("发送人："+content));

        String sdk = SDKManager.initSDK();
        Toast.makeText(this,sdk,Toast.LENGTH_LONG).show();
    }
}
