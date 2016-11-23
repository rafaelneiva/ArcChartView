package br.com.zup.arcchartexample;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;
import android.widget.TextView;

import br.com.zup.arcchartlib.ArcChartView;

public class MainActivity extends AppCompatActivity {
    
    ArcChartView chart;
    AppCompatSeekBar seekBar;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        seekBar = (AppCompatSeekBar) findViewById(R.id.seekbar);
        chart = (ArcChartView) findViewById(R.id.chart);
        text = (TextView) findViewById(R.id.text);

        int progress = 55;

        chart.startAnimation(progress);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            seekBar.setProgress(progress, true);
        } else {
            seekBar.setProgress(progress);
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                chart.setProgress(seekBar.getProgress());
                chart.invalidate();

                text.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
