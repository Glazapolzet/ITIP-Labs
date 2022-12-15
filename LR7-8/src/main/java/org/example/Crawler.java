package org.example;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 * Этот класс реализует основную функциональность нашего приложения web crawler.
 * Он имеет метод getAllLinks для хранения всех ссылок на данной веб-странице в
 * дополнение к основному методу, который отслеживает важные переменные.
 */
public class Crawler {



    /**
     * Основной метод Crawler. Программа должна принимать строку,
     * представляющую URL-адрес, с которого начинается просмотр, и положительное целое число,
     * представляющее максимальную глубину поиска. Сохраняет URL-адрес в виде строки с
     * глубиной в виде URLDepthPair. Отслеживает обработанные ссылки, ожидающие ссылки, просмотренные
     * ссылки и глубину. Распечатывает список всех обработанных ссылок
     * с их глубиной. Перебирает pendingURLs, чтобы получить все ссылки и добавляет
     * их для обработки URL-адресов и URL-адреса видел.
     */
    public static void main(String[] args) {
        args = new String[]{"https://habr.com/ru/all/", "1"};

// Переменная для текущей глубины.
        int depth = 0;

// Проверяет, была ли входная информация правильной длины
        if (args.length != 2) {
            System.out.println("Используйте: java Crawler <URL> <глубина>");
            System.exit(1);
        }
// Если ввод правильный, продолжает
        else {
            try {
// Перевод строкового элемента в целочисленный тип.
                depth = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException nfe) {
// Второе значене является не допустимым числом

                System.out.println("Используйте: java Crawler <URL> <глубина>");
                System.exit(1);
            }
        }

// список для представления ожидающих URL-адресов.
        LinkedList<URLDepthPair> pendingURLs = new LinkedList<URLDepthPair>();

// Связанный список для представления обработанных URL-адресов.
        LinkedList<URLDepthPair> processedURLs = new LinkedList<URLDepthPair>();

// Пара глубины URL-адреса для представления веб-сайта, введенного пользователем
// с глубиной 0.
        URLDepthPair currentDepthPair = new URLDepthPair(args[0], 0);

// Добавьте текущий веб-сайт из пользовательского ввода в ожидающие URL-адреса.
        pendingURLs.add(currentDepthPair);

// Arraylist для представления URL-адресов, которые были замечены. Добавить текущий веб-
//сайт.
        ArrayList<String> seenURLs = new ArrayList<String>();
        seenURLs.add(currentDepthPair.getURL());

// Пока URL-адреса не пусты, выполните итерацию, посетите каждый веб-сайт и получите все ссылки с каждого.
        while (pendingURLs.size() != 0) {

// Получите следующий URL-адрес из pendingURLs, добавьте его в обработанные URL-адреса и
//сохраняю его глубину.
            URLDepthPair depthPair = pendingURLs.pop();
            processedURLs.add(depthPair);
            int myDepth = depthPair.getDepth();

// Получая все ссылки с сайта и сохраняю их в новом связанном списке.
            LinkedList<String> linksList = new LinkedList<String>();
            linksList = Crawler.getAllLinks(depthPair);

// Если мы не достигли максимальной глубины, добавили ссылку с сайта

            if (myDepth < depth) {
// Итерация по ссылкам с сайта.
                for (int i=0;i<linksList.size();i++) {
                    String newURL = linksList.get(i);
// Если мы уже видели ссылку, продолжаю.
                    if (seenURLs.contains(newURL)) {
                        continue;
                    }
// Если мы еще не видели ссылку, создаем новую пару глубин URL
//адресов с глубиной на единицу больше текущей глубины и добавьте
//к ожидающим URL-адресам и видимым URL-адресам.
                    else {
                        URLDepthPair newDepthPair = new URLDepthPair(newURL, myDepth + 1);
                        pendingURLs.add(newDepthPair);
                        seenURLs.add(newURL);
                    }
                }
            }
        }
// Вывод всех обработанных адресов.
        Iterator<URLDepthPair> iter = processedURLs.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
    /**
     * Метод, который принимает пару глубины URL-адреса и возвращает LinkedList строкового
     * типа. Подключается к веб-сайту, находит все ссылки
     * на сайте и добавляет их в новый список ссылок, который возвращается.
     */
    private static LinkedList<String> getAllLinks(URLDepthPair myDepthPair) {

// Инициализируйте связанный список строк, в которых будут храниться ссылки
// которые мы находили.
        LinkedList<String> URLs = new LinkedList<String>();

// Инициализировать сокет.
        Socket sock;

// Попробую создать новый сокет с URL-адресом, переданным методу с двойной грубиной, URL-адресом и портом 80.
        try {
            sock = new Socket(myDepthPair.getWebHost(), 80);
        }
// Catch UnknownHostException и возвращаю пустой лист.
        catch (UnknownHostException e) {
            System.err.println("UnknownHostException: " + e.getMessage());
            return URLs;
        }
// Catch IOException и возвращаю пустой лист.
        catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
            return URLs;
        }

// Пробую установить на сокет задержку
        try {
            sock.setSoTimeout(3000);
        }
// Catch SocketException и возвращаю пустой лист.
        catch (SocketException exc) {
            System.err.println("SocketException: " + exc.getMessage());
            return URLs;
        }

// Строка для представления docPath из URL-адреса, переданного как
// двойная грубина URL-адреса и строки для представления веб-Хоста.
        String docPath = myDepthPair.getDocPath();
        String webHost = myDepthPair.getWebHost();

// Инициализировать выходной поток.
        OutputStream outStream;

// Пробую получить выходной поток с сокета.
        try {
            outStream = sock.getOutputStream();
        }
// Обрабатываю ошибку и вывожу пустой лист.
        catch (IOException exce) {
            System.err.println("IOException: " + exce.getMessage());
            return URLs;
        }

// Инициализирую PrintWriter. True означает, что PrintWriter сработал после
//каждого вывода.
        PrintWriter myWriter = new PrintWriter(outStream, true);

// Отправляю запрос на сервер.
        myWriter.println("GET " + docPath + " HTTP/1.1");
        myWriter.println("Host: " + webHost);
        myWriter.println("Connection: close");
        myWriter.println();

// Инициализирую поток ввода .
        InputStream inStream;

// Считываю входной поток с сокета.
        try {
            inStream = sock.getInputStream();
        }
// Обрабатываю ошибку и возвращаю пустой лист.
        catch (IOException excep){
            System.err.println("IOException: " + excep.getMessage());
            return URLs;
        }
// Создаю новый InputStreamReader и BufferedReader для чтения строк
// с сервера.
        InputStreamReader inStreamReader = new InputStreamReader(inStream);
        BufferedReader BuffReader = new BufferedReader(inStreamReader);

// Пробую прочитать строку из bufferedReader.
        while (true) {
            String line;
            try {
                line = BuffReader.readLine();
            }
// Обрабатываю ошибку и возвращаю пустой лист.
            catch (IOException except) {
                System.err.println("IOException: " + except.getMessage());
                return URLs;
            }
// Заканчиваю чтение
            if (line == null)
                break;


// Переменные для представления индексов, где начинаются и заканчиваются ссылки, а
//также текущего индекса.
            int beginIndex = 0;
            int endIndex = 0;
            int index = 0;

            while (true) {

/**
 * Константа для строки, указывающей на ссылку.
 */
                String URL_INDICATOR = "a href=\"";

/**
 * Константа для строки, указывающей конец веб-хоста и
 * начало docpath.
 */
                String END_URL = "\"";


// Ищет начало строки
                index = line.indexOf(URL_INDICATOR, index);
                if (index == -1) // Если нет копий в строке
                    break;

// Продвигаю текущий индекс и установливаю значение beginIndex.
                index += URL_INDICATOR.length();
                beginIndex = index;

// Поиск конца текущей строки для индекса.
                endIndex = line.indexOf(END_URL, index);
                index = endIndex;

// установливаю ссылку на подстроку между начальным
//и конечным индексами. Добавляю в наш список URL-адресов.
                String newLink = line.substring(beginIndex, endIndex);
                URLs.add(newLink);
            }

        }
// возращаю список адресов.
        return URLs;
    }

}