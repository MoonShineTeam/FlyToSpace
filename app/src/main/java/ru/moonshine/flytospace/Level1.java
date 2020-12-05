package ru.moonshine.flytospace;

import androidx.appcompat.app.AppCompatActivity;
import ru.moonshine.flytospace.source.Utils;

import android.content.Context;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Level1 extends AppCompatActivity {

    public Level1() throws IOException, JSONException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level1);
        Utils.setFullScreenMode(this);
    }

    /*String jsonText = readText(this.getApplicationContext(), R.raw.levels);
    JSONObject obj = new JSONObject(jsonText);



    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String s = null;
        while((  s = br.readLine())!=null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }*/
}