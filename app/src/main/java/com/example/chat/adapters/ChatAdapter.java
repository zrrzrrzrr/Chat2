package com.example.chat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.chat.R;
import com.example.chat.utils.FaceManager;
import com.example.chat.view.AnimatedGifDrawable;
import com.example.chat.view.AnimatedImageSpan;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> data;
    private Pattern EMOTION_URL = Pattern.compile("\\[(\\S+?)\\]");
    private FaceManager fm;
    private Context context;

    public ChatAdapter(Context context,List<String> data){
        this.context = context;
        this.data = data;
        fm = FaceManager.getInstance();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==0){
            View view = LayoutInflater.from(context).inflate(R.layout.list_item,null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.left_list_item,null);
            LeftViewHolder leftViewHolder = new LeftViewHolder(view);
            return leftViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int type = getItemViewType(i);
        if (type==0){
            MyViewHolder myViewHolder = (MyViewHolder)viewHolder;
            myViewHolder.item_msg.setText(convertNormalStringToSpannableString(data.get(i),myViewHolder.item_msg));
        }else {
            LeftViewHolder myViewHolder = (LeftViewHolder) viewHolder;
            myViewHolder.left_item_msg.setText(convertNormalStringToSpannableString(data.get(i),myViewHolder.left_item_msg));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2==0){
            return 0;
        }else{
            return 1;
        }
    }

    /**
     * check the cacke first
     * @param message
     * @param tv
     * @return
     */
    private SpannableString convertNormalStringToSpannableString(String message , final TextView tv) {
        SpannableString value = SpannableString.valueOf(message);
        Matcher localMatcher = EMOTION_URL.matcher(value);
        while (localMatcher.find()) {
            String str2 = localMatcher.group(0);
            int k = localMatcher.start();
            int m = localMatcher.end();
            if (m - k < 8) {
                int face = fm.getFaceId(str2);
                if(-1!=face){//wrapping with weakReference
                    WeakReference<AnimatedImageSpan> localImageSpanRef = new WeakReference<AnimatedImageSpan>(new AnimatedImageSpan(new AnimatedGifDrawable(context.getResources().openRawResource(face), new AnimatedGifDrawable.UpdateListener() {
                        @Override
                        public void update() {//update the textview
                            tv.postInvalidate();
                        }
                    })));
                    value.setSpan(localImageSpanRef.get(), k, m, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                }
            }
        }
        return value;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView item_msg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_msg = itemView.findViewById(R.id.item_msg);
        }
    }
    class LeftViewHolder extends RecyclerView.ViewHolder{
        private TextView left_item_msg;
        public  LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            left_item_msg = itemView.findViewById(R.id.left_item_msg);
        }
    }
}
