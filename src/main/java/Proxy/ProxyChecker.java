package Proxy;

import Utils.FileProcessor;
import lombok.AllArgsConstructor;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


import static Proxy.ProxyUtil.stringToProxy;

@AllArgsConstructor
public class ProxyChecker {

    private String checkUrl;



    private boolean checkProxy(Proxy proxy){
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(checkUrl);
           urlConnection = (HttpURLConnection) url.openConnection(proxy);
           urlConnection.setConnectTimeout(5000);
           urlConnection.connect();
           return (urlConnection.getResponseCode() == 200);
        } catch(SocketException | SocketTimeoutException e) {return false;} catch(Exception e) {
            System.out.println("Error: " + e);
            return false;
        }finally {
            if(urlConnection != null)urlConnection.disconnect();
        }
    }

    public void checkProxyAndSaveToFile(String path, int proxyCount){
        List<String> list = FileProcessor.getListFromFile(path);
        List<String> listOfproxy = new ArrayList<>();
        for (String l : list) {
            Proxy proxy = stringToProxy(l);
            if(checkProxy(proxy)) {
                listOfproxy.add(l);
                if(listOfproxy.size()>proxyCount) break;
            };
        }
        FileProcessor.wrihtListToFile(listOfproxy,"src/tmp/out.txt");
    }

    public void checkProxyAndSaveToFile(List<String> list, int proxyCount){
        List<String> proxylist = new ArrayList<>();
        for (String l : list) {
            Proxy proxy = stringToProxy(l);
            if(checkProxy(proxy)) {
                proxylist.add(l);
                if(proxylist.size()>= proxyCount) break;
            }
        }
        FileProcessor.wrihtListToFile(proxylist,"src/tmp/out.txt");
    }
}