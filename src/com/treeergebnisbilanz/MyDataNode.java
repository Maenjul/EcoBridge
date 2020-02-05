package com.treeergebnisbilanz;

import java.util.Collections;
import java.util.List;

public class MyDataNode {

    private String hierarchie;
    private String lebenszyklusphase;
    private Double gwp;
    private Double odp;
    private Double pocp;
    private Double ap;
    private Double ep;
    private Double adpe;
    private Double adpf;
    private Double pene;
    private Double pe;


    private List<MyDataNode> children;

    public MyDataNode(String hierarchie, String lebenszyklusphase, Double [] wb, List<MyDataNode> children) {
        this.hierarchie = hierarchie;
        this.lebenszyklusphase = lebenszyklusphase;

        this.gwp =  wb[0];
        this.odp = wb[1];
        this.pocp =  wb[2];
        this.ap =  wb[3];
        this.ep = wb[4];
        this.adpe = wb[5];
        this.adpf = wb[6];
        this.pene= wb[7];
        this.pe = wb[8];

        this.children = children;

        if (this.children == null) {
            this.children = Collections.emptyList();
        }
    }

    public String getHierarchie() {
        return hierarchie;
    }

    public String getLebenszyklusphase() {
        return lebenszyklusphase;
    }

    public Double getGwp() {
        return gwp;
    }

    public Double getOdp() {
        return odp;
    }

    public Double getPocp() {
        return pocp;
    }

    public Double getAp() {
        return ap;
    }

    public Double getEp() {
        return ep;
    }

    public Double getAdpe() {
        return adpe;
    }

    public Double getAdpf() {
        return adpf;
    }

    public Double getPene() {
        return pene;
    }

    public Double getPe() {
        return pe;
    }

    public List<MyDataNode> getChildren() {
        return children;
    }

    /**
     * Knotentext vom JTree.
     */
    public String toString() {
        return hierarchie;
    }
}