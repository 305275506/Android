/**
 * 功能：业务类，主要实现xml数据的解析
 */
package com.example.pullparserdemo.datamanager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class DataPullParser {
	private static final String BOOK = "book";
	private static final String NAME = "name";
	private static final String PRICE = "price";
	private static final String ID = "id";
	private static final String BOOKS = "books";
	
	public static List<Map<String,Object>> parserXml(InputStream dataInputStream) throws Exception {
		/**
		 * 解析xml数据
		 * 1、构建解析器
		 * 2、设置要解析的输入流和字符集
		 * 3、获取解析器返回的事件类型，此为判断依据
		 */
		XmlPullParser dataPullParser = Xml.newPullParser();
		dataPullParser.setInput(dataInputStream, "utf-8");
		int eventType = dataPullParser.getEventType();
		
		List<Map<String, Object>> bookList = null;
		Map<String,Object> bookItem = null;
		
		//根据返回的eventType判断数据
		while(eventType!=XmlPullParser.END_DOCUMENT) { //如果解析器还没有解析到文件结尾，则循环解析不同的节点
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:  //文档开始
				bookList = new ArrayList<Map<String,Object>>();
				break;
			case XmlPullParser.START_TAG:    //开始节点
				String tag = dataPullParser.getName();  //获取节点名字
				if(BOOK.equals(tag)) {    //判断是否是<book>节点
					bookItem = new HashMap<String, Object>();
					//获取<book id=" ">节点中的id，作为bookItem的一个参数
					int id = Integer.parseInt(dataPullParser.getAttributeValue(0));//0表示该属性在当前标签的索引号
					bookItem.put(ID, id);  //添加id参数到bootItem
				} else if(NAME.equals(tag)||PRICE.equals(tag)) {  //判断是否是<name>或者<price>节点
					bookItem.put(tag, dataPullParser.nextText()); //把节点作为key和节点下定义属性作为value
				}
				break;
			case XmlPullParser.END_TAG:  //结束节点
				//判断是否是</book>节点，如果是，刚好是一项bookItem获取完毕，可以添加到bookList
				if(BOOK.equals(dataPullParser.getName())) {
					bookList.add(bookItem);
				}
				break;

			default:
				break;
			}
			eventType = dataPullParser.next();  //必须手动指向下一个节点并且赋值给eventType，继续往下解析，直到到达END_DOCUMENT,跳出循环
		}
		
		return bookList;    //最终把解析完的数据返回
		
	}

	public static void exportXmlData(List<Map<String, Object>> bookList,
			FileOutputStream fos) throws Exception{
		// 构建序列号器
		XmlSerializer xmlSerializer = Xml.newSerializer();
		xmlSerializer.setOutput(fos, "utf-8");   //设置输出文件和字符集
		xmlSerializer.startDocument("utf-8", true);  //standalone：是否可以单独存在
		xmlSerializer.startTag(null, BOOKS);  //设置<books>节点，命名空间为null，自动分配
		for(Map<String,Object> map:bookList) {
			xmlSerializer.startTag(null, BOOK);
			xmlSerializer.attribute(null, ID, map.get(ID).toString()); //设置<book>节点的id属性
			xmlSerializer.startTag(null, NAME);
			xmlSerializer.text(map.get(NAME).toString());
			xmlSerializer.endTag(null, NAME);
			xmlSerializer.startTag(null, PRICE);
			xmlSerializer.text(map.get(PRICE).toString());
			xmlSerializer.endTag(null, PRICE);
			xmlSerializer.endTag(null, BOOK);
		}
		xmlSerializer.endTag(null, BOOKS);    //设置</books>节点,
		xmlSerializer.endDocument();
		
	}

}
