package cc.chenhe.lib.androidlua.demo.chatbox;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatBoxView extends LinearLayout {

    ArrayList<BasicChatMessage> m_messages = new ArrayList<>();
    RecyclerView m_recyclerView = null;

    public void setMessages(ArrayList<BasicChatMessage> list) {
        m_messages = list;
        scrollToBottom();
    }
    public void addMessage(BasicChatMessage msg) {
        m_messages.add(msg);
        scrollToBottom();
    }

    public void updateLastMessage() {
//        m_messages.set(m_messages.size()-1,msg);
        scrollToBottom();
        m_recyclerView.getAdapter().notifyItemChanged(m_messages.size()-1);
    }
    public ChatBoxView(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        m_recyclerView = new RecyclerView(this.getContext());
        m_recyclerView.setPadding(20,20,28,20);
        m_recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                BasicMessageView messageView =  new BasicMessageView(ChatBoxView.this.getContext());
                messageView.setLayoutParams(lp);
                RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(messageView) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };

                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                BasicMessageView view = (BasicMessageView) holder.itemView;
                view.setMessage(m_messages.get(position));
                    view.setGravity(m_messages.get(position).side == BasicChatMessage.Side.LEFT? Gravity.LEFT: Gravity.RIGHT);
            }

            @Override
            public int getItemCount() {
                return m_messages.size();
            }
        };
        m_recyclerView.setAdapter(adapter);

        this.addView(m_recyclerView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed) {
            scrollToBottom();
        }
//        m_recyclerView.
    }

    private void scrollToBottom() {
        scrollToBottom(m_recyclerView);
    }

    private void scrollToBottom(final RecyclerView recyclerView) {
        // scroll to last item to get the view of last item
        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        final RecyclerView.Adapter adapter = recyclerView.getAdapter();
        final int lastItemPosition = adapter.getItemCount() - 1;

        layoutManager.scrollToPositionWithOffset(lastItemPosition, 0);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                // then scroll to specific offset
                View target = layoutManager.findViewByPosition(lastItemPosition);
                if (target != null) {
                    int offset = recyclerView.getMeasuredHeight() - target.getMeasuredHeight();
                    layoutManager.scrollToPositionWithOffset(lastItemPosition, offset);
                }
            }
        });



    }
}
