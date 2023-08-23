package com.example.fastcampus35project_4_03

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import com.example.fastcampus35project_4_03.databinding.ActivityDetailBinding
import com.example.fastcampus35project_4_03.databinding.ActivityMainBinding
import com.example.fastcampus35project_4_03.model.DetailItem
import com.example.fastcampus35project_4_03.model.Type
import java.util.Calendar

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val adapter = DetailListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        initView()

        adapter.submitList(mockDate())
    }

    private fun initView() {
        binding.cardTitleTextView.text = intent.getStringExtra(CARD_NAME)
        binding.cardLayout.backgroundTintList = intent.getParcelableExtra(CARD_COLOR) as? ColorStateList
        binding.recyclerView.adapter = adapter
    }

    private fun mockDate(): List<DetailItem> {
        fun createDate(year: Int, month: Int, day: Int) = Calendar.getInstance().apply {
            set(year, month, day)
        }.time

        val list = mutableListOf<DetailItem>().apply {
            add(
                DetailItem(
                    1,
                    createDate(2023, 1, 6),
                    "A상점",
                    24000,
                    Type.PAY
                )
            )

            add(
                DetailItem(
                    2,
                    createDate(2023, 1, 6),
                    "B상점",
                    24000,
                    Type.PAY
                )
            )

            add(
                DetailItem(
                    2,
                    createDate(2023, 1, 6),
                    "B상점",
                    24000,
                    Type.PAY
                )
            )

            add(
                DetailItem(
                    2,
                    createDate(2023, 1, 6),
                    "B상점",
                    24000,
                    Type.PAY
                )
            )

            add(
                DetailItem(
                    2,
                    createDate(2023, 1, 6),
                    "B상점",
                    24000,
                    Type.PAY
                )
            )

            add(
                DetailItem(
                    2,
                    createDate(2023, 1, 6),
                    "B상점",
                    24000,
                    Type.PAY
                )
            )

            add(
                DetailItem(
                    2,
                    createDate(2023, 1, 6),
                    "B상점",
                    24000,
                    Type.CANCEL
                )
            )

            add(
                DetailItem(
                    2,
                    createDate(2023, 1, 6),
                    "B상점",
                    24000,
                    Type.PAY
                )
            )

            add(
                DetailItem(
                    2,
                    createDate(2023, 1, 6),
                    "B상점",
                    24000,
                    Type.PAY
                )
            )
            add(
                DetailItem(
                    2,
                    createDate(2023, 1, 6),
                    "B상점",
                    24000,
                    Type.PAY
                )
            )
        }

        return list
    }

    companion object {
        private const val CARD_NAME = "CARD_NAME"
        private const val CARD_COLOR = "CARD_COLOR"

        fun start(
            context: Context,
            cardName: String,
            cardColor: ColorStateList?,
            optionsCompat: ActivityOptionsCompat
        ) {
            Intent(context, DetailActivity::class.java).apply {
                putExtra(CARD_NAME, cardName)
                putExtra(CARD_COLOR, cardColor)
            }.run {
                context.startActivity(this, optionsCompat.toBundle())
            }
        }
    }
}