# **# Android-app-mysql-        apk连接mysql数据库** #

1. android app 连接mysql数据库,需要下载jdbc, 导入到lib中,然后作为一个库文件引用
2. 务必填写好地址 ,用户名和密码
    	
    	` private static final String URL = "jdbc:mysql://192.168.3.7:3306/mydb";
    	private static final String USER = "admin";
    	private static final String PASSWD = "Root110qwe";
    		
    		jdbc:mysql://192.168.3.7:3306/mydb
    					 mysql地址 数据库的名称
    	` 
3.流程
	
    	`动态注册 
    	Class.forName("com.mysql.jdbc.Driver");`
4.连接数据库
	
    	`con = DriverManager.getConnection("jdbc:mysql://192.168.3.7:3306/mydb","admin", "Root110qwe");`

5.使用con进行数据库的操作

	`public void testConnection(Connection con1) throws java.sql.SQLException {
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
        }`