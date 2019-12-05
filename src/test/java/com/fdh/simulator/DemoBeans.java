package com.fdh.simulator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DemoBeans {  
	  
    private String demoName;  
      
    PropertyChangeSupport listeners = new PropertyChangeSupport(this);  
  
    public DemoBeans() {  
        demoName = "initValue";  
    }  
  
  
    public String getDemoName() {  
        return demoName;  
    }  
      
  
    public void setDemoName(String demoName) {  
        String oldValue = this.demoName;  
        this.demoName = demoName;  
        //发布监听事件  
        firePropertyChange("demoName", oldValue, demoName);  
//        new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds)
          
    }  
      
    public void addPropertyChangeListener(PropertyChangeListener listener) {  
        listeners.addPropertyChangeListener(listener);  
    }  
      
    public void removePropertyChangeListener(PropertyChangeListener listener){  
        listeners.addPropertyChangeListener(listener);  
    }  
      
  
    /** 
     * 触发属性改变的事件 
     */  
    protected void firePropertyChange(String prop, Object oldValue, Object newValue) {  
        listeners.firePropertyChange(prop, oldValue, newValue);  
    }  
     
}  