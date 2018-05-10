package net.archeryc.popupwindowdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MyPopupWindow popupWindow;

    Button btnShow;

    private int popHeight;

    private String tag="token_yc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShow=findViewById(R.id.btn_show);

        popHeight= (int) (getResources().getDisplayMetrics().heightPixels*0.75f);
        popupWindow=new MyPopupWindow(this);
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                Log.d(tag,"keyBoardShow height----->"+height);
                ViewGroup viewGroup= (ViewGroup) btnShow.getParent();
                int popHeight= (int) (viewGroup.getHeight()-btnShow.getHeight()-height);
                popupWindow.update(btnShow,WindowManager.LayoutParams.MATCH_PARENT,popHeight);
            }

            @Override
            public void keyBoardHide(int height) {
                Log.d(tag,"keyBoardHide height----->"+height);
                popupWindow.update(btnShow,WindowManager.LayoutParams.MATCH_PARENT,popHeight);
            }
        });
    }

    class MyPopupWindow extends PopupWindow{

        public MyPopupWindow(Context context) {
            super(context);
            View contentView= LayoutInflater.from(context).inflate(R.layout.pop_content,null,false);
            setContentView(contentView);
            setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            setHeight(popHeight);
            setFocusable(true);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            RecyclerView recyclerView=contentView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(new MyAdapter(MainActivity.this));
        }
    }

    public void showPop(View view){
        popupWindow.showAsDropDown(btnShow);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder>{

        Context mContext;

        public MyAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.tvTxt.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder{
            TextView tvTxt;

            public ItemViewHolder(View itemView) {
                super(itemView);
                tvTxt=itemView.findViewById(R.id.tv_txt);
            }
        }
    }
}
