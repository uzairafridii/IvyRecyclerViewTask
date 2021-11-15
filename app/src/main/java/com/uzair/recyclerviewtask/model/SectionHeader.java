package com.uzair.recyclerviewtask.model;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.List;

public class SectionHeader  implements Section<Items>
{
    List<Items> itemsList;
    String sectionText;

    public SectionHeader(List<Items> itemsList, String sectionText) {
        this.itemsList = itemsList;
        this.sectionText = sectionText;
    }

    @Override
    public List<Items> getChildItems() {
        return itemsList;
    }

    public String getSectionText() {
        return sectionText;
    }


}
