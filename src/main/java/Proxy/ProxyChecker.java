package Proxy;

import Utils.FileProcessor;
import lombok.AllArgsConstructor;
import java.net.*;
import java.util.List;
import java.util.stream.Collectors;

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
           if(urlConnection.getResponseCode() == 200) System.out.println(proxy + " OK");
           return (urlConnection.getResponseCode() == 200);
        } catch(Exception e) {
            System.out.println(proxy + " Error " + e);
            return false;
        }finally {
            if(urlConnection != null)urlConnection.disconnect();
        }
    }
    public void checkProxyAndSaveToFile(List<String> list){
        System.out.println("Проверка\n_______________________________________________________");
        List<String> proxylist = list.parallelStream()
                .map(ProxyUtil::stringToProxy)
                .filter(this::checkProxy)
                .map(Proxy::toString)
                .collect(Collectors.toList());
        if(proxylist.isEmpty()){
            System.out.println("_______________________________________________________\nРабочих прокси нет. Повторите подбор");

        }else {
            System.out.println(
                    "_______________________________________________________\nСохранение "+proxylist.size()+" прокси в файл\n_______________________________________________________");
            FileProcessor.writListToFile(proxylist, "src/tmp/out.txt");
        }
    }
}