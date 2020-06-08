package com.example.bicycleapp;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

public class AccidentParser {

    private static String APIkey = "i7hUwTM8Zfmbt9KD8A%2Bgeh1N61Yl%2FY50rxRFI%2BiR0psYWixhym4%2BfpIKX0hiAB%2Fs";

    public AccidentParser(){
        try {
            apiParserSearch();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<AccidentDTO> apiParserSearch() throws Exception {
        URL url = new URL(getURLAccident(null));

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        xpp.setInput(bis, "utf-8");

        String tag = null;
        int event_type = xpp.getEventType();

        ArrayList<AccidentDTO> accidentDTO = new ArrayList<AccidentDTO>();

        String occrrnc_cnt = null, la_crd= null, lo_crd=null;
        boolean boccrrnc_cnt = false, bla_crd = false, blo_crd = false;

        while (event_type != XmlPullParser.END_DOCUMENT) {
            if (event_type == XmlPullParser.START_TAG) {
                tag = xpp.getName();
                if(tag.equals("occrrnc_cnt")){
                    boccrrnc_cnt = true;
                }else if(tag.equals("la_crd")){
                    bla_crd = true;
                }else if(tag.equals("lo_crd")){
                    blo_crd = true;
                }
            } else if (event_type == XmlPullParser.TEXT) {
                if (boccrrnc_cnt == true) {
                    occrrnc_cnt = xpp.getText();
                    boccrrnc_cnt = false;
                } else if (bla_crd == true) {
                    la_crd = xpp.getText();
                    bla_crd = false;
                } else if (blo_crd == true) {
                    lo_crd = xpp.getText();
                    blo_crd = false;
                }
            }else if(event_type == XmlPullParser.END_TAG){
                tag = xpp.getName();
                if (tag.equals("item")) {
                    AccidentDTO entity = new AccidentDTO();
                    entity.setAccident(occrrnc_cnt);
                    entity.setLatitude(Double.valueOf(la_crd));
                    entity.setLongitude(Double.valueOf(lo_crd));
                    accidentDTO.add(entity);
                    System.out.println(accidentDTO.size());
                }
            }
            event_type = xpp.next();
        }
        System.out.println(accidentDTO.size());
        System.out.println(accidentDTO);
        return accidentDTO;
    }


    private String getURLAccident(String search){
        String url = "http://taas.koroad.or.kr/data/rest/frequentzone/bicycle?authKey=i7hUwTM8Zfmbt9KD8A%2Bgeh1N61Yl%2FY50rxRFI%2BiR0psYWixhym4%2BfpIKX0hiAB%2Fs&searchYearCd=2018&siDo=11&guGun=680&type=xml";
        return url;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new AccidentParser();
    }

}