package com.yasser.bluetoothnotes;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NotesEditor extends AppCompatActivity {

    private TextView mTextView;
    Paint textPaint;
    Paint piePaint;
    Paint shadowPaint;
    int textColor = 0xff4040;
    float textHeight=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);

        TextView text = findViewById(R.id.textView);
        Intent intent = getIntent();
        int noteId = intent.getIntExtra("itemId", -1);

        if (noteId != -1) {

            text.setText(MainActivity.notes.get(noteId));
        }


    }


    public class Shapes extends Canvas{


        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Draw the shadow
            canvas.drawOval(
                    shadowBounds,
                    shadowPaint
            );

            // Draw the label text
            canvas.drawText(data.get(currentItem).mLabel, textX, textY, textPaint);

            // Draw the pie slices
            for (int i = 0; i < data.size(); ++i) {
                ClipData.Item it = data.get(i);
                piePaint.setShader(it.shader);
                canvas.drawArc(bounds,
                        360 - it.endAngle,
                        it.endAngle - it.startAngle,
                        true, piePaint);
            }

            // Draw the pointer
            canvas.drawLine(textX, pointerY, pointerX, pointerY, textPaint);
            canvas.drawCircle(pointerX, pointerY, pointerSize, mTextPaint);
        }


        private void init() {
            textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setColor(textColor);
            if (textHeight == 0) {
                textHeight = textPaint.getTextSize();
            } else {
                textPaint.setTextSize(textHeight);
            }

            piePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            piePaint.setStyle(Paint.Style.FILL);
            piePaint.setTextSize(textHeight);

            shadowPaint = new Paint(0);
            shadowPaint.setColor(0xff101010);
            shadowPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
        }
    }

}