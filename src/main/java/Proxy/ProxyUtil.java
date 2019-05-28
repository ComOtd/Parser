package Proxy;

import Utils.FileProcessor;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProxyUtil {
    public static List<Proxy> proxyFileToList(String path){
        List<String> list = FileProcessor.getListFromFile(path);
        List<Proxy> proxylist = new ArrayList<>();
        for (String l : list) {
            Proxy proxy = stringToProxy(l);
            proxylist.add(proxy);
        }
        return proxylist;
    }

    private static Proxy getProxy(String pHost, int pPort, String pType) {
        SocketAddress addr = new InetSocketAddress(pHost, pPort);
        Proxy.Type _pType = (pType.equals("HTTP") ? Proxy.Type.HTTP : Proxy.Type.SOCKS);
        return new Proxy(_pType, addr);
    }

    static Proxy stringToProxy(String string){
        String pHost ="";
        int pPort = 0;
        String pType="";

        Pattern hostPattern = Pattern.compile("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}");
        Pattern portPattern = Pattern.compile(":\\d{2,4}");
        Pattern typePattern = Pattern.compile("[A-Z]{4,5}");
        Matcher hostMatcher = hostPattern.matcher(string);
        Matcher portMatcher = portPattern.matcher(string);
        Matcher typeMatcher = typePattern.matcher(string);

        if(hostMatcher.find())pHost = hostMatcher.group();
        if(portMatcher.find())pPort = Integer.parseInt(portMatcher.group().replace(":",""));
        if(typeMatcher.find())pType = typeMatcher.group();

        return getProxy(pHost, pPort, pType);
    }
}
