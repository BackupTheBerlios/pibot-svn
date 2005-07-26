
package feature.util.common.timer;

import java.awt.Image;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;


public class TimerBeanInfo extends SimpleBeanInfo {
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			PropertyDescriptor intervalProp = new PropertyDescriptor("interval", Timer.class);
			PropertyDescriptor noTicksProp = new PropertyDescriptor("noTicks", Timer.class);
			
			intervalProp.setPropertyEditorClass(IntervalEditor.class);
			return new PropertyDescriptor[] {intervalProp, noTicksProp};
		}
		catch(IntrospectionException e) {
			e.printStackTrace();
		}
		return super.getPropertyDescriptors();
	}
	
	public EventSetDescriptor[] getEventSetDescriptors() {
		EventSetDescriptor expired = null;
		EventSetDescriptor propChanged = null;
		try {
			expired = new EventSetDescriptor(Timer.class, "timer", TimerListener.class, "expired");
			propChanged = new EventSetDescriptor(Timer.class, "timer", PropertyChangeListener.class, "propertyChanged");	
			return new EventSetDescriptor[]  {expired, propChanged};
		}
		catch(IntrospectionException e) {
			e.printStackTrace();
		}
		return super.getEventSetDescriptors();
	}
	
	public Image getIcon(int iconKind) {
		if(iconKind == BeanInfo.ICON_COLOR_16x16)
			return loadImage("Timer16.gif");
		else if(iconKind == BeanInfo.ICON_COLOR_32x32)
			return loadImage("Timer32.gif");
		else return super.getIcon(iconKind);
	}
}
