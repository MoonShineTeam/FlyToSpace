package ru.moonshine.flytospace.source;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import ru.moonshine.flytospace.model.Task;


public class Utils {
    public static void setFullScreenMode(Activity activity) {
        Window w = activity.getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void startViewAnimation(Context context, View view, int animID) {
        view.startAnimation(AnimationUtils.loadAnimation(context, animID));
    }

    public static void buttonAnimation(AppCompatActivity activity, Context context,
                                       int buttonID, int animID) {
        Animation animation = AnimationUtils.loadAnimation(context, animID);
        Button btn = activity.findViewById(buttonID);
        btn.startAnimation(animation);
    }

    public static void intentAnimation(Context context, Class<?> nextClass,
                                       int animInID, int animOutID) {
        Intent intent = new Intent(context, nextClass);
        ActivityOptions option  = ActivityOptions.makeCustomAnimation(context, animInID, animOutID);
        Bundle bundle = option.toBundle();
        context.startActivity(intent, bundle);
    }

    public static void intentAnimation(Context context, Class<?> nextClass,
                                       int animInID, int animOutID, Task task) {
        Intent intent = new Intent(context, nextClass);
        ActivityOptions option  = ActivityOptions.makeCustomAnimation(context, animInID, animOutID);
        Bundle bundle = option.toBundle();
        intent.putExtra("Task", task);
        context.startActivity(intent, bundle);
    }

    public static ArrayList<Task> readXmlTasks(XmlResourceParser parser) {
        ArrayList<Task> taskList = new ArrayList<>();
        String textValue = "";
        boolean inEntry = false;
        boolean subInEntry = false;
        boolean isAnswer = false;
        ArrayList<String> equations = new ArrayList<>();
        ArrayList<Integer> answers = new ArrayList<>();
        Task currentTask = null;
        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (parser.getEventType()) {
                    case XmlPullParser.START_TAG:
                        if ("task".equalsIgnoreCase(tagName)) {
                            inEntry = true;
                            currentTask = new Task();
                            currentTask.setId(Integer.parseInt(parser.getAttributeValue(0)));
                            currentTask.setTaskType(parser.getAttributeValue(1));
                        } else if ("equation".equalsIgnoreCase(tagName)) {
                            subInEntry = true;
                        } else if ("answer".equalsIgnoreCase(tagName)) {
                            subInEntry = true;
                            isAnswer = parser.getAttributeValue(0).equals("1");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = parser.getText();
                        if (isAnswer) {
                            currentTask.setTrueAnswer(Integer.parseInt(textValue));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (inEntry) {
                            switch (tagName) {
                                case "task":
                                    taskList.add(currentTask);
                                    inEntry = false;
                                    break;
                                case "text":
                                    currentTask.setTaskText(textValue);
                                    break;
                                case "style":
                                    currentTask.setStyle(textValue);
                                    break;
                                case "answer":
                                    if (subInEntry) {
                                        answers.add(Integer.parseInt(textValue));
                                        subInEntry = false;
                                    }
                                    break;
                                case "answers":
                                    currentTask.setAnswers(answers);
                                    break;
                                case "equation":
                                    if (subInEntry) {
                                        equations.add(textValue);
                                        subInEntry = false;
                                    }
                                    break;
                                case "equations":
                                    currentTask.setEquations(equations);
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    default:
                }
                parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskList;
    }
}
