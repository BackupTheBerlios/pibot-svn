
package feature.util.common.timer;

import java.awt.Component;
import java.beans.PropertyEditorSupport;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class IntervalEditor extends PropertyEditorSupport {
	private class SliderPanel extends JPanel {
		private static final long serialVersionUID = 20000;
		
		private JSlider slider = new JSlider(0, 10000);
		
		public SliderPanel() {
			this.slider.setMajorTickSpacing(2000);
			this.slider.setMinorTickSpacing(1000);
			this.slider.setSnapToTicks(true);
			this.slider.setPaintTicks(true);
			this.slider.setPaintLabels(true);
			this.slider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					IntervalEditor.this.firePropertyChange();
				}
			});
			this.add(slider);
		}
		
		public void setValue(int val) {
			this.slider.setValue(val);
		}
		
		public int getValue() {
			return this.slider.getValue();
		}
	}//class SliderPanel
	
	protected SliderPanel sliderPanel;
	
	public IntervalEditor() {
		this.sliderPanel = new SliderPanel();
	}
	
	public void setValue(Object value) {
		this.sliderPanel.setValue(((Number)value).intValue());
	}
	
	public Object getValue() {
		return new Integer(this.sliderPanel.getValue());
	}
	
	public String getAsText() {
		return this.getValue().toString() + " ms";
	}
	
	public boolean supportsCustomEditor() {
		return true;
	}
	
	public Component getCustomEditor() {
		return this.sliderPanel;
	}
	
	public String getJavaInitializationString() {
		return this.getValue().toString();
	}
	
}
