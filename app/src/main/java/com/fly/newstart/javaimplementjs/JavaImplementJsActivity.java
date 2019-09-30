package com.fly.newstart.javaimplementjs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fly.newstart.R;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.util.HashMap;
import java.util.Map;

public class JavaImplementJsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_implement_js);
    }

    /**
     * Java执行js的方法
     */
    private static final String JAVA_CALL_JS_FUNCTION = "function test(arg){" +
            "    if (Object.prototype.toString.call(arg) == '[object Object]'){" +
            "        if(JSON.stringify(arg) == \"{}\"){" +
            "            return false" +
            "        }else{" +
            "            return true" +
            "        }" +
            "    }else{" +
            "        return false" +
            "    }" +
            "}";

    /**
     * js调用Java中的方法
     */
    private static final String JS_CALL_JAVA_FUNCTION = //
            "var ScriptAPI = java.lang.Class.forName(\"" + JavaImplementJsActivity.class.getName() + "\", true, javaLoader);" + //
                    "var methodRead = ScriptAPI.getMethod(\"jsCallJava\", [java.lang.String]);" + //
                    "function jsCallJava(url) {return methodRead.invoke(null, url);}" + //
                    "function Test(){ return jsCallJava(); }";


    public void onClick(View view) {
        Map s = new HashMap<String, String>();
        s.put("a", "b");
        boolean b = runScript(JAVA_CALL_JS_FUNCTION, "test", new Object[]{s});
        Log.e("FLY11110", "onClick: " + b);
    }

    /**
     * 执行JS
     *
     * @param js             js代码
     * @param functionName   js方法名称
     * @param functionParams js方法参数
     * @return
     */
    public boolean runScript(String js, String functionName, Object[] functionParams) {
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();

            ScriptableObject.putProperty(scope, "javaContext", Context.javaToJS(JavaImplementJsActivity.this, scope));
            ScriptableObject.putProperty(scope, "javaLoader", Context.javaToJS(JavaImplementJsActivity.class.getClassLoader(), scope));

            rhino.evaluateString(scope, js, "JavaImplementJsActivity", 1, null);

            Function function = (Function) scope.get(functionName, scope);

            Object result = function.call(rhino, scope, scope, functionParams);

            if (result instanceof NativeJavaObject) {
                return (boolean) ((NativeJavaObject) result).getDefaultValue(boolean.class);
            } else if (result instanceof NativeObject) {
                return (boolean) ((NativeObject) result).getDefaultValue(boolean.class);
            }
            return (boolean) result;
        } finally {
            Context.exit();
        }
    }

    public static String jsCallJava(String url) {
        return "农民伯伯 js call Java Rhino";
    }
}
