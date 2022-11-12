package ca.landonjw.gooeylibs2.api.helpers;

import ca.landonjw.gooeylibs2.api.template.slot.TemplateSlotDelegate;

public class TemplateHelper {

    public static TemplateSlotDelegate[] slotsOf(int size) {
        TemplateSlotDelegate[] elements = new TemplateSlotDelegate[size];
        for (int i = 0; i < size; i++) {
            elements[i] = new TemplateSlotDelegate(null, i);
        }
        return elements;
    }

}
