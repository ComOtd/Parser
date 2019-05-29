package Proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProxyUtil {
    public static List<Proxy> proxyFileToList(String path){
        List<Proxy> proxylist = null;
        try {
            proxylist =  Files.lines(Paths.get(path))
                    .map(ProxyUtil::stringToProxy)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proxylist;
    }

    private static Proxy getProxy(String pHost, int pPort, String pType) {
        SocketAddress addr = new InetSocketAddress(pHost, pPort);
        Proxy.Type _pType = (pType.equals("HTTP") ? Proxy.Type.HTTP : Proxy.Type.SOCKS);
        return new Proxy(_pType, addr);
    }

    static Proxy stringToProxy(String string){
        Pattern hostPattern = Pattern.compile("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}");
        Pattern portPattern = Pattern.compile(":\\d{2,4}");
        Pattern typePattern = Pattern.compile("[A-Z]{4,5}");

        String pHost = hostPattern.matcher(string).results().map(MatchResult::group).collect(Collectors.joining());
        String pPort = portPattern.matcher(string).results().map(MatchResult::group)
                .map(text -> text.replace(":", ""))
                .collect(Collectors.joining());
        int port = Integer.parseInt(pPort);
        String pType = typePattern.matcher(string).results().map(MatchResult::group).collect(Collectors.joining());
        return getProxy(pHost, port, pType);
    }
}
