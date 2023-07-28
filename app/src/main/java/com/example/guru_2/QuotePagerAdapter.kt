package com.example.guru_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// RecycleView를 활용한 어댑터 구현
class QuotePagerAdapter(
    private val quotes: List<Quote>,
    private val isNameRevealed: Boolean
): RecyclerView.Adapter<QuotePagerAdapter.QuoteViewHolder>() {

    // 아이템뷰를 위한 뷰홀더 객체 생성하고 설정한 레이아웃으로 전환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QuoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quote, parent, false)
        )

    // position에 해당하는 데이터를 뷰홀더의 아이템에 표시. 즉 현재 위치에 해당하는 데이터를 보여줌.
    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val actual_position = position % quotes.size // 무한 스크롤을 위한 코드
        holder.bind(quotes[actual_position], isNameRevealed)
    }

    override fun getItemCount() = Int.MAX_VALUE

    // 뷰홀더 클래스
    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val quoteTextView: TextView = itemView.findViewById<TextView>(R.id.quoteTextView)
        private val nameTextView: TextView = itemView.findViewById<TextView>(R.id.nameTextView)

        // 랜더링 방법 정의
        fun bind(quote:Quote, isNameRevealed:Boolean) {
            quoteTextView.text = "${quote.quote}"

            if(isNameRevealed){
                nameTextView.text = "${quote.name}"
                nameTextView.visibility = View.VISIBLE
            } else {
                nameTextView.visibility = View.GONE
            }

        }
    }

}