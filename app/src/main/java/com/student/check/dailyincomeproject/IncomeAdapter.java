package com.student.check.dailyincomeproject;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public IncomeAdapter(Context context, Cursor cursor){
           mContext = context;
           mCursor = cursor;
    }
    public class IncomeViewHolder extends RecyclerView.ViewHolder{
        View mView;
        CardView cardView;
        TextView mTime;
        TextView mCate;
        TextView mAmount;
        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            cardView = (CardView) mView.findViewById(R.id.card_view);
            mTime = (TextView) mView.findViewById(R.id.time_card);
            mCate = (TextView) mView.findViewById(R.id.cate_card);
            mAmount = (TextView) mView.findViewById(R.id.amount_card);
        }
    }
    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.content_detail_incom,viewGroup,false);
        return new IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder incomeViewHolder, int i) {
        if(!mCursor.moveToPosition(i)){
            return;
        }
        String time = mCursor.getString(mCursor.getColumnIndex("time"));
        String cate = mCursor.getString(mCursor.getColumnIndex("cate"));
        String money = mCursor.getString(mCursor.getColumnIndex("money"));
        String date = mCursor.getString(mCursor.getColumnIndex("date"));
        String id = mCursor.getString(mCursor.getColumnIndex("ID"));

        incomeViewHolder.mTime.setText("วันที่"+time+" "+date);
        incomeViewHolder.mCate.setText(cate);
        incomeViewHolder.mAmount.setText(money);
        incomeViewHolder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }

        mCursor = newCursor;
        if(newCursor != null){
            notifyDataSetChanged();
        }
    }



}
