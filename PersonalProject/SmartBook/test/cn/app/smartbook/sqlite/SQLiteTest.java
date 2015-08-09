package cn.app.smartbook.sqlite;

import android.test.AndroidTestCase;
/**
 * 测试类，测试数据库
 * @author Administrator
 *
 */
public class SQLiteTest extends AndroidTestCase {
	SQLiteTools tool = null;
	boolean actual;
	public void testCreateSQL() {
		tool = new SQLiteTools();
		tool.openSQL("user");
		if(tool!=null) {
			actual = true;
		} else {
			actual = false;
		}
		assertEquals(true, actual);
	}

}
