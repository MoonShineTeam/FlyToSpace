package ru.moonshine.flytospace.source;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import ru.moonshine.flytospace.model.Level;
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
//        ArrayList<Task> taskList = new ArrayList<>();
//        String textValue = "";
//        boolean inEntry = false;
//        boolean subInEntry = false;
//        boolean isAnswer = false;
//        ArrayList<String> equations = new ArrayList<>();
//        ArrayList<Integer> answers = new ArrayList<>();
//        Task currentTask = null;
//        try {
//            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
//                String tagName = parser.getName();
//                switch (parser.getEventType()) {
//                    case XmlPullParser.START_TAG:
//                        if ("task".equalsIgnoreCase(tagName)) {
//                            inEntry = true;
//                            currentTask = new Task();
//                            currentTask.setId(Integer.parseInt(parser.getAttributeValue(0)));
//                            currentTask.setTaskType(parser.getAttributeValue(1));
//                        } else if ("equation".equalsIgnoreCase(tagName)) {
//                            subInEntry = true;
//                        } else if ("answer".equalsIgnoreCase(tagName)) {
//                            subInEntry = true;
//                            isAnswer = parser.getAttributeValue(0).equals("1");
//                        }
//                        break;
//                    case XmlPullParser.TEXT:
//                        textValue = parser.getText();
//                        if (isAnswer) {
//                            currentTask.setTrueAnswer(Integer.parseInt(textValue));
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        if (inEntry) {
//                            switch (tagName) {
//                                case "task":
//                                    taskList.add(currentTask);
//                                    inEntry = false;
//                                    break;
//                                case "text":
//                                    currentTask.setTaskText(textValue);
//                                    break;
//                                case "style":
//                                    currentTask.setStyle(textValue);
//                                    break;
//                                case "answer":
//                                    if (subInEntry) {
//                                        answers.add(Integer.parseInt(textValue));
//                                        subInEntry = false;
//
//                                    }
//                                    break;
//                                case "answers":
//                                    currentTask.setAnswers(answers);
//                                    answers = new ArrayList<>();
//                                    break;
//                                case "equation":
//                                    if (subInEntry) {
//                                        equations.add(textValue);
//                                        subInEntry = false;
//                                    }
//                                    break;
//                                case "equations":
//                                    currentTask.setEquations(equations);
//                                    equations = new ArrayList<>();
//                                    break;
//                                default:
//                                    break;
//                            }
//                        }
//                        break;
//                    default:
//                }
//                parser.next();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return taskList;

        ArrayList<Task> tasks = new ArrayList<>();

        // EASY LEVELS
        tasks.add(new Task(1, "Изначально в космос запустили две ракеты, позже запустили еще одну ракету. Сколько всего запустили ракет?", "EASY",
                new ArrayList(Arrays.asList(1, 2, 3, 4)), new ArrayList(Arrays.asList("2+1=?")), "ROCKET", 3));
        tasks.add(new Task(2, "В космосе было пять ракет, одна из них вернулась на Землю. Сколько ракет осталось в космосе?", "EASY",
                new ArrayList(Arrays.asList(3, 5, 4, 2)), new ArrayList(Arrays.asList("5-1=?")), "ROCKET", 4));
        tasks.add(new Task(3, "В космосе было четыре ракеты, три из них вернулась на Землю. Сколько ракет осталось в космосе?", "EASY",
                new ArrayList(Arrays.asList(4, 2, 3, 1)), new ArrayList(Arrays.asList("4-3=?")), "ROCKET", 1));
        tasks.add(new Task(4, "Когда запустили две ракеты, в космосе их стало четыре. Сколько ракет было в космосе до запуска?", "EASY",
                new ArrayList(Arrays.asList(2, 4, 1, 3)), new ArrayList(Arrays.asList("2+?=4")), "ROCKET", 2));
        tasks.add(new Task(5, "Несколько ракет летали в космосе, когда к ним запустили ещё две, стало 5 ракет. Сколько было ракет в космосе до запуска?", "EASY",
                new ArrayList(Arrays.asList(5, 1, 3, 2)), new ArrayList(Arrays.asList("?+2=5")), "ROCKET", 3));

        // MEDIUM LEVELS
        tasks.add(new Task(6, "", "MEDIUM",
                new ArrayList(Arrays.asList(12, 11, 13, 10)), new ArrayList(Arrays.asList("5+6=?")), "ROCKET", 11));
        tasks.add(new Task(7, "", "MEDIUM",
                new ArrayList(Arrays.asList(15, 13, 14, 17)), new ArrayList(Arrays.asList("7+8=?")), "ROCKET", 15));
        tasks.add(new Task(8, "", "MEDIUM",
                new ArrayList(Arrays.asList(24, 21, 26, 25)), new ArrayList(Arrays.asList("11+14=?")), "ROCKET", 25));
        tasks.add(new Task(9, "", "MEDIUM",
                new ArrayList(Arrays.asList(18, 17, 19, 16)), new ArrayList(Arrays.asList("26-9=?")), "ROCKET", 17));
        tasks.add(new Task(10, "", "MEDIUM",
                new ArrayList(Arrays.asList(48, 59, 62, 63)), new ArrayList(Arrays.asList("23+39=?")), "ROCKET", 62));

        // HARD LEVELS
        tasks.add(new Task(11, "", "HARD",
                new ArrayList(Arrays.asList(12, 8, 6, 10)), new ArrayList(Arrays.asList("2*4=?")), "ROCKET", 8));
        tasks.add(new Task(12, "", "HARD",
                new ArrayList(Arrays.asList(1, 0, 9, 18)), new ArrayList(Arrays.asList("9/9=?")), "ROCKET", 1));
        tasks.add(new Task(13, "", "HARD",
                new ArrayList(Arrays.asList(6, 3, 5, 4)), new ArrayList(Arrays.asList("?*3=12")), "ROCKET", 4));
        tasks.add(new Task(14, "", "HARD",
                new ArrayList(Arrays.asList(2, 3, 4, 5)), new ArrayList(Arrays.asList("25/5=?")), "ROCKET", 5));
        tasks.add(new Task(15, "", "HARD",
                new ArrayList(Arrays.asList(69, 91, 81, 73)), new ArrayList(Arrays.asList("7*13=?")), "ROCKET", 91));

        return tasks;
    }
}
