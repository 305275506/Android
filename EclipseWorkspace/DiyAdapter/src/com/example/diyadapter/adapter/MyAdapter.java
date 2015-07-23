/**
 * 创建自定义的adapter类，继承BaseAdapter
 * 并实现BaseAdapter的四个方法
 */
package com.example.diyadapter.adapter;
import java.util.List;

import com.example.diyadapter.model.ChatMessage;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	List<ChatMessage> chatList;
	Context context;
	int[] resource;
	int[] resourceId;
	public MyAdapter(List<ChatMessage> chatList,Context context,int[] resource,int[] resourceId) {
		this.chatList = chatList;
		this.context = context;
		this.resource = resource;       //列表项视图layout id
		this.resourceId = resourceId;   //内容资源id
	}

	//获取列表项的数量
	public int getCount() {
		//对应chatList的size()
		return chatList==null?0:chatList.size();
	}

	//获取列表项的位置，成功返回该项
	public Object getItem(int position) {
		// 对应get(int location)的返回对象
		return chatList==null?null:chatList.get(position);
	}

	//获取列表项id
	public long getItemId(int position) {
		// 列表项id对应，chatMessage中的id
		return chatList==null?0:chatList.get(position).id;
	}

    /**
     * 取得当前要展示的列表项的视图， 该方法要依赖getCount方法，每次显示一个列表项视图都会调用该方法
     * position：当前要显示的列表项的数据在适配器中的位置 
     * convertView：View ： 可回收的View 
     * parent： ViewGroup ，列表项的父控件 ：ListView
     */
	public View getView(int position, View convertView, ViewGroup parent) {
		// 1. 设置好列表项布局   2. 当前列表项要装配的数据
		// 获取当前列表项要装配的数据 当前的chatMessage
		ChatMessage chatMessage = chatList.get(position);
		/**
		 *  实例化列表项布局为View对象
		 *  resource[0]：列表项的资源id
		 *  root：父控件 ：ListView
		 *  attachToRoot：是否要添加到父控件中,是通过addView的方式添加到父控件中,假如为假，只为列表项布局的根节点正确创建布局参数数据
		 *      比如： android:layout_height =“120dp ”
		 */
//		View itemView = LayoutInflater.from(context).inflate(resource[0], parent, false);
//	
//		//自定义适配器 作用： 1.操纵列表项布局控件的属性 2.操纵列表项装配的数据
//		TextView messageTxt=(TextView) itemView.findViewById(resourceId[0]);
//		messageTxt.setTextColor(Color.RED);//设置消息文本框的文本颜色为红色
//		messageTxt.setText("传智"+chatMessage.message);//操纵列表项的数据
//		System.out.println("行号："+position+" ID:" +chatMessage.id+ " View 对象:"+itemView);
//		return itemView;
		
		/**
		 * 通过convertView来优化adapter，
		 */
		//判断是否有可回收的view，如果没有则创建对象
//		if(convertView==null) {
//			convertView = LayoutInflater.from(context).inflate(resource[0], parent, false);
//		}
//		//自定义适配器 作用： 1.操纵列表项布局控件的属性 2.操纵列表项装配的数据
//		TextView messageTxt=(TextView) convertView.findViewById(resourceId[0]);
//		messageTxt.setTextColor(Color.RED);//设置消息文本框的文本颜色为红色
//		messageTxt.setText("优化"+chatMessage.message);//操纵列表项的数据
//		System.out.println("行号："+position+" ID:" +chatMessage.id+ " View 对象:"+convertView);
//		return convertView;
//		
	
	/**
	 * 通过创建一个HolderView类来优化adapter
	 * HolderView中记录了TextView对象，从而优化查询算法
	 */
//		HolderView holderView;
//		//判断是否有可回收的view，如果没有则创建对象
//		if(convertView==null) {
//			convertView = LayoutInflater.from(context).inflate(resource[0], parent, false);
//			holderView = new HolderView();
//			holderView.chatMessageView = (TextView) convertView.findViewById(resourceId[0]);
//			convertView.setTag(holderView);//通过对View设置tag标记来保存对要操作的子控件的引用
//		} else {
//			holderView = (HolderView)convertView.getTag();
//		}
//		//自定义适配器 作用： 1.操纵列表项布局控件的属性 2.操纵列表项装配的数据
////		TextView messageTxt=(TextView) convertView.findViewById(resourceId[0]); //findViewById方法会跟各项id比较，效率低
//		holderView.chatMessageView.setTextColor(Color.RED);//设置消息文本框的文本颜色为红色
//		holderView.chatMessageView.setText("优化"+chatMessage.message);//操纵列表项的数据
//		System.out.println("行号："+position+" ID:" +chatMessage.id+ " View 对象:"+convertView);
//		return convertView;
		
		/**
		 * ListView实现多个列表项布局
	     *根据发信息的对象不同，实现左右两个布局
	     *通过覆盖getItemViewType和getViewTypeCount来实现
		 */
		HolderView holderView;
		//判断是否有可回收的view，如果没有则创建对象
		if(convertView==null) {
			
			convertView = LayoutInflater.from(context).inflate(resource[getItemViewType(position)],
						parent, false);
			holderView = new HolderView();
			holderView.chatMessageView = (TextView) convertView.findViewById(resourceId[0]);
			convertView.setTag(holderView);//通过对View设置tag标记来保存对要操作的子控件的引用
		} else {
			holderView = (HolderView)convertView.getTag();
		}
		//自定义适配器 作用： 1.操纵列表项布局控件的属性 2.操纵列表项装配的数据
//		TextView messageTxt=(TextView) convertView.findViewById(resourceId[0]); //findViewById方法会跟各项id比较，效率低
		holderView.chatMessageView.setTextColor(Color.RED);//设置消息文本框的文本颜色为红色
		holderView.chatMessageView.setText("双向"+chatMessage.message);//操纵列表项的数据
		System.out.println("行号："+position+" ID:" +chatMessage.id+ " View 对象:"+convertView);
		return convertView;
		
	}
	
	/**
	 *ListView实现多个列表项布局
	 *根据发信息的对象不同，实现左右两个布局
	 *通过覆盖getItemViewType和getViewTypeCount来实现
	 */
	@Override
	public int getItemViewType(int position) {
		
		return chatList.get(position).isSelf?1:0;
	}
	
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	//定义一个HolderView私有类
	private class HolderView {
		TextView chatMessageView;
	}

}
