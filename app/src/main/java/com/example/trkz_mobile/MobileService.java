package com.example.trkz_mobile;

import android.util.Log;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MobileService {
    final String NAMESPACE = "http://tempuri.org/";
//    final String URL = "http://192.168.1.26/TrkzService.asmx";
    final String URL = "http://192.168.1.26:32486/trkzservice.asmx";
    final String SOAP_ACTION1 = "http://tempuri.org/Is_Handling_Unit_In_Stoc";
    final String METHOD_NAME1 = "Is_Handling_Unit_In_Stock";
    final String SOAP_ACTION = "http://tempuri.org/Get_Shipments";
    final String METHOD_NAME = "Get_Shipments";
    SoapSerializationEnvelope envelope;
    SoapObject request_;

    // VARIABLES FOR PALETLER
    final String P_SOAP_ACTION = "http://tempuri.org/GetPaletler";
    final String P_METHOD_NAME = "GetPaletler";
    // VARIABLES FOR PALETLER 2
    final String P_SOAP_ACTION2 = "http://tempuri.org/GetPaletler2";
    final String P_METHOD_NAME2 = "GetPaletler2";

    HttpTransportSE httpTransportSE;

    public  Boolean IsHuInStock(int hu_id_,String location_no_, String contract_){
        Boolean result_ = false;
        request_ = new SoapObject(NAMESPACE,METHOD_NAME1);
        request_.addProperty("handling_unit_id_",hu_id_);
        request_.addProperty("location_no_",location_no_);
        request_.addProperty("contract_",contract_);

        MarshalBase64 mBase = new MarshalBase64();

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request_;
        envelope.encodingStyle = SoapSerializationEnvelope.ENC2001;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request_);

        httpTransportSE = new HttpTransportSE(URL);
        mBase.register(envelope);
        httpTransportSE.debug = true;
        try {
            httpTransportSE.call(SOAP_ACTION1,envelope);
            SoapObject resultSoap = (SoapObject) envelope.bodyIn;

            if (resultSoap!=null)
            {
                result_ = Boolean.parseBoolean(resultSoap.getProperty(0).toString());
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return result_;
    }

    public String GetShipments(String contract_){
        String shipments ="";

        SoapObject service_ = new SoapObject(NAMESPACE,METHOD_NAME);
        service_.addProperty("contract_",contract_);

        SoapSerializationEnvelope env_ = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        env_.setOutputSoapObject(service_);
        env_.dotNet = true;

        try {
            httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION,env_);
            String ship_list = httpTransportSE.responseDump;

            if (ship_list!=null)
            {
                return  ship_list;
            }
            else{
                return "No data Found";
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return shipments;
    }

    // PALETLER 2 XML
    public String xmlGetPaletler(String depo_no_){
        String paletler ="";
        SoapObject service_ = new SoapObject(NAMESPACE,P_METHOD_NAME2);
        service_.addProperty("depo_no_",depo_no_);
        SoapSerializationEnvelope env_ = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        env_.setOutputSoapObject(service_);
        env_.dotNet = true;
        try {
            httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            httpTransportSE.debug = true;
            httpTransportSE.call(P_SOAP_ACTION2,env_);
            String paletList = httpTransportSE.responseDump;
            if (paletList!=null)
            { return  paletList;}
            else{ return "No data Found";}
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return paletler;
    }

    public String GetPaletler(String depo_no_){
        String paletList ="";

        SoapObject service_ = new SoapObject(NAMESPACE,P_METHOD_NAME);
        service_.addProperty("depo_no_",depo_no_);

        SoapSerializationEnvelope env_ = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        env_.dotNet = true;
        env_.setOutputSoapObject(service_);

        try {
            httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.debug = true;
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            //  Invoke service
            httpTransportSE.call(P_SOAP_ACTION, env_);

            // Get response
            SoapPrimitive response  = (SoapPrimitive) env_.getResponse();
//            Log.d("RESPONSE:", String.valueOf(response));
//            String responseJSON = response.toString();
            paletList = response.toString();

            if (paletList != null)
            {
                return  paletList;
            }
            else{
                return "No data Found";
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return paletList;
    }

    // GET CUSTOMERS
    final String CUSTOMERS_METHOD = "Get_Customers";
    final String CUSTOMERS_SOAP_ACTION = "http://tempuri.org/Get_Customers";
    public String getCustomers(){
        String customers ="";
        SoapObject service_ = new SoapObject(NAMESPACE,CUSTOMERS_METHOD);
//        service_.addProperty("depo_no_",depo_no_);
        SoapSerializationEnvelope env_ = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        env_.setOutputSoapObject(service_);
        env_.dotNet = true;
        try {
            httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            httpTransportSE.debug = true;
            httpTransportSE.call(CUSTOMERS_SOAP_ACTION,env_);
            customers = httpTransportSE.responseDump;
            if (customers!=null)
            { return  customers;}
            else{ return "No data Found";}
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return customers;
    }

    // GET PROD DATA
    final String PROD_DATA_METHOD = "Eslestirme";
    final String PROD_DATA_SOAP_ACTION = "http://tempuri.org/Eslestirme";
    public String getPROD_DATA(String cari_, String barkod_){
        String data ="";
        SoapObject service_ = new SoapObject(NAMESPACE,PROD_DATA_METHOD);
        service_.addProperty("cari_",cari_);
        service_.addProperty("barkod_",barkod_);
        SoapSerializationEnvelope env_ = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        env_.setOutputSoapObject(service_);
        env_.dotNet = true;
        try {
            httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            httpTransportSE.debug = true;
            httpTransportSE.call(PROD_DATA_SOAP_ACTION,env_);
            data = httpTransportSE.responseDump;
            if (data!=null)
            { return  data;}
            else{ return "No data Found";}
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return data;
    }

    final String GET_HAND_U_TYPE_METHOD = "Get_Handling_Unit_Types";
    final String GET_HAND_U_TYPE_METHOD_SOAP_ACTION = "http://tempuri.org/Get_Handling_Unit_Types";
    public String getHandlingUnitType(String category_id_, String boy_kisa_, String part_no_){
        String data ="";
        SoapObject service_ = new SoapObject(NAMESPACE,GET_HAND_U_TYPE_METHOD);
        service_.addProperty("category_id_",category_id_);
        service_.addProperty("boy_kisa_",boy_kisa_);
        service_.addProperty("part_no_",part_no_);
        SoapSerializationEnvelope env_ = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        env_.setOutputSoapObject(service_);
        env_.dotNet = true;
        try {
            httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            httpTransportSE.debug = true;
            httpTransportSE.call(GET_HAND_U_TYPE_METHOD_SOAP_ACTION,env_);
            data = httpTransportSE.responseDump;
            if (data!=null)
            { return  data;}
            else{ return "No data Found";}
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return data;
    }

    final String PALET_OLUSTUR_TYPE_METHOD = "PaletOlustur";
    final String PALET_OLUSTUR_METHOD_SOAP_ACTION = "http://tempuri.org/PaletOlustur";
    public String paletOlustur(int PaletRef_, int KullaniciRef_, String cariKod_, String cariAdi_, Boolean BoyMu_,
                               String PaletTipi_, String SecilmisUrunKodu_, int takimAdet_,
                               Boolean takimDetayli_, int Adet_, Boolean hollandaDepoMu_, String YeniKod_,
                               String IfsHandlingUnitType_, String BarkodOnEk_, int BarkodUzunluk_){
        String data ="";
        SoapObject service_ = new SoapObject(NAMESPACE,PALET_OLUSTUR_TYPE_METHOD);
        service_.addProperty("PaletRef_",PaletRef_);
        service_.addProperty("KullaniciRef_",KullaniciRef_);
        service_.addProperty("cariKod_",cariKod_);
        service_.addProperty("cariAdi_",cariAdi_);
        service_.addProperty("BoyMu_",BoyMu_);
        service_.addProperty("PaletTipi_",PaletTipi_);
        service_.addProperty("SecilmisUrunKodu_",SecilmisUrunKodu_);
        service_.addProperty("takimAdet_",takimAdet_);
        service_.addProperty("takimDetayli_",takimDetayli_);
        service_.addProperty("Adet_",Adet_);
        service_.addProperty("hollandaDepoMu_",hollandaDepoMu_);
        service_.addProperty("YeniKod_",YeniKod_);
        service_.addProperty("IfsHandlingUnitType_",IfsHandlingUnitType_);
        service_.addProperty("BarkodOnEk_",BarkodOnEk_);
        service_.addProperty("BarkodUzunluk_",BarkodUzunluk_);
//        PaletRef_,
//                KullaniciRef_,
//                cariKod_,
//                cariAdi_,
//                BoyMu_,
//                PaletTipi_,
//                SecilmisUrunKodu_,
//                takimAdet_,
//                takimDetayli_,
//                Adet_,
//                hollandaDepoMu_,
//                YeniKod_,
//                IfsHandlingUnitType_,
//                BarkodOnEk_,
//                BarkodUzunluk_
        SoapSerializationEnvelope env_ = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        env_.setOutputSoapObject(service_);
        env_.dotNet = true;
        try {
            httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            httpTransportSE.debug = true;
            httpTransportSE.call(PALET_OLUSTUR_METHOD_SOAP_ACTION,env_);
            data = httpTransportSE.responseDump;
            if (data!=null)
            { return  data;}
            else{ return "No data Found";}
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return data;
    }

    // PALET BILGILERI
    final String PALET_BILGILERI_METHOD = "PaletBilgileri";
    final String PALET_BILGILERI_METHOD_SOAP_ACTION = "http://tempuri.org/PaletBilgileri";
    public String paletBilgileri(String PaletRef_){
        String data ="";
        SoapObject service_ = new SoapObject(NAMESPACE,PALET_BILGILERI_METHOD);
        service_.addProperty("PaletRef_",PaletRef_);

        SoapSerializationEnvelope env_ = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        env_.setOutputSoapObject(service_);
        env_.dotNet = true;
        try {
            httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            httpTransportSE.debug = true;
            httpTransportSE.call(PALET_BILGILERI_METHOD_SOAP_ACTION,env_);
            data = httpTransportSE.responseDump;
            if (data!=null)
            { return  data;}
            else{ return "No data Found";}
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return data;
    }

    // PALET ICERIK DETAY
    final String PALET_ICERIK_DETAY_METHOD = "PaletIcerikDetay";
    final String PALET_ICERIK_DETAY_METHOD_SOAP_ACTION = "http://tempuri.org/PaletIcerikDetay";
    public String paletIcerikDetay(String PaletRef_){
        String data ="";
        SoapObject service_ = new SoapObject(NAMESPACE,PALET_ICERIK_DETAY_METHOD);
        service_.addProperty("PaletRef_",PaletRef_);

        SoapSerializationEnvelope env_ = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        env_.setOutputSoapObject(service_);
        env_.dotNet = true;
        try {
            httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            httpTransportSE.debug = true;
            httpTransportSE.call(PALET_ICERIK_DETAY_METHOD_SOAP_ACTION,env_);
            data = httpTransportSE.responseDump;
            if (data!=null)
            { return  data;}
            else{ return "No data Found";}
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return data;
    }

    // PALET ICERIK OZET
    final String PALET_ICERIK_OZET_METHOD = "PaletIcerikOzet";
    final String PALET_ICERIK_OZET_METHOD_SOAP_ACTION = "http://tempuri.org/PaletIcerikOzet";
    public String paletIcerikOzet(String PaletRef_){
        String data ="";
        SoapObject service_ = new SoapObject(NAMESPACE,PALET_ICERIK_OZET_METHOD);
        service_.addProperty("PaletRef_",PaletRef_);

        SoapSerializationEnvelope env_ = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        env_.setOutputSoapObject(service_);
        env_.dotNet = true;
        try {
            httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            httpTransportSE.debug = true;
            httpTransportSE.call(PALET_ICERIK_OZET_METHOD_SOAP_ACTION,env_);
            data = httpTransportSE.responseDump;
            if (data!=null)
            { return  data;}
            else{ return "No data Found";}
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return data;
    }

}