package cn.app.smartbook.sqlite;

import android.content.ContentValues;
import android.test.AndroidTestCase;
/**
 * 测试类，测试数据库
 * @author Administrator
 *
 */
public class SQLiteTest extends AndroidTestCase {
	SQLiteTools tool = null;

	//测试创建数据库
	public void testCreateSQL() {
		boolean actual = false;
		tool = new SQLiteTools();
		tool.openSQL(getContext(),"user.db");
		if(tool!=null) {
			actual = true;
		}
		assertEquals(true, actual);
	}
	
	//测试创建表
	public void testAddTable() {
		String actual = null;
		tool = new SQLiteTools();
		actual = tool.addTable(tool.openSQL(getContext(),"user.db"),"records");
		assertEquals("records", actual);
	}
	
	//测试往表中加入参数
	public void testAddPara() {
		String actual = null;
		tool = new SQLiteTools();
//		actual = tool.addPara(tool.openSQL(getContext(),"user.db"), "records", "account", "integer");
		tool.addPara(tool.openSQL(getContext(),"user.db"), "records", "name", "text");
		tool.addPara(tool.openSQL(getContext(),"user.db"), "records", "password", "integer");
//		assertEquals("account", actual);
	}
	
	//测试插入记录功能
	public void testInsertValue() {
		tool = new SQLiteTools();
		ContentValues values = new ContentValues();
		values.put("name", "小红");
		values.put("password", 345);
		values.put("account", 305275509);
		tool.insertValue(tool.openSQL(getContext(), "user.db"), "records", values);
		values.put("name", "小陈");
		values.put("password", 3459993);
		values.put("account", 305275510);
		tool.insertValue(tool.openSQL(getContext(), "user.db"), "records", values);
	}
	
	//测试删除记录功能
	public void testDeleteValue() {
		tool = new SQLiteTools();
		tool.deleteValue(tool.openSQL(getContext(), "user.db"), "records", "account","305275508");
		tool.deleteValue(tool.openSQL(getContext(), "user.db"), "records", "name","小陈");
	}
	
	//测试更新记录功能
	public void testUpdate() {
		tool = new SQLiteTools();
		ContentValues values = new ContentValues();
		values.put("name", "老刘");
		tool.update(tool.openSQL(getContext(), "user.db"), "records", values, "account", "305275507");
	}

}
