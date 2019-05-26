import lombok.AllArgsConstructor;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class ProxyChecker {

    private String checkUrl;

    private Proxy getProxy(String pHost, int pPort, String pType) {
        SocketAddress addr = new InetSocketAddress(pHost, pPort);
        Proxy.Type _pType = (pType.equals("HTTP") ? Proxy.Type.HTTP : Proxy.Type.SOCKS);
        return new Proxy(_pType, addr);
    }

    private List<String> getListFromFile(String path) {
        File file = new File(path);
        List <String> list = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while((line = bufferedReader.readLine()) != null){
                list.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Proxy stringToProxy(String string){
        Pattern hostPattern = Pattern.compile("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}");
        Pattern portPattern = Pattern.compile(":\\d{2,4}");
        Pattern typePattern = Pattern.compile("[A-Z]{4,5}");
        Matcher hostMatcher = hostPattern.matcher(string);
        Matcher portMatcher = portPattern.matcher(string);
        Matcher typeMatcher = typePattern.matcher(string);

        hostMatcher.find();
        portMatcher.find();
        typeMatcher.find();

        String pHost = hostMatcher.group();
        int pPort = Integer.parseInt(portMatcher.group().replace(":",""));
        String pType = typeMatcher.group();

        return getProxy(pHost, pPort, pType);
    }

    private boolean checkProxy(Proxy proxy){
        URL url;
        HttpURLConnection urlConnection = null;
        try {
           url = new URL(checkUrl);
           urlConnection = (HttpURLConnection) url.openConnection(proxy);
           urlConnection.setConnectTimeout(5000);
           urlConnection.connect();
           return (urlConnection.getResponseCode() == 200);

        } catch(SocketException e) {return false;}
        catch(SocketTimeoutException e) {return false;}
        catch(Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    public List<Proxy> checkProxyFromFile(String path){
        List<String> list = getListFromFile(path);
        List<Proxy> proxylist = new ArrayList<>();
        for (String l : list) {
            Proxy proxy = stringToProxy(l);
            if(checkProxy(proxy)) {
                proxylist.add(proxy);
                System.out.println(l);
            };
        }
        return proxylist;
    }
}