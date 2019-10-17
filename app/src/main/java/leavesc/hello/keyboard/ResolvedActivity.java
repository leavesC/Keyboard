package leavesc.hello.keyboard;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import leavesc.hello.keyboard.common.Message;
import leavesc.hello.keyboard.common.MessageAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ResolvedActivity extends AppCompatActivity {

    private EmojiKeyboard emojiKeyboard;

    private final String TAG = "ResolvedActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(leavesc.hello.keyboard.R.layout.activity_resolved);
        initView();
    }

    private void initView() {
        RecyclerView rv_messageList = (RecyclerView) findViewById(leavesc.hello.keyboard.R.id.rv_messageList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rv_messageList.setLayoutManager(linearLayoutManager);
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("1"));
        messageList.add(new Message("2"));
        messageList.add(new Message("3"));
        messageList.add(new Message("4"));
        messageList.add(new Message("5"));
        messageList.add(new Message("6"));
        messageList.add(new Message("7"));
        messageList.add(new Message("8"));
        messageList.add(new Message("9"));
        messageList.add(new Message("10"));
        messageList.add(new Message("11"));
        messageList.add(new Message("12"));
        messageList.add(new Message("13"));
        messageList.add(new Message("14"));
        messageList.add(new Message("15"));
        messageList.add(new Message("16"));
        messageList.add(new Message("17"));
        messageList.add(new Message("18"));
        messageList.add(new Message("19"));
        messageList.add(new Message("20"));
        MessageAdapter messageAdapter = new MessageAdapter(this, messageList, leavesc.hello.keyboard.R.layout.item_message);
        rv_messageList.setAdapter(messageAdapter);

        EditText et_inputMessage = (EditText) findViewById(leavesc.hello.keyboard.R.id.et_inputMessage);
        ImageView iv_more = (ImageView) findViewById(leavesc.hello.keyboard.R.id.iv_more);
        final LinearLayout ll_rootEmojiPanel = (LinearLayout) findViewById(leavesc.hello.keyboard.R.id.ll_rootEmojiPanel);
        emojiKeyboard = new EmojiKeyboard(this, et_inputMessage, ll_rootEmojiPanel, iv_more, rv_messageList);
        emojiKeyboard.setEmoticonPanelVisibilityChangeListener(new EmojiKeyboard.OnEmojiPanelVisibilityChangeListener() {
            @Override
            public void onShowEmojiPanel() {
                Log.e(TAG, "onShowEmojiPanel");
            }

            @Override
            public void onHideEmojiPanel() {
                Log.e(TAG, "onHideEmojiPanel");
            }
        });

        ll_rootEmojiPanel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private int statusBarHeight;
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                // 使用最外层布局填充，进行测算计算
                ll_rootEmojiPanel.getWindowVisibleDisplayFrame(r);
                int screenHeight = ll_rootEmojiPanel.getRootView().getHeight();
                int heightDiff = screenHeight - (r.bottom - r.top);
                if (heightDiff > 100) {
                    // 如果超过100个像素，它可能是一个键盘。获取状态栏的高度
                    statusBarHeight = 0;
                }
                try {
                    Class<?> c = Class.forName("com.android.internal.R$dimen");
                    Object obj = c.newInstance();
                    Field field = c.getField("status_bar_height");
                    int x = Integer.parseInt(field.get(obj).toString());
                    statusBarHeight = getResources().getDimensionPixelSize(x);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int realKeyboardHeight = heightDiff - statusBarHeight;
                Log.e("键盘", "keyboard height(单位像素) = " + realKeyboardHeight);

                emojiKeyboard.storeKeyboardHeight(realKeyboardHeight);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!emojiKeyboard.interceptBackPress()) {
            super.onBackPressed();
        }
    }

}
