package com.integbridge;

import java.util.ArrayList;

import static com.integbridge.Enums.getWbTemplate;

public class GrobElement {
    private String bezeichnung;
    private ArrayList <GrobElementInstanz> grobElementInstanzArrayList;
    private Double [] wirkungsbilanz_kumuliert;

    public GrobElement(String bezeichnung, ArrayList <GrobElementInstanz> grobElementInstanzArrayList) {
        this.grobElementInstanzArrayList = grobElementInstanzArrayList;
        this.bezeichnung = bezeichnung;
        this.wirkungsbilanz_kumuliert = getWbTemplate().clone();
        for (GrobElementInstanz grobElementInstanz : this.grobElementInstanzArrayList)
        {
            for (int i = 0; i < getWbTemplate().length; i++) {
                this.wirkungsbilanz_kumuliert[i] += grobElementInstanz.getWirkungsbilanz_kumuliert()[i];
            }
        }
    }

    public Double[] getWirkungsbilanz_kumuliert() {
        return wirkungsbilanz_kumuliert;
    }

    public ArrayList<GrobElementInstanz> getGrobElementInstanzArrayList() {
        return grobElementInstanzArrayList;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }
}


