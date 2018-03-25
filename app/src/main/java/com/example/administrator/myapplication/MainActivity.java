package com.example.administrator.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private String tag = MainActivity.class.getSimpleName().toString();
    public Button startBtn = null;
    private TextView tv = null;
    Thread startThread ;
    Context context  =MainActivity.this;

    private static final String URL = "jdbc:mysql://192.168.3.7:3306/mydb";
    private static final String USER = "admin";
    private static final String PASSWD = "Root110qwe";
    private Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = (Button) findViewById(R.id.button1);
        tv = (TextView) findViewById(R.id.textView1);
        Log.e(tag,"onCreate");

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startThread= new Thread(runnable);
                startThread.start();
                Log.e(tag,"onClick");
                tv.setText("");
            }
        });


    }

    Handler myHandler=new Handler(){

        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
                tv.setVisibility(View.VISIBLE);
                System.out.println("1");
                Bundle data=new Bundle();
                data=msg.getData();
                System.out.println("返回的信息:"+data);
                if (data!=null)
                {
                    String id= data.get("R_id").toString();
                    String name= data.get("R_name").toString();
                    String R_version= data.get("R_version").toString();
                    String R_context= data.get("R_context").toString();
                    String result = id +"\t"+name+"\t"+R_version+"\t"+R_context+"\n";
                    tv.append(result);
                }



        }
    };

    public void onConn(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = Util.openConnection(URL, USER, PASSWD);
                Log.e(tag,"onConn中  conn:"+conn);
                if (conn != null){
                    System.out.print("connect susscess!");
                }

            }
        }).start();

    }

    public void onInsert(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String sql = "insert into rc_test_result(" +
                        "R_name ,R_version ,R_context,R_result,R_note,R_projectname,R_ower,R_kexuan,R_inning,R_createtime) " +
                        "values('WAKEUP','26','xxxcxcvcvx','success','nothing','1804','sdfsdfjsdj','y','1',now()" +
                        ")";

                Util.execSQL(conn, sql);
            }
        }).start();
    }

    /*删除需要首先处理外键关联才能进行删除成功*/
    public void onDelete(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = "delete from rc_test_result where R_name='cts'";
                Util.execSQL(conn, sql);

            }
        }).start();

    }

    public void onUpdate(View view) {
        String sql = "update rc_test_result set name='lilei' where name='hanmeimei'";
        Util.execSQL(conn, sql);
    }

    public void onQuery(View view) {
        System.out.println("All users info:");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Util.query(conn, "select * from rc_test_result");
            }
        }).start();

    }


    Runnable runnable=new Runnable() {
        private Connection con = null;

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {

                Class.forName("com.mysql.jdbc.Driver");
                //引用代码此处需要修改，address为数据IP，Port为端口号，DBName为数据名称，UserName为数据库登录账户，Password为数据库登录密码
                con =
                        DriverManager.getConnection("jdbc:mysql://192.168.3.7:3306/mydb",
                                "admin", "Root110qwe");

                Log.e(tag,"con---->"+con);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                testConnection(con);    //测试数据库连接
                Log.e(tag,"testconnect will connect mysql");

            } catch (java.sql.SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void testConnection(Connection con1) throws java.sql.SQLException {
            try {
                String sql = "select * from rc_test_result";        //查询表名为“oner_alarm”的所有内容
                Statement stmt = con1.createStatement();        //创建Statement
                ResultSet rs = stmt.executeQuery(sql);          //ResultSet类似Cursor

                //<code>ResultSet</code>最初指向第一行
                Log.e(tag,"rs:"+rs);
                rs.first();
                while (!rs.isAfterLast()) {
                    Bundle bundle=new Bundle();

                    bundle.putString("R_id",rs.getString(rs.findColumn("R_id")));
                    bundle.putString("R_name",rs.getString(rs.findColumn("R_name")));
                    bundle.putString("R_version",rs.getString(rs.findColumn("R_version")));
                    bundle.putString("R_context",rs.getString(rs.findColumn("R_context")));
                    Message msg=new Message();
                    msg.setData(bundle);
                    Log.e(tag,"msg:"+msg);
                    myHandler.sendMessage(msg);
                    rs.next();
                }

                rs.close();
                stmt.close();
            } catch (SQLException e) {

            } finally {
                if (con1 != null)
                    try {
                        con1.close();
                    } catch (SQLException e) {}
            }
        }
    };

}

