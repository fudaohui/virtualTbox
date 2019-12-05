package com.fdh.simulator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//监听属性变化的类
		DemoBeans beans = new DemoBeans();
		beans.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println("OldValue:" + evt.getOldValue());
				System.out.println("NewValue:" + evt.getNewValue());
				System.out.println("tPropertyName:" + evt.getPropertyName());
			}
		});
		beans.setDemoName("test");
	}

}
