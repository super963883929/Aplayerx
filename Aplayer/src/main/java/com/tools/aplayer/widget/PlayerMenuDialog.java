package com.tools.aplayer.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tools.aplayer.R;

import java.util.List;


public class PlayerMenuDialog extends Dialog {
    private final List<String> mStringList;
    private final PlayerMenuAdapter mAdapter;

    public PlayerMenuDialog(Context context, List<String> stringList) {
        super(context);
        mStringList = stringList;
        mAdapter = new PlayerMenuAdapter(this, mStringList,getContext());

    }


    public static PlayerMenuDialog showDialog(Context context, List<String> stringList,OnItemClickListener onItemClickListener){
        PlayerMenuDialog playerMenuDialog = new PlayerMenuDialog(context,stringList);
        playerMenuDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        playerMenuDialog.setOnItemClickListener(onItemClickListener);
        playerMenuDialog.show();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                | View.SYSTEM_UI_FLAG_FULLSCREEN;

        playerMenuDialog.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        playerMenuDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        return playerMenuDialog;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_player_menu,null,false);
        setContentView(rootView);
        getWindow().setBackgroundDrawableResource(R.drawable.aplayer_bg_dialog);
        getWindow().setGravity(Gravity.END);
        getWindow().clearFlags( WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    public void setSelectPosition(int position){
        mAdapter.setSelectPosition(position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mAdapter.setOnItemClickListener(onItemClickListener);
    }


    public interface OnItemClickListener{
        void onItemClick(PlayerMenuDialog dialog,int position);
    }


    public static class PlayerMenuAdapter extends RecyclerView.Adapter<PlayerMenuAdapter.PlayerMenuItemHolder>{
       final   PlayerMenuDialog mPlayerMenuDialog;
        public PlayerMenuAdapter(PlayerMenuDialog playerMenuDialog, List<String> stringList, Context context) {
            mPlayerMenuDialog = playerMenuDialog;
            mStringList = stringList;
            mContext = context;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        OnItemClickListener mOnItemClickListener;
        private final List<String> mStringList;
        private final Context mContext;

        @SuppressLint("NotifyDataSetChanged")
        public void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
            notifyDataSetChanged();

        }

        private int selectPosition = 0;


        @NonNull
        @Override
        public PlayerMenuItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_player_menu_dialog, parent, false);
            return new PlayerMenuItemHolder(itemView);
        }


        @Override
        public void onBindViewHolder(@NonNull PlayerMenuItemHolder holder, int position) {
            holder.tvContent . setText(mStringList.get(position));
            holder.tvContent .setOnClickListener(v ->{
                if (mOnItemClickListener!=null) {
                    mOnItemClickListener.onItemClick(mPlayerMenuDialog,position);
                }
            });
            if (position == selectPosition){
                holder.tvContent .setTextColor(ContextCompat.getColor(mContext,R.color.main));
            }else{
                holder.tvContent .setTextColor(ContextCompat.getColor(mContext,R.color.ctl_font));
            }
        }


        @Override
        public int getItemCount() {
            return mStringList.size();
        }



        public static class PlayerMenuItemHolder extends RecyclerView.ViewHolder{
            public TextView tvContent;
            public PlayerMenuItemHolder(@NonNull View itemView) {
                super(itemView);
                tvContent = itemView.findViewById(R.id.tvContent);
            }
        }
    }


    /**
     * 隐藏虚拟栏 ，显示的时候再隐藏掉
     * @param window
     */
    public static void hideNavigationBar(final Window window) {
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        //布局位于状态栏下方
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        //全屏
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        //隐藏导航栏
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                if (Build.VERSION.SDK_INT >= 19) {
                    uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                } else {
                    uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
                }
                window.getDecorView().setSystemUiVisibility(uiOptions);
            }
        });
    }

}
